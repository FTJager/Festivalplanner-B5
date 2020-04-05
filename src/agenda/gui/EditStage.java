/**
 * The EditStage class is called when the "edit" button is pressed, and shows up in the GUi
 * as a pop-up that allows you to edit the details of an existing show.
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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class EditStage {
    boolean fieldAdded;
    boolean searched;
    Serializer serializer;
    Deserializer deserializer;

    private ArrayList<Show> nextArray;

    private Show deletedShow;
    private Show editedShow;
    private Show nextShow;

    private VBox artistBox;
    private VBox fieldBox;
    private VBox stageBox;
    private Text artistNotFoundText;
    private Text fillInAnArtist;

    private TextField artistField;
    private TextField genreField;
    private TextField popularityField;
    private TextField beginTimeField;
    private TextField endTimeField;
    private TextField stageField;

    private int index;



    EditStage(){
        this.nextArray = new ArrayList<>();
        this.deserializer = new Deserializer();
        this.serializer = new Serializer();
        this.index = 0;
        this.deletedShow = new Show();
        this.editedShow = new Show();
        this.nextShow = new Show();
        Stage editShow = new Stage();
        editShow.setTitle("Edit show");

        this.nextArray.addAll(DataStore.getShowsA());


        //Set up for the editStage with buttons, labels, text fields, etc.
        if (!this.deserializer.Read(Serializer.ARTISTS).isEmpty()){
            DataStore.setShowsA(this.deserializer.Read(Serializer.ARTISTS));
        }

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        this.artistNotFoundText = new Text(0, 0, "Artist not found!");
        this.fillInAnArtist = new Text(0, 0, "Please fill in an artist!");
        Text noStageFound = new Text(0, 0, "Please fill in a stage!");
        Paint artistPaint = new Color(1, 0, 0, 1);
        this.artistNotFoundText.setFill(artistPaint);
        this.fillInAnArtist.setFill(artistPaint);
        noStageFound.setFill(artistPaint);

        Label artistLabel = new Label("Artist: ");
        this.artistField = new TextField();
        Label genreLabel = new Label("Genre:  ");
        this.genreField = new TextField();
        Label popularityLabel = new Label("Popularity: ");
        this.popularityField = new TextField();
        Label stageLabel = new Label("Stage:");
        this.stageField = new TextField();
        Label beginTimeLabel = new Label("BeginTime: ");
        this.beginTimeField = new TextField();
        Label endTimeLabel = new Label("EndTime: ");
        this.endTimeField = new TextField();

        Button doneButton = new Button("Done");
        Button searchButton = new Button("Search");
        Button nextButton = new Button("Next");

        this.artistBox = new VBox();
        this.artistBox.getChildren().add(artistField);

        stageBox = new VBox();
        stageBox.getChildren().add(stageField);

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, genreLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(30);
        fieldBox = new VBox();
        fieldBox.getChildren().add(artistBox);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(doneButton, searchButton, nextButton);
        buttonBox.setSpacing(20);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, buttonBox);
        popupVBox.setSpacing(15);

        root.getChildren().addAll(popupVBox);
        editShow.setScene(scene);
        editShow.show();

        //Set the action for searching through the existing shows
        searchButton.setOnAction(e ->{
            search();
        });

        nextButton.setOnAction(event -> {
            nextArray.remove(nextShow);
            search();

        });

        //Set the action for the done button such that the changes made are saved
        doneButton.setOnAction(e ->{
            boolean inputValid = true;
            boolean timeValid = true;
            boolean timeChanged = false;

            artistBox.getChildren().remove(fillInAnArtist);
            stageBox.getChildren().remove(noStageFound);

            Artist editArtist = new Artist(artistField.getText(), "");
            ArrayList<Artist> editArtistArray = new ArrayList<>();
            editArtistArray.add(editArtist);
            this.editedShow.setArtistA(editArtistArray);
            agenda.data.Stage editStage = new agenda.data.Stage();
            editStage.setName(stageField.getText());
            if(artistField.getText().isEmpty() || artistField.getText() == null || artistField.getText().equals("")) {
                artistBox.getChildren().add(fillInAnArtist);
                inputValid = false;
            }
            if(searched) {
                if(inputValid) {
                    this.editedShow.setStage(editStage);
                    this.editedShow.setGenre(genreField.getText());
                    this.editedShow.setPopularity(Integer.parseInt(popularityField.getText()));
                    this.editedShow.setBeginTime(Integer.parseInt(beginTimeField.getText()));
                    this.editedShow.setEndTime(Integer.parseInt(endTimeField.getText()));
                }

                //Checks if the begin time of a show is not equal or smaller than the end time of a shows
                if(inputValid) {
                    try {
                        timeValid = (Integer.parseInt(beginTimeField.getText()) != Integer.parseInt(endTimeField.getText())
                                && Integer.parseInt(beginTimeField.getText()) < Integer.parseInt(endTimeField.getText())) && inputValid;
                    } catch (NumberFormatException notANumberException) {
                        inputValid = false;
                        timeValid = false;
                    }
                    //Checks if the begin and/or end time of a show has been modified, needs to be a valid input, or a indexOutOfBoundsException occurs
                    timeChanged = this.editedShow.getStartTime() != Integer.parseInt(beginTimeField.getText()) ||
                            this.editedShow.getEndTime() != Integer.parseInt(endTimeField.getText());
                }
                if(timeValid && timeChanged) {
                    boolean oldShow;
                    //Will look if new times overlap
                    if(!DataStore.getShowsA().isEmpty()) {
                        for(Show show : DataStore.getShowsA()) {
                            oldShow = false;
                            //Will ignore the old show
                            if(show.getStage().getName().equalsIgnoreCase(deletedShow.getStage().getName()) && show.getStartTime() == deletedShow.getStartTime()) {
                                oldShow = true;
                            }
                            //Will compare stages
                            if(show.getStage().getName().equalsIgnoreCase(editedShow.getStage().getName()) && !oldShow){
                                //If times overlap, validation fails
                                if(editedShow.getStartTime() > show.getStartTime() && editedShow.getStartTime() < show.getEndTime()) {
                                    inputValid = false;
                                };
                                //Will look if endtime overlaps, and if endTime completely skips a show
                                if(editedShow.getStartTime() < show.getStartTime()) {
                                    //Looks if endtime overlaps with another show
                                    if(editedShow.getEndTime() > show.getStartTime()) {
                                        inputValid = false;
                                    }
                                }
                            }
                            //looks if the newShow artist has the same time and name of an other artist
                            for(Artist artist : show.getArtistA()) {
                                for(Artist artist2 : editedShow.getArtistA()) {
                                    if(artist.getName().equalsIgnoreCase(artist2.getName())) {
                                        //looks if the startTime is in the artists show
                                        if(editedShow.getStartTime() > show.getStartTime() && editedShow.getStartTime() < show.getEndTime()) {
                                            inputValid = false;
                                        }
                                        //looks if the endtime is in the artists show
                                        if(editedShow.getEndTime() > show.getStartTime() && editedShow.getEndTime() < show.getEndTime()) {
                                            inputValid = false;
                                        }
                                        //looks if the show overlaps
                                        if(editedShow.getStartTime() < show.getStartTime() && editedShow.getEndTime() > show.getEndTime()) {
                                            inputValid = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                System.out.println(inputValid);
                if (popularityField.getText().isEmpty()) {
                    inputValid = false;
                }
                if (stageField.getText().isEmpty()) {

                    stageBox.getChildren().add(noStageFound);
                    inputValid = false;
                }

                //Set a new show based on the changes made
                if (inputValid) {
                    //Checks if a new stage needs to be made or not
                    boolean stageExists = false;
                    ArrayList<agenda.data.Stage> stageList = new ArrayList<>(DataStore.getStages());
                    for(agenda.data.Stage stage : stageList) {
                        if(editedShow.getStage().getName().equalsIgnoreCase(stage.getName())) {
                            stageExists = true;
                        }
                    }
                    if(!stageExists) {
                        DataStore.setNewStages(editedShow.getStage());
                        serializer.WriteStage(DataStore.getStages());
                    }
                    editShow.close();
                    DataStore.getShowsA().remove(deletedShow);
                    DataStore.getShowsA().add(editedShow);
                    serializer.Write(DataStore.getShowsA(), Serializer.SHOWS);

                }
            }

            fieldAdded = false;
        });
    }

    public void search() {
        artistBox.getChildren().remove(artistNotFoundText);
        artistBox.getChildren().remove(fillInAnArtist);
        fieldBox.getChildren().removeAll(popularityField, genreField, stageBox, beginTimeField, endTimeField);

        boolean valid = true;
        boolean found = false;
        searched = true;

        //If nothing is filled in, it will set valid on false and wont search for an artist.
        if(artistField.getText().isEmpty()) {
            artistBox.getChildren().add(fillInAnArtist);
            valid = false;
        }
        //Loop through all the shows saved and checks for the one matching the given text in the textfield
        if(valid) {
            for (Show show : nextArray){
                for(int i = 0; i < show.getArtistA().size() ; i++) {
                    String showArtist = show.getArtistA().get(i).getName() + "";
                    if (showArtist.equalsIgnoreCase(artistField.getText())){
                        found = true;
                        deletedShow = show;
                    }
                }
                if(found) {
                    fieldBox.getChildren().removeAll(genreField, popularityField, stageBox, beginTimeField, endTimeField);
                    fieldBox.getChildren().addAll(genreField, popularityField, stageBox, beginTimeField, endTimeField);
                    genreField.setText(show.getGenre());
                    popularityField.setText(Integer.toString(show.getPopularity()));
                    stageField.setText(show.getStage().getName());
                    beginTimeField.setText(Integer.toString(show.getStartTime()));
                    endTimeField.setText(Integer.toString(show.getEndTime()));

                    nextShow = show;
                }
            }
            this.index++;
        }
        //No artist found, so artistNotFoundText is displayed
        if(!found && valid) {
            artistBox.getChildren().remove(artistNotFoundText);
            artistBox.getChildren().add(artistNotFoundText);
        }
        //Prevents clicking on done, when the search failed
        if(!valid) {
            searched = false;
        }
        this.index = 0;

    }
}
