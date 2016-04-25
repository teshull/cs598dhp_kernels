package kernel_four;

import java.util.*;

public class Graph {
    private static Random rn = new Random();
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public void addNodes(int numToAdd, double mean, double variance){
        for(int i = 0; i < numToAdd; i++) {
            Node n  = new Node(new Item(rn.nextInt(100)));
            double numNeighbors = (int) (rn.nextGaussian() * variance + mean);
            Set<Node> neighbors = new HashSet<Node>();
            int numNodes = nodes.size();
            //if no nodes have been added already, then have to wait
            for (int j = 0; numNodes > 0 && j < numNeighbors; j++) {
                //picking random node
                //NOTE: this node may have been picked already (this is fine)
                int randIndex = rn.nextInt(numNodes);
                neighbors.add(nodes.get(randIndex));
            }
            n.addNeighbors(neighbors);
            nodes.add(n);
        }
    }

    public void removeNodes(Set<Node> items){
        //first removing all of neighbors connections to this node
        for(Node n : nodes){
            n.removeNeighbors(items);
        }

        //now deleting the node itself
        for(Node n : items){
            nodes.remove(n);
        }
    }

    public Set<Node> pickNodesToRemove(int numToRemove){
        Set<Node> nodesToRemove = new HashSet<Node>();
        int numNodes = nodes.size();
        for(int i = 0; i < numToRemove; i++){
            //picking random node
            //NOTE: this node may have been picked already (this is fine)
            int randIndex = rn.nextInt(numNodes);
            nodesToRemove.add(nodes.get(randIndex));
        }
        return nodesToRemove;
    }

    public double performCalculation(){
        int totalEdges = 0;
        for(Node n : nodes){
            totalEdges += n.neighbors.size();
        }
        return 1.0 * totalEdges / nodes.size();
    }
}

