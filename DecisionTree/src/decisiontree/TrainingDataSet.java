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
public class TrainingDataSet {
    
    ArrayList<TrainingChar> trainingChars;
    ArrayList<TrainingRow> trainingRows;

    public TrainingDataSet() {
        this.trainingChars = new ArrayList<>();
        this.trainingRows = new ArrayList<>();
    }

    public ArrayList<TrainingChar> getTrainingChars() {
        return trainingChars;
    }

    public void setTrainingChars(ArrayList<String> characteristics) {
        TrainingChar trainingChar;
        for(String characteristic : characteristics){
            trainingChar = new TrainingChar(characteristic);
            trainingChars.add(trainingChar);
        }
    }

    public ArrayList<TrainingRow> getTrainingRows() {
        return trainingRows;
    }

    public ArrayList<TrainingRow> setTrainingRows(List<Map<String, Object>> aCsvRecords) {        
        convertCsvRecordsToTrainingRow(trainingChars, aCsvRecords); 
        return this.trainingRows;
    }

    private void convertCsvRecordsToTrainingRow(List<TrainingChar> trainingCharList, List<Map<String, Object>> aCsvRecords) {
        for(Map<String, Object> mapEntry : aCsvRecords){
            Map<TrainingChar, TrainingValue> trainingRowMap = new HashMap<>();;
            for(TrainingChar trainingChar: trainingCharList){
                if(mapEntry.containsKey(trainingChar.getName())){
                    Object trainingValue = mapEntry.get(trainingChar.getName());
                    TrainingValue aTrainigValue = null;
                    try{
                        aTrainigValue = new NumericalTrainingValue(Integer.parseInt(trainingValue.toString())); 
                    }catch(Exception e){
                        aTrainigValue = new TextualTrainingValue((String) trainingValue); 
                    }                    
                    trainingRowMap.put(trainingChar, aTrainigValue);
                }
            }
            this.trainingRows.add(new TrainingRow(Integer.parseInt((String)mapEntry.get("passengerid")), trainingRowMap));            
        }
    }
    
    public SingleCharacteristicTree createSingleCharTree(ArrayList<TrainingRow> trainingRows){
        SingleCharacteristicTree singleCharacteristicTree = new SingleCharacteristicTree();
        Integer noOfPeopleNotSurvived = 0;
        Integer noOfPeopleSurvived = 0;
        Tree characterTree = null;
        Integer targetValue = null;
        String profileValue = null;
        List<String> profileValueList = new ArrayList<>();
        for(TrainingChar trainingChar:trainingChars){
            if(!trainingChar.getName().equals(Constants.SURVIVED)){
               characterTree = createDecisionTree(trainingChar.getName());
            }
        }
        for(TrainingRow row : trainingRows){
            for(Map.Entry<TrainingChar, TrainingValue> trainingRow : row.getRowData().entrySet()){
                if(!trainingRow.getKey().getName().equals(Constants.SURVIVED)){
                    if(trainingRow.getValue() instanceof TextualTrainingValue){
                        profileValue = ((TextualTrainingValue)trainingRow.getValue()).getTextValue();
                    }else{
                        profileValue = ((NumericalTrainingValue)trainingRow.getValue()).getNumValue().toString();
                    }
                    if(!profileValueList.contains(profileValue)){
                        profileValueList.add(profileValue);
                    }
                }else{
                   targetValue = ((NumericalTrainingValue)trainingRow.getValue()).getNumValue();
                }
            }
            List<MutablePair<String, Integer>> neighbours = characterTree.neighbors_of(profileValue);
            for(int idxNeighbours=0; idxNeighbours<neighbours.size();idxNeighbours++){
                Pair<String, Integer> neighbourVertex = neighbours.get(idxNeighbours);
                if(neighbourVertex.getKey().equals(Constants.SURVIVE_TAEGET_MAP.get(targetValue))){
                    neighbourVertex.setValue(neighbourVertex.getValue()+1);
                }
            }
        }
        for(String profValue: profileValueList){
            List<MutablePair<String, Integer>> neighbours = characterTree.neighbors_of(profValue);
            for(int idxNeighbours=0; idxNeighbours<neighbours.size();idxNeighbours++){
                Pair<String, Integer> neighbourVertex = neighbours.get(idxNeighbours);
                if(neighbourVertex.getKey().equals(Constants.SURVIVED_NOT)){
                    noOfPeopleNotSurvived = neighbourVertex.getValue();
                }else {
                    noOfPeopleSurvived = neighbourVertex.getValue();
                }
            }
            if(noOfPeopleSurvived >= noOfPeopleNotSurvived){
                singleCharacteristicTree.setTargetValueToMap(profValue, 1);
            }else{
                singleCharacteristicTree.setTargetValueToMap(profValue, 0);
            }
        }
        System.out.println(singleCharacteristicTree.toString());
        return singleCharacteristicTree;
    }

    private Tree createDecisionTree(String character) {
        Tree characterTree = new Tree();
        switch(character) {
        case Constants.SEX :
            characterTree.addNode(Constants.SEX);
            characterTree.addNode(Constants.MALE);
            characterTree.addNode(Constants.FEMALE);
            characterTree.addNode(Constants.SURVIVED_NOT);
            characterTree.addNode(Constants.SURVIVED_YES);
            characterTree.addEdge(0, 1, 0);
            characterTree.addEdge(0, 2, 0);
            characterTree.addEdge(1, 3, 0);
            characterTree.addEdge(1, 4, 0);
            characterTree.addEdge(2, 3, 0);
            characterTree.addEdge(2, 4, 0);
           break; 
        case Constants.CLASS :
            characterTree.addNode(Constants.CLASS);
            characterTree.addNode(Constants.CLASS_ONE);
            characterTree.addNode(Constants.CLASS_TWO);
            characterTree.addNode(Constants.CLASS_THREE);
            characterTree.addNode(Constants.SURVIVED_NOT);
            characterTree.addNode(Constants.SURVIVED_YES);
            characterTree.addEdge(0, 1, 0);
            characterTree.addEdge(0, 2, 0);
            characterTree.addEdge(0, 3, 0);
            characterTree.addEdge(1, 4, 0);
            characterTree.addEdge(1, 5, 0);
            characterTree.addEdge(2, 4, 0);
            characterTree.addEdge(2, 5, 0);
            characterTree.addEdge(3, 4, 0);
            characterTree.addEdge(3, 5, 0);
           break; // optional
        default : // Optional
           // Statements
            break; 
        }
        return characterTree;
    }
}
