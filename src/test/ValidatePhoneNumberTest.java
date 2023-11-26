package controller;

import controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertEquals;
class ValidatePhoneNumberTest {
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
			"0123456789,true",
			"012345678,false",
			"01234567892,false",
			"a123456789,false",
			",false"
	})
	public void test(String phone, boolean trueVal) {
		boolean isValid = placeOrderController.validatePhoneNumber(phone);
		assertEquals(trueVal, isValid);
	}
}
