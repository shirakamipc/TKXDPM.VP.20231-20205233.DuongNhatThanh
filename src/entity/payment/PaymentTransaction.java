package entity.payment;

import utils.MyMap;

public class PaymentTransaction {
	private String errorCode;
	private CreditCard card;
	private String transactionId;
	private String transactionContent;
	private int amount;
	private String createdAt;
	private MyMap VNpayQueryRequest;

	public PaymentTransaction(String errorCode, CreditCard card, String transactionId, String transactionContent,
							  int amount, String createdAt) {
		super();
		this.errorCode = errorCode;
		this.card = card;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public PaymentTransaction(MyMap VNpayQueryRequest) {
		super();
		this.setVNpayQueryRequest(VNpayQueryRequest);
	}
	public PaymentTransaction() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public MyMap getVNpayQueryRequest() {
		return VNpayQueryRequest;
	}

	public void setVNpayQueryRequest(MyMap vNpayQueryRequest) {
		VNpayQueryRequest = vNpayQueryRequest;
	}
}
