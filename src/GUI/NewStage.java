package GUI;

import Data.Show;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class NewStage {
    ArrayList<Show> shows;
    public NewStage(ArrayList<Show> shows){

        this.shows = shows;

        Stage newStage = new Stage();
        newStage.setTitle("New show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 400);

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

        doneButton.setOnAction(e ->{
            for(Show show : shows){
                if(!shows.contains(show)){
                    shows.add(show);
                }
            }
            System.out.println("New shows: " + shows);
            newStage.close();
        });

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
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
