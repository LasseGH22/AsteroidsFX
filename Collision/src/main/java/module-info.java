import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses AsteroidSPI;
    uses dk.sdu.mmmi.cbse.playersystem.PlayerSPI;
    uses dk.sdu.mmmi.cbse.playersystem.PlayerTargetSPI;
    exports dk.sdu.mmmi.cbse.collisionsystem;
    requires Common;
    requires Asteroid;
    requires Player;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
}