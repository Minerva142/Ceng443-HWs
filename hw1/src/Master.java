import java.awt.*;

public class Master extends AgentDecorator {
    private final Agent agent; //Agent
    //constructor
    public Master(Agent agent) {
        super(agent.position.getIntX(),agent.position.getIntY());
        this.agent=agent;
    }

    @Override
    public void draw(Graphics2D g2d) {
        //checks the conditions then decorate
        if (agent.getCurrent_amount()>4000) {
            g2d.setColor(Color.yellow);
            g2d.fillRect(position.getIntX() + 17, position.getIntY() - 40, 10, 10);
        }
    }

}