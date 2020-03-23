/**
 * The EditStage class is called when the "edit" button is pressed, and shows up in the GUi
 * as a pop-up that allows you to edit the details of an existing show.
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

import java.util.ArrayList;

public class EditStage {
    boolean fieldAdded;
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();
    private int showIndex;
    private Show changedShow = new Show();
    private int index = 0;

    EditStage(){
        //Set up for the editStage with buttons, labels, text fields, etc.
        if (!deserializer.ReadArtist().isEmpty()){
            DataStore.setShowsA(deserializer.ReadArtist());
        }
        Stage editStage = new Stage();
        editStage.setTitle("Edit show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        Text artistNotFoundText = new Text(0, 0, "Artist not found!");
        Text fillInAnArtist = new Text(0, 0, "Please fill in an artist!");
        Text noStageFound = new Text(0, 0, "Please fill in a stage!");
        Paint artistPaint = new Color(1, 0, 0, 1);
        artistNotFoundText.setFill(artistPaint);
        fillInAnArtist.setFill(artistPaint);
        noStageFound.setFill(artistPaint);




        Label artistLabel = new Label("Artist: ");
        TextField artistField = new TextField();
        Label popularityLabel = new Label("Popularity: ");
        TextField popularityField = new TextField();
        Label stageLabel = new Label("Stage:");
        TextField stageField = new TextField();
        Label beginTimeLabel = new Label("BeginTime: ");
        TextField beginTimeField = new TextField();
        Label endTimeLabel = new Label("EndTime: ");
        TextField endTimeField = new TextField();

        Button doneButton = new Button("Done");
        Button searchButton = new Button("Search");

        VBox artistBox = new VBox();
        artistBox.getChildren().add(artistField);

        VBox stageBox = new VBox();
        stageBox.getChildren().add(stageField);

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(30);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().add(artistBox);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(doneButton, searchButton);
        buttonBox.setSpacing(20);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, buttonBox);
        popupVBox.setSpacing(15);

        root.getChildren().addAll(popupVBox);
        editStage.setScene(scene);
        editStage.show();

        //Set the action for searching through the existing shows
        searchButton.setOnAction(e ->{
            artistBox.getChildren().remove(artistNotFoundText);
            artistBox.getChildren().remove(fillInAnArtist);
            boolean valid = true;
            boolean found = false;

            //If nothing is filled in, it will set valid on false and wont search for an artist.
            if(artistField.getText().isEmpty()) {
                artistBox.getChildren().add(fillInAnArtist);
                valid = false;
            }
            //Loop through all the shows saved and checks for the one matching the given text in the textfield
            if(valid) {
                for (Show show : DataStore.getShowsA()){
                    if (show.getShow().equalsIgnoreCase(artistField.getText())){
                        showIndex = this.index;
                        found = true;
                    }
                    if(found) {
                        fieldBox.getChildren().addAll(popularityField, stageBox, beginTimeField, endTimeField);

                        popularityField.setText(Integer.toString(show.getPopularity()));
                        stageField.setText(show.getStage().getName());
                        beginTimeField.setText(Integer.toString(show.getStartTime()));
                        endTimeField.setText(Integer.toString(show.getEndTime()));
                    }
                }
                this.index++;
            }
            //No artist found, so artistNotFoundText is displayed
            if(!found && valid) {
                artistBox.getChildren().add(artistNotFoundText);
            }
            this.index = 0;
        });

        //Set the action for the done button such that the changes made are saved
        doneButton.setOnAction(e ->{
            boolean inputValid = true;
            boolean timeValid;
            boolean timeChanged;

            artistBox.getChildren().remove(artistNotFoundText);
            artistBox.getChildren().remove(fillInAnArtist);

            if(artistField.getText().isEmpty() || artistField.getText() == null) {
                artistBox.getChildren().add(fillInAnArtist);
                inputValid = false;
            }
            //Checks if the begin time of a show is not equal or smaller than the end time of a shows
            if ((Integer.parseInt(beginTimeField.getText()) != Integer.parseInt(endTimeField.getText())
                    && Integer.parseInt(beginTimeField.getText()) < Integer.parseInt(endTimeField.getText())) && inputValid){
                timeValid = true;
            } else timeValid = false;

            //Checks if the begin and/or end time of a show has been modified
            if (DataStore.getShowsA().get(showIndex).getStartTime() == Integer.parseInt(beginTimeField.getText()) ||
                    DataStore.getShowsA().get(showIndex).getEndTime() == Integer.parseInt(endTimeField.getText())){
                timeChanged = false;
            } else timeChanged = true;

            if(timeValid || timeChanged) {
                changedShow.setShow(artistField.getText());

                if (popularityField.getText().isEmpty()) {
                    popularityField.setText("0");
                }
                if (stageField.getText().isEmpty()) {
                    stageBox.getChildren().remove(noStageFound);
                    stageBox.getChildren().add(noStageFound);
                    inputValid = false;
                }

                //Set a new show based on the changes made
                changedShow.setBeginTime(Integer.parseInt(beginTimeField.getText()));
                changedShow.setEndTime(Integer.parseInt(endTimeField.getText()));
                changedShow.setPopularity(Integer.parseInt(popularityField.getText()));
                data.Stage newStage = new data.Stage();
                newStage.setName(stageField.getText());
                changedShow.setStage(newStage);
                if (inputValid) {
                    //Checks if a new stage needs to be made or not
                    boolean stageExists = false;
                    ArrayList<data.Stage> stageList = new ArrayList<>(DataStore.getStages());
                    for(data.Stage stage : stageList) {
                        if(changedShow.getStage().getName().equalsIgnoreCase(stage.getName())) {
                            stageExists = true;

                        }
                    }
                    if(!stageExists) {
                        DataStore.setNewStages(changedShow.getStage());
                        serializer.WriteStage(DataStore.getStages());
                    }
                    editStage.close();
                    DataStore.getShowsA().remove(this.showIndex);
                    DataStore.getShowsA().add(changedShow);
                    serializer.Write(DataStore.getShowsA());
                    this.showIndex = 0;
                }
            }
            fieldAdded = false;
        });
    }
}
