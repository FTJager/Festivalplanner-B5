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

    /**
     * Boolean visited to check if the current tile has been visited or not.
     * @return to return the visited boolean
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * This setter sets the current tile's visited boolean either to true(while in the algorith)
     * or false(when the algorithm has been completed)
     * @param visited is to check if the current tile has been visited or not.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     *
     * @param route is the name of the given route
     * @param pos is the starting position of the target(endposition)
     */
    public void addRoute( String route, Point2D pos){
        this.route.put(route, pos);
    }
}
