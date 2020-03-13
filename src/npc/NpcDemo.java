package npc;

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

public class NpcDemo extends Application {

    private ResizableCanvas canvas;

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


        //Target selection
//        canvas.setOnMouseMoved(e -> {
//            for(NPC character : people) {
//                character.setTarget(new Point2D.Double(e.getX(), e.getY()));
//            }
//        });
    }


    ArrayList<NPC> people;


    public void init() {
        this.people = new ArrayList<>();

        //Sprite selection
        BufferedImage imageArtist = null;
        BufferedImage imageHick = null;
        try {
            imageArtist = ImageIO.read(this.getClass().getResourceAsStream("/npc.png"));
            imageHick = ImageIO.read(this.getClass().getResourceAsStream("/hick.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 5; i++) {
            this.people.add(new Artist(new Point2D.Double(Math.random()*1800, Math.random()*1000), imageArtist, new Point2D.Double(250,250),"name"));
        }

        for(int i = 0; i < 15; i++) {
            this.people.add(new Visitor(new Point2D.Double(Math.random()*1800, Math.random()*1000), imageHick, new Point2D.Double(1000,1000)));
        }

    }


    public void draw(FXGraphics2D g2) {
        g2.setTransform(new AffineTransform());
        g2.setBackground(new Color(100,75,75));
        g2.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());


        for(NPC person : people) {
            person.draw(g2);
        }



    }

    public void update(double frameTime) {
        for(NPC person : people) {
            if (person.getPosition().distance(person.getTarget()) <= 100){
                person.setTarget(new Point2D.Double(1500,100));
            }
            person.update(people);
        }
    }
}