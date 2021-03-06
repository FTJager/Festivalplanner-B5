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
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.omg.CORBA.DATA_CONVERSION;

import java.util.ArrayList;
import java.util.List;

public class NewStage {
    Show newShow = new Show();

    transient Serializer serializer = new Serializer();
    transient Deserializer deserializer = new Deserializer();
    private TextField artistField;
    private TextField stageField;
    private TextField popularityField;
    private TextField beginTimeField;
    private TextField endTimeField;

    private int lableBoxSpacing;

    private Stage newStage;

    public NewStage() {
        //initialise
        this.artistField = new TextField();
        this.popularityField = new TextField();
        this.stageField = new TextField();
        this.beginTimeField = new TextField();
        this.endTimeField = new TextField();

       ArrayList<Integer> artistSpacing = new ArrayList<>();
       ArrayList<Integer> popularitySpacing = new ArrayList<>();
       ArrayList<Integer> stageSpacing = new ArrayList<>();
       ArrayList<Integer> beginTimeSpacing = new ArrayList<>();
       ArrayList<Integer> endTimeSpacing = new ArrayList<>();

        this.lableBoxSpacing = 30;

        //Setup for the newStage with buttons, labels, text fields, etc.
        State state = new State();
        DataStore.setShowsA(this.deserializer.Read(Serializer.SHOWS));
        DataStore.setArtistsS(this.deserializer.Read(Serializer.ARTISTS));
        DataStore.setStages(this.deserializer.Read(Serializer.STAGES));
        this.newStage = new Stage();
        this.newStage.setTitle("New show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 400);

        Label artistLabel = new Label("Artist: ");
        Label popularityLabel = new Label("Popularity: ");
        Label stageLabel = new Label("Stage:");
        Label beginTimeLabel = new Label("BeginTime: ");
        Label endTimeLabel = new Label("EndTime: ");

        Button doneButton = new Button("Done");

        //Creates the fields, labels and buttons for the pop-up
        VBox artistBox = new VBox();
        artistBox.getChildren().add(this.artistField);
        VBox popularityBox = new VBox();
        popularityBox.getChildren().add(this.popularityField);
        VBox stageBox = new VBox();
        stageBox.getChildren().add(this.stageField);
        VBox beginTimeBox = new VBox();
        beginTimeBox.getChildren().add(this.beginTimeField);
        VBox endTimeBox = new VBox();
        endTimeBox.getChildren().add(this.endTimeField);

        Paint errorPaint = new Color(1, 0, 0, 1);

        Text fillInAnArtistText = new Text("Please fill in an artist");
        Text fillInAPopularityText = new Text("Please fill in a popularity!");
        Text fillInAStageText = new Text("Please fill in a stage!");
        Text fillInBeginTimeText = new Text("Please fill in a begin time!");
        Text fillInEndTimeText = new Text("Please fill in a end time!");

        Text popularityNotCorrectText = new Text("Please fill in a valid popularity!");
        Text endTimeNotCorrectText = new Text("Please fill in a valid time!");
        Text beginTimeNotCorrectText = new Text("Please fill in a valid time!");

        fillInAnArtistText.setFill(errorPaint);
        fillInAStageText.setFill(errorPaint);
        fillInAPopularityText.setFill(errorPaint);
        fillInBeginTimeText.setFill(errorPaint);
        fillInEndTimeText.setFill(errorPaint);

        popularityNotCorrectText.setFill(errorPaint);
        endTimeNotCorrectText.setFill(errorPaint);
        beginTimeNotCorrectText.setFill(errorPaint);

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(this.lableBoxSpacing);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistBox, popularityBox, stageBox, beginTimeBox, endTimeBox);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, doneButton);
        popupVBox.setSpacing(15);

