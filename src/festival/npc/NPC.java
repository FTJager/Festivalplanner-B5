package festival.npc;

import festival.map.Camera;
import festival.map.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Main NPC class. This contains all the logic for the NPCs.
 */
public abstract class NPC {
    public final static int SPRITESIZE = 32;

    private Point2D endPoint;
    private Point2D position;
    Boolean stageCollision;
    private double angle;
    private double speed;
    private BufferedImage sprite;
    private Camera camera;

    private Point2D target;
    private double rotationSpeed;
    private boolean wander;
    private String route;

    /**
     * Constructor for the NPC class
     * @param position Current position of the NPC
     * @param sprite Sprite used to represent the NPC on the map
     * @param target Point the NPC needs to move to
     */
    public NPC(Point2D position, BufferedImage sprite, Point2D target){
        this.position = position;
        this.sprite = sprite;
        this.endPoint = target;
        this.target = target;
        this.angle = 0;
        this.rotationSpeed = 0.15;
        wander = false;
    }

    /**
     * This updates the current location, target, rotation and collision for each NPC.
     * @param npcs List of all created NPCs
     */
    public void update(ArrayList<NPC> npcs){

        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(),
                this.target.getX() - this.position.getX());

        //Determines how far the NPC has to rotate to face towards its target
        double angleDifference = this.angle - targetAngle;
        while (angleDifference < -Math.PI){
            angleDifference += 2 * Math.PI;
        }
        while (angleDifference > Math.PI){
            angleDifference -= 2 * Math.PI;
        }

        //Rotates the NPC towards its target.
        if (Math.abs(angleDifference) < this.rotationSpeed){
            this.angle = targetAngle;
        }else if (angleDifference < 0){
            this.angle += this.rotationSpeed;
        }else{
            this.angle -= this.rotationSpeed;
        }

        Point2D newPosition = new Point2D.Double(this.position.getX() + this.speed * Math.cos(this.angle),
                this.position.getY() + this.speed * Math.sin(this.angle));

        boolean collided = false;

        //Checks for collision and makes the NPCs avoid each other when they collide
        for (NPC other : npcs){
            if (other != this && newPosition.distance(other.position) < (SPRITESIZE / 2)){
                collided = true;
            }
        }
        if (!collided){
            this.position = newPosition;
        }else {
//            this.target = new Point2D.Double(getTarget().getX(), getTarget().getY() + 32);
            this.angle -= this.rotationSpeed * 2;
        }
    }

    /**
     * Calculates the movement, rotation and scale of the character sprites
     * @return returns the calculated movement, rotation and scale
     */
    private AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - this.sprite.getWidth()/2, position.getY() - this.sprite.getHeight()/2);
        tx.rotate(this.angle, this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        tx.scale(this.camera.getZoom(), this.camera.getZoom());
        return tx;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    /**
     * Draws the character sprites on the map
     * @param g Graphics2d object
     * @param camera Camera object, used to obtain the zoom level
     */
    public void draw(Graphics2D g, Camera camera) {
        this.camera = camera;
        g.drawImage(sprite, getTransform(), null);
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getTarget() {
        return target;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point2D endPoint) {
        this.endPoint = endPoint;
    }

    public void setWander(boolean wander) {
        this.wander = wander;
    }

    public boolean isWander() {
        return wander;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngle() {
        return angle;
    }
}
