package festival.map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Tilelayer {

    private int height;
    private int width;
    private String name;
    private String type;
    private boolean visibility;
    private int[][] layer;

    /**
     * This tilelayer class stores 1 layer from the tilemap.
     * @param fileName this parameter is the name of the json file.
     * @param jsonObject this parameter is the number of the tilelayer you want to get
     */
    public Tilelayer(String fileName, int jsonObject) {
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        this.height = root.getJsonArray("layers").getJsonObject(jsonObject).getInt("height");
        this.width = root.getJsonArray("layers").getJsonObject(jsonObject).getInt("width");
        this.name = root.getJsonArray("layers").getJsonObject(jsonObject).getString("name");
        this.type = root.getJsonArray("layers").getJsonObject(jsonObject).getString("type");
        this.visibility = root.getJsonArray("layers").getJsonObject(jsonObject).getBoolean("visible");

        layer = new int[height][width];
        int number = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                layer[y][x] = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("data").getInt(number);
                number++;
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    /**
     * With this boolean you can check if the layer is set visible or not
     * @return returns true if visible and false if not visible
     */
    public boolean isVisibility() {
        return visibility;
    }

    /**
     * With this you can get the value of a specific layer.
     * @return returns the layer
     */
    public int[][] getLayer() {
        return layer;
    }
}

