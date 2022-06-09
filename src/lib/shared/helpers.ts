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

function delay(ms: number) {
  return new Promise(res => setTimeout(res, ms))
}

function randomString(length: number) {
  let result           = '';
  let characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let charactersLength = characters.length;
  for ( let i = 0; i < length; i++ ) {
    result += characters.charAt(Math.floor(Math.random() *
        charactersLength));
  }
  return result;
}

function formatDate(dateString: string) {
  const options: Intl.DateTimeFormatOptions = { year: "numeric", month: "long", day: "numeric" }
  return new Date(dateString).toLocaleDateString(undefined, options)
}

export {msToTime, delay, randomString, formatDate}