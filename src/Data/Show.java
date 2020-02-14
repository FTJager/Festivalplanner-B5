package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Show implements Serializable {
    private String show;
    private HashMap<String, Artist> artistH;
    private int startTime;
    private int endTime;
    private int popularity;
    private int stage;


    public Show() {
    }

//    public Show(String show, int startTime, int endTime, int popularity, int stage) {
//        this.show = show;
//        this.artistH = new HashMap<>();
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.popularity = popularity;
//        this.stage = stage;
//    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public HashMap<String, Artist> getArtistH() {
        return artistH;
    }

    public void setArtistH(HashMap<String, Artist> artistH) {
        this.artistH = artistH;
    }

    public void addArtists(Artist artist){
        String name = artist.getName().toLowerCase();
        if(!this.artistH.containsKey(name) && name.equalsIgnoreCase(artist.getName())){
            this.artistH.put(name, artist);
        }
    }
}
