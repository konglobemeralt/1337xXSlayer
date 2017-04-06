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
        Position[] positionArray = new Position[base.getPositions().size()];
        int i=0;
        for (Position pos: base.getPositions()){
            positionArray[i] = pos;
        }
        // TODO this(positionArray, base.getColor());
    }
}
