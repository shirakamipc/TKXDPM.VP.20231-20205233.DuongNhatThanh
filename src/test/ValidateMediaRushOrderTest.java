package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import controller.PlaceOrderController;

public class ValidateMediaRushOrderTest {

    private PlaceOrderController placeOrderController;


    /**
     * @throws Exception
     */
    @BeforeEach
    void setup() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "book 1, true",
            "book 2, true",
            "book 3, true",
    })

    @Test
    public void test(String titleProduct, boolean expectedValidity) {
        boolean isValid = placeOrderController.validateMediaPlaceRushorder();
        assertEquals(expectedValidity, isValid);
    }
}
