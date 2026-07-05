package mediavault.enums;

public enum Genre
{
	INVALID(-1),
    ACTION(1),
    ADVENTURE(2),
    COMEDY(3),
    CRIME(4),
    DOCUMENTARY(5),
    DRAMA(6),
    FANTASY(7),
    HISTORICAL_FICTION(8),
    HORROR(9),
    MUSIC(10),
    MYSTERY(11),
    PSYCHOLOGICAL(12),
    ROMANCE(13),
    SCIENCE_FICTION(14),
    SPORTS(15),
    THRILLER(16);

    private final int id;

    Genre(int id) {
    	this.id = id;
    }

    public int getId() {
    	return this.id;
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
