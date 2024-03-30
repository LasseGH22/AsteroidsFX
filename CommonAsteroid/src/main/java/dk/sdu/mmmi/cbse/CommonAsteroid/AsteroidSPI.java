package dk.sdu.mmmi.cbse.CommonAsteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    void asteroidSplit(Entity entity, World world);
}
