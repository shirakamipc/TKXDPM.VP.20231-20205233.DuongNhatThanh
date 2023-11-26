package test;

//import controller.PlaceOrderController;
import controller.PlaceOrderController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.Assert.assertEquals;

public class ValidateName {
    private controller.PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Duong Nhat Thanh,true",
            "thanhdn205233,false",
            "duongnhatthanh2001@vjppro,false",
            ",false",
    })
    public void test(String name, boolean trueVal) {
        boolean boolVal = placeOrderController.validateName(name);
        assertEquals(trueVal, boolVal);
    }
}

