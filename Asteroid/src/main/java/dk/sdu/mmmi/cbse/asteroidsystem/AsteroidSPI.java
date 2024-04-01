package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    void asteroidSplit(Entity entity, World world);

    void asteroidBounce(Asteroid asteroid1, Asteroid asteroid2);
}
