package map;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

//TODO More comments, and the commented methods need more explanation

/**
 * This creates a map object, which is needed to read the JSON file
 */
public class Map {
    private ArrayList<Tilelayer> tilelayers = new ArrayList<>();
    private ArrayList<BufferedImage> tiles = new ArrayList<>();
    private ArrayList<TileObject> objects = new ArrayList<>();

    private int width;
    private int height;
    private int tileHeight;
    private int tileWidth;
    private JsonObject root;
    private Tile tileMap[][];
    private Point2D gridPos = new Point2D.Double();


    public Map(String fileName) {
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
         this.root = reader.readObject();

        this.width = this.root.getInt("width");
        this.height = this.root.getInt("height");

        //load the tilemaps
        try {

            BufferedImage tilemapPath = ImageIO.read(getClass().getResourceAsStream("/" + this.root.getJsonArray("tilesets").getJsonObject(0).getString("image")));
            BufferedImage tilemapAtlas = ImageIO.read(getClass().getResourceAsStream("/" + this.root.getJsonArray("tilesets").getJsonObject(1).getString("image")));
            BufferedImage tilemapMed = ImageIO.read(getClass().getResourceAsStream("/" + this.root.getJsonArray("tilesets").getJsonObject(2).getString("image")));

            this.tileHeight = this.root.getInt("tileheight");
            this.tileWidth = this.root.getInt("tilewidth");


            //adds tilemap to array tiles
            addtilemap(tilemapPath);
            addtilemap(tilemapAtlas);
            addtilemap(tilemapMed);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //adds layers to array tilelayers
        for (int i = 0; i < this.root.getJsonArray("layers").size(); i++) {
            if (this.root.getJsonArray("layers").getJsonObject(i).getString("type").equals("tilelayer")) {
                this.tilelayers.add(new Tilelayer(fileName, i));
            }
        }

        //adds object to array objects
        for (int i = 0; i < this.root.getJsonArray("layers").size(); i++) {
            if (this.root.getJsonArray("layers").getJsonObject(i).getString("type").equals("objectgroup")) {
                for (int j = 0; j < this.root.getJsonArray("layers").getJsonObject(i).size(); j++) {
                    this.objects.add(new TileObject(fileName, i, j));
                }
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<Tilelayer> getTilelayers() {
        return tilelayers;
    }

    public void draw(FXGraphics2D g2d, ResizableCanvas canvas) {
        g2d.setColor(Color.black);
        g2d.clearRect(-(int) canvas.getWidth() * 2, -(int) canvas.getHeight() * 2,
                (int) canvas.getWidth() * 10, (int) canvas.getHeight() * 10);
        for (int i = 0; i < this.tilelayers.size(); i++) {
            if (this.tilelayers.get(i).isVisibility()) {
                drawLayers(g2d, this.tilelayers.get(i).getLayer());
            }
        }
    }


    public void addtilemap(BufferedImage tilemap) {
        for (int y = 0; y < tilemap.getHeight(); y += tileHeight) {
            for (int x = 0; x < tilemap.getWidth(); x += tileWidth) {
                //add subimage of tilemap at height y and width x
                tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
            }
        }
    }

    private void drawLayers(FXGraphics2D g2d, int[][] map) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x] <= 0)
                    continue;
                //draw tileMap from tiles, at height y and width x
                g2d.drawImage(
                        tiles.get((map[y][x]) - 1),
                        AffineTransform.getTranslateInstance(x * tileWidth, y * tileHeight), null);
            }
        }
    }

    /**
     * Creates a grid based on the collision layer
     * @param map is the collision layer of the map
     * @param bfs is the breadth first search algorithm, which needs a grid to read
     */
    public void createGrid(int[][] map, BreadthFirstSearch bfs) {
        int posX = 0;
        int posY = 0;
        this.tileMap = new Tile[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gid = map[y][x];
                this.tileMap[y][x] = new Tile(new Point2D.Double(x,y), false, false, false);

                if (gid == 975) {
                    this.tileMap[y][x].setWall(true);
                }
            }

        }
        bfs.setTileMap(this.tileMap);
    }

    /**
     * Reads the object layer and returns the starting position of the route of the requested target.
     * @param route this is a string data type, which corresponds with the route of the requested target
     * @return To return the x and y coordinates of the starting/end point.
     */
    public Point2D objectTargets( String route){
        Point2D point = new Point2D.Double();
        for(TileObject object : this.objects){
            if(object.getName().equals(route)){
                point = new Point2D.Double(object.getX(), object.getY());
                return point;
            }
        }
        return point;
    }

    //Maybe to make the grid a bit easier to build?
//    public int[][] getCollisionLayer(){
//        return
//    }


}