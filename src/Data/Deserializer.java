package data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Deserializer implements Serializable{
    Serializer serializer = new Serializer();

    public Deserializer() {
    }

    public List Read(){
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
}
