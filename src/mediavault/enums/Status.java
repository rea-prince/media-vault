package mediavault.enums;
public enum Status
{
    PLANNED("Planned"),
    IN_PROGRESS("In-Progress"),
    COMPLETED("Completed");

    private final String name;

    /**
     * Constructs a Status ENUM with the given name.
     * @param name Name of the enum to be created
     */
    Status(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the enum's instance.
     * @return String ID of the enum's instance
     */
    public String getName() {
        return this.name;
    }
}
