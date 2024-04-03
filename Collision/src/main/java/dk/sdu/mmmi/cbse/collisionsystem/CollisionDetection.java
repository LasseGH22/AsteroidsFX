package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.asteroidsystem.Asteroid;
import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemySPI;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.playersystem.PlayerSPI;
import dk.sdu.mmmi.cbse.playersystem.PlayerTargetSPI;


import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetection implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisionEntity : world.getEntities()) {

                if  (entity.equals(collisionEntity)) {
                    continue;
                }

                if (collidesWith(entity,collisionEntity)) {
                    if (entity.canCollide() && collisionEntity.canCollide()) {
                        boolean successfulCollision = false;

                        String collisionBuddies = entity.getTag().toString() + "/" + collisionEntity.getTag().toString();

                        switch (collisionBuddies) {
                            case ("ASTEROID/ASTEROID"), ("ASTEROID/SPLIT_ASTEROID"), ("SPLIT_ASTEROID/SPLIT_ASTEROID"):
                                getAsteroidSPIs().stream().findFirst().ifPresent(
                                        spi -> spi.asteroidBounce((Asteroid) entity, (Asteroid) collisionEntity)
                                );

                                break;

                            case ("PLAYER/ASTEROID"):
                                // Player loses 1 life and resets to center
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.removeLife(entity);
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );

                                // Asteroid gets removed and splits
                                world.removeEntity(collisionEntity);
                                if (collisionEntity.getTag().equals(EntityTag.ASTEROID)) {
                                    getAsteroidSPIs().stream().findFirst().ifPresent(
                                            spi -> spi.asteroidSplit(collisionEntity,world)
                                    );
                                }

                                break;

                            case ("PLAYER_BULLET/ASTEROID"), ("PLAYER_BULLET/SPLIT_ASTEROID"):
                                world.removeEntity(entity);
                                world.removeEntity(collisionEntity);

                                if (collisionEntity.getTag().equals(EntityTag.ASTEROID)) {
                                    getAsteroidSPIs().stream().findFirst().ifPresent(
                                            spi -> spi.asteroidSplit(collisionEntity,world)
                                    );
                                }

                                break;

                            case ("PLAYER/ENEMY_BULLET"):
                                world.removeEntity(collisionEntity);
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.removeLife(entity);
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );

                                break;


                            case ("ENEMY/ASTEROID"):
                                getEnemySPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetEnemy(entity,gameData);
                                        }
                                );

                                world.removeEntity(collisionEntity);
                                if (collisionEntity.getTag().equals(EntityTag.ASTEROID)) {
                                    getAsteroidSPIs().stream().findFirst().ifPresent(
                                            spi -> spi.asteroidSplit(collisionEntity,world)
                                    );
                                }

                                break;

                            case ("ENEMY_BULLET/ASTEROID"):
                                world.removeEntity(entity);
                                getPlayerTargetSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            double[] playerCoords = spi.getPlayerCoords(world);
                                            collisionEntity.setRotation(Math.toDegrees(Math.atan2(playerCoords[1] - collisionEntity.getY(), playerCoords[0] - collisionEntity.getX())));
                                        }
                                );

                                break;

                            case ("ENEMY/PLAYER_BULLET"):
                                world.removeEntity(collisionEntity);

                                getEnemySPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetEnemy(entity,gameData);
                                        }
                                );

                                break;

                            case ("PLAYER/ENEMY"):
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.removeLife(entity);
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );

                                getEnemySPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetEnemy(collisionEntity,gameData);
                                        }
                                );
                                break;

                        }
                    }
                }
            }
        }
    }

    public boolean collidesWith(Entity entity1, Entity entity2) {
        double distance = Math.sqrt(Math.pow(entity1.getX() - entity2.getX(),2) + Math.pow(entity1.getY() - entity2.getY(),2));
        return distance <= entity1.getBoundingCircleRadius() + entity2.getBoundingCircleRadius();
    }

    private Collection<? extends AsteroidSPI> getAsteroidSPIs() {
        return ServiceLoader.load(AsteroidSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends PlayerSPI> getPlayerSPIs() {
        return ServiceLoader.load(PlayerSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends PlayerTargetSPI> getPlayerTargetSPIs() {
        return ServiceLoader.load(PlayerTargetSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends EnemySPI> getEnemySPIs() {
        return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
