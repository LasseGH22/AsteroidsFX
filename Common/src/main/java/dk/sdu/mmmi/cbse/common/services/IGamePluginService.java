package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Provides start and stop methods
 */
public interface IGamePluginService {

    /**
     * Runs at launch
     * @param gameData
     * @param world
     */
    void start(GameData gameData, World world);

    /**
     * Runs at termination
     * @param gameData
     * @param world
     */
    void stop(GameData gameData, World world);
}
