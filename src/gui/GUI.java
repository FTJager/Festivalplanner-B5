package gui;


import data.DataStore;
import data.Deserializer;
import data.Serializer;
import data.Show;
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
        this.canvas = new Canvas(900, 630);
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

        for (int i = 0; i < 25; i++) {
            GeneralPath tablePath = new GeneralPath();
            tablePath.moveTo(beginX, beginY);
            tablePath.lineTo(canvas.getWidth(), beginY);
            tablePath.lineTo(canvas.getWidth(), beginY + 20);
            tablePath.lineTo(beginX, beginY + 20);
            //Makes the table in 2 colors
            if (i % 2 == 0) {
                graphics.setColor(Color.getHSBColor(0.953f, 0.20f, 0.95f));
                graphics.fill(tablePath);
            } else {
                graphics.setColor(Color.white);
                graphics.fill(tablePath);
            }
            graphics.setColor(Color.black);
            graphics.setFont(new Font("System", Font.PLAIN, 15));
            if (i < 10) {
                graphics.drawString("0" + i, beginX + 15, beginY + 15);
            } else graphics.drawString(i + "", beginX + 15, beginY + 15);
            beginY += 20;
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
        RoundRectangle2D newButton = new RoundRectangle2D.Double(30, canvas.getHeight() - 50, 80, 30, BUTTON_ARC, BUTTON_ARC);
        RoundRectangle2D editButton = new RoundRectangle2D.Double(135, canvas.getHeight() - 50, 80, 30, BUTTON_ARC, BUTTON_ARC);
        RoundRectangle2D deleteButton = new RoundRectangle2D.Double(240, canvas.getHeight() - 50, 80, 30, BUTTON_ARC, BUTTON_ARC);

        graphics.setColor(Color.getHSBColor(0.95f, 1, 0.65f));
        graphics.fill(newButton);
        graphics.fill(editButton);
        graphics.fill(deleteButton);


        graphics.setColor(Color.white);
        graphics.setFont(GUIFont);
        graphics.drawString("new", 51, (int) canvas.getHeight() - 30);
        graphics.drawString("edit", 158, (int) canvas.getHeight() - 30);
        graphics.drawString("delete", 252, (int) canvas.getHeight() - 30);

        //Updates the canvas so whenever the mouse reenters the main stage it draws all shows
        canvas.setOnMouseEntered(event -> {

            graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());

            tableDraw(new FXGraphics2D(canvas.getGraphicsContext2D()));

            drawArtist(new FXGraphics2D(canvas.getGraphicsContext2D()));
            if(DataStore.isStateS()){
                System.out.println("test");
            }
        });

    }

    //Makes the buttons intractable, remember to match the event box coordinates with the buttons coordinates if the buttons are ever moved.
    //TODO make the rest of the buttons privates, just in case
    public void Buttoninteraction() {
        canvas.setOnMouseClicked(event -> {



            //Event for "New" button
            if (event.getX() > 30 && event.getX() < 110 && event.getY() > 570 && event.getY() < 600) {
                newStage = new NewStage();
            }
            //Event for "Edit" button
            if (event.getX() > 135 && event.getX() < 215 && event.getY() > 570 && event.getY() < 600) {
                EditStage editStage = new EditStage();
            }
            //Event for "Delete" button.
            if (event.getX() > 240 && event.getX() < 320 && event.getY() > 570 && event.getY() < 600) {
                DeleteStage deleteStage = new DeleteStage();
            }
        });
    }

    //* 47 * 49
    private void drawArtist(FXGraphics2D graphics) {
        int stage = 0;
        int x = 0;
        int beginTime = 0;
        int endTime = 0;

        for (Show show : DataStore.getShowsA()) {
            stage = show.getStage();
            beginTime = show.getStartTime() * 3;
            endTime = show.getEndTime() * 3;

            beginTime = beginTime * 7 + 60;
            endTime = endTime * 7 + 70;
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

            GeneralPath artistField = new GeneralPath();
            artistField.moveTo(x, beginTime);
            artistField.lineTo(x + 150, beginTime);
            artistField.lineTo(x + 150, endTime);
            artistField.lineTo(x, endTime);
            artistField.lineTo(x, beginTime);

            graphics.setColor(Color.MAGENTA);
            graphics.draw(artistField);
            graphics.fill(artistField);

            graphics.setColor(Color.YELLOW);
            graphics.drawString("Artist: ", x + 7, beginTime + 25);
            graphics.drawString("Time" + beginTime + " " + endTime, x + 7, beginTime + 50);

        }
    }
}

