package controller;

import databaseHandler.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
    private Stage prmStage;
    private DatabaseHandler dbHandler;
    @FXML ListView<String> productTypesListView;
    @FXML ChoiceBox<String> productTypesChoiceBox;
    @FXML ListView<String> brandsListView;
    private ObservableList<String> productTypes;

    /**
     * Controller constructor
     */
    public Controller(){
        dbHandler = new DatabaseHandler();
        dbHandler.connect();
    }

    /**
     * Stores reference to primary stage
     * @param prmStage is a reference to primary stage of type Stage
     */
    public void setPrmStage(Stage prmStage) {
        this.prmStage = prmStage;
    }

    /**
     * This method load the fxml view and shows it in primary stage(the window)
     */
    public void launchView(){
        try {
            Parent scene = FXMLLoader.load(getClass().getResource("/show_result.fxml"));
            prmStage.setScene(new Scene(scene));
        } catch (Exception ex) {
            System.err.println("Could not load view!");
            ex.printStackTrace();
        }
    }

    @FXML
    public void showAllProductTypes(){
        ObservableList<String> databaseChoices = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
        productTypesListView.setItems(databaseChoices);
    }

    @FXML
    public void showAllBrandsByProductType(){

    }

    @FXML
    public void addNewCustomer(){

    }

    @FXML private void initialize(){
        productTypes = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
        productTypesChoiceBox.setItems(productTypes);
        productTypesChoiceBox.setValue(productTypes.get(0));
    }
}