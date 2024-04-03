package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface EnemySPI {

    /**
     * resets enemy to one of the 4 corners
     * @param entity Takes entity (Enemy)
     * @param gameData
     */
    void resetEnemy(Entity entity, GameData gameData);
}
