import java.awt.*;

public abstract class Order extends Entity {
    public Order(double x, double y) {
        super(x, y);
    }

    //methods for overwriting
    public abstract void setYakalandi();
    public abstract int getord_num();
    public abstract String get_ord();
    public abstract int getExecute_et();
    public abstract int getYakalandi();
    public abstract Country getName_country();

    @Override
    public void draw(Graphics2D g2d) {}
    @Override
    public void step() {}

}