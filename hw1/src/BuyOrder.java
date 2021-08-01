import java.awt.*;
import java.util.Random;

public class BuyOrder extends Order {
    private static final Random randomGenerator = new Random();
    private final int ord_num= randomGenerator.nextInt(5)+1; //random order number
    private final int speed_divisor=randomGenerator.nextInt(100)+150; //random speed value
    private final int destination_x=(randomGenerator.nextInt(1367)+randomGenerator.nextInt(1367))/2; //random number for destination x
    private final int destination_y=100; //the gold price line's Y coordinate
    private final double speed_x = ((double)(position.getIntX()-destination_x))/(double)(speed_divisor);
    private final double speed_y = ((double)(position.getIntY()-destination_y))/(double)(speed_divisor);
    private int yakalandi =0; // value that changes if the order catches by any agent
    private int execute_et = 0; // value that will be 1 if the order dissappear when the touches line under the gold price
    private Country name_country;
    private final String real_name_ctr;
    private final Font font = new Font("Verdana", Font.BOLD, 9);


    //basic const
    public BuyOrder(double x, double y, Country coun_name) {
        super(x, y);
        this.name_country=coun_name;
        this.real_name_ctr=coun_name.getCountry_name();
    }

    //if the order touches the agent
    public void setYakalandi(){
        yakalandi=1;
    }

    //getters
    public int getYakalandi(){
        return yakalandi;
    }
    public int getord_num(){
        return ord_num;
    }
    public String get_ord(){
        return "Buy";
    }
    public int getExecute_et(){
        return execute_et;
    }
    public Country getName_country(){return name_country;}

    @Override
    public void draw(Graphics2D g2d) {
        //if given condition holds the order will be drawn
        if(yakalandi==0 && execute_et==0) {
            g2d.setFont(font);
            g2d.setColor(Color.green);
            g2d.fillOval(getPosition().getIntX(), getPosition().getIntY(), 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("%d", ord_num), getPosition().getIntX()+4, getPosition().getIntY()+11);
            g2d.drawString(String.format("%s", real_name_ctr),getPosition().getIntX(), getPosition().getIntY()-2);
        }
    }

    @Override
    public void step() {
        // if the order hit the line under the gold price execute_et will be 1
        if(position.getIntY()<destination_y-10){
            execute_et=1;
        }
        //change the coordinates according to given random speed
        else{
            position.setX(position.getIntX()-speed_x);
            position.setY((position.getIntY())-speed_y);
        }
    }

}