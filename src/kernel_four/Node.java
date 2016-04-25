package kernel_four;

import java.util.*;

public class Node {
    public Item value;
    public Set<Node> neighbors = new HashSet<Node>();

    public Node(Item v, Set<Node> n){
        value = v;
        for(Node neighbor: n){
            addNeighbor(neighbor);
        }

    }

    public Node(Item v){
        value = v;
    }

    public void addNeighbor(Node neighbor)
    {
        //add each other as neighbors
        this.neighbors.add(neighbor);
        neighbor.neighbors.add(this);
    }
    public void addNeighbors(Set<Node> n){
        for(Node neighbor: n){
            addNeighbor(neighbor);
        }
    }

    public void removeNeighbor(Node neighbor)
    {
        //remove each other as neighbors
        this.neighbors.remove(neighbor);
        neighbor.neighbors.remove(this);
    }
    public void removeNeighbors(Set<Node> n){
        for(Node neighbor: n){
            removeNeighbor(neighbor);
        }
    }
}
