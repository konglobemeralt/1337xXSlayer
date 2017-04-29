import java.awt.*;

/**
 * Created by Emil Jansson on 2017-03-30.
 */
public class Walker {
    private Position pos;
    private double speed;
    private UnitVectorTwoD direction = new UnitVectorTwoD(0,0);;
    private Color color;
    private WalkwayNode targetNode;
    private WalkwayNode lastNode;
    private int stopTimer = 0;
    private int randomStopTime = 0;

    private static double proximityDistance = 0.3; //What counts as in proximity.

    public void updatePos() {

        if (stopTimer > randomStopTime) {
            double x = this.pos.getX() + this.speed * this.direction.getxComposite();
            double y = this.pos.getY() + this.speed * this.direction.getyComposite();
            this.pos = new Position(x, y);
            chanceToWait(0.001, 100);

            if (this.pos.isInProximityOf(targetNode.getPos(), proximityDistance)) {
                targetNewNode();
                chanceToWait(0.1, 200);
            }
        }else{ stopTimer++; }
    }

    private void chanceToWait(double chance, int maxTime){
        double rand = Math.random();
        if (rand < chance) {
            waitRandomTics(maxTime);
        }
    }

    private void waitRandomTics(int tics){
        randomStopTime = (int)(Math.random()*tics);
        stopTimer = 0;
    }

    private void targetNewNode(){
        WalkwayNode nextNode = targetNode.getNextNode();
        while (nextNode.equals(lastNode)){
            nextNode = targetNode.getNextNode();
        }
        this.direction = new UnitVectorTwoD(nextNode.getPos().getX()-this.getPos().getX(), nextNode.getPos().getY()-this.getPos().getY());
        this.lastNode = this.targetNode;
        this.targetNode = nextNode;
    }

    public static double getProximityDistance() {
        return proximityDistance;
    }

    public Position getPos() {
        return pos;
    }

    public double getSpeed() {
        return speed;
    }

    public UnitVectorTwoD getDirection() {
        return direction;
    }

    public Color getColor() {
        return color;
    }

    public WalkwayNode getTargetNode() {
        return targetNode;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(UnitVectorTwoD direction) {
        this.direction = direction;
    }

    public void setTargetNode(WalkwayNode targetNode) {
        this.targetNode = targetNode;
    }

    public Walker(Position pos, double speed, Color color, WalkwayNode targetNode) {
        this.pos = pos;
        this.speed = speed;
        this.color = color;
        this.targetNode = targetNode;
        targetNewNode();
    }

    public Walker(Position pos, WalkwayNode targetNode) {
        this.pos = pos;
        this.speed = 1;
        this.color = Color.GRAY;
        this.targetNode = targetNode;
        targetNewNode();
    }

}
