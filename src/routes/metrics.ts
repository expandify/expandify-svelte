import {register} from "prom-client"

import type {RequestHandler} from './__types/metrics';

export const get: RequestHandler = async function (_) {

  const metrics = await register.metrics()

  return {
    status: 200,
    body: metrics
  }
}