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
    private String errorBeforeView = "";

    /**
     * Controller constructor
     */
    public Controller(){
        dbHandler = new DatabaseHandler();
        if(!dbHandler.connect())
            errorBeforeView = "Could not find database!";

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

    /**
     * This method is called when ShowAllProductTypes button is clicked
     * This method gets calls database handler to get all product types from database
     * and outputs result in a list view
     */
    @FXML
    public void showAllProductTypes(){
        resetMessageToUser();
        try {
            ObservableList<String> allProductTypes = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
            productTypesListView.setItems(allProductTypes);
        }catch (NullPointerException ex){
            messageToUser.setText(errorBeforeView);
        }
    }

    /**
     * This method is called when ShowBrands button is clicked
     * This method gets list of brands by product type from database handler
     * and outputs it in the list view for brands
     */
    @FXML
    public void showAllBrandsByProductType(){
        resetMessageToUser();
        try {
        ObservableList<String> brands = FXCollections.observableArrayList(dbHandler.getBrandsByProductType(productTypesChoiceBox.getValue()));
        brandsListView.setItems(brands);
        }catch (NullPointerException ex){
            messageToUser.setText(errorBeforeView);
        }
    }

    /**
     * This method is called when Register button is clicked
     * This method will send data that user entered to database handler
     * and output message to user depending on if registration went well or not
     */
    @FXML
    public void addNewCustomer(){
        resetMessageToUser();
        UserData userData = new UserData(pnr.getText(), fnamn.getText(), enamn.getText(),
                gadress.getText(), postort.getText(), postnr.getText(), telefon.getText(), email.getText());
        try {
            int cardNr = dbHandler.registerNewUser(userData);
            if (cardNr != 0) {
                messageToUser.setText("New customer is successfully registered! Card number: " + cardNr);
                emptyAllInputFields();
            }else
                messageToUser.setText("Could not add new customer - your input may not match database requirements!");
        }catch(SQLException ex) {
            messageToUser.setText("Unable to register new user right now! Please try again.");
        }catch (IllegalArgumentException ex){
            messageToUser.setText(ex.getMessage());
        }catch (NullPointerException ex){
            messageToUser.setText(errorBeforeView);
        }
    }

    /**
     * This method is called when ShowProductsByStore button is clicked
     * This method will call databasehandler and output result of database answer
     */
    @FXML
    public void showProductsByStore(){
        resetMessageToUser();
        try{
            ObservableList<String> productsInfo = FXCollections.observableArrayList(dbHandler.getProductsInfoByStore(storesChoiceBox.getValue()));
            productsInStore.setItems(productsInfo);
            if(productsInfo.size() == 0)
                messageToUser.setText("No results found!");
        }catch (NullPointerException ex){
            messageToUser.setText(errorBeforeView);
        }
    }

    private void resetMessageToUser(){
        messageToUser.setText("");
    }

    private void emptyAllInputFields(){
        pnr.setText("");
        fnamn.setText("");
        enamn.setText("");
        gadress.setText("");
        postort.setText("");
        postnr.setText("");
        telefon.setText("");
        email.setText("");
    }

    @FXML private void initialize(){
        try {
            ObservableList<String> productTypes = FXCollections.observableArrayList(dbHandler.getAllProductTypes());
            ObservableList<String> stores = FXCollections.observableArrayList(dbHandler.getAllStores());

            productTypesChoiceBox.setItems(productTypes);
            productTypesChoiceBox.setValue(productTypes.get(0));

            storesChoiceBox.setItems(stores);
            storesChoiceBox.setValue(stores.get(0));
        }catch (Exception ex){
            messageToUser.setText(errorBeforeView);
        }
    }
}