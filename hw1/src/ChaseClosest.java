import java.util.Vector;

public class ChaseClosest extends State {
    private Vector<Order> all_orders; //all orders in the simulation at the time
    private double speed_x; // x coordinates speed value
    private double speed_y; // y coordinates speed value
    public ChaseClosest(BasicAgent work_on, Vector<Order> all_orders) {
        super(work_on);
        this.all_orders=all_orders;
    }

    @Override
    public void step() {
        double distance=1000000000; //max value for distance
        int index=-1;
        //decide which order is the closest one
        for(int i=0;i<all_orders.size();i++){
            if(!(all_orders.get(i).getName_country().getCountry_name().equals(work_on.getBelonging_country_name().getCountry_name()))){
                double d_x=work_on.position.getIntX()-all_orders.get(i).position.getIntX();
                double d_y=work_on.position.getIntY()-all_orders.get(i).position.getIntY();
                double dist=d_x*d_x+d_y*d_y;
                if(distance>dist){
                    index=i;
                    distance=dist;
                }
            }
        }
        //if there is an closest order, calculate speed values and try to catch it
        if(index!=-1) {
            speed_x = ((double) all_orders.get(index).position.getIntX() - work_on.position.getIntX()) / (double) 250;
            speed_y = ((double) all_orders.get(index).position.getIntX() - work_on.position.getIntX()) / (double) 250;
            if(work_on.position.getIntY()>103 && work_on.position.getIntY()<400) {
                work_on.position.setX(work_on.position.getIntX() + speed_x);
                work_on.position.setY(work_on.position.getIntY() + speed_y);
            }
        }
    }
}