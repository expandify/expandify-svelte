import {collectDefaultMetrics, Counter, register} from "prom-client"

// Clean the register so a hot reload, will not try to register a metric twice.
register.clear()

collectDefaultMetrics();

const requests = new Counter({
  name: "exportify_requests_total",
  help: "Requests made to Exportify",
  labelNames: ["requests", "method", "path"]
});


export async function handle_metrics({ event, resolve }) {
  requests.inc()
  requests.inc({method: event.request.method, path: event.url.pathname})
  return await resolve(event);
}