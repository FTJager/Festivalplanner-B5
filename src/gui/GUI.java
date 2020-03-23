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
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;


public class GUI extends Application {

    Serializer serializer = new Serializer();
    Deserializer deserializer = new Deserializer();
    private Stage stage;
    private Canvas canvas;
    private NewStage newStage;
    private static final int BUTTON_ARC = 3;
    List<data.Stage> stageList;
    int stageX;


    public static void main(String[] args) {
        launch(GUI.class);
    }

    @Override
    public void start(Stage stage) {
        DataStore.setShowsA(deserializer.ReadArtist());
        DataStore.setStages(deserializer.ReadStages());
        stageList = new ArrayList<>();
        
        this.canvas = new Canvas(900, 710);
        tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));


        this.stage = stage;
        this.stage.setScene(new Scene(new Group(canvas)));
        this.stage.setResizable(true);
        this.stage.show();
        Buttoninteraction();

        //Displays the current shows and stages, may throw NullPointerException, so its in a try catch.
        try {
            System.out.println("Current saved shows: " + deserializer.ReadArtist().size());
            if(DataStore.getStages().isEmpty()) {
                System.out.println("Current saved stages: 0");
            }
            else System.out.println("Current saved stages: "+ deserializer.ReadStages().size());
            drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
        } catch (NullPointerException n) {
            n.printStackTrace();
        }
    }

    /**
     * This method initialises the whole table
     * @param graphics the java graphics.
     */
    public void tableDraw(FXGraphics2D graphics) {

        //Makes the top part of the table
        Rectangle2D topBlock = new Rectangle2D.Double(0, 0, canvas.getWidth(), 57);
        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
        graphics.fill(topBlock);

        //Draws the stages at the top
        drawStage(new FXGraphics2D(canvas.getGraphicsContext2D()));

        //Makes the small black line at the top of the table
        Rectangle2D topLine = new Rectangle2D.Double(0, 57, canvas.getWidth(), 3);
        graphics.setColor(Color.black);
        graphics.fill(topLine);

        //Lower bar of the GUI
        Rectangle2D bottomBlock = new Rectangle2D.Double(0, 560, canvas.getWidth(), canvas.getHeight() - 560);
        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
        graphics.fill(bottomBlock);
        graphics.draw(bottomBlock);

        //Makes the main table
        float beginX = 0;
        float beginY = 60;
        graphics.setColor(Color.white);
        for(int i = 0; i < 50; i++ ) {
            Rectangle2D tableBlock = new Rectangle2D.Double(0, beginY, canvas.getWidth(), 12);

            //Makes the table in 2 colors
            if(i % 2 == 0) {
                graphics.setColor(Color.getHSBColor(0.953f, 0.20f, 0.95f));
            }
            else {
                graphics.setColor(Color.white);
                graphics.fill(tableBlock);
            }
            graphics.fill(tableBlock);

            if(i % 2 == 0) {
                graphics.setColor(Color.black);
                Font textFont = new Font("Arial", Font.PLAIN, 12);
                graphics.setFont(textFont);
                if(i < 20) {
                    graphics.drawString("0"+  i / 2,10, beginY + 10);
                }
                else graphics.drawString(i / 2 + "",10, beginY + 10);
            }
            beginY += 12;
        }

        //Makes the text for the stages
        graphics.setColor(Color.white);
        Font GUIFont = new Font("Roboto", Font.BOLD, 20);
        graphics.setFont(GUIFont);
        graphics.drawString("Time", 15, 45);

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
            DataStore.setShowsA(deserializer.ReadArtist());
            DataStore.setStages(deserializer.ReadStages());

            graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

            tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));
            drawStage(new FXGraphics2D(canvas.getGraphicsContext2D()));
            drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
        });
    }

    /**
     * Makes the buttons intractable, remember to match the event box coordinates with the buttons coordinates if the buttons are ever moved
     */
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

    /**
     * draws the stage text at the top of the agenda module.
     * @param graphics the graphics of java
     */
    public void drawStage(FXGraphics2D graphics) {
        int X = 100;
        int Y = 45;
        Font GUIFont = new Font("Roboto", Font.BOLD, 20);
        graphics.setFont(GUIFont);
        graphics.setColor(Color.white);
        //gets all the stages and puts them into a list
       stageList = DataStore.getStages();
       //cycles through them and displays them correctly
        for(data.Stage stage : stageList) {
            graphics.drawString(stage.getName() + "", X, Y);
            X += 200;
        }
    }

    /**
     * draws the artist box
     * @param graphics the graphics of java
     */
    //Draws the box with the artist in the schedule.
    private void drawArtist(FXGraphics2D graphics) {
        stageX = 0;
        float beginTime;
        float endTime;

        //Determined the X with the stage, so the artist box lines up with the stages.
        drawStage(new FXGraphics2D(canvas.getGraphicsContext2D()));
        for (Show show : DataStore.getShowsA()) {
            beginTime = show.getStartTime() * 24 + 60;
            endTime = show.getEndTime() * 24 + 60;

            for(int i = 0; i < stageList.size(); i++) {
//                System.out.println(stageList.get(i).getName());
//                System.out.println(show.getStage());
                //puts the show under the correct stage
                if(stageList.get(i).getName().equalsIgnoreCase(show.getStage())) {
                    stageX = 100 + i * 200;
                    break;
                }
            }


            RoundRectangle2D artistRectangle = new RoundRectangle2D.Double(stageX, beginTime, 150, endTime - beginTime, 5, 5);

            graphics.setColor(Color.getHSBColor(0.953f, 0.90f, 0.95f));
            graphics.draw(artistRectangle);
            graphics.fill(artistRectangle);

            Font artistFont;
            graphics.setColor(Color.white);
            //if else statements for the Text in the artist box. Scales with the time of the show. Shorter show == smaller text.
            if (show.getEndTime() - show.getStartTime() <= 1) {
                artistFont = new Font("Arial", Font.BOLD, 10);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", stageX + 7, beginTime + 8);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", stageX + 7, beginTime + 20);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 32);
            } else if (show.getEndTime() - show.getStartTime() <= 2) {
                artistFont = new Font("Arial", Font.BOLD, 15);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", stageX + 7, beginTime + 19);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", stageX + 7, beginTime + 35);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 51);
            } else if (show.getEndTime() - show.getStartTime() <= 3) {
                artistFont = new Font("Arial", Font.BOLD, 18);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", stageX + 7, beginTime + 25);
                graphics.drawString("Time: " + show.getStartTime() + "h - " + show.getEndTime() + "h", stageX + 7, beginTime + 45);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 65);
            } else {
                artistFont = new Font("Arial", Font.BOLD, 20);
                graphics.setFont(artistFont);
                graphics.drawString(show.getShow() + "", stageX + 7, beginTime + 30);
                graphics.drawString("Time: \n" + show.getStartTime() + "h - " + show.getEndTime() + "h", stageX + 7, beginTime + 60);
//                graphics.drawString("Popularity: " + show.getPopularity(), x + 7, beginTime + 108);
            }
        }
    }
}

