package GUI;

import Data.Artist;
import Data.Show;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
//        ArrayList<String> artists = new ArrayList<>();
//        ArrayList<String> stages = new ArrayList<>();
//        ArrayList<Integer> beginTimes = new ArrayList<>();
//        ArrayList<Integer> endTimes = new ArrayList<>();
//        ArrayList<Integer> popularity = new ArrayList<>();

        ArrayList<Show> shows = new ArrayList<>();

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

        //NEW AND EDIT POPUP ASSETS
        Popup popup = new Popup();
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

        popup.getContent().addAll(popupPane);
        popup.setAutoHide(false);

        //DELETE POPUP ASSETS
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

        //BUTTON ACTIONS
        newButton.setOnAction(event -> {
            if (!popup.isShowing() && !delPopup.isShowing()){
                popup.show(stage);
//                artists.add(artistField.getText());
//                stages.add(stageField.getText());
//                beginTimes.add(Integer.parseInt(beginTimeField.getText()));
//                endTimes.add(Integer.parseInt(endTimeField.getText()));
            }

        });

        editButton.setOnAction(event ->{
            if(!popup.isShowing() && !delPopup.isShowing()){
                popup.show(stage);
            }
        });

        doneButton.setOnAction (event -> {
            try {
                if(popup.isShowing()) {
                    for (Show show : shows){
                        if (!shows.contains(show)){
                            shows.add(new Show(artistField.getText(), Integer.parseInt(beginTimeField.getText()),
                                    Integer.parseInt(endTimeField.getText()), Integer.parseInt(popularityField.getText()), Integer.parseInt(stageField.getText())));
                        }
                    }
                    System.out.println(shows);
                    popup.hide();
                }
            }
            catch (IOError e) {
                e.printStackTrace();
            }
            catch (NullPointerException e){
                System.out.println("is null");
            }
        });

        deleteButton.setOnAction(event ->{
            if(!delPopup.isShowing() && !popup.isShowing()){
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
}
