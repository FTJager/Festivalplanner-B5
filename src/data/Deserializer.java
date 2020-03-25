package data;

import java.io.*;
import java.util.List;


public class Deserializer implements Serializable{
    Serializer serializer = new Serializer();

    public Deserializer() {
    }

    /**
     * The Read method reads the objects in the dataStore.ser file and places them in an ArrayList that other classes can copy
     * @return List contains an arrayList with all the created objects
     */
    public List ReadArtist(){
        List<Show> shows = null;

        try{
            FileInputStream fileIn = new FileInputStream("Resources/dataStore.ser");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            shows = (List)objIn.readObject();

            objIn.close();
            fileIn.close();
        }catch (IOException i){
            i.printStackTrace();
            serializer.Clear();
            System.out.println("Please restart the program");
            return null;
        }catch (ClassNotFoundException c){
            System.out.println("Show class not found");
            c.printStackTrace();
            return null;
        }

        return shows;
    }

    public List ReadStages(){
        List<Show> stages = null;

        try{
            FileInputStream fileIn = new FileInputStream("Resources/stageStore.ser");
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            stages = (List)objIn.readObject();

            objIn.close();
            fileIn.close();
        }catch (IOException i){
            i.printStackTrace();
            serializer.Clear();
            System.out.println("Please restart the program");
            return null;
        }catch (ClassNotFoundException c){
            System.out.println("Show class not found");
            c.printStackTrace();
            return null;
        }

        return stages;
    }
}
