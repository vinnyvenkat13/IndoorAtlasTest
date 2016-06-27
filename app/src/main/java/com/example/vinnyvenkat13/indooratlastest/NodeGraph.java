package com.example.vinnyvenkat13.indooratlastest;

/**
 * Created by vinnyvenkat13 on 6/21/16.
 */
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class NodeGraph {

    ArrayList<Vertex> vertices;

    class Vertex {
        LatLng latLng;
        ArrayList<ListDist> edges;


        Vertex(double lat, double lng) {
            latLng = new LatLng(lat, lng);
            edges = new ArrayList<>();
        }

        public void addEdge(Vertex edge) {
            double dist = findDist(latLng, edge.latLng);
            ListDist toAdd = new ListDist(edge.latLng.latitude, edge.latLng.longitude, dist);
            if(edges.isEmpty()) {
                edges.add(toAdd);
            }
            else {
                int index = 0;
                boolean isFound = false;
                while(!isFound || index >= edges.size()) {
                    if(dist >= edges.get(index).dist) {
                        index--;
                        isFound = true;
                    }
                    else index++;
                }
                edges.add(index, toAdd);
            }
        }

        public double findDist(LatLng latLng1, LatLng latLng2) {
            double lat1 = latLng1.latitude;
            double lat2 = latLng2.latitude;
            double lon1 = latLng1.longitude;
            double lon2 = latLng2.longitude;

            double theta = lon1-lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                    Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                    * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515 * 1.609344;
            return dist;
        }

        @Override
        public boolean equals(Object o) {
            if(o == null || (getClass() != o.getClass())) return false;
            final Vertex check = (Vertex) o;
            if(!latLng.equals(check.latLng)) return false;
            for(int x = 0; x < edges.size(); x++) {
                if(!edges.get(x).loc.equals(check.edges.get(x).loc) ||
                        edges.get(x).dist != check.edges.get(x).dist) {
                    return false;
                }
            }
            return true;
        }
    }

    class ListDist {
        //Vertex vert;
        LatLng loc;
        double dist;

        ListDist(double lat, double lng, double newDist) {
            loc = new LatLng(lat, lng);
            dist = newDist;
        }

        @Override
        public boolean equals(Object o) {
            if(o == null || (getClass() != o.getClass())) return false;
            final ListDist check = (ListDist) o;
            if(!check.loc.equals(loc)) return false;
            return true;
        }
    }

    NodeGraph() {
        //create nodes
        vertices = new ArrayList<>();
        //add nodes, add edges
    }

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }


}
