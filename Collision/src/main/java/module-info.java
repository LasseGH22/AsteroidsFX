import dk.sdu.mmmi.cbse.CommonAsteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses AsteroidSPI;
    uses dk.sdu.mmmi.cbse.CommonPlayer.PlayerSPI;
    uses dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;
    uses dk.sdu.mmmi.cbse.CommonEnemy.EnemySPI;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    requires CommonEnemy;
    requires CommonPlayer;
    requires CommonAsteroid;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
}