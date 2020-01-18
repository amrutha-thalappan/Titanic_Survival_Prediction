/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Amrutha
 */
public class CSVFileHandler {

    public CSVFileHandler() {
    }
    
    public ArrayList<Map<String, Object>> readCSV(String filePath){
        ArrayList<Map<String, Object>> aCsvRecords = new ArrayList<Map<String, Object>>();
        try {
            CSVReader aCsvFileBuffer = new CSVReader(new FileReader(filePath) );  
            String[] aHeaderValues = aCsvFileBuffer.readNext();
            String[] aCsvline;
            while ((aCsvline = aCsvFileBuffer.readNext()) != null) {
                Map<String, Object> aCsvLineMap = new HashMap<String, Object>();
                if(aCsvline.length == aHeaderValues.length){
                    for (int aIdx = 0; aIdx < aCsvline.length; aIdx++) {
                        aCsvLineMap.put(aHeaderValues[aIdx].toLowerCase(), aCsvline[aIdx]) ;
                    }
                    aCsvRecords.add(aCsvLineMap);
                }
            }
        }catch (Exception e){
                e.printStackTrace();
        }
        return aCsvRecords;
    }
    
    public void writeCSV(List<Map<String, Object>> dataList) throws URISyntaxException{
        CSVWriter csvWriteBuffer = null;
        try {
            URL path = DecisionTree.class.getResource("CSVFileHandler.class");
            String predictionCsv = null;
            if(!path.toString().startsWith(Constants.JAR_COLON))
            {
                predictionCsv = Constants.PREDICTION_FILE_PATH;
            }
            else
            { 
                predictionCsv = PredictionUI.predictionFolder + "/prediction.csv";
            }
            csvWriteBuffer = new CSVWriter(new FileWriter(predictionCsv));
            List<String> headers = dataList.stream().flatMap(map -> map.keySet().stream()).distinct().collect(Collectors.toList());
            csvWriteBuffer.writeNext(headers.toArray(new String[0]));
            
            for (Map<String, Object> map : dataList) {
                List<String> aCsvLines = new ArrayList<String>();
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    aCsvLines.add(entry.getValue().toString());
                }
                csvWriteBuffer.writeNext(aCsvLines.toArray(new String[0]));
            }   
        } catch (IOException ex) {
            Logger.getLogger(CSVFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                csvWriteBuffer.close();
            } catch (IOException ex) {
                Logger.getLogger(CSVFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}