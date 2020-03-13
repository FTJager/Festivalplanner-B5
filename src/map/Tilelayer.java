package map;

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

    public boolean isVisibility() {
        return visibility;
    }

    public int[][] getLayer() {
        return layer;
    }
}

