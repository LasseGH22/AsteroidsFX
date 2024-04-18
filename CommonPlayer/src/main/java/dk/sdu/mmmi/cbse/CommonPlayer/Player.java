package dk.sdu.mmmi.cbse.CommonPlayer;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Emil
 */
public class Player extends Entity {

    private final int[] originalRgb = new int[] {56, 100, 194};
    private final int[] collisionRgb = new int[] {228, 242, 22};

    public Player() {
        this.setRgb(56, 100, 194);
    }

    public int[] getOriginalRgb() {
        return originalRgb;
    }

    public int[] getCollisionRgb() {
        return collisionRgb;
    }
}
