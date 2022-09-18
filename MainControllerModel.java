import java.util.*;

/**
 * Handles the functionality of the GUI application using the data set provided.
 *
 * @author Manivannan Prushorth, Hieu Trung Nguyen, Alexander Baker
 * @version 2021
 */
public class MainControllerModel
{
    private static ArrayList<AirbnbListing> availablePropertiesInRange = null;
    private static AirbnbDataLoader loader = new AirbnbDataLoader();
    private static ArrayList<AirbnbListing> dataSet = loader.load();

    /**
     * Constructor for objects of class MainControllerModel
     * Initialise the data loader and load the data from
     * the database.
     */
    public MainControllerModel() {}

    public ArrayList<AirbnbListing> getAvailablePropertiesInRange() {
        return availablePropertiesInRange;
    }

    public void setAvailablePropertiesInRange(ArrayList<AirbnbListing> availablePropertiesInRange) {
        this.availablePropertiesInRange = availablePropertiesInRange;
    }

    /**
     * Get a list of properties that are available for renting.
     *
     * @return availableProperties A list of properties that is available for rent.
     */
    public ArrayList<AirbnbListing> getAvailableProperties()
    {
        ArrayList<AirbnbListing> availableProperties = new ArrayList<>();

        dataSet.stream()
                .filter(listing -> listing.getAvailability365() > 0)
                .forEach(listing -> availableProperties.add(listing));

        return availableProperties;
    }

    /**
     * Get the number of properties in each area/borough within a specified dataset
     *
     * @param properties A list of properties
     * @return numPropertiesPerArea A HashMap that map each area/borough
     * to its corresponding number of properties in the area/borough
     */
    public HashMap<LondonBorough, Integer> getNumberOfPropertiesPerArea(List<AirbnbListing> properties)
    {
        if (properties == null) {
            throw new IllegalArgumentException("No properties provided");
        }

        HashMap<LondonBorough, Integer> numPropertiesPerArea = new HashMap<>();

        for (LondonBorough borough : LondonBorough.values()) {
            String boroughName = borough.getAreaName();

            long numberOfProperties = properties.stream()
                    .filter(property -> property.getNeighbourhood().equals(boroughName))
                    .count();
            numPropertiesPerArea.put(borough, Math.toIntExact(numberOfProperties));
        }

        return numPropertiesPerArea;
    }

    /**
     * Get the minimum price from the airbnb dataset
     *
     * @return the minimum price
     */
    public int getMinPrice()
    {
        int minPrice = getDataSet().stream()
                .map(property -> property.getPrice())
                .min((propertyPrice1, propertyPrice2) -> propertyPrice1.compareTo(propertyPrice2))
                .get();
        return minPrice;
    }

    /**
     * Get the maximum price from the airbnb dataset
     *
     * @return the maximum price
     */
    public int getMaxPrice()
    {
        int maxPrice = getDataSet().stream()
                .map(property -> property.getPrice())
                .max((propertyPrice1, propertyPrice2) ->  propertyPrice1.compareTo(propertyPrice2))
                .get();
        return maxPrice;
    }

    /**
     * Get the list of property listings.
     *
     * @return The list of property listings.
     */
    public ArrayList<AirbnbListing> getDataSet()
    {
        return dataSet;
    }
}

