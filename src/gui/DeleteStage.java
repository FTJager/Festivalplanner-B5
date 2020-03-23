/**
 * The DeleteStage is called when the "delete" button is pressed, and shows up on the GUI
 * as a pop-up that allows you to delete a single show or all of the existing shows.
 */

package gui;

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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;

public class DeleteStage {
    public static final int DELETE_ALL = -1;
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    private int showIndex;
    private int index = 0;

    DeleteStage(){
        //Set up for the deleteStage with buttons, labels, text fields, etc.
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        if (!deserializer.ReadArtist().isEmpty()){
            DataStore.setShowsA(deserializer.ReadArtist());
        }

        Text artistNotFoundText = new Text(0, 0, "Artist not found!");
        Paint artistNotFoundPaint = new Color(1, 0, 0, 1);
        artistNotFoundText.setFill(artistNotFoundPaint);

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
        Label endtimeDisplay = new Label();

        Button doneButton = new Button("Done");
        Button searchButton = new Button("Search");
        Button clearAllButton = new Button("Clear all");

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox artistBox = new VBox();
        artistBox.getChildren().add(artistField);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistBox, popularityDisplay, stageDisplay, beginTimeDisplay, endtimeDisplay);
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
            boolean found = false;
            artistBox.getChildren().remove(artistNotFoundText);
            //Loop through all the shows saved and checks for the one matching the given text in the textfield
            for (Show show : DataStore.getShowsA()){
                if (show.getShow().equals(artistField.getText())){
                    showIndex = this.index;
                    popularityDisplay.setText(Integer.toString(show.getPopularity()));
                    stageDisplay.setText(show.getStage());
                    beginTimeDisplay.setText(Integer.toString(show.getStartTime()));
                    endtimeDisplay.setText(Integer.toString(show.getEndTime()));
                    found = true;
                }
                this.index++;
            }
            if(!found) {
                artistBox.getChildren().add(artistNotFoundText);
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
