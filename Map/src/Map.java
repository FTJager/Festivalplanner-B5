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
    private BufferedImage maptest = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);

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

//        for(int x = 0; x < tiles.get(20).getWidth(); x ++){
//            for(int y = 0; y < tiles.get(0).getHeight(); y++){
//                map.setRGB(x,y,tiles.get(0).getRGB(x, y));
//            }
//        }

        //makes map image
        for(Tilelayer layer : tilelayers){

            for(int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if(layer.getLayer()[y][x] <= 0)
                        continue;

                    for(int tileY = 0; tileY < 32; tileY++){
                        for (int tileX = 0; tileX < 32; tileX++){
                            //tiles.get((layer.getLayer()[y][x])-1).getRGB(tileX,tileY);
                            map.setRGB(x*tileX,y*tileY, tiles.get((layer.getLayer()[y][x])-1).getRGB(tileX,tileY));
                        }
                    }
                }
            }
        }
    }

    void draw(Graphics2D g2d, ResizableCanvas canvas) {
        g2d.setColor(Color.black);
        g2d.clearRect(-(int)canvas.getWidth()*2,-(int)canvas.getHeight()*2,(int)canvas.getWidth()*10, (int)canvas.getHeight()*10);
//        for(int i = 0; i < this.tilelayers.size(); i++){
//            if(this.tilelayers.get(i).isVisibility()) {
//                drawLayers(g2d, this.tilelayers.get(i).getLayer());
//            }
//        }
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

    private void drawLayers(Graphics2D g2d, int[][] map) {
        for(int y = 0; y < height; y++)
        {
            for(int x = 0; x < width; x++)
            {
                if(map[y][x] <= 0)
                    continue;
                //draw tile from tiles, at height y and width x
                g2d.drawImage(
                        tiles.get((map[y][x])-1),
                        AffineTransform.getTranslateInstance(x*tileWidth, y*tileHeight), null);

            }
        }
    }
}