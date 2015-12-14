package databaseHandler;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler {
    static protected Connection con;
    private String URL = "jdbc:ucanaccess://DatalagringV6.accdb";
    private String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
    private String userID = "";
    private String password = "";

    public boolean connect(){
        try{
            Class.forName(driver);
            con = DriverManager.getConnection(URL, userID, password);
            con.setAutoCommit(false);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

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
}
