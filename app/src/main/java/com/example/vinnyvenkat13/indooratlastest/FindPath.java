package com.example.vinnyvenkat13.indooratlastest;

import java.util.ArrayList;

/**
 * Created by vinnyvenkat13 on 6/21/16.
 */
public class FindPath {

    ArrayList<NodeGraph.Vertex> graph;
    NodeGraph.Vertex start;

    FindPath() {
        NodeGraph temp = new NodeGraph();
        graph = temp.getVertices();
        start = graph.get(0);
    }

    /*
     * This is a function that finds the optimal path in the graph using the A* algorithm
     * It returns an arraylist ordered by the optimal path
     */
    public ArrayList<NodeGraph.Vertex> getPath(NodeGraph.Vertex goal) {
        ArrayList<NodeGraph.Vertex> closed = new ArrayList<>();
        ArrayList<NodeGraph.Vertex> open = new ArrayList<>();
        NodeGraph.Vertex[] cameFrom = new NodeGraph.Vertex[graph.size()];
        open.add(start);
        //Cost of going from start node to each node, ordered in the way of the graph list
        double[] gScore = new double[graph.size()];
        //cost of start is 0
        gScore[0] = 0.0;
        double[] fScore = new double[graph.size()];
        fScore[0] = start.findDist(start.latLng, goal.latLng);

        while(!open.isEmpty()) {
            NodeGraph.Vertex curr = null;
            int i = 0;
            double lowest = fScore[0];
            for(NodeGraph.Vertex v : graph) {
                //write if it has the lowest fScore, next line belongs in if statement
                if(fScore[i] < lowest) {
                    lowest = fScore[i];
                    curr = v;
                }
                i++;
            }
            if(curr.equals(goal)) {
                //return path
                return reconstructPath(cameFrom, curr);
            }
            open.remove(curr);
            closed.add(curr);
            for(NodeGraph.ListDist ld : curr.edges) { //looks of neighbors of curr
                NodeGraph.Vertex neighbor = null;
                for(NodeGraph.Vertex v : graph) { //may be better as a while loop
                    if(v.latLng.equals(ld)) {
                        neighbor = v;
                    }
                }
                if(!closed.contains(neighbor)) {
                    int indexCurr = graph.indexOf(curr);
                    int indexNei = graph.indexOf(neighbor);
                    double ten_gScore = gScore[indexCurr] + ld.dist;
                    if(!open.contains(neighbor)) open.add(neighbor);
                    else if(ten_gScore < gScore[indexNei]) {
                        //deal with CameFrom
                        cameFrom[indexNei] = curr;
                        gScore[indexNei] = ten_gScore;
                        fScore[indexNei] = gScore[indexNei] + neighbor.findDist(neighbor.latLng,
                                goal.latLng);
                    }
                }
            }
        }
        return null;
    }

    private ArrayList<NodeGraph.Vertex> reconstructPath(NodeGraph.Vertex[] cameFrom, NodeGraph.Vertex curr) {
        ArrayList<NodeGraph.Vertex> totalPath = new ArrayList<>();
        totalPath.add(curr);
        int currIndex = 0;
        for(int x = 0; x < cameFrom.length; x++) {
            if(cameFrom[x].equals(curr)) {
                currIndex = x;
            }
        }
        boolean isDone = false;
        while(!isDone) {
            NodeGraph.Vertex currVertex = cameFrom[currIndex];
            if(currVertex != null) {
                totalPath.add(0, currVertex);
                for(int x = 0; x < cameFrom.length; x++) {
                    if(cameFrom[x].equals(curr)) {
                        currIndex = x;
                    }
                }
            }
            else {
                isDone = true;
            }
        }
        return totalPath;
    }

}
