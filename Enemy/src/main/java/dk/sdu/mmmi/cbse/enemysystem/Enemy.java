package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;

import java.util.Random;

public class Enemy extends Entity implements EnemySPI {

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
