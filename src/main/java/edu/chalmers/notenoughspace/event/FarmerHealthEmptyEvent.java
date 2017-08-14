package edu.chalmers.notenoughspace.event;

import edu.chalmers.notenoughspace.core.entity.enemy.Farmer;

/**
     * Event fired when the farmer runs out of health.
     */
    public class FarmerHealthEmptyEvent {
        private final Farmer farmer;

        public FarmerHealthEmptyEvent(Farmer farmer) {
            this.farmer = farmer;
            System.out.println("FarmerHealthEmptyEvent fired.");
        }

        public Farmer getFarmer(){ return farmer;}

    }

