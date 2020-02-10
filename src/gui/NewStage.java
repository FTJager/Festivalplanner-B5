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
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NewStage {
    List<Show> showList = new ArrayList<>();
    Show newShow = new Show();

    transient Serializer serializer = new Serializer();
    transient Deserializer deserializer = new Deserializer();
    private TextField artistField = new TextField();
    private TextField stageField = new TextField();
    private TextField popularityField = new TextField();
    private TextField beginTimeField = new TextField();
    private TextField endTimeField = new TextField();

    private Stage newStage;


    public NewStage() {
        DataStore dataStore = new DataStore();

        showList = deserializer.Read();

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
//            dataStore.setShow(new Show(artistField.getText(),
//                    Integer.parseInt(beginTimeField.getText()),
//                    Integer.parseInt(endTimeField.getText()),
//                    Integer.parseInt(popularityField.getText()),
//                    Integer.parseInt(stageField.getText())));
            newShow.setShow(artistField.getText());
            newShow.setStartTime(Integer.parseInt(beginTimeField.getText()));
            newShow.setEndTime(Integer.parseInt(endTimeField.getText()));
            newShow.setPopularity(Integer.parseInt(popularityField.getText()));
            newShow.setStage(Integer.parseInt(stageField.getText()));
            this.showList.add(newShow);

            for(Show show : this.showList){
                System.out.println(show.getEndTime());
            }

            newStage.close();
            System.out.println(this.showList);
            if (!this.showList.isEmpty()){
                serializer.Write(this.showList);
            }

            //TEMP, TEST
            System.out.println(deserializer.Read());
            for (Show show : showList){
                System.out.println(show.getShow());
            }
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
