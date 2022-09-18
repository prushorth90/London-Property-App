import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit test class for MainControllerModel class
 *
 * @author Manivannan Prushorth, Hieu Trung Nguyen, Alexander Baker
 * @version 2021
 */
public class MainControllerModelTest {

    //Test data
    private AirbnbListing testListingOne;
    private AirbnbListing testListingTwo;
    private AirbnbListing testListingThree;
    private AirbnbListing testListingFour;
    private ArrayList<AirbnbListing> testPropertyListings;


    private MainControllerModel mainControllerModel;

    /**
     * Default constructor for test class MainControllerModelTest
     */
    public MainControllerModelTest() {
        testListingOne = new AirbnbListing("11111111", "test property 1", "test1", "123456", "Enfield", 0, 0, "", 100, 3, 100, "", 5, 5, 60);
        testListingTwo = new AirbnbListing("22222222", "test property 2", "test2", "789123", "Enfield", 0, 0, "",200, 3,100,"",5, 5, 60);
        testListingThree = new AirbnbListing("33333333", "test property 3", "test3", "456789", "Enfield", 0, 0, "",300, 3,100,"",5, 5, 60);
        testListingFour = new AirbnbListing("4444444444", "test property 4", "test4", "0123456", "Islington", 0, 0, "", 400, 3, 100, "", 5, 5, 60);

        testPropertyListings = new ArrayList<>();
        testPropertyListings.add(testListingOne);
        testPropertyListings.add(testListingTwo);
        testPropertyListings.add(testListingThree);
        testPropertyListings.add(testListingFour);
    }

    /**
     * Sets up the test fixture.
     * <p>
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        mainControllerModel = new MainControllerModel();
    }

    /**
     * Tears down the test fixture.
     * <p>
     * Called after every test case method.
     */
    @After
    public void tearDown() {
    }

    @Test
    public void testGetAvailablePropertiesInRange() {
        assertEquals(null, mainControllerModel.getAvailablePropertiesInRange());
    }

    @Test
    public void testGetAvailableProperties() {
        ArrayList<AirbnbListing> availableProperties = mainControllerModel.getAvailableProperties();

        for (AirbnbListing listing : availableProperties) {
            assertEquals(true, listing.getAvailability365() > 0);
        }
    }

    @Test
    public void testGetNumberOfPropertiesPerArea() {

        // asserts(IllegalArgumentException.class, () -> {
            // mainControllerModel.getNumberOfPropertiesPerArea(null);
        // });

        HashMap<LondonBorough, Integer> numPropertiesPerArea = mainControllerModel.getNumberOfPropertiesPerArea(testPropertyListings);

        for(Map.Entry<LondonBorough, Integer> entry : numPropertiesPerArea.entrySet()) {
            if(entry.getKey().getAreaName().equals("Enfield")){
                assertEquals(true, entry.getValue() == 3);
            } else if(entry.getKey().getAreaName().equals("Islington")){
                assertEquals(true, entry.getValue() == 1);
            } else {
                assertEquals(true, entry.getValue() == 0);
            }
        }
    }

    @Test
    public void testGetMinPrice() {
        int minPrice = mainControllerModel.getMinPrice();
        
        for (AirbnbListing listing : mainControllerModel.getDataSet()) {
            assertEquals(true, listing.getPrice() >= minPrice);
        }
    }

    @Test
    public void testGetMaxPrice() {
        int maxPrice = mainControllerModel.getMaxPrice();
        
        for (AirbnbListing listing : mainControllerModel.getDataSet()) {
            if(listing.getPrice() > maxPrice) {
                assertEquals(true, listing.getPrice() <= maxPrice);
            }
        }
    }
}