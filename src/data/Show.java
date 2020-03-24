package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Show implements Serializable {
    private String show;
    private ArrayList<Artist> artistA;
    private int startTime;
    private int endTime;
    private int popularity;
    private data.Stage stage;
    private int stageX;


    public Show() {}

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public int getStartTime() {
        //Made final, so events handel them better
        final int finalStartTime = startTime;
        return finalStartTime;
    }

    public void setBeginTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        final int finalEndTime = endTime;
        return finalEndTime;
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

    public data.Stage getStage() {
        return stage;
    }

    public void setStage(data.Stage stage) { this.stage = stage; }

    public ArrayList<Artist> getArtistA() {
        return artistA;
    }

    public void setArtistA(ArrayList<Artist> artistA) {
        if(artistA == null) {
            throw new NullPointerException("Artist Array doesn't exit yet!");
        }
        this.artistA = artistA;
    }

    public void addArtists(Artist artist){
        this.artistA.add(artist);
    }

    public void setStageX(int stageX){
        this.stageX = stageX;
    }

    public int getStageX() {
        final int finalStageX = stageX;
        return finalStageX;
    }
}
