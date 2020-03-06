import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Hick extends NPC {
    public Hick(Point2D position, BufferedImage sprite) {
        super(position, sprite);
        super.stageCollision = true;
    }
}
