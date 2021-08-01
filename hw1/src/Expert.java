import java.awt.*;

public class Expert extends AgentDecorator {
    private final Agent agent; //Agent
    //constructor
    public Expert(Agent agent) {
        super(agent.position.getIntX(),agent.position.getIntY());
        this.agent=agent;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //checks the conditions then decorate
        if(agent.getCurrent_amount()>6000) {
            g2d.setColor(Color.red);
            g2d.fillRect(position.getIntX() + 29, position.getIntY() - 40, 10, 10);
        }
    }


}