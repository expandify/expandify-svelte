package dev.kenowi.exportify.api.controllers;

import dev.kenowi.exportify.authentication.services.AuthenticationService;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@Path("/authorize")
class AuthenticationController {

    public static final String CODE_REQUEST_PARAM = "code";
    private final AuthenticationService authenticationService;


    AuthenticationController(AuthenticationService authenticationService) {

        this.authenticationService = authenticationService;
    }

    @GET
    @PermitAll
    public Response authorize(@QueryParam("redirect_uri") @DefaultValue("/authorize/token") String redirectURI,
                              @QueryParam("show_dialog") @DefaultValue("false") boolean showDialog) {
        return Response
                .status(Response.Status.FOUND)
                .header(HttpHeaders.LOCATION, authenticationService.buildAuthorizeURL(redirectURI, showDialog).toString())
                .build();
    }

    @GET
    @PermitAll
    @Path("/callback")
    public Response callback(@QueryParam("code") String spotifyCode,
                             @QueryParam("state") String encryptedRedirectUri) {
        return Response
                .status(Response.Status.FOUND)
                .header(HttpHeaders.LOCATION, authenticationService
                        .buildCallbackURI(spotifyCode, encryptedRedirectUri, CODE_REQUEST_PARAM)
                        .toString())
                .build();
    }

    @GET
    @PermitAll
    @Path("/token")
    public String authenticateUser(@QueryParam(CODE_REQUEST_PARAM) String internalCode) {
        return authenticationService.authenticateUser(internalCode);
    }
}
