import { get, writable } from "svelte/store";



export declare interface IndicatorData {
	type: Indicator.Type;
	message: string;	
  value?: number | null;
	max?: number | null;
}

export const indicators = writable<IndicatorData[]>([]);

  
export namespace Indicator {
  export const enum Type {
    Announcement = "Announcement",
    Error = "Error",
    Loading = "Loading",  
    Success = "Success",
    Warning = "Warning",
  }
  export function addIndicator(type: Type, msg: string) {
    const indicator = {type: type, message: msg};
    indicators.update((current) => [...current, indicator]);
    setTimeout(() => removeIndicator(indicator), getTimeout(indicator));	
  }

  export function addAnnouncement(msg: string) {
    addIndicator(Type.Announcement, msg)
  }
  export function addError(msg: string) {
    addIndicator(Type.Error, msg)
  }
  export function addSuccess(msg: string) {
    addIndicator(Type.Success, msg)
  }
  export function addWarning(msg: string) {
    addIndicator(Type.Warning, msg)
  }

  export function addLoading(message: string, value: number | null = null, max: number | null = null) {
    const indicator = {type: Type.Loading, message, value, max};
    indicators.update((current) => [...current, indicator]);

    return {
      update: (nextValue: number, nextMax: number) => {      
        get(indicators).forEach((i) => {
          if (i.message === indicator.message) {
            i.max = nextMax;
            i.value = nextValue
          }
        });
      },
      stop: () => removeIndicator(indicator)
    }
  }  
  
  function removeIndicator(indicator: IndicatorData) {
    const updateded = get(indicators).filter((i) => i.message !== indicator.message);
    indicators.set(updateded);
  }
  
  function getTimeout(indicator: IndicatorData) {
    switch (indicator.type) {
      case Type.Announcement:
        return 5000;
      case Type.Error:
        return 8000;
      case Type.Loading:
        return 5000;
      case Type.Success:
        return 3000;	
      case Type.Warning:
        return 8000;
      default:
        return 0;
    }	
  }
}

