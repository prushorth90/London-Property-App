import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A controller for main window GUI elements.
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class MainController {

    private static MainControllerModel mainControllerModel = new MainControllerModel();
    // User selected price range
    private static Integer currFromSelection;
    private static Integer currToSelection;

    /**
     * Get the current user selected price range.
     *
     * @return An integer represent the starting price of
     * the user selected price range
     */
    public static Integer getCurrFromSelection()
    {
        return currFromSelection;
    }

    /**
     * Set the current selected price range to start from the
     * specified price range.
     *
     * @param currFromSelection The starting of the price range.
     */
    public static void setCurrFromSelection(Integer currFromSelection)
    {
        MainController.currFromSelection = currFromSelection;
    }

    /**
     * Get the current user selected price range.
     *
     * @return An integer represent the ending price of
     * the user selected price range
     */
    public static Integer getCurrToSelection()
    {
        return currToSelection;
    }

    /**
     * Set the current selected price range up to the
     * specified price range.
     *
     * @param currToSelection The ending of the price range.
     */
    public static void setCurrToSelection(Integer currToSelection)
    {
        MainController.currToSelection = currToSelection;
    }

    /**
     * Create an invalid range message dialogue to be display.
     */
    public void createInvalidRangeMessage()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Range invalid");
        alert.setHeaderText(null);
        alert.setContentText("You have selected invalid range.Please select from " + mainControllerModel.getMinPrice() + " to " + mainControllerModel.getMaxPrice());
        alert.showAndWait();
    }

    /**
     * Change the current scene to a specified panel.
     *
     * @param e An event which represent when the user click on a navigation button
     * @param location The location of the panel display file (i.e. the fxml file)
     */
    public void changePanel(ActionEvent e, URL location) throws IOException
    {
        Scene currentScene = ((Node) e.getSource()).getScene();
        Stage stage = (Stage) currentScene.getWindow();

        FXMLLoader ld = new FXMLLoader(location);
        Parent newPanelView = ld.load();
        Scene newScene = new Scene(newPanelView);

        stage.setScene(newScene);
        stage.show();
    }

    /**
     * Get the list of available properties for rent within the user specified price range.
     *
     * @return availablePropertiesWithinRange A list of available properties for rent
     * within the user specified price range
     */
    public ArrayList<AirbnbListing> getAvailablePropertiesWithinRange()
    {
        ArrayList<AirbnbListing> availablePropertiesWithinRange = new ArrayList<>();

        if (isValidRange(getCurrFromSelection(), getCurrToSelection())) {
            mainControllerModel.getAvailableProperties()
                    .stream()
                    .filter(listing -> listing.getPrice() >= getCurrFromSelection() && listing.getPrice() <= getCurrToSelection())
                    .forEach(listing -> availablePropertiesWithinRange.add(listing));
        }

        return availablePropertiesWithinRange;
    }

    /**
     * Determine whether user selected price range is valid.
     *
     * @return True if the range is valid and false otherwise.
     */
    public boolean isValidRange(int fromPrice, int toPrice)
    {
        if (fromPrice < 0 || toPrice < 0) {
            return false;
        }

        if (fromPrice < mainControllerModel.getMinPrice() || toPrice > mainControllerModel.getMaxPrice()) {
            return false;
        }

        if (fromPrice > toPrice) {
            return false;
        }

        return true;
    }

    /**
     * Set the list of available properties for rent within the user selected price range
     */
    public void setAvailablePropertiesInRange() {
        mainControllerModel.setAvailablePropertiesInRange(getAvailablePropertiesWithinRange());
    }
}
