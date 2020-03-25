package agenda.data;

import agenda.data.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Every capital S at the end of an attribute means that it is a static variable
//These variables store data from the input in the gui
public class DataStore {
    private static boolean stateS;
    private static Artist artistS;
    private static Show showS;
    private static ArrayList<Artist> artistsS;
    private static List<Show> showsAS;
    private static HashMap<String, Artist> showsHS;
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
        showsHS = new HashMap<>();
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
        return showsAS;
    }

    //This method sets the List of shows
    public static void setShowsA(List<Show> showsA){
        showsAS = showsA;
    }

    public static void setShowA(Show show) {
        showsAS.add(show);
    }

    //This method sets the HashMap of artists in the object Show
    public static void setShowsH(HashMap<String, Artist> shows){
        showsHS = shows;
    }

    //This method checks if the HashMap is not null, and gets the HashMap if it isn't null
    public static HashMap<String, Artist> getShows(){
        if(showsHS == null){
            throw new NullPointerException("Ya doofus, ya did again!");
        } else {
            return showsHS;
        }
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



