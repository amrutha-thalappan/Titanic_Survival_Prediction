/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author Amrutha
 */
public class DecisionTree {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {        
        PredictionUI predictionUI = new PredictionUI();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;
        predictionUI.setSize(width/2, height*2/3);
        predictionUI.setLocationRelativeTo(null);
        predictionUI.setTitle("Predict Titanic Data");
        predictionUI.setVisible(true);       
    }
    
}
