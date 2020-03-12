package map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class TileObject {

    private String name;
    private String type;
    private double height;
    private double width;
    private double x;
    private double y;

    public TileObject(String fileName, int jsonObject, int object){
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        this.name = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getString("name");
        this.type = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getString("type");
        this.height = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getInt("height");
        this.width = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getInt("width");
        this.x = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getInt("x");
        this.y = root.getJsonArray("layers").getJsonObject(jsonObject).getJsonArray("objects").getJsonObject(object).getInt("y");
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
