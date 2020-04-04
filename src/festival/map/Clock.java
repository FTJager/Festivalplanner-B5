package festival.map;

import javafx.animation.Timeline;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Clock {

    private BufferedImage clock;
    private Rectangle2D.Double bigHand;
    private Rectangle2D.Double smallHand;
    private Point2D clockMiddle;


    public Clock(){
        try {
            this.clock = ImageIO.read(getClass().getResourceAsStream("/clock.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.clockMiddle = new Point2D.Double(2800 + this.clock.getWidth()/2, this.clock.getHeight()/2);

        this.bigHand = new Rectangle2D.Double((this.clockMiddle.getX() - 5), 300, 10, 280);
        this.smallHand = new Rectangle2D.Double((this.clockMiddle.getX() - 5),300, 10, 150);
    }

    /**
     * This methode draws the clock and its hands depending on the given degree.
     * @param g2d The graphics2D from the main class
     * @param bigDegree This degree sets the rotation of the big hand
     * @param smallDegree This degree sets the rotation of the small hand
     */
    void draw(Graphics2D g2d, double bigDegree, double smallDegree) {
        AffineTransform af = new AffineTransform();
        af.translate(2800, 0);
        g2d.drawImage(clock, af, null);

        g2d.setColor(Color.red);
        g2d.fill(getTransformedShape(this.bigHand , bigDegree));

        g2d.setColor(Color.BLUE);
        g2d.fill(getTransformedShape(this.smallHand , smallDegree));

        g2d.setColor(Color.black);
        g2d.fill(new Ellipse2D.Double(this.clockMiddle.getX()  - 15, this.clockMiddle.getY() - 15, 30, 30));
    }

    public Shape getTransformedShape(Shape shape, double degree) {
        return getTransform(degree).createTransformedShape(shape);
    }

    public AffineTransform getTransform(double degree) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(degree + 180), clockMiddle.getX(), clockMiddle.getY());
        return tx;
    }
}
