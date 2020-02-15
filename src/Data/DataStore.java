package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Every capital S at the end of an attribute means that it is a static variable
//These variables store data from the input in the gui
public class DataStore {
    private static Artist artistS;
    private static Show showS;
    private static ArrayList<Artist> artistsS;
    private static List<Show> showsAS;
    private static HashMap<String, Artist> showsHS;

    public DataStore() {
        artistsS = new ArrayList<>();
        showsHS = new HashMap<>();
        showsAS = new ArrayList<>();
    }

    //Sets an artist object
    public static void setArtist(Artist artist) throws IllegalArgumentException{
        artistS = artist;
    }

    //Gets artist object and checks if it isn't null
    public static Artist getArtist(){
        if(artistS == null){
            throw new NullPointerException("Artist does not exist!");
        } else {
            return artistS;
        }
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

    //This method sets an ArrayList of Artists
    public static void setArtists(ArrayList<Artist> artists){
        artistsS = artists;
    }

    //This method checks the ArrayList of Artists and checks if the object in the list aren't null
    public ArrayList<Artist> getArtists(){
        if(artistsS == null){
            throw new NullPointerException("Ya doofus, it has not been initialized yet!");
        } else {
            return artistsS;
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

}



