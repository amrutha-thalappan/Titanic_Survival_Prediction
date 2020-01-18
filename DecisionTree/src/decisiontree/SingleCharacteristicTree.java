/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Amrutha
 */
public class SingleCharacteristicTree {
    
    private final Map<String,Integer> targetMap = new HashMap<>();

    public SingleCharacteristicTree() {
    }

    public void setTargetValueToMap(String profileValue, Integer targetValue) {
        this.targetMap.put(profileValue, targetValue);
    }
    
    public String getTargetValue(String profileValue){
        if(!this.targetMap.containsKey(profileValue)){
            return "#";
        }
        return this.targetMap.get(profileValue).toString();
    }

    @Override
    public String toString() {
        return "SingleCharacteristicTree{" + "targetMap=" + targetMap + '}';
    }
 
    
    
}
