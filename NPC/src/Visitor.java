import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Visitor extends NPC {
    public Visitor(Point2D position, BufferedImage sprite, Point2D target) {
        super(position, sprite, target);
        super.stageCollision = true;
        setSpeed(Math.random() * 1.5 + 0.5);
        setRotationSpeed(Math.random() / 10 + 0.05);
    }
}
