package GUI;

import data.Show;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;

public class GUI extends Application {

    private HBox buttonBox = new HBox();
    private TableView<TableColumn> tableView = new TableView<>();
    private ArrayList<Show> shows;
    private Stage stage;

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initialiseButtons();
        tableView();
        initialiseMainScene();
    }

    //Initialises the main scene
    public void initialiseMainScene(){
        this.shows = new ArrayList<>();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(tableView);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.CENTER);

        this.stage.setWidth(900);
        this.stage.setHeight(500);
        this.stage.setTitle("Festival Agenda");

        Scene scene = new Scene(root, 900, 500);
        root.getChildren().addAll(vBox, this.buttonBox);

        this.stage.setScene(scene);
        this.stage.show();
    }

    //Makes Buttons
    public void initialiseButtons() {
        Button newButton = new Button("New");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");

        this.buttonBox.setSpacing(50);
        this.buttonBox.getChildren().addAll(newButton, editButton, deleteButton);

        newButton.setOnAction(event -> {
            NewStage newStage = new NewStage(shows);
        });

        editButton.setOnAction(event ->{
            EditStage editStage = new EditStage();
        });

        deleteButton.setOnAction(event ->{
            DeleteStage deleteStage = new DeleteStage();
        });
    }

    //Makes the table on the main scene
    public void tableView() {
        TableColumn timeColumn = new TableColumn("Time:");
        TableColumn mainStageColumn = new TableColumn("Main stage:");
        TableColumn backStageColumn = new TableColumn("Back stage:");
        TableColumn sideStageColumn = new TableColumn("Side stage:");
        TableColumn smallStageColumn = new TableColumn("Small stage:");

        this.tableView.getColumns().addAll(timeColumn,
                mainStageColumn,
                backStageColumn,
                sideStageColumn,
                smallStageColumn);

        timeColumn.setPrefWidth(80);
        mainStageColumn.setPrefWidth(200);
        backStageColumn.setPrefWidth(200);
        sideStageColumn.setPrefWidth(200);
        smallStageColumn.setPrefWidth(200);



        this.tableView.setEditable(false);
    }
}
