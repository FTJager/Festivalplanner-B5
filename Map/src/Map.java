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


public class Map {
    private int width;
    private int height;

    private int tileHeight;
    private int tileWidth;

    private ArrayList<BufferedImage> tiles = new ArrayList<>();

    private int[][] map1;
    private int[][] map2;
    private int[][] map3;

    public Map(String fileName)
    {
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        this.width = root.getInt("width");
        this.height = root.getInt("height");

        //load the tilemaps
        try {

            BufferedImage tilemapPath = ImageIO.read(getClass().getResourceAsStream(root.getJsonArray("tilesets").getJsonObject(0).getString("image")));
            BufferedImage tilemapAtlas = ImageIO.read(getClass().getResourceAsStream(root.getJsonArray("tilesets").getJsonObject(1).getString("image")));
            BufferedImage tilemapMed = ImageIO.read(getClass().getResourceAsStream(root.getJsonArray("tilesets").getJsonObject(2).getString("image")));

            tileHeight = root.getInt("tileheight");
            tileWidth = root.getInt("tilewidth");


            //adds tilemap to array tiles
            addtilemap(tilemapPath);
            addtilemap(tilemapAtlas);
            addtilemap(tilemapMed);

        } catch (IOException e) {
            e.printStackTrace();
        }

        int number = 0;
        map1 = new int[height][width];
        map2 = new int[height][width];
        map3 = new int[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                map1[y][x] = root.getJsonArray("layers").getJsonObject(0).getJsonArray("data").getInt(number);
                map2[y][x] = root.getJsonArray("layers").getJsonObject(1).getJsonArray("data").getInt(number);
                map3[y][x] = root.getJsonArray("layers").getJsonObject(2).getJsonArray("data").getInt(number);
                number++;
            }
        }
    }

    void draw(Graphics2D g2d, Point2D position) {
        drawLayers(g2d, map1, position);
        drawLayers(g2d, map2, position);
        drawLayers(g2d, map3, position);

    }



    public void addtilemap(BufferedImage tilemap){
        for(int y = 0; y < tilemap.getHeight(); y += tileHeight)
        {
            for(int x = 0; x < tilemap.getWidth(); x += tileWidth)
            {
                //add subimage of tilemap at height y and width x
                tiles.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
            }
        }
    }

    private void drawLayers(Graphics2D g2d, int[][] map, Point2D position) {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if(map[y][x] <= 0)
                    continue;
                //draw tile from tiles, at height y and width x
                g2d.drawImage(
                        tiles.get((map[y][x])-1),
                        AffineTransform.getTranslateInstance(x*tileWidth + position.getX(), y*tileHeight + position.getY()),
                        null);

            }
        }
    }
}