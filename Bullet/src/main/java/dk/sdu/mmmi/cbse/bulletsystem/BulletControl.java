package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

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
        int[] color = shooter.getRgb();
        bullet.setRgb(color[0],color[1],color[2]);

        switch (shooter.getName()) {

            case ("enemy"):
                bullet.setRotation(shooter.getRotation());
                bullet.setName("enemybullet");
                System.out.println("enemy");
                for (Entity player : world.getEntities()) {
                    if (player.getName().equals("player")) {
                        bullet.setRotation(Math.toDegrees(Math.atan2(player.getY()- bullet.getY(), player.getX()-bullet.getX())));
                    }
                }
                break;

            case ("player"):
                bullet.setRotation(shooter.getRotation());
                bullet.setName("playerbullet");
                System.out.println("player");
                break;
        }

        return bullet;
    }

    private void setShape(Entity entity) {
        entity.setPolygonCoordinates(2,0,0,2,-2,0,0,-2);
    }
}
