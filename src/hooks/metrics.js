import {collectDefaultMetrics, Counter, register} from "prom-client";

register.clear()
collectDefaultMetrics();

const requests = new Counter({
  name: "exportify_requests_total",
  help: "Requests made to Exportify",
  labelNames: ["method", "path"]
});

async function handle(event) {
  requests.inc()
  requests.inc({method: event.request.method, path: event.url.pathname})
}

async function getSession(event) {
  return {}
}

export {handle, getSession}