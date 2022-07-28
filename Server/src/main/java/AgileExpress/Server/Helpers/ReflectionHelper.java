package AgileExpress.Server.Helpers;

import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Utility.PropertyInfo;
import org.bson.Document;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class ReflectionHelper {

    public static ArrayList<PropertyInfo<?>> getFieldsWithValues(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        ArrayList<PropertyInfo<?>> propertyInfoList = new ArrayList<>();

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldClass = field.getType().getSimpleName();
            if (fieldClass.equals("String")) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value != null && !value.toString().trim().isEmpty()) {
                        propertyInfoList.add(new PropertyInfo<>(fieldName, value.toString()));
                    }
                } catch (Exception e) {
                }
            } else if (fieldClass.equals("Number") || fieldClass.equals("int")) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if ((Integer) value > 0) {
                        propertyInfoList.add(new PropertyInfo<>(fieldName, ((Integer) value).intValue()));
                    }
                } catch (Exception e) {
                }
            } else if (fieldClass.equals("List")) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (!CollectionUtils.isEmpty((Collection<?>) field.get(object))) {
                        propertyInfoList.add(new PropertyInfo<>(fieldName, (Collection<?>) value));
                    }
                } catch (Exception e) {
                }
            }
        }
        return propertyInfoList;
    }

    public static Document toDocument(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        Document document = new Document();

        for(Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = null;
            field.setAccessible(true);
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) { }

            if(checkIfFieldAnnotationHasId(field)) {
               document.put(MongoConstants.Id, QueryHelper.createdID());
            } else
            {
                document.put(fieldName, fieldValue);
            }
        }
        document.put(MongoConstants.Class, object.getClass().getName());
        return document;
    }

    public static boolean checkIfFieldAnnotationHasId(Field field) {
        boolean hasIdAsAnnotation = false;
        for(Annotation annotation : field.getDeclaredAnnotations()) {
            if(annotation.annotationType().getSimpleName().equals("Id")) {
                return true;
            }
        }
        return hasIdAsAnnotation;
    }
}