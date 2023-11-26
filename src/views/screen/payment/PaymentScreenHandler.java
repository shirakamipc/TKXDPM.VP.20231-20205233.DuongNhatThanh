package views.screen.payment;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import controller.PaymentController;
import entity.cart.Cart;
import common.exception.PlaceOrderException;
import entity.invoice.Invoice;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import subsystem.interbank.VnpaySubsystemController;
import utils.Configs;
import utils.MyMap;
import views.screen.BaseScreenHandler;
import views.screen.popup.PopupScreen;

public class PaymentScreenHandler extends BaseScreenHandler {
	private String paymentType = "CREDITCARD";
	
	@FXML
	private RadioButton creditCardPaymentMethod;
	
	@FXML
	private RadioButton vnpayPaymentMethod;

	@FXML
	private Button btnConfirmPayment;
	
	@FXML
	private VBox cardInfo;

	@FXML
	private ImageView loadingImage;
	
	@FXML
	private Label pageTitle;

	@FXML
	private TextField cardNumber;

	@FXML
	private TextField holderName;

	@FXML
	private TextField expirationDate;

	@FXML
	private TextField securityCode;

	private Invoice invoice;
	
	@FXML
	private void handlePaymentType() {
//		creditCardPaymentMethod.setDisable(true);
//		vnpayPaymentMethod.setDisable(true);
//	    expirationDate.setDisable(true);
//	    securityCode.setDisable(true);
	   if (!creditCardPaymentMethod.isSelected()) {
	    cardNumber.setDisable(true);
	    holderName.setDisable(true);
	    expirationDate.setDisable(true);
	    securityCode.setDisable(true);
	   }else {
		cardNumber.setDisable(false);
		holderName.setDisable(false);
		expirationDate.setDisable(false);
		securityCode.setDisable(false);
	   }
//	   System.out.println(cardNumber.getText());
//	    handleProvinceError(event);
	 }

	public PaymentScreenHandler(Stage stage, String screenPath, int amount, String contents) throws IOException {
		super(stage, screenPath);
	}

	public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		
		creditCardPaymentMethod.setOnMouseClicked(e-> {
			handlePaymentType();
		});
		vnpayPaymentMethod.setOnMouseClicked(e-> {
			handlePaymentType();
		});
		btnConfirmPayment.setOnMouseClicked(e -> {
			try {
				confirmToPayOrder();
				((PaymentController) getBController()).emptyCart();
			} catch (Exception exp) {
				System.out.println(exp.getStackTrace());
			}
		});
	}

	
	
	

	void confirmToPayOrder() throws IOException{
		String contents = "pay order";
		PaymentController ctrl = (PaymentController) getBController();
		Map<String, Object> response = ctrl.payOrder(invoice.getAmount(), contents, cardNumber.getText(), holderName.getText(),
				expirationDate.getText(), securityCode.getText());
		if (vnpayPaymentMethod.isSelected()) {
			paymentType = "VNPAY";
			VnpaySubsystemController test = new VnpaySubsystemController();
			try {
				test.payOrder(invoice.getAmount(), "Kwang iu Myyyyyyy gui My tien an choi");
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, Configs.RESULT_SCREEN_PATH, (String) response.get("RESULT"),(String) response.get("MESSAGE"), paymentType, (MyMap) response.get("VNPAY_TRANSACTION"));
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
	}

}