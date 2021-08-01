import java.util.Random;
import java.util.Vector;

public class Common {
    private static final String title = "Gold Wars";
    private static final int windowWidth = 1366;
    private static final int windowHeight = 768;

    private static final GoldPrice goldPrice = new GoldPrice(455, 62);

    private static final Random randomGenerator = new Random(1234);
    private static final int upperLineY = 100;
    
    //private static 
    private static final Country ctr_1;
    private static final Country ctr_2;
    private static final Country ctr_3;
    private static final Country ctr_4;
    private static final Country ctr_5;
    private static final Agent agt_1;
    private static final Agent agt_2;
    private static final Agent agt_3;
    private static final Agent agt_4;
    private static final Agent agt_5;


    static  {
        //initialize all declared variables
    	ctr_1=new Country( 125 , 530 , "images/usa.jpg" , "USA");
    	ctr_2=new Country(375,530,"images/israel.jpg","Israel");
        ctr_3=new Country(625,530,"images/turkey.jpg","Turkey");
        ctr_4=new Country(875,530,"images/russia.jpg","Russia");
        ctr_5=new Country(1125,530,"images/china.jpg","China");
        agt_1=new BasicAgent(145,250,"images/cia.png","CIA",ctr_1);
        agt_2=new BasicAgent(395,250,"images/mossad.png","Mossad",ctr_2);
        agt_3=new BasicAgent(645,250,"images/mit.png","MIT",ctr_3);
        agt_4=new BasicAgent(895,250,"images/svr.png","SVR",ctr_4);
        agt_5=new BasicAgent(1145,250,"images/mss.png","MSS",ctr_5);
    }


    // getters
    public static String getTitle() { return title; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }

    // getter
    public static GoldPrice getGoldPrice() { return goldPrice; }

    //getters for countries
    public static Country getCtr1() {
        return ctr_1;
    }
    public static Country getCtr2() {
        return ctr_2;
    }
    public static Country getCtr3() {
        return ctr_3;
    }
    public static Country getCtr4() { return ctr_4; }
    public static Country getCtr5() { return ctr_5; }
    public static Agent getAgt_1(){return agt_1; }
    public static Agent getAgt_2(){return agt_2; }
    public static Agent getAgt_3(){return agt_3; }
    public static Agent getAgt_4(){return agt_4; }
    public static Agent getAgt_5(){return agt_5; }


    // getters
    public static Random getRandomGenerator() { return randomGenerator; }
    public static int getUpperLineY() { return upperLineY; }

    public static void stepAllEntities() {
        if (randomGenerator.nextInt(200) ==0 ) goldPrice.step();
        //set all countries gold price to current gold price at the time and call step functions
        ctr_1.setGold_price(goldPrice.getCurrentPrice());ctr_1.step();
        ctr_2.setGold_price(goldPrice.getCurrentPrice());ctr_2.step();
        ctr_3.setGold_price(goldPrice.getCurrentPrice());ctr_3.step();
        ctr_4.setGold_price(goldPrice.getCurrentPrice());ctr_4.step();
        ctr_5.setGold_price(goldPrice.getCurrentPrice());ctr_5.step();
        //declare order vector for all orders at the time
        Vector<Order> total_vec= new Vector<Order>(0);
        // add all orders to the total_vec
        Vector<Order> k1=ctr_1.getOrder_vec();
        Vector<Order> k2=ctr_2.getOrder_vec();
        Vector<Order> k3=ctr_3.getOrder_vec();
        Vector<Order> k4=ctr_4.getOrder_vec();
        Vector<Order> k5=ctr_5.getOrder_vec();
        for(int i=0;i<k1.size();i++){
            total_vec.add(k1.get(i));
        }
        for(int i=0;i<k2.size();i++){
            total_vec.add(k2.get(i));
        }
        for(int i=0;i<k3.size();i++){
            total_vec.add(k3.get(i));
        }
        for(int i=0;i<k4.size();i++){
            total_vec.add(k4.get(i));
        }
        for(int i=0;i<k5.size();i++){
            total_vec.add(k5.get(i));
        }
        //set all agents gold price to current gold price at the time
        agt_1.setCurrent_gold(goldPrice.getCurrentPrice());
        agt_2.setCurrent_gold(goldPrice.getCurrentPrice());
        agt_3.setCurrent_gold(goldPrice.getCurrentPrice());
        agt_4.setCurrent_gold(goldPrice.getCurrentPrice());
        agt_5.setCurrent_gold(goldPrice.getCurrentPrice());
        //set the agents all_order variable
        agt_1.setAll_orders(total_vec);
        agt_2.setAll_orders(total_vec);
        agt_3.setAll_orders(total_vec);
        agt_4.setAll_orders(total_vec);
        agt_5.setAll_orders(total_vec);
        //call step functions
        agt_1.step();
        agt_2.step();
        agt_3.step();
        agt_4.step();
        agt_5.step();
        //Control the catches
        agt_1.touch_control(total_vec);
        agt_2.touch_control(total_vec);
        agt_3.touch_control(total_vec);
        agt_4.touch_control(total_vec);
        agt_5.touch_control(total_vec);

    }
}