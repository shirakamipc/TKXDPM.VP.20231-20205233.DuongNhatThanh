package controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

public class ValidateAddress {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "1 Dai Co Viet,true",
            "8/115 Dai Co Viet,true",
            ",false"
    })
    public void test(String address, boolean trueVal) {
        boolean boolVal = placeOrderController.validateAddress(address);
        assertEquals(trueVal, boolVal);
    }
}
