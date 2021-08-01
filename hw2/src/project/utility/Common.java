package project.utility;

import project.parts.Arm;
import project.parts.Base;
import project.parts.Part;
import project.parts.logics.Builder;
import project.parts.logics.Fixer;
import project.parts.logics.Inspector;
import project.parts.logics.Supplier;
import project.parts.payloads.Camera;
import project.parts.payloads.Gripper;
import project.parts.payloads.MaintenanceKit;
import project.parts.payloads.Welder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Common
{
    public static Random random = new Random() ;

    public static synchronized Object get (Object object , String fieldName )
    {
        try {
            Field private_fieldname = object.getClass().getDeclaredField(fieldName); // get object field
            private_fieldname.setAccessible(true); // change accesibility
            Object field_value =  private_fieldname.get(object); //get the value of object
            return field_value;
        }
        catch(NoSuchFieldException | IllegalAccessException tm){
            throw new SmartFactoryException( "Failed: get!" ) ; //throw exception
        }
    }
    public static synchronized void set ( Object object , String fieldName , Object value )
    {
        try{
            Field private_fieldname = object.getClass().getDeclaredField(fieldName); //// get object field
            private_fieldname.setAccessible(true); // change accesibility
            private_fieldname.set(object,value); // set the field
        }catch (NoSuchFieldException  | IllegalAccessException e) {
            throw new SmartFactoryException( "Failed: images!" ) ; //throw exception
        }
    }


    //use reflection to create new base object with getting its constructor then call the newInstance with serialNo
    //this function handles with Base creations
    // after doing it, I noticed that I used double reflection.
    // I think there is no problem with logic.
    // return type is Object, the function don't know the return type of object.
    private static Object base_creator(int serialno) {

            try {
                Constructor constructor = Base.class.getConstructor(int.class);
                Object result = constructor.newInstance(serialno);
                return result;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                try {
                    Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                    SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createBase!");
                    throw tw;
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                    x.printStackTrace();
                }
            }
            return null;
        }

     // I used switch case to find correct part
    //cases about given name. Provide Parts according to given name.
    // use reflection to create new part object with getting its constructor. then call the newInstance.
    // return type is Object, the function don't know the return type of object.
    private static Object part_creator(String name) {
            try {
                switch (name) {
                    case "Arm":
                        try {
                            Constructor constructor = Arm.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Gripper":
                        try {
                            Constructor constructor = Gripper.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Supplier":
                        try {
                            Constructor constructor = Supplier.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Welder":
                        try {
                            Constructor constructor = Welder.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Builder":
                        try {
                            Constructor constructor = Builder.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Camera":
                        try {
                            Constructor constructor = Camera.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Inspector":
                        try {
                            Constructor constructor = Inspector.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "MaintenanceKit":
                        try {
                            Constructor constructor = MaintenanceKit.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    case "Fixer":
                        try {
                            Constructor constructor = Fixer.class.getConstructor();
                            Object result = constructor.newInstance();
                            return result;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                            try {
                                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                                SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                                throw tw;
                            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                                x.printStackTrace();
                            }
                        }
                    default:
                        try {
                            Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                            SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                            throw tw;
                        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                            x.printStackTrace();
                        }
                }
            } catch (SmartFactoryException tm) {
                try {
                    Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                    SmartFactoryException tw = (SmartFactoryException) constructor.newInstance("Failed: createPart!");
                    throw tw;
                } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                    x.printStackTrace();
                }
            }
            return null;
        }

        // this method helps the making smartfactoryexceptions.
        // it uses exactly reflection but this reflection is not necessary.
        // I choose public because if I try to get this method with reflection there can be a different exception can be occured
        // I don't want to handle with them.
        public static Object smart_fac(String excp){
            try {
                Constructor constructor = SmartFactoryException.class.getConstructor(String.class);
                Object tw = constructor.newInstance(excp);
                return tw;
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException x) {
                x.printStackTrace();
            }
            return null;
        }
}
