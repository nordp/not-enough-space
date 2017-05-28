package edu.chalmers.notenoughspace.core.entity.beamable;

import edu.chalmers.notenoughspace.core.entity.beamable.Cow;
import edu.chalmers.notenoughspace.core.entity.beamable.CowMood;
import edu.chalmers.notenoughspace.core.move.PlanetaryInhabitant;
import edu.chalmers.notenoughspace.ctrl.TestInhabitant;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vibergf on 11/05/2017.
 */
public class CowTest {

    float reactionDistance;
    int maxStamina;
    int staminaReduction;
    int sprintCooldown;

    @Before
    public void setUp(){

        //Values taken from the Cow class to help with testing.
        reactionDistance = 3f;
        maxStamina = 3000;
        staminaReduction = 1000;
        sprintCooldown = 3000;
    }

    @Test
    public void cowTest(){
        Cow cow = new Cow();
        assertEquals(CowMood.CALM, cow.getMood());
        assertEquals(false, cow.isInBeam());



        float xpos = reactionDistance/2 - 0.5f; // To make sure the cow can't run away from the ship

        PlanetaryInhabitant ship = new TestInhabitant(xpos, 0f, 0f);
        cow.setPlanetaryInhabitant(new TestInhabitant(xpos, 0f, 0f));

        /*
            Testing whether tpf has an effect on the cows mood. Ideally the cow should get tired if tpf > max_stamina, but
            the current cow implementation only updates the mood at the start of each update, before reducing stamina.
        */
        for(float tpf = 0.001f ; tpf < 10 ; tpf += 0.01){
            cow.update(ship, tpf);
            assertEquals(CowMood.SCARED, cow.getMood());

            cow = new Cow();
            cow.setPlanetaryInhabitant(new TestInhabitant(xpos, 0f, 0f));
        }

        /*
            Testing the stamina and sprint cooldown times. The extra update calls are to make sure the cows update their mood.
        */
        ship = new TestInhabitant(xpos + reactionDistance + 0.5f, 0f, 0f);
        cow.update(ship, 0.01f);
        assertEquals(CowMood.CALM, cow.getMood());

        ship = new TestInhabitant(xpos, 0f, 0f);
        cow.update(ship, maxStamina/staminaReduction - 0.01f);
        assertEquals(CowMood.SCARED, cow.getMood());

        cow.update(ship, 0.02f);
        cow.update(ship, 0f);
        assertEquals(CowMood.TIRED, cow.getMood());

        cow.update(ship, sprintCooldown/staminaReduction);
        cow.update(ship, 0f);
        assertEquals(CowMood.SCARED, cow.getMood());

    }
}
