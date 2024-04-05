package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.CommonEnemy.Enemy;
import dk.sdu.mmmi.cbse.CommonEnemy.EnemySPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService, EnemySPI {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    // Creates enemy ship entity
    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();

        // Sets shape
        enemyShip.setPolygonCoordinates(12, -1, 8, -1, 8, -3, 6, -3, 6, -5, -2, -5, -2, -7, 0, -7, 0, -9, -10, -9, -10, -5, -8, -5, -8, -3, -6, -3, -6, -1, -10, -1, -10, 1, -6, 1, -6, 3, -8, 3, -8, 5, -10, 5, -10, 9, 0, 9, 0, 7, -2, 7, -2, 5, 2, 5, 2, 1, 4, 1, 4, -1, 2, -1, 2, -3, 4, -3, 4, -1, 6, -1, 6, 1, 4, 1, 4, 3, 2, 3, 2, 5, 6, 5, 6, 3, 8, 3, 8, 1, 12, 1);

        // Sets spawn coordinates
        resetEnemy(enemyShip,gameData);

        enemyShip.setBoundingCircleRadius(15);

        // Sets color
        enemyShip.setRgb(158, 39, 30);

        // Sets Tag
        enemyShip.setTag(EntityTag.ENEMY);
        return enemyShip;
    }

    // Removes all enemy ship entities when application is stopped
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

    @Override
    public void resetEnemy(Entity entity, GameData gameData) {
        Enemy enemy = (Enemy) entity;
        Random random = new Random();

        int chooser = random.nextInt(5);
        int fourthX = gameData.getDisplayWidth() / 4;
        int fourthY = gameData.getDisplayHeight() / 4;

        switch (chooser) {

            // Bottom Left
            case 1:
                enemy.setX(fourthX);
                enemy.setY(fourthY * 3);
                break;

            // Bottom Right
            case 2:
                enemy.setX(fourthX * 3);
                enemy.setY(fourthY * 3);
                break;

            // Top Left
            case 3:
                enemy.setX(fourthX);
                enemy.setY(fourthY);
                break;

            // Top Right
            case 4:
                enemy.setX(fourthX * 3);
                enemy.setY(fourthY);
                break;
        }

        enemy.setImmunityFrames(3000);
        enemy.markCollision();
    }
}
