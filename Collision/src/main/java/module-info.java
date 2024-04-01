import dk.sdu.mmmi.cbse.asteroidsystem.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    uses AsteroidSPI;
    requires Common;
    requires Asteroid;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
}