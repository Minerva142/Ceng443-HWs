import com.sun.org.apache.xpath.internal.operations.Or;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class BasicAgent extends Agent {
    private Vector<Order> all_orders; //all orders at the time
    private String current_state = "Rest"; // state String
    private State state=new Rest(this); // starting state which is rest
    private final Random random_generator= new Random(); //random generator
    private int current_amount=0; //stolen money
    private double current_gold=50; // current gold price
    private BufferedImage image;
    private final String agent_name;
    private final Country belonging_country_name;
    private final Font font = new Font("Verdana", Font.BOLD, 10); //font information


    //constructor
    public BasicAgent(double x, double y,String img_path,String agent_name,Country belonging_country_name) {
        super(x, y);
        this.agent_name=agent_name;
        this.belonging_country_name=belonging_country_name;
        try{
            image = ImageIO.read(new File(img_path));
        }catch(IOException e){
            System.out.println("Image okunamadi"); //error message
            System.exit(1);
        }
    }

    //setters
    public void setCurrent_gold(double gold_price){
        this.current_gold=gold_price;
    }
    public void setAll_orders(Vector<Order> all){this.all_orders=all;};

    //getters
    public int getCurrent_amount(){return current_amount;}
    public Country getBelonging_country_name(){return belonging_country_name;}

    @Override
    public void draw(Graphics2D g2d) {
        //basic draw methods for image,agent name etc.
        g2d.setFont(font);
        g2d.drawImage(image,position.getIntX(), position.getIntY() ,60,70, null);
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.format("%s",agent_name),position.getIntX()+10, position.getIntY()-10);
        g2d.setColor(Color.BLUE);
        g2d.drawString(String.format("%s",current_state),position.getIntX()+10, position.getIntY()+80);
        g2d.setColor(Color.RED);
        g2d.drawString(String.format("%d",current_amount),position.getIntX()+10, position.getIntY()+90);
    }

    //this functions checks whether the agent stole some orders
    public void touch_control(Vector<Order> dene){
        for(int i=0;i<dene.size();i++){
            if(!(dene.get(i).getName_country().getCountry_name().equals(belonging_country_name.getCountry_name()))){
                if(position.getIntX()+90>dene.get(i).position.getIntX() && position.getIntX()<dene.get(i).position.getIntX()
                    && position.getIntY()+90>dene.get(i).position.getIntY()  && position.getIntY()>dene.get(i).position.getIntY()) {
                    dene.get(i).setYakalandi();
                    if (dene.get(i).get_ord().equals("Buy")) {
                        current_amount = (int) (current_gold * dene.get(i).getord_num())+current_amount;
                        dene.get(i).getName_country().setCurrent_cash(dene.get(i).getName_country().getCurrent_cash()-(int) (current_gold * dene.get(i).getord_num()));
                        belonging_country_name.setCurrent_cash(belonging_country_name.getCurrent_cash()+(int) (current_gold * dene.get(i).getord_num()));
                    } else {
                        current_amount = (int) (current_gold * dene.get(i).getord_num())+current_amount;
                        dene.get(i).getName_country().setCurrent_gold(dene.get(i).getName_country().getCurrent_gold()-dene.get(i).getord_num());
                        belonging_country_name.setCurrent_gold(belonging_country_name.getCurrent_gold()+dene.get(i).getord_num());
                    }
                }
            }
        }
    }

    @Override
    public void step() {
        // in randomized way agents are generates their current states
        int i=random_generator.nextInt(700);
        if(i==1){
            current_state="Rest";
            state=new Rest(this);
        }
        else if(i==2){
            current_state="Shake";
            state=new Shake(this);
        }
        else if(i==3){
            current_state="GotoXY";
            state=new GotoXY(this);
        }
        else if(i==4){
            current_state="ChaseClosest";
            state=new ChaseClosest(this,all_orders);
        }
        //finally it calls states' step method
        state.step();
    }

}