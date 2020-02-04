package data;

import data.Artist;
import data.Show;
import data.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class DataStore {
    private Artist artist;
    private Show show;
    private Stage stage;
    private ArrayList<Artist> artists;
    private HashMap<String, Artist> shows;

    public DataStore() {
        this.artists = new ArrayList<>();
        this.shows = new HashMap<>();
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

    public void setShows(HashMap<String, Artist> shows){
        this.shows = shows;
    }

    public HashMap<String, Artist> getShows(){
        if(this.shows == null){
            throw new NullPointerException("Ya doofus, ya did again!");
        } else {
            return this.shows;
        }
    }
}



