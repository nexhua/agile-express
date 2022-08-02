package AgileExpress.Server.Constants;

import org.bson.Document;

public class ErrorMessages {

    public static final String Title = "Error";

    public static String MissingPropertyError(String missingPropertyName) {
        return String.format("Missing property. Send the request with the %s property included", missingPropertyName);
    }

    public static String NoPropertyToUpdateError(int foundPropertyCount) {
        return String.format("Found %s properties to update. Please send the request with the correct properties", foundPropertyCount);
    }

    public static String PropertyAlreadyExistsWithValueError(String fieldName) {
        return String.format("The %s already exists with given value. Please send the request again with different values", fieldName);
    }

    public static String DocumentNotFoundError(String seearchedObjectName) {
        return String.format("Could not find the \"%s\" object in the database.", seearchedObjectName);
    }

    public static String UnauthorizedAccessError(UserTypes requiredAccessLevel) {
        return String.format("Unauthorized operation. This operation requires your access level to be higher or equal to %s", requiredAccessLevel.name());
    }

    public static String UserNotFoundError() {
        return "User not found";
    }

    public static Document with(String errorMes) {
        return new Document(ErrorMessages.Title, errorMes);
    }
}
