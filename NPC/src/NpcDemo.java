import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * This class determines the tasks for the NPCs. It can be used to create, delete and save NPCs, as well as change their destination.
 */
public class NpcDemo extends Application {

    private ResizableCanvas canvas;

    /**
     * This method has the general setup for JavaFX to make the NPCs show up in the GUI
     * @param stage Javafx stage
     * @throws Exception Javafx exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane, 1500, 800));
        stage.setTitle("Festival sim");
        stage.show();
        draw(g2d);


        //Set target to mouse position
//        canvas.setOnMouseMoved(e -> {
//            for(NPC character : people) {
//                character.setTarget(new Point2D.Double(e.getX(), e.getY()));
//            }
//        });
    }


    ArrayList<NPC> people;

    /**
     * In this method the NPCs get created to populate the map.
     */
    public void init() {
        this.people = new ArrayList<>();

        //Sprite selection
        BufferedImage imageArtist = null;
        BufferedImage imageHick = null;
        try {
            imageArtist = ImageIO.read(this.getClass().getResourceAsStream("/npc.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            imageHick = ImageIO.read(this.getClass().getResourceAsStream("/hick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create 5 Artists
        for(int i = 0; i < 5; i++) {
            this.people.add(new Artist(new Point2D.Double(Math.random()*1800 + 1920, Math.random()*1000 + 1080), imageArtist, new Point2D.Double(250,250),"name"));
        }

        //Create 15 Visitors
        for(int i = 0; i < 15; i++) {
            this.people.add(new Visitor(new Point2D.Double(Math.random()*1800 - 1920, Math.random()*1000 - 1080), imageHick, new Point2D.Double(1000,1000)));
        }

    }


    /**
     * This method draws the NPCs on the GUI
     * @param g2 The javaFX package used to draw
     */
    public void draw(FXGraphics2D g2) {
        g2.setTransform(new AffineTransform());
        g2.setBackground(new Color(100,75,75));
        g2.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());

        //This part prints out small black circles to display each NPC's current target location.
        //TODO disable this for the final version, only used for testing
        for(NPC person : people) {
            person.draw(g2);
            g2.drawOval((int)person.getTarget().getX(), (int)person.getTarget().getY(), 25, 25);
        }
    }

    public void update(double frameTime) {
        for(NPC person : people) {
            if (person.getPosition().distance(person.getTarget()) <= 100){
//                person.setTarget(new Point2D.Double(Math.random() * 1700, Math.random() * 800));
//                person.setTarget(new Point2D.Double(1500,100));
            }
            person.update(people);
        }
    }
}