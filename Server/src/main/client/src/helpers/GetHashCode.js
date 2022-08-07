function hashCodeStr(string) {
  var hash = 0;
  for (var i = 0; i < string.length; i++) {
    var code = string.charCodeAt(i);
    hash = (hash << 5) - hash + code;
    hash = hash & hash; // Convert to 32bit integer
  }
  return hash;
}

function hashCodeArr(array) {
  var hash = 0;
  for (var i = 0; i < array.length; i++) {
    var code = hashCodeStr(array[i]);
    hash += code;
  }
  return Math.abs(hash);
}

export { hashCodeStr, hashCodeArr };
