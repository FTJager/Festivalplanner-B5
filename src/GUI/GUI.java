package GUI;

import Data.Show;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;

public class GUI extends Application {

    Button newButton = new Button("New");
    Button editButton = new Button("Edit");
    Button deleteButton = new Button("Delete");
    Canvas canvas = new Canvas();
//    Button doneButton = new Button("Done");
//    Button yesButton = new Button("Yes");
//    Button noButton = new Button("No");

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ArrayList<Show> shows = new ArrayList<>();
        stage.setTitle("Agenda");

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 1000, 800);

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

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(50);
        buttonBox.getChildren().addAll(newButton, editButton, deleteButton);

        newButton.setOnAction(e -> new NewStage(shows));
        System.out.println(" GUI: " + shows);
        editButton.setOnAction(e -> new EditStage());
        deleteButton.setOnAction(e -> new DeleteStage());

        pane.setCenter(vBox);
        pane.setBottom(buttonBox);

        root.getChildren().addAll(vBox, buttonBox);
        stage.setScene(scene);
        stage.show();
    }
}
