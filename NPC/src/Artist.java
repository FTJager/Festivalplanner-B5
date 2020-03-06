import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Artist extends NPC {

    public Artist(Point2D position, BufferedImage sprite) {
        super(position, sprite);
        super.stageCollision = false;
    }

}
