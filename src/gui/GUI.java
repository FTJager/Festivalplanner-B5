/**
 * This is the main class for the GUi application
 * When run, the GUI for the festival agenda opens and the user can add, edti, and delete shows.
 */

package gui;

import data.DataStore;
import data.*;
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

    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();
    private ArrayList<Show> shows1;
    private Stage stage;

    private Canvas canvas;
    private NewStage newStage;
    private static final int BUTTON_ARC = 3;


    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DataStore.setShowsA(deserializer.Read());
        this.canvas = new Canvas(900, 710);
        tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));

        this.stage = stage;
        this.stage.setScene(new Scene(new Group(canvas)));
        this.stage.setResizable(true);
        this.stage.show();
        Buttoninteraction();

        try {
            System.out.println("Current saved shows: " + deserializer.Read().size());
            drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
            if(DataStore.isStateS()){
                drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
            }
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

    //Initialises the whole table
    public void tableDraw(FXGraphics2D graphics) {
        //Makes the top part of the table
        GeneralPath topPath = new GeneralPath();
        topPath.moveTo(0, 0);
        topPath.lineTo(canvas.getWidth(), 0);
        topPath.lineTo(canvas.getWidth(), 57);
        topPath.lineTo(0, 57);
        topPath.lineTo(0, 0);
        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
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

        //Lower bar of the GUI
        GeneralPath bottomPath = new GeneralPath();
        bottomPath.moveTo(0, 560);
        bottomPath.lineTo(canvas.getWidth(), 560);
        bottomPath.lineTo(canvas.getWidth(), canvas.getHeight());
        bottomPath.lineTo(0, canvas.getHeight());
        bottomPath.lineTo(0, 560);
        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
        graphics.fill(bottomPath);
        graphics.draw(bottomPath);

        //Makes the main table
        float beginX = 0;
        float beginY = 60;
        graphics.setColor(Color.white);
        for(int i = 0; i < 50; i++ ) {

            GeneralPath tablePath = new GeneralPath();
            tablePath.moveTo(beginX, beginY);
            tablePath.lineTo(canvas.getWidth(), beginY);
            tablePath.lineTo(canvas.getWidth(), beginY + 12);
            tablePath.lineTo(beginX, beginY + 12);
            //Makes the table in 2 colors
            if(i % 2 == 0) {
                graphics.setColor(Color.getHSBColor(0.953f, 0.20f, 0.95f));
            }
            else {
                graphics.setColor(Color.white);
                graphics.fill(tablePath);
            }
            graphics.fill(tablePath);

            if(i % 2 == 0) {
                graphics.setColor(Color.black);
                Font textFont = new Font("Arial", Font.PLAIN, 12);
                graphics.setFont(textFont);
                if(i < 20) {
                    graphics.drawString("0"+  i / 2, beginX + 10, beginY + 10);
                }
                else graphics.drawString(i / 2 + "", beginX + 10, beginY + 10);
            }
            beginY += 12;
        }

        //Makes the text for the stages
        graphics.setColor(Color.white);
        Font GUIFont = new Font("Roboto", Font.BOLD, 20);
        graphics.setFont(GUIFont);
        graphics.drawString("Time", 15, 45);
        graphics.drawString("Main Stage", 100, 45);
        graphics.drawString("Side Stage", 300, 45);
        graphics.drawString("Back Stage", 500, 45);
        graphics.drawString("Small Stage", 700, 45);

        //Makes the buttons "New", "Edit" and "Delete"
        RoundRectangle2D newButton = new RoundRectangle2D.Double(30, canvas.getHeight() - 40, 80, 30, BUTTON_ARC, BUTTON_ARC);
        RoundRectangle2D editButton = new RoundRectangle2D.Double(135, canvas.getHeight() - 40, 80, 30, BUTTON_ARC, BUTTON_ARC);
        RoundRectangle2D deleteButton = new RoundRectangle2D.Double(240, canvas.getHeight() - 40, 80, 30, BUTTON_ARC, BUTTON_ARC);
        RoundRectangle2D stageButton = new RoundRectangle2D.Double(345, canvas.getHeight() - 40, 80, 30, BUTTON_ARC, BUTTON_ARC);

        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
        graphics.fill(newButton);
        graphics.fill(editButton);
        graphics.fill(deleteButton);
        graphics.fill(stageButton);

        graphics.setColor(Color.white);
        graphics.setFont(GUIFont);
        graphics.drawString("new", 51, (int) canvas.getHeight() - 20);
        graphics.drawString("edit", 158, (int) canvas.getHeight() - 20);
        graphics.drawString("delete", 252, (int) canvas.getHeight() - 20);
        graphics.drawString("stages", 350, (int)canvas.getHeight() - 20);

        //Updates the canvas so whenever the mouse reenters the main stage it draws all shows
        canvas.setOnMouseEntered(event -> {

            graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

            tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));

            drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });
    }

    //Makes the buttons intractable, remember to match the event box coordinates with the buttons
    // coordinates if the buttons are ever moved.commit
    public void Buttoninteraction() {
        canvas.setOnMouseClicked(event -> {
            //Event for "New" button
            if (event.getX() > 30 && event.getX() < 110 && event.getY() > canvas.getHeight() - 40 && event.getY() < canvas.getHeight() - 10) {
                newStage = new NewStage();
            }
            //Event for "Edit" button
            if (event.getX() > 135 && event.getX() < 215 && event.getY() > canvas.getHeight() - 40 && event.getY() < canvas.getHeight() - 10) {
                EditStage editStage = new EditStage();
            }
            //Event for "Delete" button.
            if (event.getX() > 240 && event.getX() < 320 && event.getY() > canvas.getHeight() - 40 && event.getY() < canvas.getHeight() - 10) {
                DeleteStage deleteStage = new DeleteStage();
            }
            if (event.getX() > 345 && event.getX() < 425 && event.getY() > canvas.getHeight() - 40 && event.getY() < canvas.getHeight() - 10) {
                AddStage addStage = new AddStage();
            }
        });
    }

    //Draws the box with the artist in the schedule.
    private void drawArtist(FXGraphics2D graphics) {
        int stage = 0;
        int x = 0;
        float beginTime = 0;
        float endTime = 0;

        //Determined the X with the stage, so the artist box lines up with the stages.
        for (Show show : DataStore.getShowsA()) {
            stage = show.getStage();
            beginTime = show.getStartTime() * 24 + 60;
            endTime = show.getEndTime() * 24 + 60;
            switch (stage) {
                case 1:
                    x = 100;
                    break;
                case 2:
                    x = 300;
                    break;
                case 3:
                    x = 500;
                    break;
                case 4:
                    x = 700;
                    break;
            }

            RoundRectangle2D artistRectangle = new RoundRectangle2D.Double(x, beginTime, 150, endTime - beginTime, 5, 5);

            graphics.setColor(Color.getHSBColor(0.953f, 0.90f, 0.95f));
            graphics.draw(artistRectangle);
            graphics.fill(artistRectangle);

            Font artistFont;
            graphics.setColor(Color.white);
            //if else statements for the Text in the artist box. Scales with the time of the show. Shorter show == smaller text.
            if (show.getEndTime() - show.getStartTime() <= 1) {
                artistFont = new Font("Arial", Font.BOLD, 10);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", x + 7, beginTime + 8);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", x + 7, beginTime + 20);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 32);
            } else if (show.getEndTime() - show.getStartTime() <= 2) {
                artistFont = new Font("Arial", Font.BOLD, 15);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", x + 7, beginTime + 19);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", x + 7, beginTime + 35);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 51);
            } else if (show.getEndTime() - show.getStartTime() <= 3) {
                artistFont = new Font("Arial", Font.BOLD, 18);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", x + 7, beginTime + 25);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", x + 7, beginTime + 45);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 65);
            } else {
                artistFont = new Font("Arial", Font.BOLD, 20);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", x + 7, beginTime + 30);
                graphics.drawString("Time: \n" + show.getStartTime() + "h - " + show.getEndTime() + "h", x + 7, beginTime + 60);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 108);
            }
        }
    }
}

