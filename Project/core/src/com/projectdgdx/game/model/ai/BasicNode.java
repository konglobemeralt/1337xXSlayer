package com.projectdgdx.game.model.ai;

import com.projectdgdx.game.model.objectStructure.GameObject;
import com.projectdgdx.game.utils.Vector3d;

import java.util.ArrayList;
import java.util.List;

/**
 * An overall node that has a position in the room and defined connections to other nodes in the room.
 */
public abstract class BasicNode extends GameObject {


    private ArrayList<BasicNode> connectingNodes = new ArrayList<BasicNode>();
    private ArrayList<Double> connectionStrengths = new ArrayList<Double>();

    int nodeId;
    List<Integer> friendList;

    public BasicNode(Vector3d position, Vector3d scale, Vector3d rotation, String id, int nodeId, List<Integer> friendList) {
        super(position, scale, rotation, id);
        this.nodeId = nodeId;
        this.friendList  = friendList;
    }

    /**
     * Adds a new connection to this node. Connecting nodes can be accessed from getNextNode.
     * @param node The BasicNode to be connected.
     * @param strength The strength of the connection, meaning the relative probability (before normalisation) that this node is chosen from getNextNode.
     */

    public void addConnection(BasicNode node, double strength){
        connectingNodes.add(node);
        connectionStrengths.add(strength);
    }

    /**
     * Sets this nodes connections. Connecting nodes can be accessed from getNextNode.
     * @param nodes The AINodes to be set as connections.
     * @param strengths The strength of the connections, meaning the relative probability (before normalisation) that these nodes is chosen from getNextNode.
     */

    public void setConnections(BasicNode[] nodes, double[] strengths){
        connectingNodes.clear();
        connectionStrengths.clear();
        int i = 0;
        for (BasicNode node : nodes) {
            connectingNodes.add(node);
            connectionStrengths.add(strengths[i]);
            i++;
        }
    }

    /**
     * Returns a random node based on the current node's connection. The connecting nodes strengths is proportional to their chance of being chosen.
     * @return A random connecting node.
     */

    public BasicNode getNextNode(){ //Returns a node based on connection strength.
        double strenghtSum = 0;
        for (double d : connectionStrengths){
            strenghtSum += d;
        }
        double threshold = strenghtSum*Math.random();
        strenghtSum = 0;
        int i = 0;
        for (double d : connectionStrengths){
            strenghtSum += d;
            if (strenghtSum > threshold){
                break;
            }
            i++;
        }

        return connectingNodes.get(i);
    }

    public int getNodeId() {
        return nodeId;
    }

    /**
     *  Will add connections to other nodes and their corresponding strength.
     * @param nodeList , a list of nodes that this node should connect to.
     */
    public void init(List<BasicNode> nodeList) { //TODO use node strength. All connections have strength 1 atm.
        for(BasicNode node : nodeList) {
            if(friendList.contains(node.getNodeId())) {
                addConnection(node, 1);
            }
        }
    }

}
