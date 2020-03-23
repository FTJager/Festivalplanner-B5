/**
 * The NewStage class gets called when the "new" button is pressed, and shows up in the GUI
 * as a pop-up that allows you to fill in the details for a new show.
 */
package agenda.gui;

import agenda.data.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewStage {
    Show newShow = new Show();

    transient Serializer serializer = new Serializer();
    transient Deserializer deserializer = new Deserializer();
    private TextField artistField = new TextField();
    private TextField stageField = new TextField();
    private TextField popularityField = new TextField();
    private TextField beginTimeField = new TextField();
    private TextField endTimeField = new TextField();

    private Stage newStage;

    /**
     * The popup that appears when the New button is clicked in the GUI. This contains a window that allows the user to create a new show object.
     */
    public NewStage() {
        //Setup for the newStage with buttons, labels, text fields, etc.
        DataStore.setShowsA(deserializer.Read(Serializer.SHOWS));
        DataStore.setArtists(deserializer.Read(Serializer.ARTISTS));
        this.newStage = new Stage();
        newStage.setTitle("New show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        Label artistLabel = new Label("Artist: ");
        Label popularityLabel = new Label("Popularity: ");
        Label stageLabel = new Label("Stage:");
        Label beginTimeLabel = new Label("BeginTime: ");
        Label endTimeLabel = new Label("EndTime: ");

        Button doneButton = new Button("Done");

        doneButton.setOnAction(e -> {
            //This variable is used to make sure the program doesn't create an object with invalid variables
            boolean inputValid = false;

            // if-else statement makes sure a name has been given for the show
            if (artistField.getText().isEmpty() || artistField.getText() == null) {
                artistField.setText("please type in a name");
            } else {
                boolean artistFound = false;
                List<Artist> artists = DataStore.getArtists();
                for (Artist artist : artists){
                    if (artist.getName().equalsIgnoreCase(artistField.getText())){
                        newShow.setArtist(artist);
                        artistFound = true;
                    }
                }
                if (!artistFound){
                    Artist newArtist = new Artist(artistField.getText());
                    newShow.setArtist(newArtist);
                    artists.add(newArtist);
                    DataStore.setArtists(artists);
                }
                inputValid = true;
            }
            //this makes sure the times are either both 0, meaning they are not yet decided, or the endTime is later than the startTime
            if (!beginTimeField.getText().isEmpty()
                    && Integer.parseInt(beginTimeField.getText()) != Integer.parseInt(endTimeField.getText())
                    && Integer.parseInt(beginTimeField.getText()) < Integer.parseInt(endTimeField.getText())) {
                newShow.setStartTime(Integer.parseInt(beginTimeField.getText()));
            } else if (beginTimeField.getText().isEmpty()) {    //If the field is empty, replace it with 0
                newShow.setStartTime(0);
            } else {
                inputValid = false;
                endTimeField.setText("enter a valid time");
            }

            //If the field is not empty, retrieve the text. Otherwise make it 0
            if (!endTimeField.getText().isEmpty()) {
                newShow.setEndTime(Integer.parseInt(endTimeField.getText()));
            } else {
                newShow.setEndTime(0);
            }

            for (Show show : DataStore.getShowsA()){
                if (show.getArtist().getName().equalsIgnoreCase(newShow.getArtist().getName())){
                    if (newShow.getStartTime() < show.getEndTime() && newShow.getStartTime() >= show.getStartTime() || newShow.getEndTime() <= show.getEndTime() && newShow.getEndTime() > show.getStartTime()){
                        ErrorStage stage = new ErrorStage();
                        inputValid = false;
                    }
                }
            }

            if (!popularityField.getText().isEmpty()) {
                newShow.setPopularity(Integer.parseInt(popularityField.getText()));
            } else {
                newShow.setPopularity(0);
            }

            if (!stageField.getText().isEmpty()) {
                newShow.setStage(Integer.parseInt(stageField.getText()));
            } else {
                newShow.setStage(0);
            }

            //Add the newly created show into the dataStore
            if (inputValid) {
                DataStore.getShowsA().add(newShow);
                newStage.close();
            }
            //Write the saved shows into the file
            if (!DataStore.getShowsA().isEmpty()) {
                serializer.Write(DataStore.getShowsA(), Serializer.SHOWS);
            }
            //Write the saved artists into the file
            if (!DataStore.getArtists().isEmpty()){
                serializer.Write(DataStore.getArtists(), Serializer.ARTISTS);
            }
        });

        //Creates the fields, labels and buttons for the pop-up
        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(30);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistField, popularityField, stageField, beginTimeField, endTimeField);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, doneButton);
        popupVBox.setSpacing(15);

        root.getChildren().addAll(popupVBox);
        newStage.setScene(scene);
        newStage.show();
    }
}
