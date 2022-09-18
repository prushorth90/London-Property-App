import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

/**
 * Unit test class for MainController class
 *
 * @author Manivannan Prushorth, Hieu Trung Nguyen, Alexander Baker
 * @version 2021
 */
public class MainControllerTest
{
    private MainController main;
    
    /**
     * Constructor for test class MainControllerTest
     */
    public MainControllerTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        main = new MainController();
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testIsValidRange(){
        assertEquals(true, main.isValidRange(100, 200));
        assertEquals(true, main.isValidRange(100, 1000));
        assertEquals(false, main.isValidRange(200, 100));
        assertEquals(false, main.isValidRange(-1, 100));
        assertEquals(false, main.isValidRange(100, -1));
        assertEquals(false, main.isValidRange(0, 100));
    }
    
    @Test
    public void testGetAvailablePropertiesWithinRange() {
        MainController.setCurrFromSelection(100);
        MainController.setCurrToSelection(200);

        ArrayList<AirbnbListing> availablePropertiesWithinRange = main.getAvailablePropertiesWithinRange();

        for (AirbnbListing listing : availablePropertiesWithinRange) {
            assertEquals(true, listing.getPrice() >= MainController.getCurrFromSelection());
            assertEquals(true, listing.getPrice() <= MainController.getCurrToSelection());
        }
    }
}