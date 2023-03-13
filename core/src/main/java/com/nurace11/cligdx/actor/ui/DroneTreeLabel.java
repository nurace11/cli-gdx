package com.nurace11.cligdx.actor.ui;

import com.kotcrab.vis.ui.widget.VisLabel;
import com.nurace11.cligdx.entity.Drone;

public class DroneTreeLabel extends VisLabel {
//    Drone drone;

    public DroneTreeLabel() {
    }

    public DroneTreeLabel(Drone drone) {
        super(drone.toString());
//        this.drone = drone;
    }

    public DroneTreeLabel(CharSequence text) {
        super(text);
    }

//    public Drone getDrone() {
//        return drone;
//    }
}
