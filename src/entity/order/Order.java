package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Order {
    
    private int shippingFees;
    private List lstOrderMedia;
    private HashMap<String, String> deliveryInfo;

    public Order(){
        this.lstOrderMedia = new ArrayList<>();
    }

    public Order(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    
    /** 
     * @param om
     */
    public void addOrderMedia(OrderMedia om){
        this.lstOrderMedia.add(om);
    }

    
    /** 
     * @param om
     */
    public void removeOrderMedia(OrderMedia om){
        this.lstOrderMedia.remove(om);
    }

    
    /** 
     * @return List
     */
    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    
    /** 
     * @param lstOrderMedia
     */
    public void setlstOrderMedia(List lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }

    
    /** 
     * @param shippingFees
     */
    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }

    
    /** 
     * @return int
     */
    public int getShippingFees() {
        return shippingFees;
    }

    
    /** 
     * @return HashMap
     */
    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    
    /** 
     * @param deliveryInfo
     */
    public void setDeliveryInfo(HashMap deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    
    /** 
     * @return int
     */
    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            OrderMedia om = (OrderMedia) object;
            amount += om.getPrice();
        }
        return (int) (amount + (10/100)*amount);
    }

}
