package AgileExpress.Server.Constants;

public class ErrorMessages {


    public static String MissingPropertyError(String missingPropertyName) {
        return String.format("Missing property. Send the request with the %s property included", missingPropertyName);
    }
}
