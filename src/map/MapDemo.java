package map;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MapDemo extends Application {

    private Map map;
    private ResizableCanvas canvas;
    private Camera camera;
    private int mapMatrix[][];
    private boolean isWall = false;
    private int height, width;

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
        this.width = map.getWidth();
        this.height = map.getHeight();

    }


    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.setTransform(camera.getTransform(0, 0));
        map.draw(graphics, canvas);
        map.createNode(graphics, map.getTilelayers().get(3).getLayer());

        mapMatrix = map.cloneMatrix();

        Queue<Tile> open = new LinkedList<>();
        Queue<Tile> closed = new LinkedList<>();

        ((LinkedList<Tile>) open).add(0, new Tile(0, 0));
        int value = 0;
        StringBuilder test = new StringBuilder();
        while(!open.isEmpty()){
            Tile current = ((LinkedList<Tile>) open).getFirst();
            open.remove(current);

            for (int i = 0; i < mapMatrix.length; i++){
                for (int j = 0; j < mapMatrix[i].length; j++){
                    if(!closed.contains(new Tile(i, j)) && (mapMatrix[i][j] ==0)){
                        ((LinkedList<Tile>) open).add(new Tile(i, j));
                    }
                    //Get the gid value of the corresponding coördinate
                    int gid = mapMatrix[i][j];

                    //Check what gid is returned
                    if (gid == 0){
                        test.append(value);
                    } else if (gid == 975){
                        isWall = true;
                        test.append("x");
                    }

                    //For each cell, check the surrounding cells
                    if (mapMatrix[i][j] == mapMatrix[height-1][width-1] || mapMatrix[i][j] != 0){
                        //Look at top cell
                        if (i > 0 && mapMatrix[i-1][j] == (height - 1) && gid != 975){
                            value++;
                        }
                        //Look at left cell
                        if (j > 0 && mapMatrix[i][j-1] == (width - 1) && gid != 975){
                            value++;
                        }
                        //Look at right cell
                        if (j + 1 < mapMatrix[1].length && mapMatrix[i][j+1] == (width - 1) && gid != 975){
                            value++;
                        }
                        //Look at bottom cell
                        if (i + 1 < mapMatrix.length && mapMatrix[i+1][j] == (height - 1) && gid != 975){
                            value++;
                        }
                    }
                }
                test.append("\n");
            }
            System.out.println(test);
            closed.add(current);
        }


        /*StringBuilder s = new StringBuilder();
        System.out.println("MapDemo Matrix");
        for(int i = 0; i < mapMatrix.length; i++){
            for(int j = 0; j < mapMatrix[i].length; j++){

                int gid = mapMatrix[i][j];

                if(gid == 0) {
                    s.append("o");
                    value++;
                } else if(gid == 975){
                    s.append("x");
                    isWall = true;
                }
                System.out.println(i + ", "+ j + ": " + gid + " - " + value);
            }
            s.append("\n");
        }
        System.out.println(s);
        */
    }


    public void update(double deltaTime) {

    }


    public static void main(String[] args) {
        launch(MapDemo.class);
    }
}
