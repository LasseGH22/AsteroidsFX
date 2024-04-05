
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerSPI;
import dk.sdu.mmmi.cbse.CommonPlayer.PlayerTargetSPI;

module Player {
    exports dk.sdu.mmmi.cbse.playersystem;
    requires Common;
    requires CommonBullet;
    requires CommonPlayer;
    requires javafx.graphics;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    opens dk.sdu.mmmi.cbse.playersystem to javafx.graphics;
    provides IGamePluginService with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides PlayerSPI with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    provides PlayerTargetSPI with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
}
