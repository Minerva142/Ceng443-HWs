import java.awt.*;

public class Novice extends AgentDecorator {
    private final Agent agent; //Agent
    //constructor
    public Novice(Agent agent) {
        super(agent.position.getIntX(),agent.position.getIntY());
        this.agent=agent;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //checks the conditions then decorate
        if(agent.getCurrent_amount()>2000) {
            g2d.setColor(Color.white);
            g2d.fillRect(position.getIntX() + 5, position.getIntY() - 40, 10, 10);
        }
    }


}