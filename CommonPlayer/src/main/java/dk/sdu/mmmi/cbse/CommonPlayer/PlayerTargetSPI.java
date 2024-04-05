package dk.sdu.mmmi.cbse.CommonPlayer;

import dk.sdu.mmmi.cbse.common.data.World;

public interface PlayerTargetSPI {

    /**
     * Assembles players coordinates into an array
     * @param world
     * @return Player coords array
     * <p> First value = X coordinate</p>
     * <p> Second value = Y coordinate</p>
     */
    double[] getPlayerCoords(World world);
}
