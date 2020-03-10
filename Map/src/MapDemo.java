import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;

public class MapDemo extends Application {

    private Map map;
    private ResizableCanvas canvas;
    private Camera camera;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        g2d.scale(0.4, 0.4);
        this.camera = new Camera(canvas, g -> draw(g), g2d);
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Festival");
        stage.show();
        draw(g2d);

        map.createNode(g2d, map.getTilelayers().get(3).getLayer());
    }


    public void init() {
        map = new Map("festival.json");
    }


    public void draw(Graphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.setTransform(camera.getTransform(0, 0));
        map.draw(graphics, canvas);
    }

    public void update(double deltaTime) {

    }


    public static void main(String[] args) {
        launch(MapDemo.class);
    }
}
