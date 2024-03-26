package de.wittenbude.exportify.models;

import com.neovisionaries.i18n.CountryCode;
import de.wittenbude.exportify.models.embeds.ExplicitContent;
import de.wittenbude.exportify.models.embeds.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Accessors(chain = true)
@Entity
public class PrivateUser {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL, optional = false)
    @JoinColumn(nullable = false, unique = true)
    private PublicUser publicUser;

    private CountryCode country;

    private String email;

    @JdbcTypeCode(SqlTypes.JSON)
    private ExplicitContent explicitContent;

    private ProductType product;

}

