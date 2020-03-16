package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ErrorStage {

    ErrorStage(){
        Stage errorStage = new Stage();
        errorStage.setTitle("Error");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 100);

        Label errorLabel = new Label("Error: Artist can't have multiple shows at the same time");

        Button okButton = new Button("Ok");

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(errorLabel);
        labelBox.setSpacing(35);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox);
        hBox.setSpacing(10);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(okButton);
        buttons.setSpacing(25);

        VBox popupBox = new VBox();
        popupBox.getChildren().addAll(hBox, buttons);
        popupBox.setSpacing(20);

        okButton.setOnAction(event -> {
            errorStage.close();
        });

        root.getChildren().addAll(popupBox);
        errorStage.setScene(scene);
        errorStage.show();
    }
}
