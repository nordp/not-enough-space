package edu.chalmers.notenoughspace.core.entity.ship;

import com.google.common.eventbus.Subscribe;
import edu.chalmers.notenoughspace.core.entity.beamable.BeamableEntity;
import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.event.BeamableStoredEvent;
import edu.chalmers.notenoughspace.event.Bus;
import edu.chalmers.notenoughspace.event.StorageChangedEvent;

import java.util.*;

/**
 * The storage storing the objects beamed up by the ship.
 */
public class Storage {

    private List<BeamableEntity> storedObjects;

    public Storage() {
        storedObjects = new ArrayList<BeamableEntity>();

        Bus.getInstance().register(this);
        Bus.getInstance().post(new StorageChangedEvent(getScore(), getNumberOfCows()));
    }


    public int getScore(){
        float score = 0;
        for (BeamableEntity b : storedObjects) {
            if (b instanceof Cow) {
                Cow cow = (Cow) b;
                float weight = cow.getWeight();
                score += weight;
            }
        }
        return (int) (score * 100000);
    }

    public int getNumberOfCows() {
        int count = 0;
        for (BeamableEntity b : storedObjects) {
            if (b instanceof Cow) {
                count++;
            }
        }
        return count;
    }

    public float getTotalWeight() {
        float totalWeight = 0;
        for (BeamableEntity b : storedObjects) {
            totalWeight += b.getWeight();
        }
        return totalWeight;
    }

    @Subscribe
    public void entityStored(BeamableStoredEvent event) {
        if (!storedObjects.contains(event.getBeamable())) {
            storedObjects.add(event.getBeamable());
            Bus.getInstance().post(new StorageChangedEvent(getScore(), getNumberOfCows()));
        }
    }

}
