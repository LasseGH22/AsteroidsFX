import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Bullet {
    uses dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;
    provides IGamePluginService with dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
    provides BulletSPI with dk.sdu.mmmi.cbse.bulletsystem.BulletControl;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.bulletsystem.BulletControl;
}