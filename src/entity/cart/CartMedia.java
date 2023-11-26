package entity.cart;

import entity.media.Media;

public class CartMedia {

    private Media media;
    private int quantity;
    private int price;

    public CartMedia() {

    }

    public CartMedia(Media media, Cart cart, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }

    
    /** 
     * @return Media
     */
    public Media getMedia() {
        return this.media;
    }

    
    /** 
     * @param media
     */
    public void setMedia(Media media) {
        this.media = media;
    }

    
    /** 
     * @return int
     */
    public int getQuantity() {
        return this.quantity;
    }

    
    /** 
     * @param quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    /** 
     * @return int
     */
    public int getPrice() {
        return this.price;
    }

    
    /** 
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    
    /** 
     * @return String
     */
    @Override
    public String toString() {
        return "{"
                + " media='" + media + "'"
                + ", quantity='" + quantity + "'"
                + "}";
    }

}
