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
