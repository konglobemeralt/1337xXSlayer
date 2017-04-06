import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-03-30.
 */
public class WalkwayNode {

    private final Position pos;
    private final List<WalkwayNode> connectingNodes = new ArrayList<WalkwayNode>();
    private final List<Double> connectionStrenghts = new ArrayList<Double>();

    public Position getPos() {
        return pos;
    }

    public WalkwayNode getConnectingNode(int index) {
        return connectingNodes.get(index);
    }

    public Double connectionStrenght(int index) {
        return connectionStrenghts.get(index);
    }

    public int getConnectionAmount(){
        return connectingNodes.size();
    }

    public WalkwayNode getNextNode(){
        double totalStrength = 0;
        for (double i :connectionStrenghts){
            totalStrength+=i;
        }
        double random = Math.random()*totalStrength;
        int j  = 0;
        totalStrength = 0;
        while (totalStrength < random){
            totalStrength+= connectionStrenghts.get(j);
            j++;
        }

        return connectingNodes.get(j-1);
    }

    public void addConnection(WalkwayNode node, double strength){
        if (strength>0 ) {
            connectingNodes.add(node);
            connectionStrenghts.add(strength);
        }else{
            System.out.println("Error: Invalid input");
        }
    }

    public void removeConnection(WalkwayNode node){
        int strength = connectingNodes.indexOf(node);
        connectingNodes.remove(node);
        connectionStrenghts.remove(strength);
    }

    public WalkwayNode(Position pos) {
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalkwayNode that = (WalkwayNode) o;

        if (!pos.equals(that.pos)) return false;
        return connectingNodes.equals(that.connectingNodes);
    }

    @Override
    public int hashCode() {
        int result = pos.hashCode();
        result = 31 * result + connectingNodes.hashCode();
        return result;
    }
}