        //Donebutton does al the validaton, so a /really/ big setOnAction
        doneButton.setOnAction(e -> {
            //This variable is used to make sure the program doesn't create an object with invalid variables
            boolean inputValid = true;
            boolean beginTimeIsInteger = true;
            boolean endTimeIsInteger = true;
            boolean stageFound = false;
            List<agenda.data.Stage> showStageList = new ArrayList<>(DataStore.getStages());
            ArrayList<Artist> newShowArtists = new ArrayList<>();


            // if-else statement makes sure a name has been given for the show
            //LOT of if statements. They will make it look nice on the GUI
            //Comments by artist can be used by other validation methods

            //if nothing is filled in
            if (this.artistField.getText().isEmpty() || this.artistField.getText() == null) {
                //removes warning first, so no double warnings show up
                artistBox.getChildren().remove(fillInAnArtistText);
                artistBox.getChildren().add(fillInAnArtistText);
                inputValid = false;
                //Could not use changing booleans, so the array list is a boolean. Makes the spacing bigger, so it looks better.
                if(artistSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    artistSpacing.add(1);
                }
            //valid input
            } else {
                //removes the error
                artistBox.getChildren().remove(fillInAnArtistText);
                //again, an array list as boolean. Makes the spacing smaller, so it looks better
                if(artistSpacing.size() > 0) {
                    artistSpacing.clear();
                    this.lableBoxSpacing -= 3;
                }
                //Separates the artist, if there are more than 1 of them
                this.newShow.setArtistA(new ArrayList<>(ArtistSeparation(this.artistField.getText())));
                for(Artist artist : this.newShow.getArtistA()) {
                    System.out.println(artist.getName());
                }
            }

            //Same comments as artist
            if(this.popularityField.getText().isEmpty() || this.popularityField.getText() == null) {
                popularityBox.getChildren().remove(fillInAPopularityText);
                popularityBox.getChildren().add(fillInAPopularityText);
                inputValid = false;
                if(popularitySpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    popularitySpacing.add(1);
                }
            } else {
                popularityBox.getChildren().removeAll(fillInAPopularityText, popularityNotCorrectText);
                if(popularitySpacing.size() > 0) {
                    this.lableBoxSpacing -= 3;
                    popularitySpacing.clear();
                }
                //Try and catch for integer validation
                try {
                    popularityBox.getChildren().removeAll(fillInAPopularityText, popularityNotCorrectText);
                    this.newShow.setPopularity(Integer.parseInt(this.popularityField.getText()));
                } catch (NumberFormatException popularityExeption) {
                    popularityBox.getChildren().add(popularityNotCorrectText);
                    inputValid = false;
                }

            }

            //StageValidation is skipped here, will be done below!

            //try and catch for integer validation
            try {
                Integer.parseInt(this.beginTimeField.getText());
            } catch (NumberFormatException beginTimeException) {
                beginTimeIsInteger = false;
                inputValid = false;
            }
            //try and catch for integer validation
            try {
                Integer.parseInt(this.endTimeField.getText());
            } catch (NumberFormatException endTimeException) {
                endTimeIsInteger = false;
                inputValid = false;
            }

            //If nothing is filled in
            if(this.beginTimeField.getText().isEmpty() || this.beginTimeField.getText() == null) {
                beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                beginTimeBox.getChildren().add(fillInBeginTimeText);
                inputValid = false;
                if(beginTimeSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    beginTimeSpacing.add(1);
                }
            //Extra if statement for correct error display
            } else if(!beginTimeIsInteger){
                beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                beginTimeBox.getChildren().add(beginTimeNotCorrectText);
                if (beginTimeSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    beginTimeSpacing.add(1);
                }
            } else {
                //if the begin time is larger then the end time, the validation fails
                if (Integer.parseInt(this.beginTimeField.getText()) > Integer.parseInt(this.endTimeField.getText()) || Integer.parseInt(this.beginTimeField.getText()) < 0 || Integer.parseInt(this.beginTimeField.getText()) > 23) {
                    beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                    beginTimeBox.getChildren().add(beginTimeNotCorrectText);
                    inputValid = false;
                    if(beginTimeSpacing.size() == 0) {
                        this.lableBoxSpacing += 3;
                        beginTimeSpacing.add(1);
                    }
                }
                else {
                    beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                    this.newShow.setBeginTime(Integer.parseInt(this.beginTimeField.getText()));
                    if(beginTimeSpacing.size() > 0) {
                        beginTimeSpacing.clear();
                        lableBoxSpacing -= 3;
                    }
                }
            }

            if(this.endTimeField.getText().isEmpty() || this.endTimeField.getText() == null) {
                endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                endTimeBox.getChildren().add(fillInEndTimeText);
                inputValid = false;
                if(endTimeSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    endTimeSpacing.add(1);
                }
            //extra if statement for correct error display
            } else if(!endTimeIsInteger){
                endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                endTimeBox.getChildren().add(endTimeNotCorrectText);
                inputValid = false;
                if(endTimeSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    endTimeSpacing.add(1);
                }
            } else {
                if(Integer.parseInt(this.endTimeField.getText()) < 1 || Integer.parseInt(this.endTimeField.getText()) > 24) {
                    endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                    endTimeBox.getChildren().add(endTimeNotCorrectText);
                    inputValid = false;
                    if (endTimeSpacing.size() == 0) {
                        this.lableBoxSpacing += 3;
                        endTimeSpacing.add(1);
                    }
                }
                //if begin time is bigger then the end time, done twice for correct error display
                if(beginTimeIsInteger){
                    if (Integer.parseInt(this.endTimeField.getText()) < Integer.parseInt(this.beginTimeField.getText())) {
                        endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                        endTimeBox.getChildren().add(endTimeNotCorrectText);
                        inputValid = false;
                        if (endTimeSpacing.size() == 0) {
                            this.lableBoxSpacing += 3;
                            endTimeSpacing.add(1);
                        }
                    }
                    else {
                        endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                        newShow.setEndTime(Integer.parseInt(endTimeField.getText()));
                        if (endTimeSpacing.size() > 0) {
                            endTimeSpacing.clear();
                            this.lableBoxSpacing -= 3;
                        }
                    }
                } else {
                    endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                    this.newShow.setEndTime(Integer.parseInt(this.endTimeField.getText()));
                    if (endTimeSpacing.size() > 0) {
                        endTimeSpacing.clear();
                        this.lableBoxSpacing -= 3;
                    }
                }
            }

            //important code. Lets the stages work properly! Stage Validation
            if (!this.stageField.getText().isEmpty()) {
                stageBox.getChildren().remove(fillInAStageText);
                if(stageSpacing.size() > 0) {
                    this.lableBoxSpacing -= 3;
                    stageSpacing.clear();
                }
                agenda.data.Stage newStage = new agenda.data.Stage();
                newStage.setName(this.stageField.getText());
                this.newShow.setStage(newStage);
                agenda.data.Stage showStage = new agenda.data.Stage();
                showStage.setName(this.stageField.getText());
                //looks at the data store, if it is empty, there is no stage to compare
                if(!DataStore.getStages().isEmpty()) {
                    //Goes trough all stages and tries to find an equal stage
                    for(agenda.data.Stage stage : DataStore.getStages()) {
                        //if found, stage found = true
                        if(stage.getName().equalsIgnoreCase(this.stageField.getText())) {
                            stageFound = true;
                        }
                    }
                    //if stage is not found, it just adds the stage to the file
                    if (!stageFound){
                        showStageList.add(showStage);
                    }
                //data store is empty, so it just adds the stage to the file
                } else {
                    showStageList = DataStore.getStages();
                    showStageList.add(showStage);
                }
            //if stageField is empty
            } else {
                stageBox.getChildren().remove(fillInAStageText);
                stageBox.getChildren().add(fillInAStageText);
                inputValid = false;
                if(stageSpacing.size() == 0) {
                    this.lableBoxSpacing += 3;
                    stageSpacing.add(1);
                }

            }

            if (!DataStore.getArtistsS().isEmpty()) {
                List<Artist> artistsNotAdded = new ArrayList<>();
//                for (Artist artist : DataStore.getArtistsS()) {
                    for (Artist artistNew : newShow.getArtistA()) {
                        if (!DataStore.getArtistsS().contains(artistNew)){
                            artistsNotAdded.add(artistNew);
                        }
//                        if (!artist.getName().equalsIgnoreCase(artistNew.getName())) {
//                        }
                    }
//                }
                List<Artist> artistsInList = DataStore.getArtistsS();
                artistsInList.addAll(artistsNotAdded);
                DataStore.setArtistsS(artistsInList);
            }
            else{
                DataStore.setArtistsS(newShow.getArtistA());
            }
            inputValid = true;

            //Looks at overlapping shows, if the datastore is empty, there is no need for this
            if(!DataStore.getShowsA().isEmpty() && inputValid) {
                for(Show show : DataStore.getShowsA()) {

                    //looks if the newShow artist has the same time and name of an other artist
                    for(Artist artist : show.getArtistA()) {
                        for(Artist artist2 : this.newShow.getArtistA()) {
                            if(artist.getName().equalsIgnoreCase(artist2.getName())) {
                                //looks if the startTime is in the artists show
                                if(this.newShow.getStartTime() >= show.getStartTime() && this.newShow.getStartTime() <= show.getEndTime()) {
                                    inputValid = false;
                                }
                                //looks if the endtime is in the artists show
                                if(this.newShow.getEndTime() >= show.getStartTime() && this.newShow.getEndTime() <= show.getEndTime()) {
                                    inputValid = false;
                                }
                                //looks if the show overlaps
                                if(this.newShow.getStartTime() <= show.getStartTime() && this.newShow.getEndTime() >= show.getEndTime()) {
                                    inputValid = false;
                                }
                            }
                        }
                    }
                    //Will compare stages
                    if(show.getStage().getName().equalsIgnoreCase(this.newShow.getStage().getName())){
                        //If times overlap, validation fails
                        if(this.newShow.getStartTime() > show.getStartTime() && this.newShow.getStartTime() < show.getEndTime()) {
                            beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                            beginTimeBox.getChildren().add(beginTimeNotCorrectText);
                            inputValid = false;
                            if(beginTimeSpacing.size() == 0) {
                                this.lableBoxSpacing += 3;
                                beginTimeSpacing.add(1);
                            }
                        }
                        //Will look if endtime overlaps, and if endTime completely skips a show
                        if(this.newShow.getStartTime() < show.getStartTime()) {
                            //Looks if endtime overlaps with another show
                            if(this.newShow.getEndTime() > show.getStartTime()) {
                                endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                                endTimeBox.getChildren().add(endTimeNotCorrectText);
                                inputValid = false;
                                if (endTimeSpacing.size() == 0) {
                                    this.lableBoxSpacing += 3;
                                    endTimeSpacing.add(1);
                                }
                            }
                        }
                        //Removes errors, if there are any
                        if(inputValid) {
                            beginTimeBox.getChildren().removeAll(fillInBeginTimeText, beginTimeNotCorrectText);
                            endTimeBox.getChildren().removeAll(fillInEndTimeText, endTimeNotCorrectText);
                            if (endTimeSpacing.size() > 0) {
                                endTimeSpacing.clear();
                                this.lableBoxSpacing -= 3;
                            }
                            if(beginTimeSpacing.size() > 0) {
                                beginTimeSpacing.clear();
                                lableBoxSpacing -= 3;
                            }
                        }
                    }
                }
            }
            labelBox.setSpacing(lableBoxSpacing);
            //Add the newly created show into the dataStore
            if (inputValid) {
                this.serializer.Write(DataStore.getArtistsS(), Serializer.ARTISTS);
                this.serializer.Write(showStageList, Serializer.STAGES);
                DataStore.setShowA(this.newShow);
                this.serializer.Write(DataStore.getShowsA(), Serializer.SHOWS);
                this.newStage.close();
            }
            //sets the correct spacing with error messages
            labelBox.setSpacing(this.lableBoxSpacing);
        });

        root.getChildren().addAll(popupVBox);
        this.newStage.setScene(scene);
        this.newStage.show();
    }

    //if multiple artists are added, this method will separate them
    public ArrayList<Artist> ArtistSeparation(String artist){
        ArrayList<Artist> artistCounter = new ArrayList<>();
        String artistSubString;
        //Artist are separated by commas.
        if(artist.contains(",")) {
            //will repeat for the whole artist lenght
            for(int i = 0; i < artist.length(); i++) {
                char separationChar = artist.charAt(i);
                String tempString = String.valueOf(separationChar);
                //Comma found, so the artists are separated
                if(tempString.equals(",")){
                    artistSubString = artist.substring(0, i);
                    //The first part is added
                    artistCounter.add(new Artist(artistSubString, ""));
                    //The first part is removed, so we dont add the same one twice
                    artist = artist.substring(i + 2);
                    i = 0;
                }
            }
            //No commas left, so the last part is also added
            if(artist.length() > 0) {
                artistCounter.add(new Artist(artist, ""));
            }
        //no commas found, so the whole string is added
        } else artistCounter.add(new Artist(artist, ""));
        return artistCounter;
    }
}
