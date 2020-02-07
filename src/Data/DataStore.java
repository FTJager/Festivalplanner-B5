package data;

import data.Artist;
import data.Show;
import data.Stage;

import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStore {
    private Artist artist;
    private Show show;
    private Stage stage;
    private ArrayList<Artist> artists;
    private ArrayList<Show> showsA;
    private HashMap<String, Artist> showsH;

    public DataStore() {
        this.artists = new ArrayList<>();
        this.showsH = new HashMap<>();
        this.showsA = new ArrayList<>();
    }


    public void setArtist(Artist artist) throws IllegalArgumentException{
        this.artist = artist;
    }

    public Artist getArtist(){
        if(this.artist == null){
            throw new NullPointerException("Artist does not exist!");
        } else {
            return this.artist;
        }
    }

    public void setShow(Show show) throws IllegalArgumentException{
        this.show = show;
    }

    public Show getShow(){
        if(this.show == null){
            throw new IllegalArgumentException("Show does not exist!");
        } else {
            return this.show;
        }
    }

    public void setStage(Stage stage) throws IllegalArgumentException{
        this.stage = stage;
    }

    public Stage getStage(){
        if(this.stage == null){
            throw new IllegalArgumentException("Stage does not exist!");
        } else {
            return this.stage;
        }
    }

    public void setArtists(ArrayList<Artist> artists){
        this.artists = artists;
    }

    public ArrayList<Artist> getArtists(){
        if(this.artists == null){
            throw new NullPointerException("Ya doofus, it has not been initialized yet!");
        } else {
            return this.artists;
        }
    }

    public List<Show> getShowsA(List<Show> showsA){
        if(this.showsA == null){
            throw new NullPointerException("The list has not been initialized yet!");
        }
        return this.showsA;
    }

    public void setShowsA(ArrayList<Show> showsA){
        this.showsA = showsA;
    }

    public void setShowsH(HashMap<String, Artist> shows){
        this.showsH = shows;
    }

    public HashMap<String, Artist> getShows(){
        if(this.showsH == null){
            throw new NullPointerException("Ya doofus, ya did again!");
        } else {
            return this.showsH;
        }
    }
}



