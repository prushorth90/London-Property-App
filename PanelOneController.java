import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;

/**
 * A controller for panel 1 GUI components
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class PanelOneController extends MainController {

    private ObservableList<Integer> toPriceOptions;
    private ObservableList<Integer> fromPriceOptions;

    @FXML private ComboBox<Integer> fromBox, toBox;
    @FXML private Button backBtn, nextBtn;

    /**
     * Check whether the user selected price range is valid. if the range is
     * not valid display invalid range message to user. Each time the user
     * change the price range, the color corresponding to an area is also updated.
     */
    public void checkValidRange()
    {
        String fromPriceString = fromBox.getEditor().getText();
        String toPriceString = toBox.getEditor().getText();

        if (!fromPriceString.isEmpty() && !toPriceString.isEmpty()) {
            try {
                int fromPrice = Integer.parseInt(fromPriceString);
                int toPrice = Integer.parseInt(toPriceString);

                setCurrFromSelection(fromPrice);
                setCurrToSelection(toPrice);

                if (isValidRange(fromPrice, toPrice)) {
                    setAvailablePropertiesInRange();

                    nextBtn.setDisable(false);
                    backBtn.setDisable(false);
                } else {
                    nextBtn.setDisable(true);
                    backBtn.setDisable(true);
                    displayInvalidRangeMessage();
                }
            } catch (NumberFormatException e) {
                displayInvalidRangeMessage();
            }
        } else {
            nextBtn.setDisable(true);
            backBtn.setDisable(true);
        }
    }

    /**
     * Display invalid range message and disable navigation button
     */
    private void displayInvalidRangeMessage() 
    {
        createInvalidRangeMessage();
        nextBtn.setDisable(true);
        backBtn.setDisable(true);
    }

    /**
     * Make the current window to transit into the next panel display
     *
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goForward(ActionEvent event) throws IOException 
    {
        URL location = getClass().getResource("panel2.fxml");
        changePanel(event, location);
    }

    /**
     *Make the current window to transit into the previous panel display
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException 
    {
        URL location = getClass().getResource("panel4.fxml");
        changePanel(event, location);
    }

    /**
     * Initialise the fxml element for panel 1
     */
    public void initialize() 
    {
        fromPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);
        toPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);

        fromBox.setItems(fromPriceOptions);
        toBox.setItems(toPriceOptions);

        if(getCurrFromSelection() != null && getCurrToSelection() != null) {
            fromBox.getEditor().setText(getCurrFromSelection().toString());
            toBox.getEditor().setText(getCurrToSelection().toString());
        }

        checkValidRange();
    }
}
