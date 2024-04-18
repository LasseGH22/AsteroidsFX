package dk.sdu.mmmi.cbse.CommonPlayer;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface PlayerCollisionSPI {
    /**
     * Resets player to center and gives 3 seconds of immunity
     * @param entity Takes entity (Player)
     * @param gameData Uses gamedata for display width & height
     */
    void resetPlayer(Entity entity, GameData gameData);

    /**
     * Removes 1 life from player
     * <p>
     *     If lives go below 1, the application is terminated
     * </p>
     * @param entity Takes entity (Player)
     */
    //void removeLife(Entity entity);
}
