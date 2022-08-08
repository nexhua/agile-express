function getStoryPoint(str) {
  const index = str.indexOf("/point");
  if (index !== -1) {
    const numberStr = str.substring(index + "/point".length).trim();
    const point = parseInt(numberStr, 10);

    if (Number.isInteger(point)) {
      return point;
    } else {
      return -2;
    }
  }
  return -1;
}

function calculateSpentPoints(comments) {
  let point = 0;
  const pointComments = comments.filter((c) => c.action === "POINT");

  for (var i = 0; i < pointComments.length; i++) {
    const comment = pointComments[i];

    const commentPoint = getStoryPoint(comment.comment);

    if (commentPoint > 0) {
      point += commentPoint;
    }
  }
  return point;
}

export { getStoryPoint, calculateSpentPoints };
