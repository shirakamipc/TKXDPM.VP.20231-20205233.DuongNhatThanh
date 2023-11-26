package views.screen.shipping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.Order;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;

public class DeliveryMethodsScreenHandler extends BaseScreenHandler {

  private Order order;

  @FXML
  private RadioButton placeRushOrderValue;

  @FXML
  private RadioButton placeOrderValue;

  @FXML
  private TextField deliveryInstruction;

  @FXML
  private TextField shipmentDetail;

  @FXML
  private DatePicker deliveryTime;

  @FXML
  private Label errorProvince;

  @FXML
  private Button updateDeliveryMethodInfoButton;

  public DeliveryMethodsScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
    super(stage, screenPath);
    this.order = order;
  }

  
  /** 
   * @param event
   * @throws IOException
   */
  @FXML
  private void updateDeliveryMethodInfo(MouseEvent event) throws IOException {
    String deliveryInstructionString = new String(deliveryInstruction.getText());
    String shipmentDetailString = new String(shipmentDetail.getText());
    String deliveryDateString = new String();
    if (deliveryTime.getValue() != null) {
        deliveryDateString = new String(deliveryTime.getValue().toString());
    }

    HashMap<String, String> deliveryData = new HashMap<String, String>();
    deliveryData.put("deliveryInstruction", deliveryInstructionString);
    deliveryData.put("shipmentDetail", shipmentDetailString);
    deliveryData.put("deliveryDate", deliveryDateString);

    int typeDelivery;
    if (placeRushOrderValue.isSelected()) {
        typeDelivery = Configs.PLACE_RUSH_ORDER;
    } else {
        typeDelivery = Configs.PLACE_ORDER;
    }

    PlaceRushOrderController.validatePlaceRushOrderData(deliveryData, typeDelivery);

    // // create invoice screen
    Invoice invoice = getBController().createInvoice(order);
    BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
    InvoiceScreenHandler.setPreviousScreen(this);
    InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
    InvoiceScreenHandler.setScreenTitle("Invoice Screen");
    InvoiceScreenHandler.setBController(getBController());
    InvoiceScreenHandler.show();
  }

  
  /** 
   * @param event
   * @throws IOException
   */
  @FXML
  private void handleBack(MouseEvent event) throws IOException {
    // Back to previous screen
    BaseScreenHandler ShippingScreenHandler = new ShippingScreenHandler(this.stage, Configs.SHIPPING_SCREEN_PATH,
        this.order);
    ShippingScreenHandler.setPreviousScreen(this);
    ShippingScreenHandler.setHomeScreenHandler(homeScreenHandler);
    ShippingScreenHandler.setScreenTitle("Shipping Screen");
    ShippingScreenHandler.setBController(getBController());
    ShippingScreenHandler.show();
  }

  
  /** 
   * @param event
   */
  @FXML
  private void handleDeliveryType(ActionEvent event) {
    if (placeOrderValue.isSelected()) {
      deliveryInstruction.setDisable(true);
      shipmentDetail.setDisable(true);
      deliveryTime.setDisable(true);
    } else if (placeRushOrderValue.isSelected()) {
      deliveryInstruction.setDisable(false);
      shipmentDetail.setDisable(false);
      deliveryTime.setDisable(false);
    }
    handleProvinceError(event);
  }

  
  /** 
   * @param event
   */
  @FXML
  private void handleProvinceError(ActionEvent event) {
    HashMap<String, String> deliveryInfo = this.order.getDeliveryInfo();
    String province = new String(deliveryInfo.get("province"));

    errorProvince.setVisible(false);
    deliveryInstruction.setDisable(true);
    shipmentDetail.setDisable(true);
    deliveryTime.setDisable(true);
    updateDeliveryMethodInfoButton.setDisable(false);

    if (!province.equals("Hà Nội")) {
      if (placeRushOrderValue.isSelected()) {
        errorProvince.setVisible(true);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        updateDeliveryMethodInfoButton.setDisable(true);
      } else {
        updateDeliveryMethodInfoButton.setDisable(false);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
      }
    } else {
      if (placeRushOrderValue.isSelected()) {
        errorProvince.setVisible(false);
        deliveryInstruction.setDisable(false);
        shipmentDetail.setDisable(false);
        deliveryTime.setDisable(false);
        updateDeliveryMethodInfoButton.setDisable(false);
      } else {
        updateDeliveryMethodInfoButton.setDisable(false);
        deliveryInstruction.setDisable(true);
        shipmentDetail.setDisable(true);
        deliveryTime.setDisable(true);
        errorProvince.setVisible(false);
      }
    }
  }

  /**
   * @return PlaceOrderController
   */
  public PlaceOrderController getBController() {
    return (PlaceOrderController) super.getBController();
  }
}