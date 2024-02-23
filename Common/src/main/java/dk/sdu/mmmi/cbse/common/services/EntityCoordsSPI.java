package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;

public interface EntityCoordsSPI {

    double[] getCoords(Entity entity);
}
