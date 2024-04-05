package dk.sdu.mmmi.cbse.CommonAsteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;

import java.util.Random;

public class Asteroid extends Entity {
    private double speed;
    private int size;

    Random random = new Random();
    public Asteroid() {
        this.speed = random.nextDouble(0.8,1.2);
        this.size = random.nextInt(15,25);
    }
    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
