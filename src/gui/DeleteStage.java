/**
 * The DeleteStage is called when the "delete" button is pressed, and shows up on the GUI
 * as a pop-up that allows you to delete a single show or all of the existing shows.
 */

package gui;

import com.sun.xml.internal.bind.v2.TODO;
import data.DataStore;
import data.Deserializer;
import data.Serializer;
import data.Show;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.Serializable;

public class DeleteStage {
    public static final int DELETE_ALL = -1;
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    private int showIndex;
    private int index = 0;

    /**
     * DeleteStage is the popup window that appears when the Delete button on the GUI is pressed.
     */
    DeleteStage(){
        //Set up for the deleteStage with buttons, labels, text fields, etc.
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        if (!deserializer.Read(Serializer.SHOWS).isEmpty()){
            DataStore.setShowsA(deserializer.Read(Serializer.SHOWS));
        }
        if (!deserializer.Read(Serializer.ARTISTS).isEmpty()){
            DataStore.setArtists(deserializer.Read(Serializer.ARTISTS));
        }

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        Label artistLabel = new Label("Artist: ");
        TextField artistField = new TextField();
        Label popularityLabel = new Label("Popularity: ");
        Label popularityDisplay = new Label();
        Label stageLabel = new Label("Stage:");
        Label stageDisplay = new Label();
        Label beginTimeLabel = new Label("BeginTime: ");
        Label beginTimeDisplay = new Label();
        Label endTimeLabel = new Label("EndTime: ");
        Label endTimeDisplay = new Label();

        Button doneButton = new Button("Done");
        Button searchButton = new Button("Search");
        Button clearAllButton = new Button("Clear all");

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistField, popularityDisplay, stageDisplay, beginTimeDisplay, endTimeDisplay);
        fieldBox.setSpacing(31);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(doneButton, searchButton, clearAllButton);
        buttonBox.setSpacing(25);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, buttonBox);
        popupVBox.setSpacing(20);

        root.getChildren().addAll(popupVBox);
        delStage.setScene(scene);
        delStage.show();

        //Set the action for searching through the existing shows
        searchButton.setOnAction(e ->{
            //Loop through all the shows saved and checks for the one matching the given text in the textfield
            //TODO Make it compatible with multiple shows by the same artist.
            for (Show show : DataStore.getShowsA()){
                if (show.getArtist().getName().equalsIgnoreCase(artistField.getText())){
                    showIndex = this.index;
                    popularityDisplay.setText(Integer.toString(show.getPopularity()));
                    stageDisplay.setText(Integer.toString(show.getStage()));
                    beginTimeDisplay.setText(Integer.toString(show.getStartTime()));
                    endTimeDisplay.setText(Integer.toString(show.getEndTime()));
                }
                this.index++;
            }
        });

        //When pressed on the "done" button, a new stage will pop-up to confirm your action of deleting a SINGLE show
        doneButton.setOnAction(e ->{
            SureStage stage = new SureStage(this.showIndex);
            this.showIndex = 0;
            delStage.close();
        });

        //When pressed on the "clearAll" button, a new stage wil pop-up to confirm your action of deleting ALL existing shows
        clearAllButton.setOnAction(event -> {
            SureStage stage = new SureStage(DELETE_ALL);
            this.showIndex = 0;
            delStage.close();
        });
    }
}
