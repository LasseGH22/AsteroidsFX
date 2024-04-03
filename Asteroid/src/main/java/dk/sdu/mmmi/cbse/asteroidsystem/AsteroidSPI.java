package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    /**
     * Creates a Split_Asteroid for each entry in provided array
     * @param entity Takes the parent asteroid - Asteroid to be split -  as entity
     * @param world
     */
    void asteroidSplit(Entity entity, World world);

    /**
     * Bounces two asteroids off each other in a mathematically correct way
     * @param asteroid1 Asteroid 1 of the collision
     * @param asteroid2 Asteroid 2 of the collision
     */
    void asteroidBounce(Entity asteroid1, Entity asteroid2);
}
