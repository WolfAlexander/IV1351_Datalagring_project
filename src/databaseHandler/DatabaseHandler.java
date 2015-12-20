package databaseHandler;

import DTO.UserData;
import net.ucanaccess.jdbc.UcanaccessSQLException;
import util.CheckInput;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseHandler {
    static protected Connection con;
    private String URL = "jdbc:ucanaccess://DatalagringV8.5.accdb";
    private String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private String userID = "";
    private String password = "";

    /**
     * This method connects to database
     * @return boolean true if connection established, else false
     */
    public boolean connect(){
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(URL, userID, password);
            con.setAutoCommit(false);
        } catch (Exception e){

            return false;
        }
        return true;
    }

    /**
     * This method gets list of product type in db
     * @return list of product types
     */
    public ArrayList<String> getAllProductTypes(){
        ArrayList<String> productTypes = new ArrayList<String>();
        String query = "SELECT namn FROM ProduktTyp";

        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
                productTypes.add(rs.getString("namn"));

            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return productTypes;
    }

    /**
     * This method get productbrands for a specific product type
     * @param productType is given product type
     * @return list of brands
     */
    public ArrayList<String> getBrandsByProductType(String productType){
        ArrayList<String> brands = new ArrayList<String>();
        String query = "SELECT DISTINCT Marke.namn\n" +
                "         FROM Marke , ProduktTyp, Paket, Produkt\n" +
                "         WHERE ProduktTyp.namn = ?\n" +
                "         AND Produkt.produktTyp = ProduktTyp.produktTypID\n" +
                "         AND Paket.produkt = Produkt.namn\n" +
                "         AND Paket.marke = Marke.markeID";

        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, productType);
            ResultSet rs = statement.executeQuery();

            while(rs.next())
                brands.add(rs.getString("namn"));

            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return brands;
    }

    /**
     * This method inserts new user into database
     * @param userData is user information of type DTO.UserData
     * @return int 0 if user is not added or card number if user successfully added
     * @throws SQLException if an sql error happened
     * @throws IllegalArgumentException can be thrown any field input do not pass util.CheckInput validation
     */
    public int registerNewUser(UserData userData) throws SQLException, IllegalArgumentException, UcanaccessSQLException{
        CheckInput.checkUserDataInput(userData);
        String query = "INSERT INTO Medlem(pnr, fnamn, enamn, gaddress, postOrt, postNr, kortNr) " +
                "VALUES( ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = con.prepareStatement(query);
        statement.setString(1, userData.getPnr());
        statement.setString(2, userData.getFnamn());
        statement.setString(3, userData.getEnamn());
        statement.setString(4, userData.getGadress());
        statement.setString(5, userData.getPostort());
        statement.setInt(6, Integer.parseInt(userData.getPostnr()));
        int cardNr = generateNumber();
        statement.setString(7, Integer.toString(cardNr));

        int rs = statement.executeUpdate();

        con.commit();
        statement.close();

        if(rs == 1)
            rs = cardNr;

        return rs;
    }

    /**
     * This method gets list of stores in db
     * @return list of stores
     */
    public ArrayList<String> getAllStores(){
        ArrayList<String> stores = new ArrayList<String>();
        String query = "SELECT namn FROM Butik";

        try{
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
                stores.add(rs.getString("namn"));

        }catch(SQLException ex){
            ex.printStackTrace();
        }

        return stores;
    }

    /**
     * This method gets product's name, size, unit and amount of the product in specific store
     * @param store is given store name
     * @return list of productinformation
     */
    public ArrayList<String> getProductsInfoByStore(String store){
        ArrayList<String> productsInfo = new ArrayList<String>();
        String query = "SELECT Marke.namn, Paket.produkt, Paket.storlek, Enhet.namn, PaketStatus.antal\n" +
                "FROM Marke, Paket, Butik, PaketStatus, Produkt, Enhet\n" +
                "WHERE Butik.namn = ?\n" +
                "AND Butik.butikID = PaketStatus.butik\n" +
                "AND PaketStatus.paket = Paket.streckkod\n" +
                "AND Paket.produkt = Produkt.namn\n" +
                "AND Enhet.enhetID = Produkt.enhet " +
                "AND Paket.marke = Marke.markeID ORDER BY Marke.namn";
        try{
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, store);
            ResultSet rs = statement.executeQuery();

            while(rs.next())
                productsInfo.add(rs.getString("Marke.namn") + " " + rs.getString("produkt") + " " + rs.getString("storlek").replace("E0", "") + " " + rs.getString("Enhet.namn") + " has current amount " + rs.getString("antal"));

            statement.close();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        return productsInfo;
    }

    private int generateNumber(){
        Random random = new Random();
        return 10000000 + random.nextInt(9999999);
    }
}