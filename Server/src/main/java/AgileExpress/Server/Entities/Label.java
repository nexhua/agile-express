package AgileExpress.Server.Entities;

public class Label {

    private String labelName;

    private String labelColorCode;

    private String labelDescription;

    public Label() {}

    public Label(String labelName, String labelColorCode, String labelDescription) {
        this.labelName = labelName;
        this.labelColorCode = labelColorCode;
        this.labelDescription = labelDescription;
    }

    //region Getter and Setters

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public String getLabelColorCode() {
        return labelColorCode;
    }

    public void setLabelColorCode(String labelColorCode) {
        this.labelColorCode = labelColorCode;
    }

    public String getLabelDescription() {
        return labelDescription;
    }

    public void setLabelDescription(String labelDescription) {
        this.labelDescription = labelDescription;
    }


    //endregion
}
