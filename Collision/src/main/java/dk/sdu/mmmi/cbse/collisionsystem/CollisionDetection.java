package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetection implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisionEntity : world.getEntities()) {
                if (!entity.equals(collisionEntity) && colidesWith(entity,collisionEntity)) {

                    // Asteroid & Asteroid Collision
                    if (entity.getName().equals("Asteroid") && collisionEntity.getName().equals("Asteroid")) {
                        System.out.println("Asteroid & Asteroid Collision Detected");
                    }

                    /*
                    // Bullet & Asteroid Collision
                    if (entity.getClass().toString().contains("Bullet") && collisionEntity.getClass().toString().contains("Asteroid")) {
                        System.out.println("Asteroid & Bullet Collision Detected");
                    }

                    // Player & Asteroid Collision
                    if (entity.getClass().toString().contains("Player") && collisionEntity.getClass().toString().contains("Asteroid")) {
                        System.out.println("Player & Asteroid Collision Detected");
                    }

                    // Player & Enemy Collision
                    if (entity.getClass().toString().contains("Player") && collisionEntity.getClass().toString().contains("Enemy")) {
                        System.out.println("Player & Enemy Collision Detected");
                    }

                    // Player & Bullet Collision
                    if (entity.getClass().toString().contains("Player") && collisionEntity.getClass().toString().contains("Bullet")) {
                        System.out.println("Player & Bullet Collision Detected");
                    }

                    //Enemy & Bullet Collision
                    if (entity.getClass().toString().contains("Enemy") && collisionEntity.getClass().toString().contains("Bullet")) {
                        System.out.println("Enemy & Bullet Collision Detected");
                    }

                     */
                }
            }
        }
    }

    private boolean colidesWith(Entity entity1, Entity entity2) {
        double distance = Math.sqrt(Math.pow(entity1.getX() - entity2.getX(),2) + Math.pow(entity1.getY() - entity2.getY(),2));
        return distance <= entity1.getRadius() + entity2.getRadius();
    }
}
