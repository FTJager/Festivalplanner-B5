package map;

public class Neighbour {
    private int fromTile;
    private int toTile;
    private int value;

    public Neighbour(int fromTile, int toTile){
        this.fromTile = fromTile;
        this.toTile = toTile;
        this.value = 1;
    }

    //getters for the tiles
    public int getFromTile(){
        return fromTile;
    }

    public int getToTile(){
        return toTile;
    }

    public int getValue(){
        return value;
    }

    //determines neighboring node of given node, based on edge between the two nodes
    public int getNeighbour(int tileIndex){
        if(this.fromTile == tileIndex){
            return this.toTile;
        } else {
            return this.fromTile;
        }
    }
}
