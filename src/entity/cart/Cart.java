package entity.cart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import common.exception.MediaNotAvailableException;
import entity.media.Media;

public class Cart {

    private List<CartMedia> lstCartMedia;
    private static Cart cartInstance;


    /**
     * @return Cart
     */
    public static Cart getCart(){
        if(cartInstance == null) cartInstance = new Cart();
        return cartInstance;
    }

    private Cart(){
        lstCartMedia = new ArrayList<>();
    }


    /**
     * @param cm
     */
    public void addCartMedia(CartMedia cm){
        lstCartMedia.add(cm);
    }


    /**
     * @param cm
     */
    public void removeCartMedia(CartMedia cm){
        lstCartMedia.remove(cm);
    }


    /**
     * @return List
     */
    public List getListMedia(){
        return lstCartMedia;
    }

    public void emptyCart(){
        lstCartMedia.clear();
    }


    /**
     * @return int
     */
    public int getTotalMedia(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getQuantity();
        }
        return total;
    }


    /**
     * @return int
     */
    public int calSubtotal(){
        int total = 0;
        for (Object obj : lstCartMedia) {
            CartMedia cm = (CartMedia) obj;
            total += cm.getPrice()*cm.getQuantity();
        }
        return total;
    }


    /**
     * @throws SQLException
     */
    public void checkAvailabilityOfProduct() throws SQLException{
        boolean allAvai = true;
        for (Object object : lstCartMedia) {
            CartMedia cartMedia = (CartMedia) object;
            int requiredQuantity = cartMedia.getQuantity();
            int availQuantity = cartMedia.getMedia().getQuantity();
            if (requiredQuantity > availQuantity) allAvai = false;
        }
        if (!allAvai) throw new MediaNotAvailableException("Some media not available");
    }


    /**
     * @param media
     * @return CartMedia
     */
    public CartMedia checkMediaInCart(Media media){
        for (CartMedia cartMedia : lstCartMedia) {
            if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
        }
        return null;
    }

}
