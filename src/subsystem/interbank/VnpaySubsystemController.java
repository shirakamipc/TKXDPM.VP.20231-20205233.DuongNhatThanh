package subsystem.interbank;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import common.exception.InternalServerErrorException;
import common.exception.InvalidCardException;
import common.exception.InvalidTransactionAmountException;
import common.exception.InvalidVersionException;
import common.exception.NotEnoughBalanceException;
import common.exception.NotEnoughTransactionInfoException;
import common.exception.SuspiciousTransactionException;
import common.exception.UnrecognizedException;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import utils.Configs;
import utils.MyMap;
import utils.Utils;

public class VnpaySubsystemController {

	private static InterbankBoundary interbankBoundary = new InterbankBoundary();

	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		return null;
	}
	
	private String generateData(Map<String, Object> data) {
		return ((MyMap) data).toJSON();
	}

	public PaymentTransaction payOrder(int amount, String contents) throws URISyntaxException, IOException {

		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
//        amount = amount;
        String bankCode = "";
        
        String vnp_TxnRef = Utils.getRandomNumber(8);
        String vnp_IpAddr = "14.231.8.200";

        String vnp_TmnCode = Configs.vnp_TmnCode;
        String vnp_OrderInfo = "Thanh toan don hang:" + vnp_TxnRef;
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "vn";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Configs.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Utils.hmacSHA512(Configs.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Configs.vnp_PayUrl + "?" + queryUrl;
        System.out.println("hash: " + vnp_SecureHash);
        System.out.println(paymentUrl);
        // Todo: open the website with paymentUrl
        URI payment_website_link = new URI(paymentUrl);
        java.awt.Desktop.getDesktop().browse(payment_website_link);
        
        // query transaction status
        String vnp_RequestId = Utils.getRandomNumber(8);
        String vnp_TransDate = Long.toString(Long.parseLong(vnp_CreateDate) + 1);
        vnp_Command = "querydr";
        String hash_Data= String.join("|", vnp_RequestId, vnp_Version, vnp_Command, vnp_TmnCode, vnp_TxnRef, vnp_TransDate, vnp_CreateDate, vnp_IpAddr, vnp_OrderInfo);
        String vnp_query_SecureHash = Utils.hmacSHA512(Configs.secretKey, hash_Data.toString());
        
		Map<String, Object> vnp_query_Params = new MyMap();
        
		vnp_query_Params.put("vnp_RequestId", vnp_RequestId);
		vnp_query_Params.put("vnp_Version", vnp_Version);
		vnp_query_Params.put("vnp_Command", vnp_Command);
		vnp_query_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_query_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_query_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        //vnp_Params.put("vnp_TransactionNo", vnp_TransactionNo);
		vnp_query_Params.put("vnp_TransactionDate", vnp_TransDate);
		vnp_query_Params.put("vnp_CreateDate", vnp_CreateDate);
		vnp_query_Params.put("vnp_IpAddr", vnp_IpAddr);
		vnp_query_Params.put("vnp_SecureHash", vnp_query_SecureHash);
		
		System.out.println("responseText: " + generateData(vnp_query_Params));
		
		
//        String responseText = interbankBoundary.query(Configs.vnp_ApiUrl, generateData(vnp_query_Params));
//        System.out.println("responseText: " + responseText);
		PaymentTransaction transaction = new PaymentTransaction( (MyMap) vnp_query_Params);
		return transaction;
//        MyMap response = null;
//		try {
//			response = MyMap.toMyMap(responseText, 0);
//			System.out.println("response: " + response);
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			throw new UnrecognizedException();
//		}
	}

	private PaymentTransaction makePaymentTransaction(HashMap response) {
		if (response == null)
			return null;
		MyMap transcation = (MyMap) response.get("transaction");
		CreditCard card = new CreditCard((String) transcation.get("cardCode"), (String) transcation.get("owner"),
				Integer.parseInt((String) transcation.get("cvvCode")), (String) transcation.get("dateExpired"));
		PaymentTransaction trans = new PaymentTransaction((String) response.get("errorCode"), card,
				(String) transcation.get("transactionId"), (String) transcation.get("transactionContent"),
				Integer.parseInt((String) transcation.get("amount")), (String) transcation.get("createdAt"));

		switch (trans.getErrorCode()) {
		case "00":
			break;
		case "01":
			throw new InvalidCardException();
		case "02":
			throw new NotEnoughBalanceException();
		case "03":
			throw new InternalServerErrorException();
		case "04":
			throw new SuspiciousTransactionException();
		case "05":
			throw new NotEnoughTransactionInfoException();
		case "06":
			throw new InvalidVersionException();
		case "07":
			throw new InvalidTransactionAmountException();
		default:
			throw new UnrecognizedException();
		}

		return trans;
	}
	public static void main(String[] args) throws URISyntaxException, IOException {
		VnpaySubsystemController test = new VnpaySubsystemController();
		test.payOrder(500000000, "Kwang iu Myyyyyyy gui My tien an choi");
	}
	
}
