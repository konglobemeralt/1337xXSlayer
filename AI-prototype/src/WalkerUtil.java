import java.awt.*;

/**
 * Created by Emil Jansson on 2017-04-03.
 */
public class WalkerUtil {

    public static void setupOne(WalkerModel model){
        model.addWalkwayNode(new WalkwayNode(new Position(0,0)));
        model.addWalkwayNode(new WalkwayNode(new Position(10,0)));
        model.addWalkwayNode(new WalkwayNode(new Position(0,10)));
        model.addWalkwayNode(new WalkwayNode(new Position(10,10)));
        model.addWalkwayNode(new WalkwayNode(new Position(5,0)));
        model.addWalkwayNode(new WalkwayNode(new Position(0,5)));
        model.addWalkwayNode(new WalkwayNode(new Position(10,5)));
        model.addWalkwayNode(new WalkwayNode(new Position(5,10)));

        for (WalkwayNode n: model.getNodes()){
            for (WalkwayNode m: model.getNodes()){
                n.addConnection(m,1);
            }
        }

        model.addWalker(new Walker(new Position(0,0),0.0001, Color.BLUE, model.getNodes().get(0)));
    }

    public static void setupTwo(WalkerModel model){
        model.addWalkwayNode(new WalkwayNode(new Position(1,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,6)));

        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,1.5), new Position(1.5,5.5), new Position(5.5,5.5),  new Position(5.5,1.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,6.5), new Position(6.5,10.5), new Position(10.5,10.5),  new Position(10.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,6.5), new Position(1.5,10.5), new Position(5.5,10.5),  new Position(5.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,1.5), new Position(6.5,5.5), new Position(10.5,5.5),  new Position(10.5,1.5)}));

        for (WalkwayNode n: model.getNodes()){
            for (WalkwayNode m: model.getNodes()){
                if (n.getPos().getX() == m.getPos().getX() || n.getPos().getY() == m.getPos().getY())
                n.addConnection(m,1);
            }
        }

        for (WalkwayNode n: model.getNodes()){
            model.addWalker(new Walker(new Position(20,10),0.0001, Color.BLUE, n));
        }

    }

    public static void setupThree(WalkerModel model){
        model.addWalkwayNode(new WalkwayNode(new Position(1,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,6)));

        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,1.5), new Position(1.5,5.5), new Position(5.5,5.5),  new Position(5.5,1.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,6.5), new Position(6.5,10.5), new Position(10.5,10.5),  new Position(10.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,6.5), new Position(1.5,10.5), new Position(5.5,10.5),  new Position(5.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,1.5), new Position(6.5,5.5), new Position(10.5,5.5),  new Position(10.5,1.5)}));

        for (WalkwayNode n: model.getNodes()){
            for (WalkwayNode m: model.getNodes()){
                if (n.getPos().getDistance(m.getPos())==5)
                    n.addConnection(m,1);
            }
        }

        for (WalkwayNode n: model.getNodes()){
            model.addWalker(new Walker(new Position(6.5,6.5),0.01, Color.BLUE, n));
            model.addWalker(new Walker(new Position(6.5,6.5),0.01, Color.BLACK, n));
            model.addWalker(new Walker(new Position(6.5,6.5),0.01, Color.RED, n));
        }

    }

    public static void setupFour(WalkerModel model){
        model.addWalkwayNode(new WalkwayNode(new Position(1,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,1)));
        model.addWalkwayNode(new WalkwayNode(new Position(1,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(11,6)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,11)));
        model.addWalkwayNode(new WalkwayNode(new Position(6,6)));

        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,1.5), new Position(1.5,5.5), new Position(5.5,5.5),  new Position(5.5,1.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,6.5), new Position(6.5,10.5), new Position(10.5,10.5),  new Position(10.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(1.5,6.5), new Position(1.5,10.5), new Position(5.5,10.5),  new Position(5.5,6.5)}));
        model.addObstacle(new Obstacle(new Position[]{new Position(6.5,1.5), new Position(6.5,5.5), new Position(10.5,5.5),  new Position(10.5,1.5)}));

        for (WalkwayNode n: model.getNodes()){
            for (WalkwayNode m: model.getNodes()){
                if (n.getPos().getDistance(m.getPos())==5)
                    n.addConnection(m,1);
            }
        }


        model.addWalker(new Walker(new Position(10,10),0.00007, Color.BLUE, model.getNodes().get(0)));


    }

    public static void setupMapOne(WalkerModel model){
        Obstacle tile = new Obstacle(new Position[] {new Position(0,0), new Position(1,0), new Position(1,1), new Position(0, 1)});
        model.getObservers().get(0).setDoubleToIntFactor(30);
        model.getObservers().get(0).setWalkerRadius(30);

        //Create frame
        for (int i = 0; i<45; i++){
            for (int j = 0; j<40; j+=30){
                model.addObstacle(new Obstacle(tile, new Position(i,j)));
            }
        }
        for (int j = 0; j<31; j++){
            for (int i = 0; i<50; i+=44){
                model.addObstacle(new Obstacle(tile, new Position(i,j)));
            }
        }


        model.addObstacle(new Obstacle(tile, new Position(10,10)));
        model.addObstacle(new Obstacle(new Position[] {new Position(22,22), new Position(23,22), new Position(23,23), new Position(22, 23)}));

    }

    public static void setupMapTwo(WalkerModel model){
        model.getObservers().get(0).setDoubleToIntFactor(30);
        model.getObservers().get(0).setWalkerRadius(30);

        //Outer frame
        {
            Obstacle upperWall = new Obstacle(new Position[] {new Position(1,1), new Position(44,1), new Position(44,2), new Position(1, 2)});
            model.addObstacle(upperWall);
            model.addObstacle(new Obstacle(upperWall, new Position(0, 29)));

            Obstacle leftWall = new Obstacle(new Position[] {new Position(1,1), new Position(1,31), new Position(2,31), new Position(2, 1)});
            model.addObstacle(leftWall);
            model.addObstacle(new Obstacle(leftWall, new Position(42, 0)));
        }

        Obstacle verticalWallTwelve = new Obstacle(new Position[] {new Position(0,0), new Position(0,12), new Position(1,12), new Position(1, 0)});
        Obstacle verticalWallFive = new Obstacle(new Position[] {new Position(0,0), new Position(0,5), new Position(1,5), new Position(1, 0)});
        Obstacle horisontalWallTwelve = new Obstacle(new Position[] {new Position(0,0), new Position(12,0), new Position(12,1), new Position(0, 1)});
        Obstacle horisontalWallFive = new Obstacle(new Position[] {new Position(0,0), new Position(5,0), new Position(5,1), new Position(0, 1)});

        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(11, 3)));
        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(13, 1)));
        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(13, 18)));
        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(40, 3)));
        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(29, 5)));
        model.addObstacle(new Obstacle(verticalWallTwelve, new Position(19, 12)));
        model.addObstacle(new Obstacle(verticalWallFive, new Position(17, 1)));
        model.addObstacle(new Obstacle(verticalWallFive, new Position(8, 26)));
        model.addObstacle(new Obstacle(verticalWallFive, new Position(17, 20)));
        model.addObstacle(new Obstacle(verticalWallFive, new Position(22, 1)));






    }
}
