package mediavault.enums;
public enum MediaType
{
    VIDEOGAME("Video Game"),
    ANIME("Anime"),
    NOVEL("Novel");

    private final String name;


    /**
     * Constructs a MediaType ENUM with the given name.
     * @param name Name of the enum to be created
     */
    MediaType(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the enum's instance.
     * @return String ID of the enum's instance
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the equivalent enum based on the string.
     * @return Status Equivalent of enum's String ID
     */
    public static MediaType fromString(String s) {
    	for (MediaType stat : values()) {
     		if  (stat.getName().equals(s)) {
       			return stat;
       		}
     	}
      	return MediaType.NOVEL;
    }
}
