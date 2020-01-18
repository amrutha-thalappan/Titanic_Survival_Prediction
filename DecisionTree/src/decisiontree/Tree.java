/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 *
 * @author Amrutha
 */
public class Tree implements ITree<String>{
    
    private final List<String> nodes;
    private final Map<String, List<MutablePair<String,Integer>>> adjacencies;

    public Tree() {
        this.nodes = new ArrayList<>();
        this.adjacencies = new HashMap<>();
    }

    public Map<String, List<MutablePair<String,Integer>>> getAdjacencies() {
        return adjacencies;
    }

    @Override
    public int addNode(String v) {
        if(nodes.contains(v)){
            return 0;  
        }else{
            nodes.add(v);
            adjacencies.put(v, new ArrayList<MutablePair<String,Integer>>());
            return 1;
        }
    }

    @Override
    public void removeNode(String v) {
        if(nodes.contains(v)){
            nodes.remove(v); 
            adjacencies.remove(v);
        }
    }

    @Override
    public void addEdge(String v1, String v2, int weight) {
        List<MutablePair<String,Integer>> adjescentVerticesV1 = neighbors_of(v1);
        adjescentVerticesV1.add(new MutablePair<>(v2,weight));
        addNode(v2);               
    }

    @Override
    public void addEdge(int i, int j, int w) {
        String vi = this.nodes.get(i);
        String vj = this.nodes.get(j);
        addEdge(vi, vj, w);
    }

    @Override
    public boolean isAdjacent(String v1, String v2) {
        List<MutablePair<String,Integer>> adjescentVertices = neighbors_of(v1);
        
        for (Pair<String, Integer> pairEntry : adjescentVertices) {
            if(v2 == pairEntry.getKey()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAdjacent(int i, int j) {
        String vi = this.nodes.get(i);
        String vj = this.nodes.get(j);
        return isAdjacent(vi, vj);
    }

    @Override
    public List<String> getNodes() {
        return nodes;
    }

    @Override
    public List<MutablePair<String,Integer>> neighbors_of(String v) {
        return adjacencies.get(v);
    }

    @Override
    public String toString() {
        System.out.println("string");
        return "Graph{" + "vertices=" + nodes + ", adjacencies=" + adjacencies + '}';
    } 

    @Override
    public void removeEdge(String s, String t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeEdge(int s, int t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
