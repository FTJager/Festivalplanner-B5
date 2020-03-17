package map;

import java.awt.geom.Point2D;

import java.util.*;


public class Tile {
    private Point2D gridPos;
    private HashMap<String, Point2D> route;
    private boolean isDestination;
    private boolean isWall;
    private boolean visited;

    public Tile(Point2D gridPos, boolean isWall, boolean isDestination, boolean visited) {
        this.gridPos = gridPos;
        this.route = new HashMap<String, Point2D>();
        this.isDestination = isDestination;
        this.isWall = isWall;
        this.visited = visited;

    }

    public Point2D getGridPos() {
        return gridPos;
    }

    public void setGridPos(Point2D gridPos) {
        this.gridPos = gridPos;
    }

    public HashMap<String, Point2D> getRoute() {
        return route;
    }

    public boolean isDestination() {
        return isDestination;
    }

    public void setDestination(boolean destination) {
        isDestination = destination;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addRoute( String route, Point2D pos){
        this.route.put(route, pos);
    }
}
