import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Artist extends NPC {
    private String name;

    public Artist(Point2D position, BufferedImage sprite, Point2D target,String name) {
        super(position, sprite, target);
        super.stageCollision = false;
        this.name = name;
        setSpeed(Math.random() + 1);
        setRotationSpeed(Math.random() / 10 + 0.05);
    }

}
