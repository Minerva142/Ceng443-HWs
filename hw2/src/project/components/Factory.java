package project.components;

import project.parts.Base;
import project.parts.Part;
import project.utility.Common;
import project.utility.SmartFactoryException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Factory
{
    private static int nextSerialNo = 1 ;


    public static Base createBase ()
    {
        //firstly I want to say that, ı decided to hide the creator methods in common which are base_creator,part_creator
        // because I want to use them with reflection. However I dont want to be busy about smart_fuc exceptions while I calling them with reflection
        //invoke the private method base_creator and call the smart_fuc which is in common
        //then create new objects with these methods
        nextSerialNo++;
        try {
            Method base_creator = Common.class.getDeclaredMethod("base_creator",int.class);
            base_creator.setAccessible(true);
            return (Base) base_creator.invoke(null , nextSerialNo-1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw (SmartFactoryException) Common.smart_fac("Failed: createBase!");
            //* * * * * * * * * * * extra part * * * * * * * * * * * * * *
            //if smart_fuc method is private, I will use the code piece below for invoke the private method in Common.
            // I didn't understand this part fully so
            //I wanted to add the code snippet in the comment below
            // If there is a wrong approach as I am using now, I would appreciate it if you can take the following comment into account.
            //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
            /*
            try {
                Method smart_fac = Common.class.getDeclaredMethod("smart_fac");
                smart_fac.setAccessible(true);
                throw (SmartFactoryException) smart_fac.invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
            */
        }
    }

    public static Part createPart (String name )
    {
        //invoke the method in Common with use reflection to access private method part_creator
        //then create new objects with these methods
        // Call smart_fuc method in Common which is public static method for create new instance of smart factory exception
        try {
            Method part_creator = Common.class.getDeclaredMethod("part_creator", String.class);
            part_creator.setAccessible(true);
            return (Part) part_creator.invoke(null , name);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw (SmartFactoryException) Common.smart_fac("Failed: createPart!");
            //* * * * * * * * * * * extra part * * * * * * * * * * * * * *
            //if smart_fuc method is private, I will use the code piece below for invoke the private method in Common
            // I didn't understand this part fully.
            //I wanted to add the code snippet in the comment below
            // If there is a wrong approach as I am using now, I would appreciate it if you can take the following comment into account.
            //* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
            /*
            try {
                Method smart_fac = Common.class.getDeclaredMethod("smart_fac",String.class);
                smart_fac.setAccessible(true);
                throw (SmartFactoryException) smart_fac.invoke(null,"Failed: createPart!");
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
            */
        }
    }

    public  int            maxRobots      ;
    public List<Robot>     robots         ;
    public ProductionLine  productionLine ;
    public  Storage        storage        ;
    public  List<Robot>    brokenRobots   ;
    public  boolean        stopProduction ;

    public Factory ( int maxRobots , int maxProductionLineCapacity , int maxStorageCapacity )
    {
        this.maxRobots      = maxRobots                                       ;
        this.robots         = new ArrayList<>()                               ;
        this.productionLine = new ProductionLine( maxProductionLineCapacity ) ;
        this.storage        = new Storage( maxStorageCapacity        )        ;
        this.brokenRobots   = new ArrayList<>()                               ;
        this.stopProduction = false                                           ;

        Base robot ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Gripper"        ) ) ;
        Common.set( robot , "logic"   , createPart( "Supplier"       ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Welder"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Builder"        ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Camera"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Inspector"      ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "Camera"         ) ) ;
        Common.set( robot , "logic"   , createPart( "Inspector"      ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "MaintenanceKit" ) ) ;
        Common.set( robot , "logic"   , createPart( "Fixer"          ) ) ;
        robots.add(robot ) ;

        robot = createBase()                                             ;
        Common.set( robot , "arm"     , createPart( "Arm"            ) ) ;
        Common.set( robot , "payload" , createPart( "MaintenanceKit" ) ) ;
        Common.set( robot , "logic"   , createPart( "Fixer"          ) ) ;
        robots.add(robot ) ;
    }

    public void start ()
    {
        for ( Robot r : robots )  { new Thread( r ).start() ; }
    }

    public void initiateStop ()
    {
        stopProduction = true ;

        synchronized ( robots )
        {
            for ( Robot r : robots )  { synchronized ( r )  { r.notifyAll() ; } }
        }

        synchronized ( productionLine )  { productionLine.notifyAll() ; }
        synchronized ( brokenRobots   )  { brokenRobots  .notifyAll() ; }
    }
}