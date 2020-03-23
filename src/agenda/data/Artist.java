package agenda.data;

import java.io.Serializable;

/**
 * This class is used to create Artist objects. They have a name and genre, which can be retrieved or overwritten using
 * the getters and setters
 */
public class Artist implements Serializable {
    private String name;
    private String genre;

    public Artist(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getGenre(){
        return this.genre;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

}
