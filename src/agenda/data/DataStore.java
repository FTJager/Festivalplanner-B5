package agenda.data;

import agenda.data.Stage;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Every capital S at the end of an attribute means that it is a static variable
//These variables store data from the input in the gui
public class DataStore {
    private static boolean stateS;
//    private static Artist artistS;
    private static Show showS;
    private static List<Artist> artistsS;
    private static List<Show> showsAS;
    private static List<Stage> stages;
    private Serializer serializer = new Serializer();
    private Deserializer deserializer = new Deserializer();

    public static boolean isStateS() {
        return stateS;
    }

    public static void setStateS(boolean stateS) {
        DataStore.stateS = stateS;
    }

    public DataStore() {
        stateS = true;
        artistsS = new ArrayList<>();
        showsAS = new ArrayList<>();
        stages = new ArrayList<>();
    }

    //Sets a show object
    public static void setShow(Show show) throws IllegalArgumentException{
        showS = show;
    }

    //Gets a show object and checks if it isn't null
    public static Show getShow(){
        if(showS == null){
            throw new IllegalArgumentException("Show does not exist!");
        } else {
            return showS;
        }
    }

    //This method gets a list of the object Show and checks if it the list isn't null
    public static List<Show> getShowsA(){
        if(showsAS == null){
            throw new NullPointerException("The list has not been initialized yet!");
        }
        return new ArrayList<Show>(showsAS);
    }

    public static void setArtistsS(List<Artist> artists){
        artistsS = artists;
    }

    public static List<Artist> getArtistsS(){
        if(artistsS == null){
            throw new NullPointerException("The list of artists has not been initialized yet!");
        }
        return artistsS;
    }

    //This method sets the List of shows
    public static void setShowsA(List<Show> showsA){
        showsAS = showsA;
    }

    public static void setShowA(Show show) {
        showsAS.add(show);
    }

    //This method gets a lists of stages
    public static List<Stage> getStages() {
        if(stages == null) {
            stages = new ArrayList<>();
        }
        return stages;
    }

    public static void setNewStages(Stage stage){
        stages.add(stage);
    }

    public static void setStages(List<Stage> stage) {
        stages = stage;
    }

}



