export function msToTime(s: number, milli = false) {

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


export function formateDate(dateString?: string) {

  if (!dateString) {
    return "unknown"
  }
  const date = new Date(dateString);
  const month = date.toLocaleString('en-US', { month: 'short' });
  const day = date.getDate();
  const year = date.getFullYear();

  return `${month} ${day} ${year}`
}