package data;

public class Stage {
    private String name;
    public Stage(){
        this.name = "";
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        if(name != null)
        return this.name;
        else return "null";
    }
}
