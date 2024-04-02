import dk.sdu.mmmi.cbse.collisionsystem.CollisionDetection;
import dk.sdu.mmmi.cbse.common.data.Entity;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AsteroidXCollisionTest {

    private CollisionDetection collisionDetection;

    @Before
    public void setUp() {
        collisionDetection = new CollisionDetection();
    }

    @Test
    public void testEntitiesFarApartDoNotCollide() {
        Entity entity1 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        entity1.setBoundingCircleRadius(1);

        Entity entity2 = new Entity();
        entity2.setX(5);
        entity2.setY(5);
        entity2.setBoundingCircleRadius(1);

        assertFalse(collisionDetection.collidesWith(entity1, entity2));
    }

    @Test
    public void testEntitiesTouchingEdgeToEdgeCollide() {
        Entity entity1 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        entity1.setBoundingCircleRadius(1);

        Entity entity2 = new Entity();
        entity2.setX(2);
        entity2.setY(0);
        entity2.setBoundingCircleRadius(1);

        assertTrue(collisionDetection.collidesWith(entity1, entity2));
    }

    @Test
    public void testEntitiesOverlapCollide() {
        Entity entity1 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        entity1.setBoundingCircleRadius(2);

        Entity entity2 = new Entity();
        entity2.setX(1);
        entity2.setY(1);
        entity2.setBoundingCircleRadius(2);

        assertTrue(collisionDetection.collidesWith(entity1, entity2));
    }

    @Test
    public void testEntityCollidesWithItself() {
        Entity entity = new Entity();
        entity.setX(0);
        entity.setY(0);
        entity.setBoundingCircleRadius(1);

        assertTrue(collisionDetection.collidesWith(entity, entity));
    }
}
