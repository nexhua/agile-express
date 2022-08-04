export default function userTypeStringToOrdinal(userTypeString) {
  let ordinal = 0;
  if (userTypeString === "TEAM_MEMBER") {
    ordinal = 0;
  } else if (userTypeString === "TEAM_LEAD") {
    ordinal = 1;
  } else if (userTypeString === "PROJECT_MANAGER") {
    ordinal = 2;
  } else if (userTypeString === "ADMIN") {
    ordinal = 3;
  } else {
    ordinal = -1;
  }

  return ordinal;
}
