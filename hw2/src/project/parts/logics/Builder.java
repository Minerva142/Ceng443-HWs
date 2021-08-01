package project.parts.logics;

import project.SimulationRunner;

import project.components.Robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import project.parts.Part;
import project.utility.Common;


public class Builder extends Logic
{
    @Override public void run ( Robot robot )
    {
        synchronized (robot){
           try {
               System.out.printf("Robot %02d : Builder cannot build anything, waiting!%n", Common.get(robot,"serialNo"));
               robot.wait(); // waits for supplier
               System.out.printf("Robot %02d : Builder woke up, going back to work.%n",Common.get(robot,"serialNo"));
               int isproduct=0; // counter for production
               // Base-arm production checker if it happen isproduct incremented by 1
               synchronized (SimulationRunner.factory.productionLine.parts) {
                       //temporary list for productionline for solve the dynamic list problem when doing any action about parts
                       List<Part> tmp = new ArrayList<>();
                       //add all parts into tmp
                       for(Part x : SimulationRunner.factory.productionLine.parts)
                           tmp.add(x);
                       for (Iterator<Part> iterator1 = tmp.iterator() ; iterator1.hasNext();) {
                           Part rb = iterator1.next();
                           if (rb.getClass().getSimpleName().equals("Base") && Common.get(rb,"arm")==null) {
                               for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator() ; iterator2.hasNext();) {
                                   Part x = iterator2.next();
                                   if (x.getClass().getSimpleName().equals("Arm")) {
                                           Common.set(rb, "arm", x);
                                           iterator2.remove();
                                           System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                           isproduct++;
                                           break;
                                   }
                               }
                           }
                       }
                       SimulationRunner.productionLineDisplay.repaint();
               }
               // (Base-arm) and payload checker if happen isprodcut incremented by 1
               if(isproduct != 1) {
                   synchronized (SimulationRunner.factory.productionLine.parts) {
                       //temporary list for productionline for solve the dynamic list problem when doing any action about parts
                       List<Part> tmp = new ArrayList<>();
                       //add all parts into tmp
                       for (Part x : SimulationRunner.factory.productionLine.parts)
                           tmp.add(x);
                       for (Iterator<Part> iterator1 = tmp.iterator(); iterator1.hasNext(); ) {
                           Part rb = iterator1.next();
                           if (rb.getClass().getSimpleName().equals("Base") && Common.get(rb, "arm") != null && Common.get(rb, "payload") == null) {
                               for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator(); iterator2.hasNext(); ) {
                                   Part x = iterator2.next();
                                   if (x.getClass().getSimpleName().equals("Camera")
                                           || x.getClass().getSimpleName().equals("MaintenanceKit")
                                           || x.getClass().getSimpleName().equals("Welder")
                                           || x.getClass().getSimpleName().equals("Gripper")) {
                                       Common.set(rb, "payload", x);
                                       iterator2.remove();
                                       System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                       isproduct++;
                                       break;
                                   }
                               }
                           }
                       }
                       SimulationRunner.productionLineDisplay.repaint();
                   }
               }
               // (Base-arm-payload) and logic checker if it happen isproduct incremented by 1
               if(isproduct != 1) {
                   synchronized (SimulationRunner.factory.productionLine.parts){
                       // temporary list for productionline for solve the dynamic list problem when doing any action about parts
                       List<Part> tmp = new ArrayList<>();
                       // add all parts into tmp
                       for (Part x : SimulationRunner.factory.productionLine.parts)
                           tmp.add(x);
                       for (Iterator<Part> iterator1 = tmp.iterator(); iterator1.hasNext(); ) {
                           Part rb = iterator1.next();
                           if(rb.getClass().getSimpleName().equals("Base")
                                   && Common.get(rb, "arm") != null
                                   && Common.get(rb, "payload") != null
                                   && Common.get(rb, "logic") == null){
                                   String payload_name = Common.get(rb,"payload").getClass().getSimpleName();
                                   //check Camera
                                   if(payload_name.equals("Camera")) {
                                       for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator(); iterator2.hasNext(); ) {
                                           Part x = iterator2.next();
                                           if(x.getClass().getSimpleName().equals("Inspector")){
                                               Common.set(rb,"logic",x);
                                               iterator2.remove();
                                               System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                               isproduct++;
                                               break;
                                           }
                                       }
                                   }
                                   //check Gripper
                                   else if(payload_name.equals("Gripper")) {
                                       for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator(); iterator2.hasNext(); ) {
                                           Part x = iterator2.next();
                                           if(x.getClass().getSimpleName().equals("Supplier")){
                                               Common.set(rb,"logic",x);
                                               iterator2.remove();
                                               System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                               isproduct++;
                                               break;
                                           }
                                       }
                                   }
                                   //check MaintenanceKit
                                   else if(payload_name.equals("MaintenanceKit")) {
                                       for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator(); iterator2.hasNext(); ) {
                                           Part x = iterator2.next();
                                           if(x.getClass().getSimpleName().equals("Fixer")){
                                               Common.set(rb,"logic",x);
                                               iterator2.remove();
                                               System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                               isproduct++;
                                               break;
                                           }
                                       }
                                   }
                                   //check welder
                                   else if(payload_name.equals("Welder")) {
                                       for (Iterator<Part> iterator2 = SimulationRunner.factory.productionLine.parts.iterator(); iterator2.hasNext(); ) {
                                           Part x = iterator2.next();
                                           if(x.getClass().getSimpleName().equals("Builder")){
                                               Common.set(rb,"logic",x);
                                               iterator2.remove();
                                               System.out.printf("Robot %02d : Builder attached some parts or relocated a completed robot.%n", Common.get(robot, "serialNo"));
                                               isproduct++;
                                               break;
                                           }
                                       }
                                   }
                           }
                       }
                       SimulationRunner.productionLineDisplay.repaint();
                   }
               }
               // last checker is about full robot. If is there any completed robot it takes that and randomly place
               // it into storage or robots.
               if(isproduct!=1) {
                   synchronized (SimulationRunner.factory.productionLine.parts){
                       synchronized (SimulationRunner.factory.storage) {
                           synchronized (SimulationRunner.factory.robots) {
                               for (Iterator<Part> iterator = SimulationRunner.factory.productionLine.parts.iterator(); iterator.hasNext(); ) {
                                   Part rb = iterator.next();
                                   if (rb.getClass().getSimpleName().equals("Base")
                                           && Common.get(rb, "arm") != null
                                           && Common.get(rb, "payload") != null
                                           && Common.get(rb, "logic") != null) {
                                       int rand_val = Common.random.nextInt(10);
                                       if (rand_val < 6) {
                                           //place checking if there is no place in the storage then try to add it into robots
                                           if (SimulationRunner.factory.storage.robots.size() < SimulationRunner.factory.storage.maxCapacity) {
                                               SimulationRunner.factory.storage.robots.add((Robot) rb);
                                               iterator.remove();
                                               isproduct++;
                                               break;
                                           } else if (SimulationRunner.factory.robots.size() < SimulationRunner.factory.maxRobots) {
                                               SimulationRunner.factory.robots.add((Robot) rb);
                                               new Thread((Robot) rb).start();
                                               iterator.remove();
                                               isproduct++;
                                               break;
                                           }
                                       } else {
                                           //place checking if there is no place in the robots then try to add it into storage
                                           if (SimulationRunner.factory.robots.size() < SimulationRunner.factory.maxRobots) {
                                               SimulationRunner.factory.robots.add((Robot) rb);
                                               new Thread((Robot) rb).start();
                                               iterator.remove();
                                               isproduct++;
                                               break;
                                           } else if (SimulationRunner.factory.storage.robots.size() < SimulationRunner.factory.storage.maxCapacity) {
                                               SimulationRunner.factory.storage.robots.add((Robot) rb);
                                               iterator.remove();
                                               isproduct++;
                                               break;
                                           }
                                       }
                                   }
                               }
                               // repaint the displays
                               SimulationRunner.robotsDisplay.repaint();
                               SimulationRunner.storageDisplay.repaint();
                           }
                       }
                   }
               }

               //stop production if there is no any place for completed robots
               if (SimulationRunner.factory.robots.size() == SimulationRunner.factory.maxRobots
                       && SimulationRunner.factory.storage.robots.size() == SimulationRunner.factory.storage.maxCapacity) {
                   SimulationRunner.factory.initiateStop();
               }
           }catch (InterruptedException e) {
               e.printStackTrace();
           }

        }
    }
}