package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {


        for (Entity enemy : world.getEntities(Enemy.class)) {
            if (Math.random() > 0.8) {
                if (Math.random() > 0.5) {
                    enemy.setRotation(enemy.getRotation() - 10);
                } else if (Math.random() > 0.5) {
                    enemy.setRotation(enemy.getRotation() + 10);
                }
            }




            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);


            if (Math.random() > 0.975) {
                System.out.println(Math.random());
                for (BulletSPI bulletSPI : getBulletSPIs()) {
                    Entity bullet = bulletSPI.createBullet(enemy,gameData);
                    world.addEntity(bullet);
                }
            }

            if (enemy.getX() > gameData.getDisplayWidth() || enemy.getX() + gameData.getDisplayWidth() < gameData.getDisplayWidth()) {
                enemy.setRotation(enemy.getRotation() + 180);
            }
            if (enemy.getY() > gameData.getDisplayHeight() || enemy.getY() + gameData.getDisplayHeight() < gameData.getDisplayHeight()) {
                enemy.setRotation(enemy.getRotation() + 180);
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
