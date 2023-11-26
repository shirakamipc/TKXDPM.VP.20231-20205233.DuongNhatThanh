package views.screen.payment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.MyMap;
import views.screen.BaseScreenHandler;

public class ResultScreenHandler extends BaseScreenHandler {

	private String result;
	private String message;
	private String paymentType;
	
	@FXML
	private Label VNpayMessage;
	
	@FXML
	private Button checkVNpay;

	public ResultScreenHandler(Stage stage, String screenPath, String result, String message, String paymentType, MyMap transactionData) throws IOException {
		super(stage, screenPath);
		resultLabel.setText(result);
		messageLabel.setText(message);
		if(!this.paymentType.equals("VNPAY")) {
			VNpayMessage.setVisible(false);
			checkVNpay.setVisible(false);
		}else {
			VNpayMessage.setVisible(true);
			checkVNpay.setVisible(true);
			checkVNpay.setOnMouseClicked(e -> {
				try {
					checkVNPayTransaction(transactionData);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
	}

	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Button okButton;
	
	@FXML
	private Label messageLabel;

	@FXML
	void confirmPayment(MouseEvent event) throws IOException {
		homeScreenHandler.show();
	}
	
	void checkVNPayTransaction(MyMap vnp_query_Params) throws IOException{
		URL url = new URL (Configs.vnp_ApiUrl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes((vnp_query_Params).toJSON());
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + vnp_query_Params);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
        response.append(output);
        }
        in.close();
        System.out.println(response.toString());
//        return response
	}

}
