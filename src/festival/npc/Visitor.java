package festival.npc;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Visitor extends NPC {

    /**
     * This class is used to create Visitor NPCs
     * @param position Visitor's current position
     * @param sprite The image used to represent the Visitor
     * @param target The target location, the Visitor will walk towards this point
     */
    public Visitor(Point2D position, BufferedImage sprite, Point2D target) {
        super(position, sprite, target);
        super.isArtist = false;
        super.stageCollision = true;
//        setSpeed(Math.random() + 2.5);
    }
}
