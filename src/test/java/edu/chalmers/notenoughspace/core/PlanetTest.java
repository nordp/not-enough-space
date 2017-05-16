package edu.chalmers.notenoughspace.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Phnor on 2017-05-13.
 */
public class PlanetTest {
    Planet planet;

    @Before
    public void setUp() throws Exception {
        planet = new Planet();
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void populate() throws Exception {
        for (int i = 0; i < 100; i++) {
            planet.populate(i, i, i, i);
        }
    }

    @Test
    public void getPlanetaryInhabitant() throws Exception {
    }

    @Test
    public void setPlanetaryInhabitant() throws Exception {
    }

}