import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class Display extends JPanel {
    public Display() { this.setBackground(new Color(180, 180, 180)); }

    @Override
    public Dimension getPreferredSize() { return super.getPreferredSize(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Common.getGoldPrice().draw((Graphics2D) g);
        g.drawLine(0, Common.getUpperLineY(), Common.getWindowWidth(), Common.getUpperLineY());
        //draw all agents and countries
        Common.getCtr1().draw((Graphics2D) g);
        Common.getCtr2().draw((Graphics2D) g);
        Common.getCtr3().draw((Graphics2D) g);
        Common.getCtr4().draw((Graphics2D) g);
        Common.getCtr5().draw((Graphics2D) g);
        Common.getAgt_1().draw((Graphics2D) g);
        Common.getAgt_2().draw((Graphics2D) g);
        Common.getAgt_3().draw((Graphics2D) g);
        Common.getAgt_4().draw((Graphics2D) g);
        Common.getAgt_5().draw((Graphics2D) g);
        //add it all together in one vector
        Vector<Agent> all_vec=new Vector<>(0);
        all_vec.add(Common.getAgt_1());
        all_vec.add(Common.getAgt_2());
        all_vec.add(Common.getAgt_3());
        all_vec.add(Common.getAgt_4());
        all_vec.add(Common.getAgt_5());
        //checks the decorators
        for(int i=0;i<all_vec.size();i++) {
            Novice nov_1 = new Novice(all_vec.get(i));
            nov_1.draw((Graphics2D) g);
            Master mast_1 = new Master(all_vec.get(i));
            mast_1.draw((Graphics2D) g);
            Expert exp_1 = new Expert(all_vec.get(i));
            exp_1.draw((Graphics2D) g);
        }
    }
}