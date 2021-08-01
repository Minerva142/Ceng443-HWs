package project.parts.logics;

import project.SimulationRunner;
import project.components.Factory;
import project.components.Robot;
import project.utility.Common;

public class Fixer extends Logic
{
    @Override public void run ( Robot robot )
    {
        synchronized (robot) {
                try {
                    System.out.printf("Robot %02d : Nothing to fix, waiting!%n",
                            Common.get(robot, "serialNo")); //message
                    robot.wait();
                    robot.wait(400); // for visualization fixer waits a little bit s
                    System.out.printf("Robot %02d : Fixer woke up, going back to work.%n",
                            Common.get(robot, "serialNo")); // message
                    synchronized (SimulationRunner.factory.brokenRobots) {
                        // in below piece of code checks the brokenrobots list if there is an broken one
                        // it finds the correct part and fix the robot
                        if (!SimulationRunner.factory.brokenRobots.isEmpty()) {
                            Robot broken_one = SimulationRunner.factory.brokenRobots.get(0);
                            //check null condition
                            if(broken_one!=null) {
                                if (Common.get(broken_one, "arm") == null) {
                                    Common.set(broken_one, "arm", Factory.createPart("Arm"));
                                }
                                if (Common.get(broken_one, "payload") == null) {
                                    String logic_name = Common.get(broken_one, "logic").getClass().getSimpleName();
                                    if (logic_name.equals("Fixer")) {
                                        Common.set(broken_one, "payload", Factory.createPart("MaintenanceKit"));
                                    }
                                    if (logic_name.equals("Builder")) {
                                        Common.set(broken_one, "payload", Factory.createPart("Welder"));
                                    }
                                    if (logic_name.equals("Inspector")) {
                                        Common.set(broken_one, "payload", Factory.createPart("Camera"));
                                    }
                                    if (logic_name.equals("Supplier")) {
                                        Common.set(broken_one, "payload", Factory.createPart("Gripper"));
                                    }
                                }
                                if (Common.get(broken_one, "logic") == null) {
                                    String payload_name = Common.get(broken_one, "payload").getClass().getSimpleName();
                                    if (payload_name.equals("Gripper")) {
                                        Common.set(broken_one, "logic", Factory.createPart("Supplier"));
                                    }
                                    if (payload_name.equals("Welder")) {
                                        Common.set(broken_one, "logic", Factory.createPart("Builder"));
                                    }
                                    if (payload_name.equals("Camera")) {
                                        Common.set(broken_one, "logic", Factory.createPart("Inspector"));
                                    }
                                    if (payload_name.equals("MaintenanceKit")) {
                                        Common.set(broken_one, "logic", Factory.createPart("Fixer"));
                                    }
                                }
                                //remove fixed one from broken robots list
                                SimulationRunner.factory.brokenRobots.remove(broken_one);
                                Common.set(broken_one, "isWaiting", false); // change the value of iswaiting
                                System.out.printf("Robot %02d : Fixed and waken up robot (%02d).%n", Common.get(robot, "serialNo"), Common.get(broken_one, "serialNo"));
                                synchronized (broken_one) {
                                    broken_one.notify(); // for starting again the fixed robot
                                }
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}