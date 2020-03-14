package map;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class Tile {
    private Point2D gridPos;
    private Point2D currentPos;
    private HashMap<String, Vector> route;
    private boolean isDestination;
//    private ArrayList<Vector> visited;

    public Tile(Point2D gridPos) {
        this.gridPos = gridPos;
        this.currentPos = new Point2D.Double();
        this.route = new HashMap<>();
        this.isDestination = false;
    }


}
