import java.util.Random;

public class GotoXY extends State {
    private final Random ran_gen= new Random(); //random generator
    private final int dest_x= ran_gen.nextInt(1366); //random x coordinate
    private final int dest_y=100+ ran_gen.nextInt(300); //random y coordinate
    private final int speed_divisor=150; //speed divisor
    private final double speed_x=((double)(work_on.position.getIntX()-dest_x))/(double)(speed_divisor);
    private final double speed_y=((double)(work_on.position.getIntY()-dest_y))/(double)(speed_divisor);
    //constructor
    public GotoXY(BasicAgent work_on) {
        super(work_on);
    }

    @Override
    public void step() {
            //checks the border conditions then change the coordinates of agent
            if((work_on.position.getIntX()+20 > dest_x && work_on.position.getIntX()-20<dest_x) || (work_on.position.getIntY()+20>dest_y && work_on.position.getIntY()-20<dest_y)) {

            }else{
                work_on.position.setX(work_on.position.getIntX() - speed_x);
                work_on.position.setY(work_on.position.getIntY() - speed_y);
            }
    }

}