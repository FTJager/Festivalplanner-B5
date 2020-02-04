package Data;

import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.time.LocalDateTime;

public class Show {
    private String show;
    private int startTime;
    private int endTime;
    private int popularity;
    private int stage;

    public Show(String show, int startTime, int endTime, int popularity, int stage) {
        this.show = show;
        this.startTime = startTime;
        this.endTime = endTime;
        this.popularity = popularity;
        this.stage = stage;
    }

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
}
