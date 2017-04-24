import javax.swing.*;
import java.awt.*;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class WalkerView extends Component implements ObserverOfWalkerModel{

    private WalkerModel model;

    private JFrame frame;

    private int walkerRadius = 10;

    private int doubleToIntFactor = 70;


    @Override
    public void reactOnUpdate(){
        frame.repaint();
    }

    private void setupScene(){

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(30,30,1400,1000);
        frame.getContentPane().add(this);
        this.setBackground(Color.BLACK);
        frame.setVisible(true);
    }

    @Override
    public void setWalkerRadius(int walkerRadius) {
        this.walkerRadius = walkerRadius;
    }

    @Override
    public void setDoubleToIntFactor(int doubleToIntFactor) {
        this.doubleToIntFactor = doubleToIntFactor;
    }

    public WalkerView(WalkerModel model, JFrame frame){
        this.model = model;
        this.frame = frame;

        setupScene();
    }

    @Override
    public void paint(Graphics g){
        paintWalkers(g);
        paintObstacles(g);
        //fillObstacles(g);
        //paintNodes(g);
    }

    private void paintWalkers(Graphics g){
        for (Walker w: model.getWalkers()){
            g.setColor(w.getColor());
            g.fillOval((int) (doubleToIntFactor*w.getPos().getX())-walkerRadius,(int) (doubleToIntFactor*w.getPos().getY())-walkerRadius,2*walkerRadius,2*walkerRadius);
        }
    }

    private void paintObstacles(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        for (Obstacle o: model.getObstacles()){
            g2.setColor(o.getColor());
            //g2.drawPolygon(o.getXPositions(doubleToIntFactor),o.getYPositions(doubleToIntFactor),o.getPositions().size()); //TODO might work better
            for (int i = 0; i < o.getPositions().size()-1; i++){
                g2.drawLine((int) (doubleToIntFactor*(o.getPositions().get(i).getX() + o.getReferencePoint().getX())), (int) (doubleToIntFactor*(o.getPositions().get(i).getY() + o.getReferencePoint().getY())),
                        (int) (doubleToIntFactor*(o.getPositions().get(i+1).getX() + o.getReferencePoint().getX())), (int) (doubleToIntFactor*(o.getPositions().get(i+1).getY() + o.getReferencePoint().getY())));

            }
            g.drawLine((int) (doubleToIntFactor*o.getPositions().get(0).getX()), (int) (doubleToIntFactor*o.getPositions().get(0).getY()),
                    (int) (doubleToIntFactor*o.getPositions().get(o.getPositions().size()-1).getX()), (int) (doubleToIntFactor*o.getPositions().get(o.getPositions().size()-1).getY()));
        }
        g2.setStroke(new BasicStroke(1));
    }

    private void fillObstacles(Graphics g){ //TODO fix
        Graphics2D g2 = (Graphics2D) g;
        for (Obstacle o: model.getObstacles()){
            g2.setColor(o.getColor());
            g2.fillPolygon(o.getXPositions(doubleToIntFactor), o.getYPositions(doubleToIntFactor), o.getPositions().size());
        }

    }

    private void paintNodes(Graphics g){ //TODO fix maybe placed todo wronng?
        g.setColor(Color.RED);
        for (WalkwayNode n: model.getNodes()){
            g.fillOval((int) (doubleToIntFactor*n.getPos().getX()-3), (int) (doubleToIntFactor*n.getPos().getY()-3),7 ,7);
            g.drawOval((int) (doubleToIntFactor*(n.getPos().getX()-Walker.getProximityDistance()/2)), (int) (doubleToIntFactor*(n.getPos().getY()-Walker.getProximityDistance()/2)),
                    (int) (doubleToIntFactor*Walker.getProximityDistance()) ,(int) (doubleToIntFactor*Walker.getProximityDistance()));
        }
    }
}
