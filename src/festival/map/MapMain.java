package festival.map;

import agenda.data.DataStore;
import agenda.data.Deserializer;
import agenda.data.Serializer;
import agenda.data.Show;
import festival.npc.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MapMain extends Application {
    private int hours;
    private double minutes;
    Deserializer deserializer = new Deserializer();
    Serializer serializer = new Serializer();

    private Clock clock;
    private double smallDegree;
    private double bigDegree;
    private int timeSpeed;
    private javafx.scene.control.Label timeSpeedLabel;

    private Map map;
    private Point2D sideStageView;
    private Point2D mainStageView;
    private Point2D toiletVisitor;
    private Point2D bsStageVisitor;
    private Point2D smallStage;
    private Point2D backStageArtist;
    private Point2D breakRoom;
    private Point2D mainStageArtist;
    private Point2D foodArea;
    private Point2D sideStageArtist;
    private String route1;
    private String route2;
    private String route3;
    private String route4;
    private String route5;
    private String route6;
    private String route7;
    private String route8;
    private String route9;
    private String route10;
    private BreadthFirstSearch bfs;
    private ResizableCanvas canvas;
    private Camera camera;
    private BufferedImage imageArtist;
    private BufferedImage imageHick;
    ArrayList<NPC> people;
    ArrayList<Visitor> visitors = new ArrayList<>();
    ArrayList<Artist> artists = new ArrayList<>();

    private ScrollBar scrollBar;

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

        HBox hbox = new HBox();
        hbox.setSpacing(5);

        this.timeSpeedLabel = new javafx.scene.control.Label("Time speed " + this.timeSpeed);
        javafx.scene.control.Button timeSpeedUp = new javafx.scene.control.Button("+");
        javafx.scene.control.Button timeSpeedDown = new javafx.scene.control.Button("-");

        timeSpeedUp.setOnAction( e -> {
            if(this.timeSpeed == 1){
                this.timeSpeed++;
            } else if(this.timeSpeed <= 8){
                this.timeSpeed += 2;
            }
        });

        timeSpeedDown.setOnAction( e -> {
            if(this.timeSpeed == 2){
                this.timeSpeed--;
            } else if(this.timeSpeed >= 4){
                this.timeSpeed -= 2;
            }
        });


        //makes a scrollbar to control the time
        this.scrollBar = new ScrollBar();
        this.scrollBar.setMin(0);
        this.scrollBar.setMax(1440);
        this.scrollBar.setMinHeight(25);
        this.scrollBar.setMinWidth(500);
        this.scrollBar.setMaxWidth(500);

        hbox.getChildren().addAll(this.scrollBar, this.timeSpeedLabel, timeSpeedUp, timeSpeedDown);
        mainPane.setTop(hbox);


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
        this.clock = new Clock();
        this.timeSpeed = 1;
        this.map = new Map("/festival.json");
        this.bfs = new BreadthFirstSearch();
        bfs.setSize(map.getWidth(), map.getHeight());
        this.route1 = "ss view area";
        this.route2 = "ms view area";
        this.route3 = "Toilet";
        this.route4 = "bs view area";
        this.route5 = "small stage";
        this.route6 = "Back stage";
        this.route7 = "break room";
        this.route8 = "Main stage";
        this.route9 = "Side stage";
        this.route10 = "food area";
        this.sideStageView = this.map.objectTargets(this.route1);
        this.mainStageView = this.map.objectTargets(this.route2);
        this.toiletVisitor = this.map.objectTargets(this.route3);
        this.bsStageVisitor = this.map.objectTargets(this.route4);
        this.smallStage = this.map.objectTargets(this.route5);
        this.backStageArtist = this.map.objectTargets(this.route6);
        this.breakRoom = this.map.objectTargets(this.route7);
        this.mainStageArtist = this.map.objectTargets(this.route8);
        this.sideStageArtist = this.map.objectTargets(this.route9);
        this.foodArea = this.map.objectTargets(this.route10);
        this.people = new ArrayList<>();
        DataStore.setShowsA(this.deserializer.Read(Serializer.SHOWS));
        DataStore.setArtistsS(this.deserializer.Read(Serializer.ARTISTS));
        System.out.println(DataStore.getShowsA().get(0).getArtistA().get(0).getName());

        //Sprite selection
        BufferedImage imageArtist = null;
        BufferedImage imageVisitor = null;
        try {
            imageArtist = ImageIO.read(this.getClass().getResourceAsStream("/artist.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            imageVisitor = ImageIO.read(this.getClass().getResourceAsStream("/Visitor.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create a number of artists
        double spawnX = NPC.SPRITESIZE;
        double spawnY = NPC.SPRITESIZE;
        for (agenda.data.Artist artist : DataStore.getArtistsS()) {
            Point2D spawnPoint = new Point2D.Double(Math.random() * 320 + (10 * 32), Math.random() * 320 + 10 * 32);
            Artist artistNpc = new Artist(spawnPoint, imageArtist, this.breakRoom, artist.getName());
            boolean spaceTaken = false;
            if (this.artists.isEmpty()) {
                artistNpc.setRoute(this.route7);
                this.people.add(artistNpc);
                this.artists.add(artistNpc);
            } else if (!this.people.isEmpty()) {
                for (NPC npc : this.people) {
                    if (npc.getPosition().distance(spawnPoint) <= NPC.SPRITESIZE) {
                        spaceTaken = true;
                    }
                }
                if (!spaceTaken) {
                    artistNpc.setRoute(this.route7);
                    this.people.add(artistNpc);
                    this.artists.add(artistNpc);
                    //TODO Tweak the spawning of the NPC in general
                    //Spawning feature, unfinished
//                    if (spawnX < (NPC.SPRITESIZE + NPC.SPRITESIZE / 2) * 12){
//                        spawnX += (NPC.SPRITESIZE  + NPC.SPRITESIZE / 2);
//                    } else {
//                        spawnX = NPC.SPRITESIZE;
//                        spawnY += NPC.SPRITESIZE +  + NPC.SPRITESIZE / 2;
//                    }

                }
            }

        }

        //Create a number of Visitors
        while (this.visitors.size() < 20) {
            Point2D spawnPoint = new Point2D.Double(Math.random() * 320 + (50 * 32), Math.random() * 320 + 40 * 32);
//            Point2D spawnPoint = new Point2D.Double(spawnX + (90 * 32), spawnY + (90 * 32));
            Visitor visitor = new Visitor(spawnPoint, imageVisitor, this.toiletVisitor);
            boolean spaceTaken = false;
            if (this.visitors.isEmpty()) {
                visitor.setRoute(this.route3);
                this.people.add(visitor);
                this.visitors.add(visitor);
            } else if (!this.people.isEmpty()) {
                for (NPC npc : this.people) {
                    if (npc.getPosition().distance(spawnPoint) <= NPC.SPRITESIZE) {
                        spaceTaken = true;
                    }
                }
                if (!spaceTaken) {
                    visitor.setRoute(this.route3);
                    this.people.add(visitor);
                    this.visitors.add(visitor);
                }
            }
        }
    }

    /**
     * This method draws the festival.map_old and the elements of the pathfinding(optional).
     * The pathfinding needs a starting position to start the algorithm,
     * which is created when a new bfs object has been intialized bfs.BFS(new Point2D.Double(this.sideStageView.getX()/32, this.sideStageView.getY()/32), this.route1);.
     *
     * @param graphics is there to draw the festival.map_old and the pathfinding algortihm elements
     */
    public void draw(FXGraphics2D graphics) {
        graphics.setBackground(Color.black);
        graphics.setTransform(this.camera.getTransform(0, 0));
        graphics.clearRect(0, 0, 3000, 3000);
        this.map.draw(graphics, this.canvas);



        this.clock.draw(graphics, this.bigDegree, this.smallDegree);



        this.map.createGrid(this.map.getTilelayers().get(3).getLayer(), this.bfs);
        //TODO make it so that the BFS gets called once, for example a for loop that gets the route name every iteration
        this.bfs.BFS(new Point2D.Double(this.sideStageView.getX() / 32 + 1, this.sideStageView.getY() / 32 + 1), this.route1);
        this.bfs.BFS(new Point2D.Double(this.mainStageView.getX() / 32 + 1, this.mainStageView.getY() / 32 + 1), this.route2);
        this.bfs.BFS(new Point2D.Double(this.toiletVisitor.getX() / 32 + 1, this.toiletVisitor.getY() / 32 + 1), this.route3);
        this.bfs.BFS(new Point2D.Double(this.bsStageVisitor.getX() / 32 + 1, this.bsStageVisitor.getY() / 32 + 1), this.route4);
        this.bfs.BFS(new Point2D.Double(this.smallStage.getX() / 32 + 1, this.smallStage.getY() / 32 + 1), this.route5);
        this.bfs.BFS(new Point2D.Double(this.backStageArtist.getX() / 32 + 1, this.backStageArtist.getY() / 32 + 1), this.route6);
        this.bfs.BFS(new Point2D.Double(this.breakRoom.getX() / 32 + 1, this.breakRoom.getY() / 32 + 1), this.route7);
        this.bfs.BFS(new Point2D.Double(this.mainStageArtist.getX() / 32 + 1, this.mainStageArtist.getY() / 32 + 1), this.route8);
        this.bfs.BFS(new Point2D.Double(this.sideStageArtist.getX() / 32 + 1, this.sideStageArtist.getY() / 32 + 1), this.route9);
        this.bfs.BFS(new Point2D.Double(this.foodArea.getX() / 32 + 1, this.foodArea.getY() / 32 + 1), this.route10);

        //This part prints out small black circles to display each NPC's current target location.
        //TODO disable this for the final version, only used for testing
        if (!this.people.isEmpty()) {
            for (NPC person : this.people) {
                person.draw(graphics, this.camera);
                graphics.setColor(Color.BLUE);
                graphics.drawOval((int) person.getTarget().getX(), (int) person.getTarget().getY(), 25, 25);
                graphics.setColor(Color.PINK);
                graphics.drawOval((int) person.getEndPoint().getX(), (int) person.getEndPoint().getY(), 25, 25);
            }
        }
        //Prints a clock on the break room
        graphics.setFont(new Font("Arial", Font.BOLD, 50));
        graphics.setColor(Color.black);
        if(this.hours < 10 && this.minutes >= 10){
            graphics.drawString("0" + this.hours + ":" + (int) this.minutes, 110, 240);
        } else if (this.hours < 10 && this.minutes < 10){
            graphics.drawString("0" + this.hours + ":" + "0" + (int) this.minutes, 110, 240);
        } else if(this.hours >= 10 && this.minutes < 10){
            graphics.drawString(this.hours + ":" + "0" + (int) this.minutes, 110, 240);
        } else {
            graphics.drawString(this.hours + ":" + (int) this.minutes, 110, 240);
        }
    }

    /**
     * Updates the targets for every NPC, checks in which direction the NPCs need to move, updates the time
     *
     * @param deltaTime
     */
    public void update(double deltaTime) {

        //increases the scrollbar and changing te hours and minutes from the scrollbar values and increasing the degrees for the clock
        if(scrollBar.getValue() < scrollBar.getMax()) {
            scrollBar.setValue(scrollBar.getValue() + (deltaTime * this.timeSpeed));
            this.hours = (int) (scrollBar.getValue() / 60) % 60;
            this.minutes = (int) (scrollBar.getValue() % 60);
            this.smallDegree = (30 * (this.hours % 120) + (this.minutes * 0.5));
            this.bigDegree = (6 * this.minutes);
        }
        timeSpeedLabel.setText("Time speed " + this.timeSpeed);

        //Reads the shows from the file

        //Updates the destination for the visitors
        for (Visitor visitor : this.visitors) {
            if (!DataStore.getShowsA().isEmpty()) {  //Checks if any shows exist
                for (Show show : DataStore.getShowsA()) {   //Loops through all shows in the show list
                    //This part is used to identify what the visitors need to do for this show
                    int visitorAction = 0;      //Integer that indicates what the visitor needs to do; 0 means nothing, 1 means go to show, -1 means leave the show.
                    if (show.getStartTime() == this.hours) {    //Verifies whether the current time matches the starting time of the show
                        //TODO use the Datastore to get the stage names, instead of hardcoding it
                        if (this.minutes <= 5) { //In the first 5 minutes of the hour all wander states are disabled if the target gets a new destination
                            visitor.setWander(false);
                            if ((Math.random() * 100) <= (double)show.getPopularity()){    //Used the popularity to determine if the visitor will go to the show
                                visitorAction = 1;
                            }
                        }
                    } else if (show.getEndTime() == this.hours) {     //Verifies whether the current time matches the end time of the show
                        if (this.minutes <= 5) { //In the first 5 minutes of the hour all wander states are disabled if the target gets a new destination
                            visitor.setWander(false);
                            visitorAction = -1;
                        }
                    }
                    //This part translates the desired actions into an actual goal and route for the visitors.
                    if (visitorAction != 0) {
                        if (show.getStage().getName().equalsIgnoreCase("main")) {
                            if (visitorAction == 1) {
                                visitor.setEndPoint(this.mainStageView);
                                visitor.setRoute(this.route2);
                            } else if (visitorAction == -1 && visitor.getRoute().equalsIgnoreCase(this.route2)) {
                                visitor.setEndPoint(this.toiletVisitor);
                                visitor.setRoute(this.route3);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("side")) {
                            if (visitorAction == 1) {
                                visitor.setEndPoint(this.sideStageView);
                                visitor.setRoute(this.route1);
                            } else if (visitorAction == -1 && visitor.getRoute().equalsIgnoreCase(this.route1)) {
                                visitor.setEndPoint(this.toiletVisitor);
                                visitor.setRoute(this.route3);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("back")) {
                            if (visitorAction == 1) {
                                visitor.setEndPoint(this.bsStageVisitor);
                                visitor.setRoute(this.route4);
                            } else if (visitorAction == -1 && visitor.getRoute().equalsIgnoreCase(this.route4)) {
                                visitor.setEndPoint(this.toiletVisitor);
                                visitor.setRoute(this.route3);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("small")) {
                            if (visitorAction == 1) {
                                visitor.setEndPoint(this.smallStage);
                                visitor.setRoute(this.route5);
                            } else if (visitorAction == -1 && visitor.getRoute().equalsIgnoreCase(this.route5)) {
                                visitor.setEndPoint(this.toiletVisitor);
                                visitor.setRoute(this.route3);
                            }
                        }
                    }
                }
            }
            visitor.update(this.people, this.bfs, this.map);
        }
        //Updates the destination for the artists
        for (Artist artist : this.artists) {
            if (!DataStore.getShowsA().isEmpty()) {  //Checks if any shows exist
                for (Show show : DataStore.getShowsA()) {   //Loops through all shows in the show list
                    int artistAction = 0;       //Integer that indicates what the artist needs to do; 0 means nothing, 1 means go to show, -1 means leave the show.
                    if (show.getStartTime() == this.hours) {    //Checks if the current time matches the start time of any shows
                        if (this.minutes <= 5) {     //In the first 5 minutes of the hour all wander states are disabled for artist that get a new destination
                            System.out.println("SET WANDER TO FALSE");
                            artist.setWander(false);
                            artistAction = 1;
                        }
                    }
                    if (show.getEndTime() == this.hours) {
                        if (this.minutes <= 5) {     //In the first 5 minutes of the hour all wander states are disabled for artist that get a new destination
                            System.out.println("SET WANDER TO FALSE");
                            artist.setWander(false);
                            artistAction = -1;
                        }
                    }
                    boolean sameArtist = false;
                    for (agenda.data.Artist artist1 : show.getArtistA()) {   //Compares the names of the artist and the name of the performing artist to see if they match
                        if (artist.getName().equalsIgnoreCase(artist1.getName())) { //If the names match this artist needs to go to the stage
                            sameArtist = true;
                        }
                    }
                    if (sameArtist && artistAction != 0) {
                        if (show.getStage().getName().equalsIgnoreCase("main")) {
                            if (artistAction == 1) {
                                artist.setEndPoint(this.mainStageArtist);
                                artist.setRoute(this.route8);
                            } else if (artistAction == -1) {
                                artist.setEndPoint(this.breakRoom);
                                artist.setRoute(this.route7);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("side")) {
                            if (artistAction == 1) {
                                artist.setEndPoint(this.sideStageArtist);
                                artist.setRoute(this.route9);
                            } else if (artistAction == -1) {
                                artist.setEndPoint(this.breakRoom);
                                artist.setRoute(this.route7);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("back")) {
                            if (artistAction == 1) {
                                artist.setEndPoint(this.backStageArtist);
                                artist.setRoute(this.route6);
                            } else if (artistAction == -1) {
                                artist.setEndPoint(this.breakRoom);
                                artist.setRoute(this.route7);
                            }
                        } else if (show.getStage().getName().equalsIgnoreCase("small")) {
                            if (artistAction == 1) {
                                artist.setEndPoint(this.smallStage);
                                artist.setRoute(this.route5);
                            } else if (artistAction == -1) {
                                artist.setEndPoint(this.breakRoom);
                                artist.setRoute(this.route7);
                            }
                        }
                    }
                }
            }

            artist.update(this.people, this.bfs, this.map);
        }
    }

    /**
     * Launches the festival.map_old application
     */
    public static void main(String[] args) {
        launch(MapMain.class);
    }
}
