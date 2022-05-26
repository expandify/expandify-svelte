import {register, collectDefaultMetrics } from "prom-client"


export async function get({locals, url}) {

  const metrics = await register.metrics()

  return {
    status: 200,
    body: metrics
  }
}