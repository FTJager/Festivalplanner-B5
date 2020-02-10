package gui;

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

import java.util.ArrayList;
import java.util.List;

public class EditStage {
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();
    List<Show> showList = new ArrayList<>();
    private int showIndex;
    private Show changedShow = new Show();
    private int index = 0;

    EditStage(){
        if (!deserializer.Read().isEmpty()){
            showList = deserializer.Read();
        }
        Stage editStage = new Stage();
        editStage.setTitle("Edit show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

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





        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().add(artistField);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, doneButton, searchButton);
        popupVBox.setSpacing(15);

        root.getChildren().addAll(popupVBox);
        editStage.setScene(scene);
        editStage.show();

        searchButton.setOnAction(e ->{
            fieldBox.getChildren().addAll(popularityField, stageField, beginTimeField, endTimeField);
            for (Show show : showList){
                System.out.println(show.getShow());
                if (show.getShow().equals(artistField.getText())){
                    showIndex = this.index;
                    popularityField.setText(Integer.toString(show.getPopularity()));
                    stageField.setText(Integer.toString(show.getStage()));
                    beginTimeField.setText(Integer.toString(show.getStartTime()));
                    endTimeField.setText(Integer.toString(show.getEndTime()));
                }
                this.index++;
            }
        });
        doneButton.setOnAction(e ->{

            changedShow.setShow(artistField.getText());
            changedShow.setStartTime(Integer.parseInt(beginTimeField.getText()));
            changedShow.setEndTime(Integer.parseInt(endTimeField.getText()));
            changedShow.setPopularity(Integer.parseInt(popularityField.getText()));
            changedShow.setStage(Integer.parseInt(stageField.getText()));

            editStage.close();
            showList.remove(this.showIndex);
            showList.add(changedShow);
            serializer.Write(showList);
            this.showIndex = 0;
        });
    }
}
