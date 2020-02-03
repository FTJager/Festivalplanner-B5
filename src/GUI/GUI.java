package GUI;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        Button newButton = new Button("New");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
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

        //POPUP ASSETS
        Popup popup = new Popup();
        Label artistLabel = new Label("Artist: ");
        TextField artistField = new TextField();
        Label popularityLabel = new Label("Popularity: ");
        TextField popularityField = new TextField();
        Label stageLabel = new Label("Stage:");

        Button doneButton = new Button("Done");

        VBox labelBox = new VBox();
        labelBox.getChildren().addAll(artistLabel, popularityLabel);
        labelBox.setSpacing(35);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistField, popularityField);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, doneButton);
        popupVBox.setSpacing(15);

        //POPUP
        Pane popupPane = new Pane();
        popupPane.setStyle("-fx-background-color: #FFFFFF;");
        popupPane.getChildren().addAll(popupVBox);

        popup.getContent().addAll(popupPane);
        popup.setAutoHide(false);
        popup.setX(1090/2);
        popup.setY(920/2);

        newButton.setOnAction(event -> {
            if (!popup.isShowing()){
                popup.show(stage);
            }
        });

        doneButton.setOnAction (event -> {
            try {
                if(popup.isShowing()) {
                    if (artistField.getText().equals(dataStore.Artists.getArtists())) {
                        //if everything is fine and the times do not overlap,
                        //put that shit up on the table
                        popup.hide();
                    }
                }
            }
            catch (//give a warning for overlappig times) // whatever i dont get it
        });

    }
}
