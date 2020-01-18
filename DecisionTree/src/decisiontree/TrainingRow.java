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
public class TrainingRow {
    
    private Integer rowId;
    Map<TrainingChar, TrainingValue> rowData;

    public TrainingRow(Integer rowId, Map<TrainingChar, TrainingValue> rowData) {
        this.rowId = rowId;
        this.rowData = rowData;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public Map<TrainingChar, TrainingValue> getRowData() {
        return rowData;
    }

    public void setRowData(Map<TrainingChar, TrainingValue> rowData) {
        this.rowData = rowData;
    }    
}
