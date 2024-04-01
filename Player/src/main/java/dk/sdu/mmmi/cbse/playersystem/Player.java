package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import javafx.application.Application;
import javafx.application.Platform;

import java.util.Random;

/**
 *
 * @author Emil
 */
public class Player extends Entity implements PlayerSPI {

    private int lives;

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public void resetPlayer(Entity entity,GameData gameData) {
        Player player = (Player) entity;
        player.setX((double) gameData.getDisplayWidth() / 2);
        player.setY((double) gameData.getDisplayHeight() / 2);

        player.setImmunityFrames(1000);
        player.markCollision();
    }

    @Override
    public void removeLife(Entity entity) {
        Player player = (Player) entity;
        Random random = new Random();
        player.setLives(player.getLives() - 1);
        player.setRotation(random.nextInt(360));
        System.out.println(player.getLives());

        if (player.getLives() < 1) {
            Platform.exit();
        }
    }
}
