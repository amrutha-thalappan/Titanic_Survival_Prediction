/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.List;
import org.apache.commons.lang3.tuple.MutablePair;

/**
 *
 * @author Amrutha
 */
public interface ITree<V> {
    
    public abstract int addNode(V v);
    public abstract void removeNode(V v);
    public abstract void addEdge(V s, V t, int w);
    public abstract void addEdge(int s, int t, int w);
    public abstract void removeEdge(V s, V t);
    public abstract void removeEdge(int s, int t);
    public abstract boolean isAdjacent(V s, V t);
    public abstract boolean isAdjacent(int i, int j);
    public abstract List<V> getNodes();
    public abstract List<MutablePair<String,Integer>> neighbors_of(V v);
    
}
