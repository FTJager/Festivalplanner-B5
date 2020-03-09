import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Hick extends NPC {
    public Hick(Point2D position, BufferedImage sprite, Point2D target) {
        super(position, sprite, target);
        super.stageCollision = true;
    }
}
