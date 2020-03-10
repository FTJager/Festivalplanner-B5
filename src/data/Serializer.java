package data;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Serializer implements Serializable{

    public Serializer() {
    }

    /**
     * The Write method receives a list of objects and places the entire list in the dataStore.ser file
     * @param shows List containing a set of created objects
     */
    public void Write(List<data.Show> shows){

        try {
            FileOutputStream fileOut = new FileOutputStream("Resources/dataStore.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(shows);

            objOut.close();
            fileOut.close();

        }catch (IOException i){
            i.printStackTrace();
        }

    }
    public void WriteStage(String stages){
        try {
            FileOutputStream fileOut = new FileOutputStream("Resources/dataStore.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(stages);

            objOut.close();
            fileOut.close();

        }catch (IOException i){
            i.printStackTrace();
        }

    }

    /**
     * the Clear method overwrites the existing List in dataStore.ser with an empty List, effectively removing all saved data while also functioning as a way to make sure the file is usable.
     */
    public void Clear(){
        try {
            FileOutputStream fileOut = new FileOutputStream("Resources/dataStore.ser");
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(new ArrayList<Show>());

            objOut.close();
            fileOut.close();

        }catch (IOException i){
            i.printStackTrace();
        }
    }
}
