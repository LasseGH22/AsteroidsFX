package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private int[] rgb = new int[3];
    private int radius;
    private double boundingCircleRadius;
    private long lastCollisionTime = 0;
    private long immunityFrames = 100;
    private EntityTag tag;
            

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(double... coordinates ) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public int[] getRgb() {
        return rgb;
    }

    public void setRgb(int red, int green, int blue) {
        this.rgb[0] = red;
        this.rgb[1] = green;
        this.rgb[2] = blue;
    }

    public void setRgb(int[] rgb) {
        this.rgb[0] = rgb[0];
        this.rgb[1] = rgb[1];
        this.rgb[2] = rgb[2];
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public double getBoundingCircleRadius() {
        return boundingCircleRadius;
    }

    public void setBoundingCircleRadius(double boundingCircleRadius) {
        this.boundingCircleRadius = boundingCircleRadius;
    }

    public EntityTag getTag() {
        return tag;
    }

    public void setTag(EntityTag tag) {
        this.tag = tag;
    }

    public boolean canCollide() {
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastCollisionTime > immunityFrames);
    }

    public void markCollision() {
        lastCollisionTime = System.currentTimeMillis();
    }

    public void setImmunityFrames(long immunityFrames) {
        this.immunityFrames = immunityFrames;
    }
}
