package AgileExpress.Server.Helpers;

import AgileExpress.Server.Constants.UserTypes;

public class AccessLevelHelper {

    public static boolean hasHigherAccessLevel(UserTypes user, UserTypes reference) {
        return user.compareTo(reference) > 0;
    }

    public static boolean hasHigherOrEqualAccessLevel(UserTypes user, UserTypes reference) {
        return user.compareTo(reference) >= 0;
    }

    public static boolean is(UserTypes user, UserTypes reference) {
        return user.compareTo(reference) == 0;
    }
}
