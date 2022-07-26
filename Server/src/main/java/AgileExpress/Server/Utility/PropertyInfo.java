package AgileExpress.Server.Utility;

import java.util.Collection;

public class PropertyInfo<T> {

    private String propertyName;

    private T propertyValue;

    public PropertyInfo(String propertyName, T propertyValue) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;

    }

    public boolean isString() {
        return this.getPropertyValue() instanceof String;
    }

    public boolean isNumeric() {
        return this.getPropertyValue() instanceof Number;
    }

    public boolean isCollection() {
        return this.getPropertyValue() instanceof Collection<?>;
    }

    //region Getter and Setters

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public T getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(T propertyValue) {
        this.propertyValue = propertyValue;
    }

    //endregion
}
