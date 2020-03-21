package map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class MapMain extends Application {

    private Map map;
    private BreadthFirstSearch bfs;
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

    }


    public void init() {
        this.map = new Map("/festival.json");
        this.bfs = new BreadthFirstSearch();
        bfs.setSize(map.getWidth(), map.getHeight());

    }


    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.setTransform(camera.getTransform(0, 0));
        map.draw(graphics, canvas);
        map.createNode(graphics, map.getTilelayers().get(3).getLayer(), this.bfs);
        bfs.BFS(new Point2D.Double(0, 0));
        for (int y = 0; y < map.getHeight(); y++) {
            System.out.println("");
            for (int x = 0; x < map.getWidth(); x++) {
                if (bfs.getTileMap()[y][x].isWall()) {
                    graphics.setColor(Color.RED);
                    graphics.draw(new Rectangle2D.Double(bfs.getTileMap()[y][x].getRoute().get("route 1").getX(), bfs.getTileMap()[y][x].getRoute().get("route 1").getY(), 32, 32));
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1") == null) {
                    System.out.print("o ");
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1").getX() == 0 && bfs.getTileMap()[y][x].getRoute().get("route 1").getY() == 0) {
                    System.out.print("X ");
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1").getX() == 1) {
                    System.out.print("> ");
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1").getX() == -1) {
                    System.out.print("< ");
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1").getY() == 1) {
                    System.out.print("v ");
                } else if (bfs.getTileMap()[y][x].getRoute().get("route 1").getY() == -1) {
                    System.out.print("^ ");
                }
            }
        }
    }

    public void update(double deltaTime) {

    }


    public static void main(String[] args) {
        launch(MapMain.class);
    }
}
