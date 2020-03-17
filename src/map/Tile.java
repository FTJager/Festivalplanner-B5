package map;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import com.sun.javafx.geom.Vec2d;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.awt.*;
import java.awt.geom.Point2D;

import java.util.*;



public class Tile {
    //TODO maak methodes om de points makkelijker te bereiken
    private class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point left(){
            return new Point(x+1,y);
        }
        
    }

    private Point2D gridPos;
    private Point2D currentPos;
    private Queue<Point> queue = new LinkedList<>();

    private HashMap<String, Vec2d> route;
    private int[][] grid;
    private boolean isDestination;
    private ArrayList<Vec2d> visited;

    public Tile(int[][] grid) {
        this.gridPos = new Point2D.Double();
        this.currentPos = new Point2D.Double();
        this.route = new HashMap<>();
        this.isDestination = false;
        this.grid = grid;
        this.visited = new ArrayList<>();
    }


    private void BFS(Point p) {
        ((LinkedList<Point>) queue).add(0, p);
        int depth = grid[p.y][p.x];


        for (DIR dir : DIR.values()) {
            switch (dir) {
                case UP:
                    if (gridPos.getY() > 0 && grid[][] == 0) {
                        System.out.println("it do te ting UP");
                        System.out.println(gridPos.getX() + gridPos.getY());

                        dir = DIR.DOWN;
                    }
                    break;
                case DOWN:
                    if (y + 1 < grid.length && grid[y + 1][x] == 0) {
                        System.out.println("it go do te ting DOWN");
                        System.out.println(gridPos.getX() + gridPos.getY());

                        dir = DIR.LEFT;
                    }
                    break;
                case LEFT:
                    if (x > 0 && grid[y][x - 1] == 0) {
                        System.out.println("it go do te ting LEFT");
                        System.out.println(gridPos.getX() + gridPos.getY());

                        dir = DIR.RIGHT;
                    }
                    break;
                case RIGHT:
                    if (x + 1 < grid[1].length && grid[y][x + 1] == 0) {
                        System.out.println("it go do te ting RIGHT");
                        System.out.println(gridPos.getX() + gridPos.getY());

                        dir = DIR.UP;
                    }
                    break;
            }
        }
        //TODO Gebruik grid, check op -1
//        visited.add(new Vec2d(currentPos.getX(), currentPos.getY()));

//        BFS(queue.poll());

        //Nested forloop for the grid[y][x]
        //In the grid check for the gridposY and gridposX
        //Then the neighbour check below:
        //if above isn't destination and checked
        //else if left isn't outside of the boundaries and hasn't been checked
        //else if below isn't outside of the boundaries and hasn't been checked
        //else if right isn't outside of the boundaries and hasn't been checked


    }

    public void doBFS(int x, int y) {
        Point p = new Point(x,y);
        grid[y][x] = 0;
        BFS(p);
    }

}
