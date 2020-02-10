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

public class DeleteStage {
    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();
    List<Show> showList = new ArrayList<>();
    private int showIndex;
    private int index = 0;

    DeleteStage(){
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        if (!deserializer.Read().isEmpty()){
            showList = deserializer.Read();
        }

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

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistField, popularityDisplay, stageDisplay, beginTimeDisplay, endtimeDisplay);
        fieldBox.setSpacing(31);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(doneButton, searchButton);
        buttonBox.setSpacing(25);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, buttonBox);
        popupVBox.setSpacing(20);

        root.getChildren().addAll(popupVBox);
        delStage.setScene(scene);
        delStage.show();

        searchButton.setOnAction(e ->{
            for (Show show : showList){
                if (show.getShow().equals(artistField.getText())){
                    showIndex = this.index;
                    popularityDisplay.setText(Integer.toString(show.getPopularity()));
                    stageDisplay.setText(Integer.toString(show.getStage()));
                    beginTimeDisplay.setText(Integer.toString(show.getStartTime()));
                    endtimeDisplay.setText(Integer.toString(show.getEndTime()));
                }
                this.index++;
            }
        });
        doneButton.setOnAction(e ->{
            SureStage stage = new SureStage(this.showIndex);
            this.showIndex = 0;
            delStage.close();
        });
    }
}
