
public class SellOrderFactory extends OrderFactory {
    //factory method
    public Order create_order(int x, int y,Country coun_name) {
            return new SellOrder(x, y,coun_name);
    }

}