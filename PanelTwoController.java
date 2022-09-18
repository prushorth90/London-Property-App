import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * A controller for panel 2 GUI elements
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class PanelTwoController extends MainController 
{
    private ObservableList<Integer> toPriceOptions;
    private ObservableList<Integer> fromPriceOptions;
    private MainControllerModel modelTwo;
    @FXML private ComboBox<Integer> fromBox, toBox;
    @FXML private Button backBtn, nextBtn;
    @FXML private Circle enfi, barn, hrgy, walt, hrrw, bren, camd, isli, hack, redb, have,
            hill, eali, kens, wstm, towh, newh, bark, houn, hamm, wand, city, gwch, bexl, rich,
            mert, lamb, sthw, lews, king, sutt, croy, brom;

    // A set to store fxml component ID of every circle that represent
    // a London borough
    Set<Circle> boroughCircles;

    // A HashMap that maps the area code of each borough in london
    // with a corresponding color depend on how many property are
    // available for rent within an area. For example ("enfi", 1000)
    HashMap<String, Color> colorOfBorough = new HashMap<>();

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

        assignColor();
        setColor();
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
        URL location = getClass().getResource("panel3.fxml");
        changePanel(event, location);
    }

    /**
     *Make the current window to transit into the previous panel display
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException
    {
        URL location = getClass().getResource("panel1.fxml");
        changePanel(event, location);
    }

    /**
     * Open a new window which display the list of all available properties
     * for rent in an area
     *
     * @param event An event of when user select an area/borough
     * @throws Exception
     */
    public void openAreaWindow(MouseEvent event) throws Exception
    {
        Circle areaSelected = (Circle) event.getSource();
        String areaName = areaSelected.getId();
        AreaWindow newWin = new AreaWindow();
        newWin.display(areaName);
    }

    /**
     * Assign a color for each borough in London based on the number of available property for rent
     * within the user selected price range.
     */
    public void assignColor()
    {
        ArrayList<AirbnbListing> availablePropertiesWithinRange = getAvailablePropertiesWithinRange();
        HashMap<LondonBorough, Integer> listPropertiesPerArea = modelTwo.getNumberOfPropertiesPerArea(availablePropertiesWithinRange);
        Set<LondonBorough> boroughs = listPropertiesPerArea.keySet();
        
        for (LondonBorough borough : boroughs) {
            Color color = Color.GREY;

            Integer numOfProperty = listPropertiesPerArea.get(borough);
            if (numOfProperty > 0 && numOfProperty <= 100) {
                color = Color.LIGHTGREEN;
            } else if (numOfProperty > 100 && numOfProperty <= 500) {
                color = Color.LAWNGREEN;
            } else if (numOfProperty > 500 && numOfProperty <= 1000) {
                color = Color.YELLOWGREEN;
            } else if (numOfProperty > 1000) {
                color = Color.GREEN;
            }

            colorOfBorough.put(borough.getAreaCode(), color);
        }
    }

    /**
     * Loop through each circle that represent area/borough in London
     * and paint the circle with the correct color based on how many
     * properties are available for rent in an area
     */
    public void setColor()
    {
        boroughCircles.forEach(boroughCircle -> boroughCircle.setFill(getColor(boroughCircle.getId())));
    }

    /**
     * Get the color of a corresponding area/borough based on the supplied area code
     *
     * @param boroughCircleCode Area/borough code (i.e. the abbreviation of a borough name)
     * @return The color of a corresponding area/borough
     */
    public Color getColor(String boroughCircleCode)
    {
        return colorOfBorough.get(boroughCircleCode);
    }

    /**
     * Initialise the fxml element for panel 2
     */
    public void initialize()
    {
        fromPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);
        toPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);

        fromBox.setItems(fromPriceOptions);
        toBox.setItems(toPriceOptions);

        modelTwo = new ModelTwo();

        if (getCurrFromSelection() != null && getCurrToSelection() != null) {
            fromBox.getEditor().setText(getCurrFromSelection().toString());
            toBox.getEditor().setText(getCurrToSelection().toString());
        }

        boroughCircles = new HashSet<>();
        boroughCircles.add(enfi);
        boroughCircles.add(barn);
        boroughCircles.add(hrgy);
        boroughCircles.add(walt);
        boroughCircles.add(hrrw);
        boroughCircles.add(bren);
        boroughCircles.add(camd);
        boroughCircles.add(isli);
        boroughCircles.add(hack);
        boroughCircles.add(redb);
        boroughCircles.add(have);
        boroughCircles.add(hill);
        boroughCircles.add(eali);
        boroughCircles.add(kens);
        boroughCircles.add(wstm);
        boroughCircles.add(towh);
        boroughCircles.add(newh);
        boroughCircles.add(bark);
        boroughCircles.add(houn);
        boroughCircles.add(hamm);
        boroughCircles.add(wand);
        boroughCircles.add(city);
        boroughCircles.add(gwch);
        boroughCircles.add(bexl);
        boroughCircles.add(rich);
        boroughCircles.add(mert);
        boroughCircles.add(lamb);
        boroughCircles.add(sthw);
        boroughCircles.add(lews);
        boroughCircles.add(king);
        boroughCircles.add(sutt);
        boroughCircles.add(croy);
        boroughCircles.add(brom);

        setAvailablePropertiesInRange();
        checkValidRange();
        assignColor();
        setColor();
    }
}