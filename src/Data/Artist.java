package Data;

import javafx.scene.image.Image;

public class Artist {

    private String name;
    private String genre;
    private Image image;

    public Artist(String name, String genre, Image image){
        this.name = name;
        this.genre = genre;
        this.image = image;
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

    public Image getImage(){
        return this.image;
    }

    public void setImage(Image image){
        this.image = image;
    }
}
