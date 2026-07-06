package mediavault.enums;

public enum Genre
{
	INVALID(-1, "ERROR INVALID GENRE"),
    ACTION(1, "Action"),
    ADVENTURE(2, "Adventure"),
    COMEDY(3, "Comedy"),
    CRIME(4, "Crime"),
    DOCUMENTARY(5, "Documentary"),
    DRAMA(6, "Drama"),
    FANTASY(7, "Fantasy"),
    HISTORICAL_FICTION(8, "Historical Fiction"),
    HORROR(9, "Horror"),
    MUSIC(10, "Music"),
    MYSTERY(11, "Mystery"),
    PSYCHOLOGICAL(12, "Psychological"),
    ROMANCE(13, "Romance"),
    SCIENCE_FICTION(14, "Sci-Fi"),
    SPORTS(15, "Sports"),
    THRILLER(16, "Thriller");

    private final int id;
    private final String name;

    Genre(int id, String name) {
    	this.id = id;
        this.name = name;
    }

    public int getId() {
    	return this.id;
    }
    public String getName() {
        return this.name;
    }

    public static Genre fromId(int id) {
    	for (Genre g : values()) {
     		if (g.id == id) {
                return g;
            }
     	}
      return INVALID;
    }
}
