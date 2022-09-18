import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * A controller for property view controller 
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class PropertyViewController {

    // Store the selected property
    private AirbnbListing propertySelected;

    @FXML
    private Label propertyID, propertyName, hostName, hostID, neighbourhood, roomType,
    price, minimumNights, lastReview, availability;

    /**
     * Set the property selected to the specified value.
     *
     * @param propertySelected The property selected by the user
     */
    public void setPropertySelected(AirbnbListing propertySelected) 
    {
        this.propertySelected = propertySelected;
    }

    /**
     * Initialise fxml elements
     */
    public void initialize() 
    {
        Platform.runLater(() -> {
            propertyID.setText(propertySelected.getId());
            propertyName.setText(propertySelected.getName());
            hostID.setText(propertySelected.getHost_name());
            hostName.setText(propertySelected.getHost_id());
            neighbourhood.setText(propertySelected.getNeighbourhood());
            roomType.setText(propertySelected.getRoom_type());
            price.setText(String.valueOf(propertySelected.getPrice()));
            minimumNights.setText(String.valueOf(propertySelected.getMinimumNights()));
            lastReview.setText(propertySelected.getLastReview());
            availability.setText(String.valueOf(propertySelected.getAvailability365()));
        });
    }
}
