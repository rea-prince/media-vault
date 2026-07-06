package mediavault.enums;
public enum MediaType
{
    VIDEOGAME("Video Game"),
    ANIME("Anime"),
    NOVEL("Novel");

    private final String name;

    MediaType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
