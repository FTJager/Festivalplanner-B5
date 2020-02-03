package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //TABLE
        TableView tableView = new TableView();
        TableColumn timeColumn = new TableColumn("Time:");
        TableColumn mainStageColumn = new TableColumn("Main stage:");
        TableColumn backStageColumn = new TableColumn("Back stage:");
        TableColumn sideStageColumn = new TableColumn("Side stage:");
        TableColumn smallStageColumn = new TableColumn("Small stage:");

        timeColumn.setPrefWidth(80);
        mainStageColumn.setPrefWidth(200);
        backStageColumn.setPrefWidth(200);
        sideStageColumn.setPrefWidth(200);
        smallStageColumn.setPrefWidth(200);

        tableView.getColumns().addAll(timeColumn,
                mainStageColumn,
                backStageColumn,
                sideStageColumn,
                smallStageColumn);
        tableView.setEditable(false);
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(tableView);
        BorderPane pane = new BorderPane();

        //BUTTONS
        Button newButton = new Button("new");
        Button editButton = new Button("edit");
        Button deleteButton = new Button("delete");
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(50);
        buttonBox.getChildren().addAll(newButton, editButton, deleteButton);

        pane.setCenter(vBox);
        pane.setBottom(buttonBox);

        stage.setWidth(900);
        stage.setHeight(500);
        stage.setTitle("Festival Agenda");

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        stage.setScene(scene);
        stage.show();

        //POPUP
        Popup popup = new Popup();
        Label artistLabel = new Label("Artist: ");
        BorderPane popupPane = new BorderPane();
        Stage popupStage = new Stage();

        //Pane popupPane = new Pane();
        popupPane.setStyle("-fx-background-color: #000000;");
        Scene popupScene = new Scene(popupPane, 200, 200);
        popupStage.setScene(popupScene);

        //popup.getContent().addAll(popupStage);
        popup.setAutoHide(false);
        popup.setX(200);
        popup.setY(200);

        newButton.setOnAction(event -> {
            if (!popup.isShowing()){
                popup.show(stage);
            }
        });

    }
}
