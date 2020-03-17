package map;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Queue;

public class BreadthFirstSearch {

    private int width;
    private int heigth;
    private Tile[][] grid;
    private Queue<Tile> queue;


    public void setSize(int width, int height) {
        this.width = width;
        this.heigth = height;
    }

    public Tile[][] getTileMap() {
        return grid;
    }

    public void setTileMap(Tile[][] tileMap) {
        this.grid = tileMap;
    }

    //TODO Implemnt the route key for the hashmap so that you can more easily "scroll" through the map
    public void BFS(Point2D pos){
        this.grid = getTileMap();
        if(inGrid(pos.getX(), pos.getY())){
            this.queue = new LinkedList<Tile>();
            grid[(int)pos.getY()][(int)pos.getX()].setDestination(true);
            grid[(int)pos.getY()][(int)pos.getX()].addRoute("route 1", new Point2D.Double(0,0));
            grid[(int)pos.getY()][(int)pos.getX()].setVisited(true);

            checkNeighbour(pos);

            while(!this.queue.isEmpty()){
                final Tile checkTile = queue.poll();
                checkNeighbour(checkTile.getGridPos());
            }
            grid[(int)pos.getY()][(int)pos.getX()].setDestination(false);

            for(int y = 0; y < heigth; y++){
                for(int x = 0; x < width; x++){
                    grid[y][x].setVisited(false);
                }
            }
        }
    }

    public void checkNeighbour(Point2D pos){
        String route = "route 1";

        if(inGrid(pos.getX(), pos.getY() + 1)){
            final Tile checkTile = this.grid[(int)pos.getY() + 1][(int)pos.getX()];
            if(!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()){
                checkTile.addRoute(route, new Point2D.Double(0,-1));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if(inGrid(pos.getX(), pos.getY() - 1)){
            final Tile checkTile = this.grid[(int)pos.getY() - 1][(int)pos.getX()];
            if(!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()){
                checkTile.addRoute(route, new Point2D.Double(0,1));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if(inGrid(pos.getX() + 1, pos.getY())){
            final Tile checkTile = this.grid[(int)pos.getY()][(int)pos.getX() + 1];
            if(!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()){
                checkTile.addRoute(route, new Point2D.Double(-1,0));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }

        if(inGrid(pos.getX() - 1, pos.getY())){
            final Tile checkTile = this.grid[(int)pos.getY()][(int)pos.getX() - 1];
            if(!checkTile.isWall() && !checkTile.isVisited() && !checkTile.isDestination()){
                checkTile.addRoute(route, new Point2D.Double(1,0));
                checkTile.setVisited(true);
                queue.add(checkTile);
            }
        }
    }

    private boolean inGrid(double x, double y){
        return x < width && x > -1 && y < heigth && y > -1;
    }





}
