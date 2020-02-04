package GUI;

import Data.Show;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOError;
import java.util.ArrayList;

public class GUI extends Application {

    private Button newButton;
    private Button editButton;
    private Button deleteButton;
    private Button doneButton;
    private Button yesButton;
    private Button noButton;

    private HBox buttonBox = new HBox();
    private TableView<TableColumn> tableView = new TableView<>();
    private Popup newAndEditPopup;
    private Popup delPopup;

    private ArrayList<Show> shows;
    private Stage stage;


    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        initialiseButtons();
        initialiseButtonActions();

        newAndEditPopup();
        delPopup();

        tableView();

        initialiseMainScene();
    }



    //Initialises the main scene
    public void initialiseMainScene(){
        this.shows = new ArrayList<>();
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.getChildren().addAll(tableView);
        BorderPane pane = new BorderPane();

        pane.setCenter(vBox);
        pane.setBottom(this.buttonBox);

        this.stage.setWidth(900);
        this.stage.setHeight(500);
        this.stage.setTitle("Festival Agenda");

        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().addAll(pane);
        this.stage.setScene(scene);
        this.stage.show();


    }

    //Making Buttons
    public void initialiseButtons() {
        this.newButton = new Button("New");
        this.editButton = new Button("Edit");
        this.deleteButton = new Button("Delete");
        this.doneButton = new Button("Done");
        this.yesButton = new Button("Yes");
        this.noButton = new Button("No");
    }

    //Making Button Actions
    public void initialiseButtonActions() {
        buttonBox.setSpacing(50);
        buttonBox.getChildren().addAll(newButton, editButton, deleteButton);

        newButton.setOnAction(event -> {
            if (!newAndEditPopup.isShowing() && !delPopup.isShowing()){
                newAndEditPopup.show(stage);
//                artists.add(artistField.getText());
//                stages.add(stageField.getText());
//                beginTimes.add(Integer.parseInt(beginTimeField.getText()));
//                endTimes.add(Integer.parseInt(endTimeField.getText()));
            }
        });

        editButton.setOnAction(event ->{
            if(!newAndEditPopup.isShowing() && !delPopup.isShowing()){
                newAndEditPopup.show(stage);
            }
        });

//        doneButton.setOnAction (event -> {
//            try {
//                if(newAndEditPopup.isShowing()) {
//                    for (Show show : shows){
//                        if (!shows.contains(show)){
//                            shows.add(new Show(artistField.getText(), Integer.parseInt(beginTimeField.getText()),
//                                    Integer.parseInt(endTimeField.getText()), Integer.parseInt(popularityField.getText()), Integer.parseInt(stageField.getText())));
//                        }
//                    }
//                    System.out.println(shows);
//                    this.newAndEditPopup.hide();
//                }
//            }
//
//
//            catch (IOError e) {
//                e.printStackTrace();
//            }
//            catch (NullPointerException e){
//                System.out.println("is null");
//            }
//        });

        deleteButton.setOnAction(event ->{
            if(!delPopup.isShowing() && !newAndEditPopup.isShowing()){
                delPopup.show(stage);
            }
        });

        noButton.setOnAction(event -> {
            if(delPopup.isShowing()){
                delPopup.hide();
            }
        });

        yesButton.setOnAction(event -> {
            if(delPopup.isShowing()){
                delPopup.hide();
            }
        });
}

    //Makes the table on the main scene
    public void tableView() {
        //TABLE
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

        this.tableView.getColumns().addAll(timeColumn,
                mainStageColumn,
                backStageColumn,
                sideStageColumn,
                smallStageColumn);
        this.tableView.setEditable(false);
    }

    //Makes the popup when you click on "New" or "Edit"
    public void newAndEditPopup() {
         Popup newAndEditPopup = new Popup();

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

        VBox labelBox = new VBox();

        labelBox.getChildren().addAll(artistLabel, popularityLabel, stageLabel, beginTimeLabel, endTimeLabel);
        labelBox.setSpacing(35);
        VBox fieldBox = new VBox();
        fieldBox.getChildren().addAll(artistField, popularityField, stageField, beginTimeField, endTimeField);
        fieldBox.setSpacing(20);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(labelBox, fieldBox);
        hBox.setSpacing(10);
        VBox popupVBox = new VBox();
        popupVBox.getChildren().addAll(hBox, doneButton);
        popupVBox.setSpacing(15);

        Pane popupPane = new Pane();
        popupPane.setStyle("-fx-background-color: #b8b8b8;");
        popupPane.getChildren().addAll(popupVBox);

        newAndEditPopup.getContent().addAll(popupPane);
        newAndEditPopup.setAutoHide(false);

        this.newAndEditPopup = newAndEditPopup;
    }

    //Makes the popup when the "Delete" button is clicked
    public void delPopup() {
        Popup delPopup = new Popup();
        Label delPopupLabel = new Label("Are you sure?");
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        VBox delBox = new VBox();
        HBox delBoxButton = new HBox();
        delBoxButton.getChildren().addAll(yesButton, noButton);
        delBoxButton.setSpacing(10);
        delBox.getChildren().addAll(delPopupLabel, delBoxButton);
        delBox.setSpacing(20);

        Pane delPopupPane = new Pane();
        delPopupPane.setStyle("-fx-background-color: #b8b8b8;");
        delPopupPane.getChildren().addAll(delBox);
        delPopup.getContent().addAll(delPopupPane);
        delPopup.setAutoHide(false);

        this.delPopup = delPopup;
    }
}
