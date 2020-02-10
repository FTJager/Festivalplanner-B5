package gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DeleteStage {
    DeleteStage(){
        Stage delStage = new Stage();
        delStage.setTitle("Delete show");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 300, 200);

        Label delPopupLabel = new Label("Are you sure?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            delStage.close();
        });

        noButton.setOnAction(e -> {
            delStage.close();
        });

        VBox delBox = new VBox();
        HBox delBoxButton = new HBox();
        delBoxButton.getChildren().addAll(yesButton, noButton);
        delBoxButton.setSpacing(10);
        delBox.getChildren().addAll(delPopupLabel, delBoxButton);
        delBox.setSpacing(20);

        root.getChildren().addAll(delBox);
        delStage.setScene(scene);
        delStage.show();
    }
}
