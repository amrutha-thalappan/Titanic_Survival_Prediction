/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.awt.HeadlessException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;

/**
 *
 * @author Amrutha
 */
public class DecisionTreeDao {
    
    private SingleCharacteristicTree singleCharacteristicTree;
    ArrayList characterList = new ArrayList();
    
    public void extractTrainingData(ArrayList<String> characteristcs) throws URISyntaxException{
        this.characterList = characteristcs;
        TrainingDataSet trainingDataSet= new TrainingDataSet();
        CSVFileHandler cSVFileHandler = new CSVFileHandler();
        List<Map<String, Object>> dataSet = null;
        try{
            URL path = DecisionTree.class.getResource("DecisionTree.class");
            String trainCsv = null;
            if(!path.toString().startsWith(Constants.JAR_COLON))
            {
                URL resPath  = getClass().getClassLoader().getResource(Constants.TRAIN_FILE_LOCATION);
                trainCsv = Paths.get(resPath.toURI()).toFile().getAbsolutePath();                
            }
            else
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Training File");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    trainCsv = fileChooser.getSelectedFile().getAbsolutePath();
                }
            }  
            
            dataSet = cSVFileHandler.readCSV(trainCsv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(dataSet == null){
            System.out.println (Constants.NULL_DATASET);
        }
        trainingDataSet.setTrainingChars(characteristcs);
        ArrayList<TrainingRow> trainingRows = trainingDataSet.setTrainingRows(dataSet);
        singleCharacteristicTree = trainingDataSet.createSingleCharTree(trainingRows);
    }
    
    public ArrayList<List<String>> extractTestData(){
        ArrayList<List<String>> lines = new ArrayList<>();
        CSVFileHandler cSVFileHandler = new CSVFileHandler();
        List<Map<String, Object>> testData = null;
        try{
            URL path = DecisionTree.class.getResource("DecisionTree.class");
            String testCsv = null;
            if(!path.toString().startsWith(Constants.JAR_COLON))
            {
                URL resPath  = getClass().getClassLoader().getResource(Constants.TEST_FILE_LOCATION);
                testCsv = Paths.get(resPath.toURI()).toFile().getAbsolutePath();                
            }
            else
            {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose Test File");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    testCsv = fileChooser.getSelectedFile().getAbsolutePath();
                    PredictionUI.predictionFolder = fileChooser.getSelectedFile().getParentFile().getAbsolutePath();
                }
            }  
            testData = cSVFileHandler.readCSV(testCsv);
        } catch (HeadlessException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }
        if(testData == null){
            System.out.println (Constants.NULL_DATASET);
        }
        predictTargetOfTestData(characterList, testData);
        createPredictionListToDisplay(testData, lines);
        return lines;
    }

    private void createPredictionListToDisplay(List<Map<String, Object>> testData, ArrayList<List<String>> lines) {
        Iterator<Map<String, Object>> testDataListIterator = testData.iterator();
        while (testDataListIterator.hasNext()) {
            Map<String, Object> testDataMap = testDataListIterator.next();
            List<String> rows = new ArrayList<>();
            rows.add(testDataMap.get(Constants.PASSENGER_ID).toString());
            if(testDataMap.get(Constants.SURVIVED).toString().equals("0")){
                rows.add(Constants.PASSENGER_NOT_SURVIVED);
            }else{
                rows.add(Constants.PASSENGER_SURVIVED);
            }
            lines.add(rows);
        }
    }

    private Boolean predictTargetOfTestData(ArrayList<String> characteristcs, List<Map<String, Object>> testData) {
        CSVFileHandler cSVFileHandler = new CSVFileHandler();
        ArrayList<String> characterListToPredict = new ArrayList();
        characteristcs.stream().filter((character) -> (!character.equalsIgnoreCase(Constants.SURVIVED))).forEachOrdered((character) -> {
            characterListToPredict.add(character);
        });
        List<String> headers = testData.stream().flatMap(map -> map.keySet().stream()).distinct().collect(Collectors.toList());
        for(String characterToPredict: characterListToPredict){
            if(headers.contains(characterToPredict)){
                testData = predictTargetOfSingleCharacter(characterToPredict, testData);
            }
        }   
        try{
            cSVFileHandler.writeCSV(testData);
            return true;
        }catch(URISyntaxException e){
            return false;
        }
    }

    private List<Map<String, Object>> predictTargetOfSingleCharacter(String characterToPredict, List<Map<String, Object>> testData) {
        Iterator<Map<String, Object>> testDataListIterator = testData.iterator();
        while (testDataListIterator.hasNext()) {
            Map<String, Object> testDataMap = testDataListIterator.next();
            if(testDataMap.keySet().contains(characterToPredict)){
                testDataMap.put(Constants.SURVIVED, singleCharacteristicTree.getTargetValue((String) testDataMap.get(characterToPredict)));
            }
        }
        return testData;
    }
}
