import java.util.*;
import java.util.stream.Collectors;
/**
 * For panel 4 controller this is the model 4 that handles logic
 *
 * @author  Manivannan Prushorth, Hieu Trung Nguyen, Alexander Baker
 * @version March 2021
 */
public class ModelFour extends MainControllerModel
{
    
    private ArrayList<AirbnbListing> availablePropertiesInRange ;
    /**
     * Constructor for model 4
     */
    public ModelFour()
    {
        availablePropertiesInRange = null;
    }

    /**
     * set the avaliable properties in given price range
     * 
     * @param the avaliable listings from data set
     */
    public void setAvailablePropertiesInRange(ArrayList<AirbnbListing> available)
    {
      availablePropertiesInRange = available;
    }
    
    /**
     * 
     * @return list of boroughs in price range specified at top of window
     */
    public List<String> getBoroughsInPriceRange()
    {
        return availablePropertiesInRange.stream()
                                         .map(property -> property.getNeighbourhood())
                                         .distinct()
                                         .collect(Collectors.toList());
    }
}

