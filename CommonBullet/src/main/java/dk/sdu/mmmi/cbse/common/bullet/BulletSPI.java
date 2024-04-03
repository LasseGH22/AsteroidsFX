package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author corfixen
 */
public interface BulletSPI {
    /**
     * Creates a bullet
     * <p>Differs depending on the shooter of the bullet</p>
     * @param entity Takes a shooter (Player Or Enemy)
     * @param gameData
     * @param world
     * @return
     */
    Entity createBullet(Entity entity, GameData gameData, World world);
}
