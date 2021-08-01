public class BuyOrderFactory extends OrderFactory {
    //factory method
    public Order create_order(int x, int y,Country coun_name) {
        return new BuyOrder(x,y,coun_name);
    }
}