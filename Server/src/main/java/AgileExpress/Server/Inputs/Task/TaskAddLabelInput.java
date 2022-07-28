package AgileExpress.Server.Inputs.Task;

import AgileExpress.Server.Entities.Label;
import AgileExpress.Server.Helpers.ReflectionHelper;
import AgileExpress.Server.Interfaces.IToInputDocument;
import AgileExpress.Server.Interfaces.IToInputObject;
import org.bson.Document;

public class TaskAddLabelInput extends BaseProjectAndTaskInput implements IToInputObject<Label>, IToInputDocument {

    private String labelName;

    private String labelColorCode;

    private String labelDescription;

    public TaskAddLabelInput() {

    }

    public TaskAddLabelInput(String projectID, String taskID, String labelName, String labelColorCode, String labelDescription) {
        super(projectID, taskID);
        this.labelName = labelName;
        this.labelColorCode = labelColorCode;
        this.labelDescription = labelDescription;
    }

    @Override
    public Label toObject() {
        return new Label(this.getLabelName(), this.labelColorCode, this.getLabelDescription());
    }

    @Override
    public Document toDocument() {
        return ReflectionHelper.toDocument(this.toObject(), null);
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
