package agenda.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;


public class Deserializer implements Serializable {
    Serializer serializer = new Serializer();

    public Deserializer() {
    }

    /**
     * The Read method reads the objects in the dataStore.ser file and places them in an ArrayList that other classes can copy
     *
     * @return List contains an arrayList with all the created objects
     */
    public List Read(int type) {
        List<Show> shows = null;
        List<Artist> artists = null;
        List<Stage> stages = null;

        try {
            FileInputStream fileIn = null;
            ObjectInputStream objIn = null;

            //Return all the Shows saved in the file
            if (type == Serializer.SHOWS){
                fileIn = new FileInputStream("Resources/dataStore.ser");
                objIn = new ObjectInputStream(fileIn);

                shows = (List<Show>) objIn.readObject();
            //Return all Artists saved in the file
            }else if (type == Serializer.ARTISTS){
                fileIn = new FileInputStream("Resources/artists.ser");
                objIn = new ObjectInputStream(fileIn);

                artists = (List<Artist>) objIn.readObject();
            //Return all Stages saved in the file
            }else if (type ==Serializer.STAGES){
                fileIn = new FileInputStream("Resources/stageStore.ser");
                objIn = new ObjectInputStream(fileIn);

                stages = (List<Stage>) objIn.readObject();
            }

            objIn.close();
            fileIn.close();
        } catch (IOException i) {   //This error commonly shows up when there is a problem with the file.
            i.printStackTrace();
            serializer.Clear();     //This line resolves most errors, restarting after this error gets caught should fix it.
            System.out.println("This error is completely normal, please restart the program to fix it");
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }

        if (type == Serializer.SHOWS){
            return shows;
        }else if (type == Serializer.ARTISTS){
            return artists;
        }else if (type == Serializer.STAGES){
            return stages;
        }

        return null;
    }

//    public List ReadStages(){
//        List<Show> stages = null;
//
//        try{
//            FileInputStream fileIn = new FileInputStream("Resources/stageStore.ser");
//            ObjectInputStream objIn = new ObjectInputStream(fileIn);
//            stages = (List)objIn.readObject();
//
//            objIn.close();
//            fileIn.close();
//        }catch (IOException i){
//            i.printStackTrace();
//            serializer.Clear();
//            System.out.println("Please restart the program");
//            return null;
//        }catch (ClassNotFoundException c){
//            System.out.println("Show class not found");
//            c.printStackTrace();
//            return null;
//        }
//
//        return stages;
//    }
}
