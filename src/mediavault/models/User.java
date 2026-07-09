package mediavault.models;

import java.io.Serializable;

public class User implements Serializable
{
    MediaVault vault = new MediaVault();
    private int ID;
    private String username;
    private String passwordHash;

    public User (String name, MediaVault vault)
    {
        username = name;
        this.vault = vault;
    }

    public void setUsername (String name)
    {
        username = name;
    }

    public int getUserID ()
    {
        return ID;
    }

    public String getPasswordHash ()
    {
        return passwordHash;
    }

    public String getUsername ()
    {
        return username;
    }

    public MediaVault getVault ()
    {
        return vault;
    }
}
