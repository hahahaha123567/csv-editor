package csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class Controller {

    private static final Logger logger = Logger.getLogger(Controller.class.getName());
    @FXML
    public Image pic;
    @FXML
    private BorderPane body;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menuFile;
    @FXML
    private MenuItem itemOpen;
    @FXML
    private MenuItem itemSave;
    @FXML
    public MenuItem itemSaveAs;

    private File file;
    private Data data;
    private Main main;

    public void handleOpenAction(ActionEvent actionEvent) {
        FileChooser openChooser = new FileChooser();
        openChooser.setTitle("Open CSV File");
        openChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        try {
            openChooser.setInitialDirectory(new File(new File("").getCanonicalPath()));
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Open Failed");
            alert.showAndWait();
        }
        file = openChooser.showOpenDialog(body.getScene().getWindow());
        if (file != null) {
            loadData();
        }
    }

    public void handleSaveAction(ActionEvent actionEvent) {
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No File Opened");
            alert.showAndWait();
            return;
        }
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(file));
            List<String[]> list = data.getData();
            writer.writeAll(list, false);
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Save Failed");
            alert.showAndWait();
        }
    }

    public void handleSaveAsAction(ActionEvent actionEvent) {
        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("No File Opened");
            alert.showAndWait();
            return;
        }
        FileChooser saveChooser = new FileChooser();
        saveChooser.setTitle("Save as CSV File");
        saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File f = saveChooser.showSaveDialog(body.getScene().getWindow());
        CSVWriter writer;
        try {
            writer = new CSVWriter(new FileWriter(f));
            List<String[]> list = data.getData();
            writer.writeAll(list, false);
            writer.close();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Save Failed");
            alert.showAndWait();
        }
    }

    private void loadData () {
        CSVReader reader = null;
        try {
            logger.info("load file: " + file.getName());
            reader = new CSVReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Load Failed");
            alert.showAndWait();
        }
        data = new Data(reader);
        data.update();
        data.testPrint();
        showData(data);
    }

    private void showData (Data data) {
        TableView<ObservableList<StringProperty>> tableView = new TableView<>();
        tableView.setPlaceholder(new Label("少女祈祷中"));
        tableView.setEditable(true); // nonsense
        // add column
        for (int i = 0; i < data.columnNum; ++i) {
            final int ii = i;
            TableColumn<ObservableList<StringProperty>, String> column = new TableColumn<>();
            column.setCellValueFactory(param -> param.getValue().get(ii));
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            column.setOnEditCommit(t -> {
                data.setProperty(t.getTablePosition().getRow(), t.getTablePosition().getColumn(), t.getNewValue());
                data.testPrint();}
            );
            tableView.getColumns().add(column);
        }
        // add item
        for (int i = 0; i < data.rowNum; ++i) {
            ObservableList<StringProperty> observableList = FXCollections.observableArrayList();
            for (int j = 0; j < data.columnNum; ++j) {
                observableList.add(data.getProperty(i, j));
            }
            tableView.getItems().add(observableList);
        }
        // bind width
        for (int i = 0; i < data.columnNum; ++i) {
            tableView.getColumns().get(i).prefWidthProperty().bind(tableView.widthProperty().divide(data.columnNum));
        }
        body.setCenter(tableView);
    }

    public void setMain (Main mainApp) {
        this.main = mainApp;
    }
}
