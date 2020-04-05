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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class AddStage {
    transient Serializer serializer = new Serializer();

    private Stage addStage;
    private javafx.scene.paint.Paint colorActive;
    private javafx.scene.paint.Paint colorUnActive;

    public AddStage() {
        this.addStage = new Stage();
        this.addStage.setTitle("New Stage");
        colorActive = new javafx.scene.paint.Color(1, 0, 0, 1);
        colorUnActive = new javafx.scene.paint.Color(1, 0, 0, 0);

        buildStage();
    }

    public void buildStage() {

        Text stageExistsText = new Text("This stage already exists!");
        stageExistsText.setFill(colorActive);
        Text tooManyStagesText = new Text("Only four stages can exist at a certain time!");
        tooManyStagesText.setFill(colorActive);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 300);

        Label stageLabel = new Label("Stage: ");
        TextField stageField = new TextField();
        Button doneButton = new Button("Done");

        VBox stageBox = new VBox();
        stageBox.getChildren().add(stageField);
        stageBox.setSpacing(10);


        HBox hbox = new HBox();
        hbox.getChildren().addAll(stageLabel, stageBox);

        VBox vBoxNormalSpacing = new VBox();
        vBoxNormalSpacing.setSpacing(30);
        vBoxNormalSpacing.getChildren().add(doneButton);

        VBox vBox = new VBox();
        vBox.setSpacing(30);
        vBox.getChildren().addAll(hbox, vBoxNormalSpacing);

        root.getChildren().add(vBox);

        //Adds new stage to the stageStore.ser after the done button is clicked
        doneButton.setOnAction(e -> {
            boolean check = true;
            stageBox.getChildren().removeAll(stageExistsText, tooManyStagesText);
            //if the field is empty
            if (stageField.getText().isEmpty()) {
                stageBox.getChildren().add(stageExistsText);
                check = false;
            } else {
                //goes through the data store to find equals, if the data store is empty, there is no need for it
                if (!DataStore.getStages().isEmpty()) {
                    ArrayList<agenda.data.Stage> stageList = new ArrayList<>(DataStore.getStages());
                    for (agenda.data.Stage stage : stageList) {
                        //If the stage Already exist
                        if (stage.getName().equalsIgnoreCase(stageField.getText())) {
                            stageBox.getChildren().add(stageExistsText);
                            check = false;
                        }
                    }
                }
                if(DataStore.getStages().size() >= 4) {
                    stageBox.getChildren().removeAll(stageExistsText);
                    check = false;
                    stageBox.getChildren().add(tooManyStagesText);
                }
            }

            //if the check passes, the stage can be added to the data store
            if(check) {
                agenda.data.Stage newStage = new agenda.data.Stage();
                newStage.setName(stageField.getText());
                DataStore.setNewStages(newStage);
                serializer.WriteStage(DataStore.getStages());
                addStage.close();
                System.out.println("Stage " + stageField.getText() + " added!");
            }
        });

        addStage.setScene(scene);
        addStage.show();
    }
}

