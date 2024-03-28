package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.EntityTag;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

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
                            case ("ASTEROID/ASTEROID"):

                                break;

                            case ("PLAYER/ASTEROID"):

                                break;

                            case ("PLAYER_BULLET/ASTEROID"):
                                collisionEntity.setRotation(collisionEntity.getRotation() - 180);
                                successfulCollision = true;
                                break;

                            case ("PLAYER/ENEMY_BULLET"):

                                break;

                            case ("ENEMY/ASTEROID"):

                                break;

                            case ("ENEMY_BULLET/ASTEROID"):

                                break;

                            case ("ENEMY/PLAYER_BULLET"):

                                break;

                            case ("PLAYER/ENEMY"):

                                break;

                        }

                        if (successfulCollision) {
                            entity.markCollision();
                            collisionEntity.markCollision();
                        }
                    }
                }
            }
        }
    }

    private boolean collidesWith(Entity entity1, Entity entity2) {
        double distance = Math.sqrt(Math.pow(entity1.getX() - entity2.getX(),2) + Math.pow(entity1.getY() - entity2.getY(),2));
        return distance <= entity1.getBoundingCircleRadius() + entity2.getBoundingCircleRadius();
    }
}
