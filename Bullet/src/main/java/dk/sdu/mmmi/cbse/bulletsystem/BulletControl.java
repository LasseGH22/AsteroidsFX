package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.CommonPlayer.Player;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class BulletControl implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {

        //Calculate movement of bullet (Taken from player)
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

            //Removes bullet from world map when out of bounds
            if (bullet.getX() > gameData.getDisplayWidth() || bullet.getX() + gameData.getDisplayWidth() < gameData.getDisplayWidth() || bullet.getY() > gameData.getDisplayHeight() || bullet.getY() + gameData.getDisplayHeight() < gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData, World world) {
        Entity bullet = new Bullet();
        setShape(bullet);
        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());
        int[] color;


        EntityTag shooterTag = shooter.getTag();
        switch (shooterTag) {

            case ENEMY:
                bullet.setRotation(shooter.getRotation());
                bullet.setTag(EntityTag.ENEMY_BULLET);
                color = shooter.getRgb();
                bullet.setRgb(color[0],color[1],color[2]);

                getPlayerTargetSPIs().stream().findFirst().ifPresent(
                        spi -> {
                            double[] playerCoords = spi.getPlayerCoords(world);
                            bullet.setRotation(Math.toDegrees(Math.atan2(playerCoords[1] - bullet.getY(), playerCoords[0] - bullet.getX())));
                        }
                );

                break;

            case PLAYER:
                bullet.setRotation(shooter.getRotation());
                bullet.setTag(EntityTag.PLAYER_BULLET);
                color = ((Player) shooter).getOriginalRgb();
                bullet.setRgb(color[0],color[1],color[2]);
                break;
        }

        return bullet;
    }

    private void setShape(Entity entity) {
        entity.setPolygonCoordinates(2,0,0,2,-2,0,0,-2);
    }

    private Collection<? extends PlayerTargetSPI> getPlayerTargetSPIs() {
        return ServiceLoader.load(PlayerTargetSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
