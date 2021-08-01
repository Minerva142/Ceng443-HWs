package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.utility.Common;

import java.util.List;

public class Inspector extends Logic
{
    @Override public void run ( Robot robot ) {
        synchronized (robot) {
            //Check all robots in the robots band and if there is an broken part,add it into broken robots list and inform fixers about it
            for (Robot x : SimulationRunner.factory.robots) {
                if (Common.get(x, "logic") == null || Common.get(x, "payload") == null
                        || Common.get(x, "arm") == null) {
                    if (!SimulationRunner.factory.brokenRobots.contains(x)) {
                        SimulationRunner.factory.brokenRobots.add(x);
                        System.out.printf(
                                "Robot %02d : Detected a broken robot (%02d), adding it to broken robots list.%n",
                                Common.get(robot, "serialNo"),
                                Common.get(x, "serialNo")
                        );
                        System.out.printf("Robot %02d : Notifying waiting fixers.%n", Common.get(robot, "serialNo"));
                        for (Robot rb : SimulationRunner.factory.robots) {
                            if (Common.get(rb, "logic") != null && Common.get(rb, "payload") != null
                                    && Common.get(rb, "arm") != null) {
                                if (Common.get(rb, "logic").getClass().getSimpleName().equals("Fixer")) {
                                    synchronized (rb) {
                                        rb.notify(); // notify the waiting fixers about broken robot
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}