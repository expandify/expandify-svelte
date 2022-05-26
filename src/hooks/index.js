import {sequence} from "@sveltejs/kit/hooks";
import {handle_auth} from "./auth.js"
import {handle_metrics} from "./metrics.js"

export const handle = sequence(handle_auth, handle_metrics)