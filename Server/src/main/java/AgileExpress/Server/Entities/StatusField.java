package AgileExpress.Server.Entities;

public class StatusField {

    private String statusName;

    private boolean isDeletable = false;

    public StatusField(String statusName, boolean isDeletable) {
        this.statusName = statusName;
        this.isDeletable = isDeletable;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        isDeletable = deletable;
    }
}
