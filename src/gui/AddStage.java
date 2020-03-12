package gui;

import data.DataStore;
import data.Deserializer;
import data.Serializer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.xml.soap.Text;

public class AddStage {
    transient Serializer serializer = new Serializer();
    transient Deserializer deserializer = new Deserializer();

    private Stage addStage;
    private TextField stageField = new TextField();

    private data.Stage stage = new data.Stage();

    public AddStage(){
        this.addStage = new Stage();
        addStage.setTitle("New Stage");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        Label stageLabel = new Label("Stage: ");
        TextField stageField = new TextField();
        Button doneButton = new Button("Done");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(
        stageLabel, stageField);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hbox, doneButton);
        vBox.setSpacing(30);
        root.getChildren().addAll(vBox);
        addStage.setScene(scene);
        addStage.show();

        doneButton.setOnAction(e -> {
            stage.setName(stageLabel.getText());
            DataStore.setStageA(stage);
            serializer.WriteStage(DataStore.getStagesA());
            addStage.close();
        });

        addStage.setScene(scene);
        addStage.show();
    }
}
