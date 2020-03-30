package festival.npc;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Artist extends NPC {
    private String name;

    /**
     * This class is used to create new Artist NPCs
     * @param position Current position of the Artist
     * @param sprite The sprite used to represent the artist
     * @param target Target location, Artist will walk towards this point
     * @param name Artist's name
     */
    public Artist(Point2D position, BufferedImage sprite, Point2D target,String name) {
        super(position, sprite, target);
        super.isArtist = true;
        super.stageCollision = false;
        this.name = name;
        setSpeed(Math.random() + 2.5);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
