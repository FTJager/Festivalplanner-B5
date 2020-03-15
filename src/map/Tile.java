package map;

import java.util.ArrayList;

public class Tile {
    private int distanceFromStart = Integer.MAX_VALUE;
    private boolean visited;
    private ArrayList<Neighbour> neighbours = new ArrayList<>();
    int row, column;

    public Tile(int row, int column){
        this.row = row;
        this.column = column;
        this.distanceFromStart = 0;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getDistanceFromStart(){
        return distanceFromStart;
    }

    public void setDistanceFromStart(int distanceFromStart){
        this.distanceFromStart = distanceFromStart;
    }
    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public ArrayList<Neighbour> getNeighbours(){
        return neighbours;
    }

    public void setNeighbours(ArrayList<Neighbour> neighbours){
        this.neighbours = neighbours;
    }
}
