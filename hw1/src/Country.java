import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.*;

public class Country extends Entity {

	private int current_cash = 10000; //cash amount
	private int current_gold = 50;		//gold amount
	private double gold_price = 50 ;	//starting gold price
	private int current_worth = (int) (current_cash + current_gold * gold_price); //dynamic worth amount
	private static final Random randomGenerator=new Random();

	private final String img_path; //image path
	private final String country_name; //name of the country
	private BufferedImage imgur=null; //buffered image
	private final Font font = new Font("Verdana", Font.BOLD, 18); //font information
	private Vector<Order> order_vec=new Vector<>(0);
	private OrderFactory getting_orders;
	
	//get the image from given path
	public Country(double x, double y, String img ,String country_name) {
		super(x,y);
		this.img_path=img;
		this.country_name=country_name;
		//get the image from given path
		try {
			imgur = ImageIO.read(new File(img_path));
		}catch(IOException e){
			System.out.println("Image okunamadi"); //error message
			System.exit(1);
		}
	}
	//getters
	public String getCountry_name(){return country_name;}
	public Vector<Order> getOrder_vec(){return order_vec;}
	public int getCurrent_cash(){return current_cash;}
	public int getCurrent_gold(){return current_gold;}

	//setters
	public void setGold_price(double gold_price) {
		this.gold_price = gold_price;
	}
	public void setCurrent_cash(int new_cash){this.current_cash=new_cash;}
	public void setCurrent_gold(int new_gold){this.current_gold=new_gold;}

	@Override
	public void draw(Graphics2D g2d) {
		//drawing country image
		g2d.drawImage(imgur,position.getIntX(), position.getIntY() ,120,80, null); //image drawer
		//other information about countries cash,gold exc.
		g2d.setColor(Color.BLACK);
        g2d.setFont(font);
		g2d.drawString(String.format("%s",country_name),position.getIntX()+20, position.getIntY()+95);
		g2d.setColor(Color.YELLOW);
		g2d.drawString(String.format("%d gold",current_gold),position.getIntX()+15, position.getIntY()+115);
		g2d.setColor(Color.GREEN);
		g2d.drawString(String.format("%d cash",current_cash),position.getIntX()+15, position.getIntY()+135);
		g2d.setColor(Color.BLUE);
		g2d.drawString(String.format("%d worth",current_worth),position.getIntX()+15, position.getIntY()+155);
		int i=0;
		for(;i<order_vec.size();i++){
			order_vec.get(i).draw(g2d);
		}
	}


	@Override
	public void step() {
		current_worth= (int) (current_cash+current_gold*gold_price);
		for(int i=0;i<order_vec.size();i++){
			order_vec.get(i).step();
			//if execute_et==1 we need to make necessary changes in gold and cas values
			if(order_vec.get(i).getExecute_et()==1){
				String order=order_vec.get(i).get_ord();
				int order_num=order_vec.get(i).getord_num();
				if(order.equals("Buy")){
					current_gold+=order_num;
					current_cash-=order_num*gold_price;
				}
				else{
					current_gold-=order_num;
					current_cash+=order_num*gold_price;
				}
				order_vec.remove(i);
				i--;
			}
			//if some agents catched the order, then the below code part will be hold and necessary changes happen in gold and cash values
			else if(order_vec.get(i).getYakalandi()==1){
				String order=order_vec.get(i).get_ord();
				int order_num=order_vec.get(i).getord_num();
				if(order.equals("Buy")){
					current_cash-=order_num*gold_price;
				}
				else{
					current_gold-=order_num;
				}
				order_vec.remove(i);
				i--;
			}
		}
		//below piece of code is decide which type of order generated randomly
		int belirle=randomGenerator.nextInt(1100);
		if(belirle==99 && current_cash>5*gold_price) {
			getting_orders = new BuyOrderFactory();
			Order eklenecek=getting_orders.create_order(position.getIntX()+60, position.getIntY(),this);
			order_vec.add(eklenecek);
		}
		if(belirle ==100 && current_gold>5){
			getting_orders =new SellOrderFactory();
			Order eklenecek=getting_orders.create_order(position.getIntX()+60, position.getIntY(),this);
			order_vec.add(eklenecek);
		}

	}

}