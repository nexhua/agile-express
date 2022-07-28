package AgileExpress.Server.Constants;

public class ErrorMessages {

    public static final String Title = "Error";

    public static String MissingPropertyError(String missingPropertyName) {
        return String.format("Missing property. Send the request with the %s property included", missingPropertyName);
    }

    public static String NoPropertyToUpdateError(int foundPropertyCount) {
        return String.format("Found %s properties to update. Please send the request with the correct properties", foundPropertyCount);
    }

    public static String PropertyAlreadyExistsWithValue(String fieldName) {
        return String.format("The %s already exists with given value. Please send the request again with different values", fieldName);
    }

    public static String DocumentNotFound(String seearchedObjectName) {
        return String.format("Could not find the \"%s\" object in the database.", seearchedObjectName);
    }
}
