import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;

/**
 * A controller class that manage GUI elements of the pop up window
 * which displays the list of properties within the user selected
 * area/borough
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class AreaViewController extends MainController {
    
    // Store the area code (i.e. the abbreviation of the user selected
    // area/borough name)
    private String areaCode;
    // an observable list to store listings (i.e. available properties
    // for rent) within the user selected price range
    private ObservableList listings = FXCollections.observableArrayList();

    @FXML
    private TableView propertyList;
    @FXML
    private TableColumn hostName, price, numOfReviews, minimumStay;
    @FXML
    private Label boroughName;

    /**
     * Get a list of available properties for rent within the user selected price range
     * and user selected area/borough.
     *
     * @return The list of available properties for rent within the user selected price range
     * and user selected area/borough.
     */
    public ObservableList<AirbnbListing> getAvailablePropertiesInSelectedArea() {
        ArrayList<AirbnbListing> availableProperties = getAvailablePropertiesWithinRange();
        availableProperties.stream()
                           .filter(listing -> listing.getNeighbourhood().equals(getAreaName()))
                           .forEach(listing -> listings.add(listing));
                           
        return listings;
    }

    /**
     * Get the full name of the user selected area/borough based on
     * the supplied area code (i.e. the abbreviation of the area/borough
     * name)
     *
     * @return The full name of the user selected area/borough
     */
    public String getAreaName() {
        String areaName = "";

        for (LondonBorough lb : LondonBorough.values()) {
            if (lb.getAreaCode().equals(areaCode)){
                areaName = lb.getAreaName();
            }
        }

        return areaName;
    }

    /**
     * Set the area code (i.e. the abbreviation of the user selected area/borough)
     * to the specified value.
     *
     * @param areaName Area code (i.e. the abbreviation of the user selected area/borough)
     * to the specified value.
     */
    public void setAreaSelected(String areaName) {
        this.areaCode = areaName;
    }

    /**
     * Open a new window which display more details about the user selected property listing
     *
     * @param event The event in which the user clicked on a property listing
     * @throws Exception
     */
    public void openPropertyWindow(MouseEvent event) throws Exception {
        TableView propertyList = (TableView) event.getSource();
        AirbnbListing listing = (AirbnbListing) propertyList.getSelectionModel().getSelectedItem();
        if (listing != null) {
            PropertyWindow newWin = new PropertyWindow();
            newWin.display(listing);
        }
    }

    /**
     * Initialise fxml elements
     */
    public void initialize() {
        Platform.runLater(() -> {
            propertyList.setOnMouseClicked(event -> {
                try {
                    openPropertyWindow(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            hostName.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("host_name"));
            price.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("price"));
            numOfReviews.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("numberOfReviews"));
            minimumStay.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("minimumNights"));

            //For testing only
            //neighbourhood.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("neighbourhood"));

            boroughName.setText(getAreaName());

            propertyList.setItems(getAvailablePropertiesInSelectedArea());
        });
    }
}

