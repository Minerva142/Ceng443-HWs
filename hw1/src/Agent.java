import java.awt.*;
import java.util.Vector;

public abstract class Agent extends Entity {

    public Agent(double x, double y) {
        super(x, y);

    }

    public void touch_control(Vector<Order> dene){}

    //getters
    public void setCurrent_gold(double gold_price){}
    public void setAll_orders(Vector<Order> all){}

    //setters
    public int getCurrent_amount() {
        return -1;
    }
    public Country getBelonging_country_name(){return null;}

    @Override
    public void draw(Graphics2D g2d) {}

    @Override
    public void step() {}

}