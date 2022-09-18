import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the functionality of the GUI components in panel three
 *
 * @author Manivannan Prushorth, Hieu Trung Nguyen, Alexander Baker
 * @version 2021
 */
public class ModelThree extends MainControllerModel
{
    /**
     * Constructor for objects of class ModelThree
     */
    public ModelThree(){}

    /**
     * @return The number of Homes and Apartments within the user selected price range
     */
    public Integer calcHomeAndApartment()
    {
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        if (listings == null) {
            return 0;
        }
        
        return Math.toIntExact(listings.stream()
                                       .filter(listing -> listing.getRoom_type().equals("Entire home/apt"))
                                       .count());
    }

    /**
     * @return The average number of reviews per property within the user selected price range
     * 
     */
    public Integer calcAverageReviews()
    {
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        
        if (listings == null) {
            return 0;
        }
        
        int totalReviews = listings.stream()
                                   .map(listing -> listing.getNumberOfReviews())
                                   .reduce(0, (total, count) -> total +count);

        return totalReviews / listings.size();
    }
    
    /**
     * @return The id of most expensive property in the user selected
     * price range. 
     */
    public String calcMostExpensivePropertyID()
    {
        HashMap<String, Integer> idToPriceRatio = new HashMap<>();
        
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        if (listings == null) {
            return "N/A";
        }
        
        listings.forEach(property -> idToPriceRatio.put(property.getId(), property.getPrice() * property.getMinimumNights()));

        int maxPrice = idToPriceRatio.values().stream()
                                              .max((propertyPrice1, propertyPrice2) -> propertyPrice1.compareTo(propertyPrice2)).get();
        
        return findPropertyId(maxPrice, idToPriceRatio);     
    }
    
    /**
     * @return The id of cheapest property in the user selected price range. 
     */
    public String calcCheapestPropertyID()
    {
        HashMap<String, Integer> idToPriceRatio = new HashMap<>();
        
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        if (listings == null) {
            return "N/A";
        }
        
        listings.forEach(property -> idToPriceRatio.put(property.getId(), property.getPrice() * property.getMinimumNights()));

        int minPrice = idToPriceRatio.values().stream()
                                              .min((propertyPrice1, propertyPrice2) -> propertyPrice1.compareTo(propertyPrice2)).get();
        
        return findPropertyId(minPrice, idToPriceRatio); 
    }
    
    /**
     * @return get the appropriate property id given the price wanted.  
     */
    private String findPropertyId(int price, HashMap<String, Integer> idToPriceRatio)
    {
        for (String id : getPropertiesId()) {
            if (idToPriceRatio.get(id) == price) {
                return id;
            }
        }
        return "No Property";
    }
    
    /**
     * @return the list of avaliable properties id
     */
    private List<String> getPropertiesId() 
    {
        return getAvailablePropertiesInRange().stream()
                                              .map(property -> property.getId())
                                              .collect(Collectors.toList());
    }
    
    /**
     * @return The average price of properties within the user selected
     * price range. If there are no properties within selected price range, return 0
     */
    public Integer calcAveragePrice()
    {
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        if (listings == null) {
            return 0;
        }
        
        int totalPrice = listings.stream()
                                 .map(listing -> listing.getPrice())
                                 .reduce(0, (total,count) -> total+count);

        return totalPrice / listings.size();
    }

    /**
     * @return The total number of available properties for rent in the user selected price range
     */
    public Integer calTotalProps() 
    {
       ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
       if (listings == null) {
            return 0;
       }
       return listings.size();
    }
    
    /**
     * 
     *
     * @return the  Host iD of host who has the most properties
     */
    public String mostPopularHost() 
    {
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        
        if (listings == null) {
            return "0";
        }
        
        HashMap<String, Integer> hostToPropertyCountRatio = new HashMap<>();
        
        List<String> hostsId = listings.stream()
                                        .map(property -> property.getHost_id())
                                        .sorted()
                                        .collect(Collectors.toList());
        
        // initially set all to 0
        hostsId.forEach(id -> hostToPropertyCountRatio.put(id, 0));
        
        int propertyCounter = 0;
        // e.g hostID = {1,1,2,2,3,4,4,4,4,4,5} so now hostToPropertyCountRatio {1:2, 2:2, 3:1, 4:5, 5:1}
        for (String id : hostsId) {
            if (hostToPropertyCountRatio.get(id) == 0) {
                propertyCounter = 1;
                hostToPropertyCountRatio.put(id, propertyCounter);
            } else {
                hostToPropertyCountRatio.put(id, propertyCounter++);
            }
        }
        
        int maxPropertyCount = hostToPropertyCountRatio.values().stream()
                                                                 .max((propertyCount1, propertyCount2) -> propertyCount1.compareTo(propertyCount2)).get();
        
        for (String id: hostsId) {
            if (hostToPropertyCountRatio.get(id) == maxPropertyCount) {
                return id;
            }
        }
        return "No host";
    }

    /**
     * Get the most expensive borough within the user selected price range.
     * 
     *
     * @return The most expensive borough within the user selected price range
     */
    public String calcMostExpensiveBorough()
    {
        ArrayList<AirbnbListing> listings = getAvailablePropertiesInRange();
        
        if (listings == null) {
            return "0";
        }
        
        List<String> boroughsInPriceRange = listings.stream()
                                                    .map(listing -> listing.getNeighbourhood())
                                                    .distinct()
                                                    .collect(Collectors.toList());
                                                    
        HashMap<String, Integer> boroughToPriceRatio = new HashMap<>();
        for (String borough: boroughsInPriceRange) {
            int totalPriceInBorough = listings.stream()
                                              .filter(listing -> listing.getNeighbourhood().equals(borough))
                                              .map(listing -> listing.getMinimumNights()*listing.getPrice())
                                              .reduce(0, (total, price) -> total +price);
            boroughToPriceRatio.put(borough, totalPriceInBorough);
        }
        
        int maxPrice = boroughToPriceRatio.values()
                                          .stream()
                                          .max((price1, price2) -> price1.compareTo(price2)).get();
        
        for (String borough: boroughsInPriceRange) {
            if (boroughToPriceRatio.get(borough) == maxPrice) {
                return borough;
            }
        }
        return "No property";
    }
    
}
