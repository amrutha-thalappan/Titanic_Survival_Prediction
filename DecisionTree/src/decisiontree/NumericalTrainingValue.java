/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisiontree;

/**
 *
 * @author Amrutha
 */
public class NumericalTrainingValue extends TrainingValue {
    
    private Integer numValue;

    public NumericalTrainingValue(Integer numValue){
        this.numValue = numValue;
    }
    public Integer getNumValue() {
        return numValue;
    }

    public void setNumValue(Integer numValue) {
        this.numValue = numValue;
    }
}
