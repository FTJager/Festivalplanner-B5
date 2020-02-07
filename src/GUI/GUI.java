package GUI;

import Data.Show;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class GUI extends Application {

    private ArrayList<Show> shows;
    private Stage stage;
    private Canvas canvas;
    private NewStage newStage;

    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.canvas = new Canvas(800, 630);
        tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));

        this.stage = stage;
        this.stage.setScene(new Scene(new Group(canvas)));
        this.stage.show();
        Buttoninteraction();
    }


    //Initialises the whole table
    public void tableDraw(FXGraphics2D graphics) {

        //Makes the top part of the table
        GeneralPath topPath = new GeneralPath();
        topPath.moveTo(0,0);
        topPath.lineTo(canvas.getWidth(), 0);
        topPath.lineTo(canvas.getWidth(), 57);
        topPath.lineTo(0, 57);
        topPath.lineTo(0,0);
        graphics.setColor(Color.getHSBColor(0, 0, 0.5f));
        graphics.fill(topPath);

        //Makes the small black line at the top of the table
        GeneralPath topLine = new GeneralPath();
        topLine.moveTo(0, 57);
        topLine.lineTo(canvas.getWidth(), 57);
        topLine.lineTo(canvas.getWidth(), 60);
        topLine.lineTo(0, 65);
        topLine.lineTo(0, 60);
        graphics.setColor(Color.black);
        graphics.fill(topLine);

        //Makes the main table
        float beginX = 0;
        float beginY = 60;

        for(int i = 0; i < 25; i++ ) {
            GeneralPath tablePath = new GeneralPath();
            tablePath.moveTo(beginX, beginY);
            tablePath.lineTo(canvas.getWidth(), beginY);
            tablePath.lineTo(canvas.getWidth(), beginY + 20);
            tablePath.lineTo(beginX, beginY + 20);
            //Makes the table in 2 colors
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

        //Makes the buttons "New", "Edit" and "Delete"
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


    //Makes the buttons intractable, remember to match the event box coordinates with the buttons coordinates if the buttons are ever moved.
    public void Buttoninteraction(){
        canvas.setOnMouseClicked(event -> {
            //Event for "New" button
            if(event.getX() > 30 && event.getX() < 110 && event.getY() >570 && event.getY() < 600) {
                newStage = new NewStage(shows);
            }
            //Event for "Edit" button
            if(event.getX() > 135 && event.getX() < 215 && event.getY() > 570 && event.getY() < 600) {
                EditStage editStage = new EditStage();
            }
            //Event for "Delete" button.
            if(event.getX() > 240 && event.getX() < 320 && event.getY() > 570 && event.getY() < 600) {
                DeleteStage deleteStage = new DeleteStage();
            }
        });
    }
}
