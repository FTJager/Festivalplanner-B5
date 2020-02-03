package Data;

public class Stage {

    private String stageName;
    private int size;
    private boolean inUse;

    public Stage(String stageName, int size, boolean inUse) {
        this.stageName = stageName;
        this.size = size;
        this.inUse = inUse;
    }

    public String getStageName() {
        return stageName;
    }
    public void setStageName(String stageName){
        this.stageName = stageName;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public boolean getInUse() {
        return inUse;
    }
    public void setInUse(boolean inUse){
        this.inUse = inUse;
    }
}
