package Data;

import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.time.LocalDateTime;

public class Show {
    private String show;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int popularity;
    private int stage;

    public Show(String show, LocalDateTime startTime, LocalDateTime endTime, int popularity, int stage) {
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

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
