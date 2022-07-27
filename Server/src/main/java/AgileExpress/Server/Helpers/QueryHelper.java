package AgileExpress.Server.Helpers;

import org.bson.types.ObjectId;

public class QueryHelper {

    public static String asInnerDocumentProperty(String parent, String property) {
        return String.format("%s.%s", parent, property);
    }

    public static String asInnerDocumentArrayProperty(String parent, String property) {
        return String.format("%s.$.%s", parent, property);
    }

    public static String asInnerDocumentArrayProperty(String parent, String property, String field) {
        return String.format("%s.$[%s].%s", parent, field, property);
    }

    public static ObjectId createID(String id) {
        return new ObjectId(id);
    }

    public static ObjectId createdID() {
        return new ObjectId();
    }
}
