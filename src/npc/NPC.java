package npc;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

abstract class NPC {
    public final static int SPRITESIZE = 64;

    private Point2D position;
    Boolean stageCollision;
    private double angle;
    private double speed;
    private BufferedImage sprite;

    private Point2D target;
    private double rotationSpeed;

    public NPC(Point2D position, BufferedImage sprite, Point2D target){
        this.position = position;
        this.sprite = sprite;
        this.target = target;
        this.angle = 0;
        this.speed = 2;
        this.rotationSpeed = 0.1;
    }

    public void update(ArrayList<NPC> npcs){

        double targetAngle = Math.atan2(this.target.getY() - this.position.getY(),
                                        this.target.getX() - this.position.getX());

        double angleDifference = this.angle - targetAngle;
        while (angleDifference < -Math.PI){
            angleDifference += 2 * Math.PI;
        }
        while (angleDifference > Math.PI){
            angleDifference -= 2 * Math.PI;
        }

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

        for (NPC other : npcs){
            if (other != this && newPosition.distance(other.position) < SPRITESIZE){
                collided = true;
            }
        }

        if (!collided){
            this.position = newPosition;
        }else {
            this.angle -= this.rotationSpeed * 2;
        }
    }

    private AffineTransform getTransform() {
        AffineTransform tx = new AffineTransform();
        tx.translate(position.getX() - this.sprite.getWidth()/2, position.getY() - this.sprite.getHeight()/2);
        tx.rotate(this.angle, this.sprite.getWidth()/2, this.sprite.getHeight()/2);
        return tx;
    }

    public void setTarget(Point2D target) {
        this.target = target;
    }

    public void draw(Graphics2D g) {
        g.drawImage(sprite, getTransform(), null);
    }

    public Point2D getPosition() {
        return position;
    }

    public Point2D getTarget() {
        return target;
    }
}
