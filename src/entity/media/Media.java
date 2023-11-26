package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.db.AIMSDB;
import utils.Utils;

/**
 * The general media class, for another media it can be done by inheriting this class
 */
public class Media {

    private static Logger LOGGER = Utils.getLogger(Media.class.getName());

    protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String imageURL;
    protected static boolean isSupportedPlaceRushOrder = new Random().nextBoolean();

    public Media() throws SQLException{
        stm = AIMSDB.getConnection().createStatement();
    }

    public Media (int id, String title, String category, int price, int quantity, String type) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;

        //stm = AIMSDB.getConnection().createStatement();
    }


    /**
     * @return int
     * @throws SQLException
     */
    public int getQuantity() throws SQLException{
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }


    /**
     * @param id
     * @return Media
     * @throws SQLException
     */
    public Media getMediaById(int id) throws SQLException{
        String sql = "SELECT * FROM Media ;";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {

            return new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
        }
        return null;
    }


    /**
     * @return List
     * @throws SQLException
     */
    public List getAllMedia() throws SQLException{
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList medium = new ArrayList<>();
        while (res.next()) {
            Media media = new Media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setType(res.getString("type"));
            medium.add(media);
        }
        return medium;
    }


    /**
     * @param tbname
     * @param id
     * @param field
     * @param value
     * @throws SQLException
     */
    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String){
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " "
                + field + "=" + value + " "
                + "where id=" + id + ";");
    }


    /**
     * @return int
     */
    // getter and setter
    public int getId() {
        return this.id;
    }


    /**
     * @param id
     * @return Media
     */
    private Media setId(int id){
        this.id = id;
        return this;
    }


    /**
     * @return String
     */
    public String getTitle() {
        return this.title;
    }


    /**
     * @param title
     * @return Media
     */
    public Media setTitle(String title) {
        this.title = title;
        return this;
    }


    /**
     * @return String
     */
    public String getCategory() {
        return this.category;
    }


    /**
     * @param category
     * @return Media
     */
    public Media setCategory(String category) {
        this.category = category;
        return this;
    }


    /**
     * @return int
     */
    public int getPrice() {
        return this.price;
    }


    /**
     * @param price
     * @return Media
     */
    public Media setPrice(int price) {
        this.price = price;
        return this;
    }


    /**
     * @return String
     */
    public String getImageURL(){
        return this.imageURL;
    }


    /**
     * @param url
     * @return Media
     */
    public Media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }


    /**
     * @param quantity
     * @return Media
     */
    public Media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }


    /**
     * @return String
     */
    public String getType() {
        return this.type;
    }


    /**
     * @param type
     * @return Media
     */
    public Media setType(String type) {
        this.type = type;
        return this;
    }


    /**
     * @return boolean
     */
    public static boolean getIsSupportedPlaceRushOrder() {
        return Media.isSupportedPlaceRushOrder;
    }


    /**
     * @return String
     */
    @Override
    public String toString() {
        return "{" +
                " id='" + id + "'" +
                ", title='" + title + "'" +
                ", category='" + category + "'" +
                ", price='" + price + "'" +
                ", quantity='" + quantity + "'" +
                ", type='" + type + "'" +
                ", imageURL='" + imageURL + "'" +
                "}";
    }

}