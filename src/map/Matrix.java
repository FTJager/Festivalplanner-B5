package map;

public class Matrix {
    private Map map;
    private Tile[] tiles;
    private int noOfTiles;
    private Neighbour[] neighbours;
    private int noOfNeighbours;

    public Matrix(Neighbour[] neighbours){
        this.neighbours = neighbours;

        this.noOfTiles = calNoOfTiles(neighbours);
        this.tiles = new Tile[this.noOfTiles];

        for(int t = 0; t < this.noOfTiles; t++){
            //this.tiles[t] = new Tile();
        }

        this.noOfNeighbours = neighbours.length;

        for (int neighbourToAdd = 0; neighbourToAdd < this.noOfNeighbours; neighbourToAdd++){
            this.tiles[neighbours[neighbourToAdd].getFromTile()].getNeighbours().add(neighbours[neighbourToAdd]);

            this.tiles[neighbours[neighbourToAdd].getToTile()].getNeighbours().add(neighbours[neighbourToAdd]);
        }
    }

    private int calNoOfTiles(Neighbour[] neighbours){
        int noOfTiles = 0;

        for (Neighbour n : neighbours){
            if(n.getToTile() > noOfTiles){
                noOfTiles = n.getToTile();
            }
            if(n.getFromTile() > noOfTiles){
                noOfTiles = n.getFromTile();
            }
        }

        noOfTiles++;
        return noOfTiles;
    }

    public void init(){
        this.map = new Map("/festival.json");
    }

    public static void main(String args[]){

    }
}


