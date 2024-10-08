package controller;

import entity.order.Order;

import java.util.Random;
import java.util.HashMap;
import java.util.logging.Logger;
import entity.shipping.Shipment;

/**
 * This class of UC place rush order in AIMS project
 */
public class PlaceRushOrderController extends PlaceOrderController{
    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    /**
     * calculate Shipping Fee when place rush order
     * @param order
     * @return
     */
    public int calculateShippingFee(Order order) {
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount());
        int additionalFee = (int)( 0.1*order.getAmount());
        fees += additionalFee;
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }

    public static void validatePlaceRushOrderData(HashMap<String, String> deliveryData, int typeDelivery) {
        if (typeDelivery == utils.Configs.PLACE_RUSH_ORDER) {
            Shipment shipment = new Shipment();
        }
    }

}
