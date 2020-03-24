package data;

import java.io.Serializable;

public class Artist implements Serializable {
    private String name;
    private String genre;

    public Artist(String name, String genre){
        this.name = name;
        this.genre = genre;

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
