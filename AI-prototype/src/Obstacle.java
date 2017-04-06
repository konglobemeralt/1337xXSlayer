import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class Obstacle {

    private List<Position> positions = new ArrayList<Position>();

    private Position referencePoint = new Position(0,0);

    public List<Position> getPositions() {
        return positions;
    }

    public Color getColor() {
        return color;
    }

    public int[] getXPositions(int doubleToIntFactor){
        int[] xPos = new int[positions.size()];
        int i=0;
        for (Position p: positions){
            xPos[i] = (int) (doubleToIntFactor*p.getX());
        }
        return xPos;
    }

    public int[] getYPositions(int doubleToIntFactor){
        int[] yPos = new int[positions.size()];
        int i=0;
        for (Position p: positions){
            yPos[i] = (int) (doubleToIntFactor*p.getY());
        }
        return yPos;
    }

    private Color color = Color.GRAY;

    public Obstacle(Position[] pos){
        for (Position p: pos){
            positions.add(p);
        }
    }

    public Obstacle(Position[] pos, Color color){
        this(pos);
        this.color = color;
    }

    public Position getReferencePoint() {
        return referencePoint;
    }

    public Obstacle(Obstacle base, Position refence){

        for (Position p: base.getPositions()){
            positions.add(p);
        }
        this.color = base.getColor();
        this.referencePoint = refence;
    }
}
