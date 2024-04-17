import dk.sdu.mmmi.cbse.CommonAsteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerCollisionSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses AsteroidSPI;
    uses PlayerCollisionSPI;
    uses dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;
    uses dk.sdu.mmmi.cbse.CommonEnemy.EnemySPI;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    requires CommonEnemy;
    requires CommonPlayer;
    requires CommonAsteroid;
    requires java.net.http;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
}