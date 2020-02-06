package GUI;

import Data.Show;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GUI extends Application {

    private HBox buttonBox = new HBox();
    private TableView<TableColumn> tableView = new TableView<>();
    private ArrayList<Show> shows;
    private Stage stage;
    private Canvas canvas;
    private NewStage newStage;

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
//        initialiseButtons();
//        initialiseMainScene();
        this.canvas = new Canvas(800, 630);
        tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        this.stage.setScene(new Scene(new Group(canvas)));
        this.stage.show();
        Buttoninteraction();
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
        this.stage.setResizable(false);
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

    public void tableDraw(FXGraphics2D graphics) {
        GeneralPath topPath = new GeneralPath();
        topPath.moveTo(0,0);
        topPath.lineTo(canvas.getWidth(), 0);
        topPath.lineTo(canvas.getWidth(), 57);
        topPath.lineTo(0, 57);
        topPath.lineTo(0,0);
        graphics.setColor(Color.getHSBColor(0, 0, 0.5f));
        graphics.fill(topPath);

        GeneralPath topLine = new GeneralPath();
        topLine.moveTo(0, 57);
        topLine.lineTo(canvas.getWidth(), 57);
        topLine.lineTo(canvas.getWidth(), 60);
        topLine.lineTo(0, 65);
        topLine.lineTo(0, 60);
        graphics.setColor(Color.black);
        graphics.fill(topLine);

        float beginX = 0;
        float beginY = 60;

        for(int i = 0; i < 25; i++ ) {
            GeneralPath tablePath = new GeneralPath();
            tablePath.moveTo(beginX, beginY);
            tablePath.lineTo(canvas.getWidth(), beginY);
            tablePath.lineTo(canvas.getWidth(), beginY + 20);
            tablePath.lineTo(beginX, beginY + 20);
            if(i % 2 == 0) {
                graphics.setColor(Color.getHSBColor(0, 0, 0.78f));
                graphics.fill(tablePath);
            }
            else {
                graphics.setColor(Color.white);
                graphics.fill(tablePath);
            }
            graphics.setColor(Color.black);
            graphics.drawString(i + "", beginX + 10, beginY + 15);
            beginY += 20;
        }
        graphics.setColor(Color.black);
        RoundRectangle2D newButton = new RoundRectangle2D.Double(30, 570.0, 80, 30, 8, 8);
        RoundRectangle2D editButton = new RoundRectangle2D.Double(135, 570.0, 80, 30, 8, 8);
        RoundRectangle2D deleteButton = new RoundRectangle2D.Double(240, 570.0, 80, 30, 8, 8);
        Font buttonFont = new Font("Arial", Font.PLAIN, 25);
        graphics.setFont(buttonFont);
        graphics.drawString("new", 45, 590);
        graphics.drawString("edit", 150, 590);
        graphics.drawString("delete", 250, 590);
        graphics.draw(newButton);
        graphics.draw(editButton);
        graphics.draw(deleteButton);
    }

    public void Buttoninteraction(){
        canvas.setOnMouseClicked(event -> {
            if(event.getX() > 30 && event.getX() < 110 && event.getY() >570 && event.getY() < 600) {
                newStage = new NewStage(shows);
            }
            if(event.getX() > 135 && event.getX() < 215 && event.getY() > 570 && event.getY() < 600) {
                EditStage editStage = new EditStage();
            }
            if(event.getX() > 240 && event.getX() < 320 && event.getY() > 570 && event.getY() < 600) {
                DeleteStage deleteStage = new DeleteStage();
            }
        });
    }

//    //Makes the table on the main scene
//    public void tableView() {
//        TableColumn timeColumn = new TableColumn("Time:");
//        TableColumn mainStageColumn = new TableColumn("Main stage:");
//        TableColumn backStageColumn = new TableColumn("Back stage:");
//        TableColumn sideStageColumn = new TableColumn("Side stage:");
//        TableColumn smallStageColumn = new TableColumn("Small stage:");
//
//        timeColumn.setPrefWidth(80);
//        mainStageColumn.setPrefWidth(200);
//        backStageColumn.setPrefWidth(200);
//        sideStageColumn.setPrefWidth(200);
//        smallStageColumn.setPrefWidth(200);
//
//        this.tableView.getColumns().addAll(timeColumn,
//                mainStageColumn,
//                backStageColumn,
//                sideStageColumn,
//                smallStageColumn);
//        this.tableView.setEditable(false);
//
//    }


}
