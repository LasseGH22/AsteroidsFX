package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {
    Random random = new Random();
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));
            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);
        }

        world.addEntity(createAsteroid(gameData));
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();

        setAsteroidShape(asteroid); // Generate semi random shape for the asteroid
        setSpawnLocation(asteroid,gameData); // Generate coordinates for spawn location of asteroid

        return asteroid;
    }

    private void setSpawnLocation(Entity asteroid,GameData gameData) {

        int asteroidSpawnX = random.nextInt(gameData.getDisplayWidth());
        int asteroidSpawnY = random.nextInt(gameData.getDisplayHeight());

        if (random.nextInt(0,3) == 1) {

            // Top
            if (asteroidSpawnX > asteroidSpawnY) {
                asteroid.setX(asteroidSpawnX);
                asteroid.setY(-5);
                asteroid.setRotation(random.nextInt(0,180));
            }

            // Left
            if (asteroidSpawnY >= asteroidSpawnX) {
                asteroid.setX(-5);
                asteroid.setY(asteroidSpawnY);
                asteroid.setRotation(90 - random.nextInt(0,180));
            }
        } else {

            // Bottom
            if (asteroidSpawnX > asteroidSpawnY) {
                asteroid.setX(asteroidSpawnX);
                asteroid.setY(gameData.getDisplayHeight() - 5);
                asteroid.setRotation(180 + random.nextInt(0,180));
            }

            // Right
            if (asteroidSpawnY >= asteroidSpawnX) {
                asteroid.setX(gameData.getDisplayWidth() - 5);
                asteroid.setY(asteroidSpawnY);
                asteroid.setRotation(270 - random.nextInt(0,180));
            }
        }
    }

    private void setAsteroidShape(Entity asteroid) {
        double size = random.nextInt(15,25);

        // Calculate points of asteroid
        double[][] points = new double[5][2];
        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(72 * i - 90);
            points[i][0] = size * Math.cos(angle) + random.nextInt(10,25);
            points[i][1] = size * Math.sin(angle) + random.nextInt(10,25);
        }

        // Set coordinates of asteroid polygon from the randomly semi randomly generated values
        asteroid.setPolygonCoordinates(
                points[0][0], points[0][1],
                points[1][0], points[1][1],
                points[2][0], points[2][1],
                points[3][0], points[3][1],
                points[4][0], points[4][1]);
    }
}
