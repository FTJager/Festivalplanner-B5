package map;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    private int width;
    private int height;
    private Tile[][] grid;
    private Queue<Tile> queue;

    /**
     * This sets the size of the grid
     * @param width is the width of the entire grid
     * @param height is the height of the entire grid
     */
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Loads the collsion layer grid
     * @return returns the grid
     */
    public Tile[][] getTileMap() {
        return grid;
    }

    /**
     * Sets the collision layer grid
     * @param tileMap is the collision layer grid
     */
    public void setTileMap(Tile[][] tileMap) {
        this.grid = tileMap;
    }

    /**
     * The algorithm has a starting position, from this position it checks it's non-diagonal neighbours
     * with the checkNeighbour method.
     * @param pos is the starting pos of the algorithm
     * @param route is the String
     */
    public void BFS(Point2D pos, String route) {
        this.grid = getTileMap();
        if (inGrid(pos.getX(), pos.getY())) {
            this.queue = new LinkedList<Tile>();
            grid[(int) pos.getY()][(int) pos.getX()].setDestination(true);
            grid[(int) pos.getY()][(int) pos.getX()].addRoute(route, new Point2D.Double(0, 0));
            grid[(int) pos.getY()][(int) pos.getX()].setVisited(true);

            checkNeighbour(pos, route);

            while (!this.queue.isEmpty()) {
                final Tile checkTile = queue.poll();
                checkNeighbour(checkTile.getGridPos(), route);
            }
            grid[(int) pos.getY()][(int) pos.getX()].setDestination(false);

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    grid[y][x].setVisited(false);
                }
            }
        }
    }

    /**
     * This method checks the non-diagonal neighbours of the current position.
     * If the neighbour isn't visited yet, and isn't a wall and isn't the destination,
     * then add it to the queue.
     * The inGrid method checks if the neighbours of the current position are in the grid,
     * if so then check those neighbours. The neighbours outside of the grid, will be neglected.
     * @param pos is the current position of the algorithm
     * @param route is the name of the current route
     */
    public void checkNeighbour(Point2D pos, String route) {

        if (inGrid(pos.getX(), pos.getY() + 1)) {
            final Tile checkTile = this.grid[(int) pos.getY() + 1][(int) pos.getX()];
            if (!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()) {
                checkTile.addRoute(route, new Point2D.Double(0, -1));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if (inGrid(pos.getX(), pos.getY() - 1)) {
            final Tile checkTile = this.grid[(int) pos.getY() - 1][(int) pos.getX()];
            if (!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()) {
                checkTile.addRoute(route, new Point2D.Double(0, 1));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if (inGrid(pos.getX() + 1, pos.getY())) {
            final Tile checkTile = this.grid[(int) pos.getY()][(int) pos.getX() + 1];
            if (!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()) {
                checkTile.addRoute(route, new Point2D.Double(-1, 0));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if (inGrid(pos.getX() - 1, pos.getY())) {
            final Tile checkTile = this.grid[(int) pos.getY()][(int) pos.getX() - 1];
            if (!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()) {
                checkTile.addRoute(route, new Point2D.Double(1, 0));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }
    }

    /**
     * This method checks if the current neighbour is in the grid.
     * @param x is the X-coordinate of the current position
     * @param y is the Y-coordinate of the current position
     * @return returns a true boolean if the neighbour is in the grid
     */
    private boolean inGrid(double x, double y) {
        return x < width && x > -1 && y < height && y > -1;
    }
}
