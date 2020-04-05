package agenda.data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Serializer implements Serializable{

    public static final int SHOWS = 0;
    public static final int ARTISTS = 1;
    public static final int STAGES = 2;

    public Serializer() {
    }

    /**
     * The Write method receives a list of objects and places the entire list in the dataStore.ser file
     * @param list List containing a set of created objects
     * @param type A string indicating what ArrayList you'd like to write to, shows or artists.
     */
    public void Write(List list, int type){
        try {
            FileOutputStream fileOut = null;
            ObjectOutputStream objOut = null;
            //If the type given is SHOWS it writes the data to the dataStore.ser file
            if (type == SHOWS){
                fileOut = new FileOutputStream("Resources/dataStore.ser");
                objOut = new ObjectOutputStream(fileOut);

                List<Show> shows = list;
                objOut.writeObject(shows);

                objOut.close();
                fileOut.close();

            //If the type given is ARTISTS it writes the data to the artists.ser file
            }else if (type == ARTISTS){
                fileOut = new FileOutputStream("Resources/artists.ser");
                objOut = new ObjectOutputStream(fileOut);

                List<Artist> artists = list;
                objOut.writeObject(artists);

            //If the given type is STAGES it writes the data to the stageStore.ser file
            }else if (type == STAGES){
                fileOut = new FileOutputStream("Resources/stageStore.ser");
                objOut = new ObjectOutputStream(fileOut);

                List<Stage> stages = list;
                objOut.writeObject(stages);
            }

            objOut.close();
            fileOut.close();



        }catch (IOException i){
            i.printStackTrace();
        }

    }

    /**
     * the Clear method overwrites the existing Lists in dataStore.ser and astists.ser with an empty List, effectively removing all saved data while also functioning as a way to make sure the file is usable.
     */
    public void Clear(){
        try {
            FileOutputStream fileOut = null;
            ObjectOutputStream objOut = null;

            fileOut = new FileOutputStream("Resources/dataStore.ser");
            objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(new ArrayList<Show>());


            fileOut = new FileOutputStream("Resources/artists.ser");
            objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(new ArrayList<Artist>());

            fileOut = new FileOutputStream("Resources/stageStore.ser");
            objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(new ArrayList<Stage>());

            objOut.close();
            fileOut.close();

        }catch (IOException i){
            i.printStackTrace();
        }
    }

    //TODO Voeg samen met de Write methode
    public void WriteStage(List<Stage> stages){
        try {
            FileOutputStream fileOut = new FileOutputStream("Resources/stageStore.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(stages);

            objOut.close();
            fileOut.close();

        }catch (IOException i){
            i.printStackTrace();
        }

    }
}
