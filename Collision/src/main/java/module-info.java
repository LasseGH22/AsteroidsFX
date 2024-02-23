import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires Asteroid;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisionsystem.CollisionControlSystem;
}