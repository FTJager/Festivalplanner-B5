/**
 * The DeleteStage is called when the "delete" button is pressed, and shows up on the GUI
 * as a pop-up that allows you to delete a single show or all of the existing shows.
 */

package agenda.gui;

import agenda.data.DataStore;
import agenda.data.Deserializer;
import agenda.data.Serializer;
import agenda.data.Show;
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

public class DeleteStage {
    public static final int DELETE_ALL = -1;
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();

    private int showIndex = 0;
    private int artistIndex = 1;
    private int stageIndex = 1;
    private int stageFinalIndex = 0;

    DeleteStage(){
        //Set up for the deleteStage with buttons, labels, text fields, etc.
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        if (!deserializer.Read(Serializer.ARTISTS).isEmpty()){
            DataStore.setShowsA(deserializer.Read(Serializer.ARTISTS));
        }

        Text artistNotFoundText = new Text("Artist not found!");
        Text stageNotFoundText = new Text("Stage not found!");
        Text fillInSomething = new Text("Please fill in an artist or stage!");
        Text oneThingOnly = new Text("Please fill in one box at the time!");
        Paint errorPaint = new Color(1, 0, 0, 1);
        artistNotFoundText.setFill(errorPaint);
        stageNotFoundText.setFill(errorPaint);
        fillInSomething.setFill(errorPaint);
        oneThingOnly.setFill(errorPaint);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 550, 450);

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

        Label stageDeleteLabel = new Label("Delete stage: ");
        TextField stageDeleteField = new TextField();

        Button doneButton = new Button("Done");
        Button searchButton = new Button("Search");
        Button clearAllButton = new Button("Clear all");

        VBox stageBox = new VBox();
        stageBox.getChildren().add(stageDeleteField);
        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox artistBox = new VBox();
        artistBox.getChildren().add(artistField);
        VBox fieldBox = new VBox();

        HBox errorTextBox = new HBox();


        HBox topLayer = new HBox();
        topLayer.getChildren().addAll(artistBox, stageDeleteLabel, stageBox);
        topLayer.setSpacing(25);

        fieldBox.getChildren().addAll(topLayer, popularityDisplay, stageDisplay, beginTimeDisplay, endtimeDisplay);
        fieldBox.setSpacing(31);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(doneButton, searchButton, clearAllButton);
        buttonBox.setSpacing(25);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, errorTextBox, buttonBox);
        popupVBox.setSpacing(20);

        root.getChildren().addAll(popupVBox);
        delStage.setScene(scene);
        delStage.show();

        //Set the action for searching through the existing shows
        searchButton.setOnAction(e ->{
            artistIndex = 1;
            stageIndex = 1;
            boolean found = false;
            artistBox.getChildren().remove(artistNotFoundText);
            stageBox.getChildren().remove(stageNotFoundText);
            errorTextBox.getChildren().removeAll(fillInSomething, oneThingOnly);
            //Will display error message if nothing is filled in
            if(stageDeleteField.getText().isEmpty() && artistField.getText().isEmpty()) {
                errorTextBox.getChildren().add(fillInSomething);
            }
            //If something is filled in the artist field, and not in the stage field
            else if(stageDeleteField.getText().isEmpty() && !artistField.getText().isEmpty()) {
                //Loop through all the shows saved and checks for the one matching the given text in the textfield
                try {
                    for (Show show : DataStore.getShowsA()) {
                        if (show.getArtistA().get(0).equals(artistField.getText())) {
                            this.showIndex = this.artistIndex;
                            popularityDisplay.setText(Integer.toString(show.getPopularity()));
                            stageDisplay.setText(show.getStage().getName());
                            beginTimeDisplay.setText(Integer.toString(show.getStartTime()));
                            endtimeDisplay.setText(Integer.toString(show.getEndTime()));
                            found = true;
                        }
                        this.artistIndex++;
                    }
                } catch (ClassCastException c) {
                    System.out.println("Please restart the program!");
                }

                //Displays error that it couldn't find an artist
                if(!found) {
                    artistBox.getChildren().add(artistNotFoundText);
                }
            }
            //Searches for a stage
            else if(artistField.getText().isEmpty() && !stageDeleteField.getText().isEmpty()){
                if(!DataStore.getStages().isEmpty()){
                    for(agenda.data.Stage stage : DataStore.getStages()) {
                        if(stageDeleteField.getText().equalsIgnoreCase(stage.getName())) {
                            found = true;
                            this.stageFinalIndex = this.stageIndex;
                        }
                        this.stageIndex++;
                    }
                }
                if(!found) {
                    stageBox.getChildren().add(stageNotFoundText);
                }
            //If something is filled in the stage and artist field, will display error
            } else {
                errorTextBox.getChildren().add(oneThingOnly);
            }
        });

        //When pressed on the "done" button, a new stage will pop-up to confirm your action of deleting a show or stage, will also function as search button for lazy people
        doneButton.setOnAction(e ->{
            artistIndex = 1;
            stageIndex = 1;
            boolean found = false;
            boolean closes = true;
            artistBox.getChildren().remove(artistNotFoundText);
            stageBox.getChildren().remove(stageNotFoundText);
            errorTextBox.getChildren().remove(oneThingOnly);

            if(stageDeleteField.getText().isEmpty() && artistField.getText().isEmpty()) {
                delStage.close();
            }
            else if(!stageDeleteField.getText().isEmpty() && !artistField.getText().isEmpty()) {
                closes = false;
                errorTextBox.getChildren().add(oneThingOnly);
            }
            else if(stageDeleteField.getText().isEmpty() && !artistField.getText().isEmpty()) {
                //Loop through all the shows saved and checks for the one matching the given text in the textfield
                try {
                    for (Show show : DataStore.getShowsA()) {
                        if (show.getArtistA().get(0).equals(artistField.getText())) {
                            this.showIndex = this.artistIndex;
                            found = true;
                        }
                        this.artistIndex++;
                    }
                } catch (ClassCastException c) {
                    System.out.println("Please restart the program!");
                }
                if (!found) {
                    artistBox.getChildren().add(artistNotFoundText);
                    closes = false;
                }
            }
            else if(artistField.getText().isEmpty() && !stageDeleteField.getText().isEmpty()) {
                if (!DataStore.getStages().isEmpty()) {
                    for (agenda.data.Stage stage : DataStore.getStages()) {
                        if (stageDeleteField.getText().equalsIgnoreCase(stage.getName())) {
                            found = true;
                            this.stageFinalIndex = this.stageIndex;
                        }
                        this.stageIndex++;
                    }
                }
                if (!found) {
                    stageBox.getChildren().add(stageNotFoundText);
                    closes = false;
                }
            }
            if(closes) {
                new SureStage(this.showIndex, this.stageFinalIndex);
                delStage.close();
            }
        });

        //When pressed on the "clearAll" button, a new stage wil pop-up to confirm your action of deleting ALL existing shows
        clearAllButton.setOnAction(event -> {
            new SureStage(DELETE_ALL, DELETE_ALL);
            this.showIndex = 0;
            this.stageFinalIndex = 0;
            delStage.close();
        });
    }
}
