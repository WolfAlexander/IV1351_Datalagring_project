package controller;

import DTO.UserData;
import databaseHandler.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.ucanaccess.jdbc.UcanaccessSQLException;

import java.sql.SQLException;

public class Controller {
    private Stage prmStage;
    private DatabaseHandler dbHandler;
    @FXML ListView<String> productTypesListView;
    @FXML ChoiceBox<String> productTypesChoiceBox;
    @FXML ListView<String> brandsListView;
    @FXML TextField pnr, fnamn, enamn, gadress, postort, postnr, telefon, email;
    @FXML ListView<String> productsInStore;
    @FXML ChoiceBox<String> storesChoiceBox;
    @FXML Label messageToUser;

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
        ObservableList<String> allProductTypes = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
        productTypesListView.setItems(allProductTypes);
    }

    @FXML
    public void showAllBrandsByProductType(){
        ObservableList<String> brands = FXCollections.observableArrayList(dbHandler.getBrandsByProductType(productTypesChoiceBox.getValue()));
        brandsListView.setItems(brands);
    }

    @FXML
    public void addNewCustomer(){
        UserData userData = new UserData(pnr.getText(), fnamn.getText(), enamn.getText(),
                gadress.getText(), postort.getText(), Integer.parseInt(postnr.getText()), telefon.getText(), email.getText());
        try {
            if (dbHandler.registerNewUser(userData))
                messageToUser.setText("New user is successfully registered!");
            else
                messageToUser.setText("No");
        }catch(UcanaccessSQLException ex){
            ex.printStackTrace();
            messageToUser.setText("Your input is incorrect - it does not match database requirements!");
        }catch(SQLException ex) {
            messageToUser.setText("Unable to register user right now! Try again!");
        }catch (IllegalArgumentException argEx){
            messageToUser.setText(argEx.getMessage());
        }
    }

    @FXML
    public void showProductsByStore(){
        ObservableList<String> productsInfo = FXCollections.observableArrayList(dbHandler.getProductsInfoByStore(storesChoiceBox.getValue()));
        productsInStore.setItems(productsInfo);
    }

    @FXML private void initialize(){
        ObservableList<String> productTypes = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
        ObservableList<String> stores = FXCollections.observableArrayList(dbHandler.getAllStores());

        productTypesChoiceBox.setItems(productTypes);
        productTypesChoiceBox.setValue(productTypes.get(0));

        storesChoiceBox.setItems(stores);
        storesChoiceBox.setValue(stores.get(0));
    }
}