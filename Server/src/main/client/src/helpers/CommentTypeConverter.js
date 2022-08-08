export default function commentTypeToOrdinal(commentTypeString) {
  let ordinal = 0;
  if (commentTypeString === "COMMENT") {
    ordinal = 0;
  } else if (commentTypeString === "POINT") {
    ordinal = 1;
  } else {
    ordinal = -1;
  }

  return ordinal;
}
