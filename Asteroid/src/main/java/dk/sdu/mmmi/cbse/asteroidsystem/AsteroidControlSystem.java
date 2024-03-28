package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.CommonAsteroid.Asteroid;
import dk.sdu.mmmi.cbse.CommonAsteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {
    Random random = new Random();
    @Override
    public void process(GameData gameData, World world) {

        // Handles movement for all Asteroids
        for (Entity entity : world.getEntities(Asteroid.class)) {
            Asteroid asteroid = (Asteroid) entity;
            double changeX = Math.cos(Math.toRadians(entity.getRotation()));
            double changeY = Math.sin(Math.toRadians(entity.getRotation()));
            entity.setX(entity.getX() + changeX * asteroid.getSpeed());
            entity.setY(entity.getY() + changeY * asteroid.getSpeed());

            // Despawns asteroid when out of bounds
            despawnAsteroid(gameData,world,entity);
        }

        // Spawns a max of 5 Asteroids
        if (world.getEntities(Asteroid.class).size() <= 15) {
            world.addEntity(createAsteroid(gameData));
        }
    }

    // Creates Asteroid
    public Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();

        // Sets "randomized" speed
        asteroid.setSpeed(random.nextDouble(0.8,1.2));

        asteroid.setRadius(random.nextInt(15,25));

        // Sets semi random shape
        setAsteroidShape(asteroid);

        // Sets spawn location
        setSpawnLocation(asteroid,gameData);

        // Sets Tag
        asteroid.setTag(EntityTag.ASTEROID);

        // Sets color
        asteroid.setRgb(255, 255, 255);

        return asteroid;
    }

    private void setSpawnLocation(Entity asteroid,GameData gameData) {

        int asteroidSpawnX = random.nextInt(gameData.getDisplayWidth());
        int asteroidSpawnY = random.nextInt(gameData.getDisplayHeight());

        if (random.nextInt(0,3) == 1) {

            // Top
            if (asteroidSpawnX > asteroidSpawnY) {
                asteroid.setX(asteroidSpawnX);
                asteroid.setY(-30);
                asteroid.setRotation(random.nextInt(0,180));
            }

            // Left
            if (asteroidSpawnY >= asteroidSpawnX) {
                asteroid.setX(-30);
                asteroid.setY(asteroidSpawnY);
                asteroid.setRotation(90 - random.nextInt(0,180));
            }
        } else {

            // Bottom
            if (asteroidSpawnX > asteroidSpawnY) {
                asteroid.setX(asteroidSpawnX);
                asteroid.setY(gameData.getDisplayHeight() + 30);
                asteroid.setRotation(180 + random.nextInt(0,180));
            }

            // Right
            if (asteroidSpawnY >= asteroidSpawnX) {
                asteroid.setX(gameData.getDisplayWidth() + 30);
                asteroid.setY(asteroidSpawnY);
                asteroid.setRotation(270 - random.nextInt(0,180));
            }
        }
    }

    private void setAsteroidShape(Entity asteroid) {

        // Calculate points of asteroid
        double[][] points = new double[5][2];
        double maxDistance = 0;
        double baseRadius = asteroid.getRadius() * 1.5;
        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(72 * i - 90);
            double offset = random.nextInt(5,10);
            points[i][0] = baseRadius * Math.cos(angle) + offset;
            points[i][1] = baseRadius * Math.sin(angle) + offset;

            // Calculate the furthest point from the center
            double distance = Math.sqrt(Math.pow(points[i][0], 2) + Math.pow(points[i][1], 2));
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        // Set coordinates of asteroid polygon from the semi randomly generated values
        asteroid.setPolygonCoordinates(
                points[0][0], points[0][1],
                points[1][0], points[1][1],
                points[2][0], points[2][1],
                points[3][0], points[3][1],
                points[4][0], points[4][1]);

        double scalingFactor = 0.5;
        asteroid.setBoundingCircleRadius(maxDistance * scalingFactor);
    }

    private void despawnAsteroid(GameData gameData, World world, Entity asteroid) {
        if (asteroid.getX() > gameData.getDisplayWidth() + 30 || asteroid.getX() + gameData.getDisplayWidth() + 30 < gameData.getDisplayWidth() || asteroid.getY() > gameData.getDisplayHeight() + 30 || asteroid.getY() + gameData.getDisplayHeight() + 30 < gameData.getDisplayHeight()) {
            world.removeEntity(asteroid);
        }
    }
}
