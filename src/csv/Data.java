package csv;

import com.opencsv.CSVReader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Data {
    private CSVReader reader;
    int rowNum, columnNum;
    private List<StringProperty[]> propertyList;

    Data (CSVReader reader) {
        this.reader = reader;
    }

    void update () {
        List<String[]> stringList = null;
        propertyList = new ArrayList<>();
        try {
            stringList = reader.readAll();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Update Failed");
            alert.showAndWait();
        }
        if (stringList != null) {
            rowNum = stringList.size();
            columnNum = stringList.get(0).length;
            for (String[] strArray : stringList) {
                StringProperty[] properties = new StringProperty[strArray.length];
                for (int i = 0; i < strArray.length; ++i) {
                    properties[i] = new SimpleStringProperty();
                    properties[i].set(strArray[i]);
                }
                propertyList.add(properties);
            }
        } else {
            rowNum = 0;
            columnNum = 0;
        }
    }

    void setProperty (int row, int column, String str) {
        propertyList.get(row)[column] = new SimpleStringProperty(str);
    }

    StringProperty getProperty (int row, int column) {
        return propertyList.get(row)[column];
    }

    List<String[]> getData () {
        List<String[]> ret = new ArrayList<>();
        for (StringProperty[] strArray : propertyList) {
            String[] temp = new String[columnNum];
            for (int i = 0; i < strArray.length; ++i) {
                temp[i] = strArray[i].getValue();
            }
            ret.add(temp);
        }
        return ret;
    }

    void testPrint () {
        for (StringProperty[] strArray : propertyList) {
            for (StringProperty str : strArray) {
                System.out.print(str.getValue() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
