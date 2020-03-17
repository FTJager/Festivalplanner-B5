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


public class Map {

    private ArrayList<Tilelayer> tilelayers = new ArrayList<>();
    private ArrayList<BufferedImage> tiles = new ArrayList<>();
    private ArrayList<TileObject> objects = new ArrayList<>();

    private BufferedImage map = new BufferedImage(110*32, 80*32, BufferedImage.TYPE_INT_RGB);

    private int width;
    private int height;
    private int tileHeight;
    private int tileWidth;


    public Map(String fileName) {
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

        //adds layers to array tilelayers
        for(int i = 0; i < root.getJsonArray("layers").size(); i++){
            if(root.getJsonArray("layers").getJsonObject(i).getString("type").equals("tilelayer")) {
                this.tilelayers.add(new Tilelayer(fileName, i));
            }
        }

        //adds object to array objects
        for(int i = 0; i < root.getJsonArray("layers").size(); i++){
            if(root.getJsonArray("layers").getJsonObject(i).getString("type").equals("objectgroup")) {
                for(int j = 0; j < root.getJsonArray("layers").getJsonObject(i).size(); j++){
                    this.objects.add(new TileObject(fileName, i, j));
                }
            }
        }

        //makes map image
        for(Tilelayer layer : tilelayers){
            //tilelayers zijn de verschillende lagen in de map
            if(layer.isVisibility()) {
                for (int y = 0; y < height; y++) {
                    //height is 80
                    for (int x = 0; x < width; x++) {
                        //width = 110
                        if (layer.getLayer()[y][x] <= 0)
                            continue;

                        for (int tileY = 0; tileY < 32; tileY++) {
                            //kijk ik naar alle 32 pixels in de hoogte van de tile
                            for (int tileX = 0; tileX < 32; tileX++) {
                                //kijk ik naar alle 32 pixels in de breedte van de tile

                                //map = een bufferedimage van 110*32 bij 80*32 dan set ik de pixel door de rgb de krijgen van de juiste tile
                                if(tiles.get((layer.getLayer()[y][x]) - 1).getRGB(tileX, tileY) != 0) {
                                    map.setRGB(x * 32 + tileX, y * 32 + tileY, tiles.get((layer.getLayer()[y][x]) - 1).getRGB(tileX, tileY));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    void draw(Graphics2D g2d, ResizableCanvas canvas) {
        g2d.setColor(Color.black);
        g2d.clearRect(-(int)canvas.getWidth()*2,-(int)canvas.getHeight()*2,(int)canvas.getWidth()*10, (int)canvas.getHeight()*10);
        AffineTransform af = new AffineTransform();
        g2d.drawImage(map, af, null);
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
}