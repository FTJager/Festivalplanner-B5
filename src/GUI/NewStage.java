package gui;

import data.DataStore;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewStage {
    ArrayList<Show> shows;

    private TextField artistField = new TextField();
    private TextField stageField = new TextField();
    private TextField popularityField = new TextField();
    private TextField beginTimeField = new TextField();
    private TextField endTimeField = new TextField();

    private Stage newStage;


    public NewStage(ArrayList<Show> shows) {
        DataStore dataStore = new DataStore();
        List<Show> showList = new ArrayList<>();
        this.shows = shows;

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
            dataStore.setShow(new Show(artistField.getText(),
                    Integer.parseInt(beginTimeField.getText()),
                    Integer.parseInt(endTimeField.getText()),
                    Integer.parseInt(popularityField.getText()),
                    Integer.parseInt(stageField.getText())));
            showList.add(dataStore.getShow());

            for(Show show : showList){
                System.out.println(show.getEndTime());
            }

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
