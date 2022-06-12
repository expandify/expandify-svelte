import _ from "lodash";
import type {Image} from "../../types/Image";

function msToTime(s: number, milli = false) {

  // Pad to 2 or 3 digits, default is 2
  function pad(n: number, z = 2) {
    return ('00' + n).slice(-z);
  }

  let ms = s % 1000;
  s = (s - ms) / 1000;
  let secs = s % 60;
  s = (s - secs) / 60;
  let mins = s % 60;
  s = (s - mins) / 60;
  let hrs = s;
  let res = ""
  if (hrs !== 0) {
    res += pad(hrs) + ':'
  }
  res += pad(mins) + ':' + pad(secs)

  if (milli) {
    res += '.' + pad(ms, 3);
  }
  return res;
}

function formatDate(dateString: string) {
  const options: Intl.DateTimeFormatOptions = { year: "numeric", month: "short", day: "numeric", hour12: false }

  return new Date(dateString).toLocaleTimeString(undefined, options)
}

function imageSelector(images: Image[] | null | undefined, fallbackImage = "/images/default.png"): string {
  if (!images || images.length === 0) {
    return fallbackImage
  }
  return _.maxBy(images, ["height", "width"])?.url || fallbackImage
}

function joinOn(list: any[], key: string) {
  return list.map(value => value[key]).join(", ")
}

export {msToTime, formatDate, imageSelector, joinOn}