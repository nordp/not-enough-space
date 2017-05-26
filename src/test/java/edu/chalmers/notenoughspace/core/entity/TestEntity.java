package edu.chalmers.notenoughspace.core.entity;

import edu.chalmers.notenoughspace.core.move.ZeroGravityStrategy;

/**
 * Created by Vibergf on 26/05/2017.
 */
public class TestEntity extends Entity {
    public TestEntity() {
        super(new ZeroGravityStrategy());
    }
}
