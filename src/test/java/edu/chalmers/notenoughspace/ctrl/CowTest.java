package edu.chalmers.notenoughspace.ctrl;

import edu.chalmers.notenoughspace.core.BeamState;
import edu.chalmers.notenoughspace.core.Cow;
import edu.chalmers.notenoughspace.core.CowMood;
import edu.chalmers.notenoughspace.core.PlanetaryInhabitant;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vibergf on 11/05/2017.
 */
public class CowTest {

    @Test
    public void cowTest(){
        Cow cow = new Cow();
        assertEquals(CowMood.CALM, cow.getMood());
        assertEquals(BeamState.NOT_IN_BEAM, cow.isInBeam());

        PlanetaryInhabitant ship = new TestInhabitant(1f, 0f, 0f);
        cow.setPlanetaryInhabitant(new TestInhabitant(1f, 0f, 0f));

        for(int tpf = 1 ; tpf < 10000 ; tpf += 50){
            cow.update(ship, tpf);
            assertEquals(CowMood.SCARED, cow.getMood());

            cow.setPlanetaryInhabitant(new TestInhabitant(1f, 0f, 0f));
        }

        

    }
}
