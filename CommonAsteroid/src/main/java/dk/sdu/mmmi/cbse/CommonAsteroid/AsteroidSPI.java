package dk.sdu.mmmi.cbse.CommonAsteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface AsteroidSPI {
    Entity createAsteroid(GameData gameData);
}