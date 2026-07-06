package mediavault.enums;
public enum Status
{
    PLANNED("Planned"),
    IN_PROGRESS("In-Progress"),
    COMPLETED("Completed");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
