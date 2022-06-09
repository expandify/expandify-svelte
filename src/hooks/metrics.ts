import {collectDefaultMetrics, Counter, register} from "prom-client";
import type {RequestEvent} from "@sveltejs/kit";

register.clear()
collectDefaultMetrics();

const requests = new Counter({
  name: "exportify_requests_total",
  help: "Requests made to Exportify",
  labelNames: ["method", "path"]
});

async function handle(event: RequestEvent) {
  requests.inc()
  requests.inc({method: event.request.method, path: event.url.pathname})
}

async function getSession(event: RequestEvent) {
  return {}
}

export {handle, getSession}