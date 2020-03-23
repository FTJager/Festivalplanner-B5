package festival.map;

import festival.npc.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapMain extends Application {

    private Map map;
    private Point2D sideStageView;
    private Point2D mainStageView;
    private Point2D toiletVisitor;
    private Point2D bsStageVisitor;
    private String route1;
    private String route2;
    private String route3;
    private String route4;
    private BreadthFirstSearch bfs;
    private ResizableCanvas canvas;
    private Camera camera;
    private BufferedImage imageArtist;
    private BufferedImage imageHick;
    ArrayList<NPC> people;
    ArrayList<Visitor> visitors = new ArrayList<>();
    ArrayList<Artist> artists = new ArrayList<>();

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

    /**
     * In the init all the route names and positions get set to be used in the draw method.
     * The festival.map_old gets initialized to read the json file, and it reads the collision layer to be used in
     * the createdNode method in the Map class.
     */
    public void init() {
        this.map = new Map("/festival.json");
        this.bfs = new BreadthFirstSearch();
        bfs.setSize(map.getWidth(), map.getHeight());
        this.route1 = "ss view area";
        this.route2 = "ms view area";
        this.route3 = "Toilet";
        this.route4 = "bs view area";
        this.sideStageView = this.map.objectTargets(route1);
        this.mainStageView = this.map.objectTargets(route2);
        this.toiletVisitor = this.map.objectTargets(route3);
        this.bsStageVisitor = this.map.objectTargets(route4);

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

        //Create 5 artists
        for(int i = 0; i < 5; i++) {
            Point2D spawnPoint = new Point2D.Double(Math.random()*320 + (90 * 32), Math.random()*32 + 78 * 32);
            Artist artist = new Artist(spawnPoint, imageArtist, toiletVisitor,"name");
            boolean spaceTaken = true;
            if (people.isEmpty()){
                this.people.add(artist);
                this.artists.add(artist);
            } else if (!people.isEmpty()){
                for (NPC npc : people){
                    if (npc.getPosition().distance(spawnPoint) > NPC.SPRITESIZE){
                        spaceTaken = false;
                    }
                }
                if (!spaceTaken){
                    this.people.add(artist);
                    this.artists.add(artist);
                }
            }
        }

        //Create 15 Visitors
//        for(int i = 0; i < 15; i++) {
//            Visitor visitor = new Visitor(new Point2D.Double(Math.random()*320 + (90 * 32), Math.random()*32 + 78 * 32), imageHick, toiletVisitor);
//            this.people.add(visitor);
//            this.visitors.add(visitor);
//        }


    }

    /**
     * This method draws the festival.map_old and the elements of the pathfinding(optional).
     * The pathfinding needs a starting position to start the algorithm,
     * which is created when a new bfs object has been intialized bfs.BFS(new Point2D.Double(this.sideStageView.getX()/32, this.sideStageView.getY()/32), this.route1);.
     * @param graphics is there to draw the festival.map_old and the pathfinding algortihm elements
     */
    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.setTransform(camera.getTransform(0, 0));
        graphics.clearRect(0,0,3000,3000);
        map.draw(graphics, canvas);
        map.createGrid(map.getTilelayers().get(3).getLayer(), this.bfs);
        bfs.BFS(new Point2D.Double(this.sideStageView.getX()/32, this.sideStageView.getY()/32), this.route1);
        bfs.BFS(new Point2D.Double(this.mainStageView.getX()/32, this.mainStageView.getY()/32), this.route2);
        bfs.BFS(new Point2D.Double(this.toiletVisitor.getX()/32, this.toiletVisitor.getY()/32), this.route3);
        bfs.BFS(new Point2D.Double(this.bsStageVisitor.getX()/32, this.bsStageVisitor.getY()/32), this.route4);

        //This part prints out small black circles to display each NPC's current target location.
        //TODO disable this for the final version, only used for testing
        if (!this.people.isEmpty()){
            for(NPC person : people) {
                person.draw(graphics, this.camera);
//            graphics.drawOval((int)person.getTarget().getX(), (int)person.getTarget().getY(), 25, 25);
            }
        }
//        for (int y = 0; y < festival.map_old.getHeight(); y++) {
//            System.out.println("");
//            for (int x = 0; x < festival.map_old.getWidth(); x++) {
//                if (bfs.getTileMap()[y][x].isWall()) {
//                    System.out.print("W ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1) == null) {
//                    System.out.print("o ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1).getX() == 0 && bfs.getTileMap()[y][x].getRoute().get(this.route1).getY() == 0) {
//                    System.out.print("X ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1).getX() == 1) {
//                    System.out.print("> ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1).getX() == -1) {
//                    System.out.print("< ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1).getY() == 1) {
//                    System.out.print("v ");
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route1).getY() == -1) {
//                    System.out.print("^ ");
//                }
//            }
//        }
//
//        for (int y = 0; y < festival.map_old.getHeight(); y++) {
//            System.out.println("");
//            for (int x = 0; x < festival.map_old.getWidth(); x++) {
//                if (bfs.getTileMap()[y][x].isWall()) {
//                    graphics.setColor(Color.RED);
//                    graphics.fill(new Rectangle2D.Double( x * 32.0, y * 32.0, 32, 32));
//                    graphics.setColor(Color.BLUE);
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2) == null) {
//                    graphics.setColor(Color.BLACK);
//                    graphics.fill(new Rectangle2D.Double( x * 32.0, y * 32.0, 32, 32));
//
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2).getX() == 0 && bfs.getTileMap()[y][x].getRoute().get(this.route2).getY() == 0) {
//
//                    graphics.drawString("X", x * 32, y * 32);
//
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2).getX() == 1) {
//                    graphics.drawString(">", x * 32, y * 32);
//
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2).getX() == -1) {
//                    graphics.drawString("<", x * 32, y * 32);
//
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2).getY() == 1) {
//                    graphics.drawString("v", x * 32, y * 32);
//
//                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route2).getY() == -1) {
//                    graphics.drawString("^", x * 32, y * 32);
//
//                }
//            }
//        }


    }

    /**
     * Should update every position and such, other than that it does nothing of use.
     * @param deltaTime
     */
    public void update(double deltaTime) {
        for(NPC person : people) {
//            if (person.getPosition().distance(person.getTarget()) <= 10){
//                person.setTarget(this.toiletVisitor);
//                person.setTarget(new Point2D.Double(Math.random() * 1700, Math.random() * 800));
//                person.setTarget(new Point2D.Double(1500,100));
//            }
            person.update(people);
        }

        /**
         * Prints out the pathfinding algorithm to the desired target
         * The two for-loops are to check the grid at that position
         */
        for (int y = 0; y < map.getHeight(); y++) {
            System.out.println("");
            for (int x = 0; x < map.getWidth(); x++) {
                if (bfs.getTileMap()[y][x].isWall()) {
//                    graphics.setColor(Color.RED);
//                    graphics.fill(new Rectangle2D.Double( x * 32.0, y * 32.0, 32, 32));
//                    graphics.setColor(Color.BLUE);
                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3) == null) {
//                    graphics.setColor(Color.BLACK);
//                    graphics.fill(new Rectangle2D.Double( x * 32.0, y * 32.0, 32, 32));

                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3).getX() == 0 && bfs.getTileMap()[y][x].getRoute().get(this.route3).getY() == 0) {
//                    graphics.drawString("X", x * 32, y * 32);

                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3).getX() == 1) {
//                    graphics.drawString(">", x * 32, y * 32);

                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3).getX() == -1) {
//                    graphics.drawString("<", x * 32, y * 32);

                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3).getY() == 1) {
//                    graphics.drawString("v", x * 32, y * 32);

                } else if (bfs.getTileMap()[y][x].getRoute().get(this.route3).getY() == -1) {
//                    graphics.drawString("^", x * 32, y * 32);

                }
            }
        }


    }

    /**
     * Launches the festival.map_old application
     */
    public static void main(String[] args) {
        launch(MapMain.class);
    }
}
