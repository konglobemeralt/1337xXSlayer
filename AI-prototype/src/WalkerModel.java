import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class WalkerModel {

    private List<ObserverOfWalkerModel> observers = new ArrayList<ObserverOfWalkerModel>();

    private List<Obstacle> obstacles = new ArrayList<Obstacle>();

    private List<WalkwayNode> nodes = new ArrayList<WalkwayNode>();

    private List<Walker> walkers = new ArrayList<Walker>();

    public void moveWalkers(){
        for (Walker i: walkers){
            i.updatePos();
        }
    }

    public void addWalker(Walker walker){
        walkers.add(walker);
    }

    public void addWalkwayNode(WalkwayNode WalkwayNode){
        nodes.add(WalkwayNode);
    }

    public List<ObserverOfWalkerModel> getObservers() {
        return observers;
    }

    public void addObstacle(Obstacle Obstacle){
        obstacles.add(Obstacle);
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public List<WalkwayNode> getNodes() {
        return nodes;
    }

    public List<Walker> getWalkers() {
        return walkers;
    }

    private void notifyListeners(){
        for (ObserverOfWalkerModel o: observers){
            o.reactOnUpdate();
        }
    }

    public void addListener(ObserverOfWalkerModel observer){
        observers.add(observer);
    }

    public void preformMainLoop(){
        moveWalkers();
        notifyListeners();
    }
}
