package AgileExpress.Server.Constants;

 public enum UserTypes {
    TEAM_MEMBER("teammember"),
    TEAM_LEAD("teamlead"),
    PROJECT_MANAGER("projectmanager"),
    ADMIN("admin");

    public final String label;

    private UserTypes(String label) {
        this.label = label;
    }
}
