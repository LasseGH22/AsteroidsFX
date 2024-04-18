package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.CommonAsteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.CommonEnemy.EnemySPI;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerCollisionSPI;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class CollisionDetection implements IPostEntityProcessingService {
    HttpClient httpClient = HttpClient.newHttpClient();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisionEntity : world.getEntities()) {

                if  (entity.equals(collisionEntity)) {
                    continue;
                }

                if (collidesWith(entity,collisionEntity)) {
                    if (entity.canCollide() && collisionEntity.canCollide()) {

                        String collisionBuddies = entity.getTag().toString() + "/" + collisionEntity.getTag().toString();

                        int scoreAdd = 0;
                        boolean scoreUpdate = false;
                        boolean playerLivesUpdate = false;

                        switch (collisionBuddies) {
                            case ("ASTEROID/ASTEROID"), ("ASTEROID/SPLIT_ASTEROID"), ("SPLIT_ASTEROID/SPLIT_ASTEROID"):
                                getAsteroidSPIs().stream().findFirst().ifPresent(
                                        spi -> spi.asteroidBounce(entity, collisionEntity)
                                );

                                break;

                            case ("PLAYER/ASTEROID"), ("PLAYER/SPLIT_ASTEROID"):
                                // Player loses 1 life and resets to center
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );
                                playerLivesUpdate = true;

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

                                scoreUpdate = true;
                                scoreAdd = 10;
                                break;

                            case ("PLAYER/ENEMY_BULLET"):
                                world.removeEntity(collisionEntity);
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );

                                playerLivesUpdate = true;
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

                                scoreUpdate = true;
                                scoreAdd = 50;
                                break;

                            case ("PLAYER/ENEMY"):
                                getPlayerSPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetPlayer(entity,gameData);
                                        }
                                );
                                playerLivesUpdate = true;

                                getEnemySPIs().stream().findFirst().ifPresent(
                                        spi -> {
                                            spi.resetEnemy(collisionEntity,gameData);
                                        }
                                );
                                break;

                        }

                        if (playerLivesUpdate) {
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("http://localhost:8080/attributes/lives/decrement/1"))
                                    .PUT(HttpRequest.BodyPublishers.ofString(""))
                                    .build();
                            try {
                                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                            } catch (IOException | InterruptedException e) {

                            }
                        }

                        if (scoreUpdate) {
                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create("http://localhost:8080/attributes/score/update/" + scoreAdd))
                                    .PUT(HttpRequest.BodyPublishers.ofString(""))
                                    .build();
                            try {
                                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                            } catch (IOException | InterruptedException e) {

                            }
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

    private Collection<? extends PlayerCollisionSPI> getPlayerSPIs() {
        return ServiceLoader.load(PlayerCollisionSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends PlayerTargetSPI> getPlayerTargetSPIs() {
        return ServiceLoader.load(PlayerTargetSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends EnemySPI> getEnemySPIs() {
        return ServiceLoader.load(EnemySPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
