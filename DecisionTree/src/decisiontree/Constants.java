/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

import java.util.Map;

/**
 *
 * @author Amrutha
 */
public class Constants {
    public static final String SELECT = "-Select-";
    public static final String SEX = "sex";
    public static final String MALE = "male";
    public static final String FEMALE = "female";
    public static final String CLASS = "pclass";
    public static final String CLASS_ONE = "1";
    public static final String CLASS_TWO = "2";
    public static final String CLASS_THREE = "3";
    public static final String SURVIVED_NOT = "survived_not";
    public static final String SURVIVED_YES = "survived_yes";
    public static final String SURVIVED = "survived";
    
    static final Map<Integer, String> SURVIVE_TAEGET_MAP = Map.of(
                                                           0, "survived_not", 
                                                           1, "survived_yes"
                                                           );
    public static String PREDICTION_FILE_PATH = "src/resources/prediction.csv";
    public static String JAR_COLON = "jar:";
    public static String PREDICTION_FILE = "/prediction.csv";
    public static String TRAIN_FILE_LOCATION = "resources/train.csv";
    public static String TEST_FILE_LOCATION = "resources/test.csv";
    public static String NULL_DATASET = "dataset null";
    public static String PASSENGER_ID = "passengerid";
    public static String PASSENGER_SURVIVED = "Survived";
    public static String PASSENGER_NOT_SURVIVED = "Not Survived";
}
