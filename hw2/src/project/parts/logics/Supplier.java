package project.parts.logics;

import project.SimulationRunner;
import project.components.Robot;
import project.utility.Common;

import java.util.Arrays;
import java.util.List;

public class Supplier extends Logic
{
    @Override public void run ( Robot robot ) {

        synchronized (robot) {
            //this block for slow down the supplier
            try {
                robot.wait(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int rand_val;
            if (SimulationRunner.factory.productionLine.parts.size() == SimulationRunner.factory.productionLine.maxCapacity) {
                System.out.printf("Robot %02d : Production line is full, removing a random part from production line.%n", Common.get(robot, "serialNo"));
                rand_val = Common.random.nextInt(SimulationRunner.factory.productionLine.maxCapacity);
                SimulationRunner.factory.productionLine.parts.remove(rand_val);
                SimulationRunner.productionLineDisplay.repaint();
                System.out.printf("Robot %02d : Waking up waiting builders.%n", Common.get(robot, "serialNo"));
                for (Robot rb : SimulationRunner.factory.robots) {
                    if (Common.get(rb, "logic") != null && Common.get(rb, "payload") != null
                            && Common.get(rb, "arm") != null) {
                        if (Common.get(rb, "logic").getClass().getSimpleName().equals("Builder")) {
                            synchronized (rb) {
                                rb.notify(); // notify waiting builders
                            }
                        }
                    }
                }
            }
            else{
                System.out.printf( "Robot %02d : Supplying a random part on production line.%n",Common.get(robot, "serialNo"));
                //list consist of all types of parts except base
                List<String> parts = Arrays.asList("Arm","Camera","Gripper","MaintenanceKit","Welder","Builder", "Fixer", "Inspector", "Supplier");
                // random value for supplying random part
                rand_val=Common.random.nextInt(10);
                if(rand_val<9)
                    //supply random part
                    SimulationRunner.factory.productionLine.parts.add(SimulationRunner.factory.createPart(parts.get(rand_val)));
                else{
                    //supply base
                    SimulationRunner.factory.productionLine.parts.add(SimulationRunner.factory.createBase());
                }
                SimulationRunner.productionLineDisplay.repaint();
                System.out.printf( "Robot %02d : Waking up waiting builders.%n",Common.get(robot, "serialNo") );
                for (Robot rb : SimulationRunner.factory.robots) {
                    if (Common.get(rb, "logic") != null && Common.get(rb, "payload") != null
                            && Common.get(rb, "arm") != null) {
                        if (Common.get(rb, "logic").getClass().getSimpleName().equals("Builder")) {
                            synchronized (rb) {
                                rb.notify(); //notify waiting builders
                            }
                        }
                    }
                }
            }
        }
    }
}