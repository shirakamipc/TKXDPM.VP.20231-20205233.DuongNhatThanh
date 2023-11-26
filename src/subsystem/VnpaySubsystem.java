package subsystem;

import java.io.IOException;
import java.net.URISyntaxException;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.interbank.InterbankSubsystemController;
import subsystem.interbank.VnpaySubsystemController;

public class VnpaySubsystem implements InterbankInterface {

	/**
	 * Represent the controller of the subsystem
	 */
	private VnpaySubsystemController ctrl;

	/**
	 * Initializes a newly created {@code InterbankSubsystem} object so that it
	 * represents an Interbank subsystem.
	 */
	public VnpaySubsystem() {
		String str = new String();
		this.ctrl = new VnpaySubsystemController();
	}

	/**
	 * @see InterbankInterface#payOrder(CreditCard, int,
	 *      String)
	 */
	public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = new PaymentTransaction();
		try {
			transaction = ctrl.payOrder(amount, contents);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transaction;
	}

	/**
	 * @see InterbankInterface#refund(CreditCard, int,
	 *      String)
	 */
	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.refund(card, amount, contents);
		return transaction;
	}
}
