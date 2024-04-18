package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.CommonPlayer.Player;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerCollisionSPI;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.application.Platform;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService, PlayerCollisionSPI, PlayerTargetSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {

            // Movement for player
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX);
                player.setY(player.getY() + changeY);
            }
            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                for (BulletSPI bulletSPI : getBulletSPIs()) {
                    Entity bullet = bulletSPI.createBullet(player,gameData,world);
                    world.addEntity(bullet);
                }
            }

            //Border control for player
            if (player.getX() < 0) {
                player.setX(1);
            }

            if (player.getX() > gameData.getDisplayWidth()) {
                player.setX(gameData.getDisplayWidth()-1);
            }

            if (player.getY() < 0) {
                player.setY(1);
            }

            if (player.getY() > gameData.getDisplayHeight()) {
                player.setY(gameData.getDisplayHeight()-1);
            }

            // Immunity graphics
            if (player.canCollide()) {
                player.setRgb(((Player) player).getOriginalRgb());
            } else {
                player.setRgb(((Player) player).getCollisionRgb());
            }
        }
    }

    @Override
    public void resetPlayer(Entity entity,GameData gameData) {
        Player player = (Player) entity;
        player.setX((double) gameData.getDisplayWidth() / 2);
        player.setY((double) gameData.getDisplayHeight() / 2);
        player.setRotation(new Random().nextInt(360));

        player.setImmunityFrames(3000);
        player.markCollision();
    }

    /*
    @Override
    public void removeLife(Entity entity) {
        Player player = (Player) entity;
        player.setLives(player.getLives() - 1);

        if (player.getLives() < 1) {
            Platform.exit();
        }
    }

     */

    @Override
    public double[] getPlayerCoords(World world) {
        double[] coords = new double[2];
        for (Entity entity : world.getEntities(Player.class)) {
            coords[0] = entity.getX();
            coords[1] = entity.getY();
        }

        return coords;
    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
