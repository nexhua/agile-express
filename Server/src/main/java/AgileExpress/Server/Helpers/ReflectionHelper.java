package AgileExpress.Server.Helpers;

import AgileExpress.Server.Constants.MongoConstants;
import AgileExpress.Server.Utility.IncludeEmpty;
import AgileExpress.Server.Utility.PropertyInfo;
import org.bson.Document;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Queue;

public class ReflectionHelper {

    public static ArrayList<PropertyInfo<?>> getFieldsWithValues(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();

        ArrayList<PropertyInfo<?>> propertyInfoList = new ArrayList<>();

        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldClass = field.getType().getSimpleName();
            switch (fieldClass) {
                case "String" -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if (value != null && !value.toString().trim().isEmpty()) {
                            propertyInfoList.add(new PropertyInfo<>(fieldName, value.toString()));
                        }
                    } catch (Exception e) {
                    }
                }
                case "Number", "int" -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if ((Integer) value > 0) {
                            propertyInfoList.add(new PropertyInfo<>(fieldName, ((Integer) value).intValue()));
                        }
                    } catch (Exception e) {
                    }
                }
                case "List" -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if (CollectionUtils.isEmpty(((Collection<?>) field.get(object)))) {
                            if (hasAnnotation(field, IncludeEmpty.class.getSimpleName())) {
                                propertyInfoList.add(new PropertyInfo<>(fieldName, (Collection<?>) value));
                            }
                        } else {
                            propertyInfoList.add(new PropertyInfo<>(fieldName, (Collection<?>) value));
                        }
                    } catch (Exception e) {
                    }
                }
                case "Date" -> {
                    field.setAccessible(true);
                    try {
                        Object value = field.get(object);
                        if (value != null) {
                            propertyInfoList.add(new PropertyInfo<>(fieldName, (Date) value));
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        return propertyInfoList;
    }

    public static Document toDocument(Object object, Queue<String> ids) {
        Field[] fields = object.getClass().getDeclaredFields();

        Document document = new Document();

        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = null;
            field.setAccessible(true);
            try {
                fieldValue = field.get(object);
            } catch (IllegalAccessException e) {
            }

            if (checkIfFieldAnnotationHasId(field)) {
                if (!getIDGenerationPolicy(field)) {
                    document.put(fieldName, QueryHelper.createID(ids.remove()));
                } else {
                    document.put(MongoConstants.Id, QueryHelper.createdID());
                }
            } else {
                document.put(fieldName, fieldValue);
            }
        }
        document.put(MongoConstants.Class, object.getClass().getName());
        return document;
    }

    public static boolean checkIfFieldAnnotationHasId(Field field) {
        boolean hasIdAsAnnotation = false;
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("Id")) {
                return true;
            }
        }
        return hasIdAsAnnotation;
    }

    public static boolean getIDGenerationPolicy(Field field) {
        boolean shouldGenerateNewObjectID = true;
        for (Annotation annotation : field.getDeclaredAnnotations()) {
            if (annotation.annotationType().getSimpleName().equals("IDGenerationPolicy")) {
                return false;
            }
        }
        return shouldGenerateNewObjectID;
    }

    public static boolean hasAnnotation(Field field, String annotationSimpleName) {
        for (Annotation a : field.getDeclaredAnnotations()) {
            if (a.annotationType().getSimpleName().equals(annotationSimpleName)) {
                return true;
            }
        }
        return false;
    }
}
