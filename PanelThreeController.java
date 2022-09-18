import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * A controller for panel 3 GUI elements
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public class PanelThreeController extends MainController {

    private ObservableList<Integer> toPriceOptions;
    private ObservableList<Integer> fromPriceOptions;
    private ModelThree modelThree;
    @FXML
    private ComboBox<Integer> fromBox, toBox;
    @FXML
    private Button backBtn, nextBtn;
    @FXML
    private Label statisticBox1Title, statisticBox2Title, statisticBox3Title, statisticBox4Title,
            statisticBox1Data, statisticBox2Data, statisticBox3Data, statisticBox4Data;

    ArrayList<Statistics> boxOneStats = new ArrayList<>();
    ArrayList<Statistics> boxTwoStats = new ArrayList<>();
    ArrayList<Statistics> boxThreeStats = new ArrayList<>();
    ArrayList<Statistics> boxFourStats = new ArrayList<>();


    /**
     * Initialise statistics and show the initial statistics
     */
    private void initialiseStats() 
    {
        Statistics.TOTAL_PROPERTY.setStat(modelThree.calTotalProps().toString());
        Statistics.AVERAGE_REVIEW_PER_PROPERTY.setStat(modelThree.calcAverageReviews().toString());
        Statistics.NUM_HOME_AND_APARTMENT.setStat(modelThree.calcHomeAndApartment().toString());
        Statistics.MOST_EXPENSIVE_BOROUGH.setStat(modelThree.calcMostExpensiveBorough().toString());

        Statistics.MOST_POPULAR_HOST.setStat(modelThree.mostPopularHost().toString());
        Statistics.AVERAGE_PRICE.setStat(modelThree.calcAveragePrice().toString());
        Statistics.CHEAPEST_PROPERTY.setStat(modelThree.calcCheapestPropertyID().toString());
        Statistics.MOST_EXPENSIVE_PROPERTY.setStat(modelThree.calcMostExpensivePropertyID().toString());

        boxOneStats.add(Statistics.TOTAL_PROPERTY);
        boxTwoStats.add(Statistics.AVERAGE_REVIEW_PER_PROPERTY);
        boxThreeStats.add(Statistics.NUM_HOME_AND_APARTMENT);
        boxFourStats.add(Statistics.MOST_EXPENSIVE_BOROUGH);

        boxOneStats.add(Statistics.MOST_POPULAR_HOST);
        boxTwoStats.add(Statistics.AVERAGE_PRICE);
        boxThreeStats.add(Statistics.CHEAPEST_PROPERTY);
        boxFourStats.add(Statistics.MOST_EXPENSIVE_PROPERTY);

        showInitialStats();
    }

    /**
     * Reset the statistics and update the display
     */
    public void resetStats() 
    {
        Statistics.TOTAL_PROPERTY.resetStat();
        Statistics.AVERAGE_REVIEW_PER_PROPERTY.resetStat();
        Statistics.NUM_HOME_AND_APARTMENT.resetStat();;
        Statistics.MOST_EXPENSIVE_BOROUGH.resetStat();
        Statistics.MOST_POPULAR_HOST.resetStat();
        Statistics.AVERAGE_PRICE.resetStat();
        Statistics.CHEAPEST_PROPERTY.resetStat();
        Statistics.MOST_EXPENSIVE_PROPERTY.resetStat();

        showNewStats();
    }

    /**
     * Update the statistics and display new statistic
     */
    public void updateStats() 
    {
        Statistics.TOTAL_PROPERTY.setStat(modelThree.calTotalProps().toString());
        Statistics.AVERAGE_REVIEW_PER_PROPERTY.setStat(modelThree.calcAverageReviews().toString());
        Statistics.NUM_HOME_AND_APARTMENT.setStat(modelThree.calcHomeAndApartment().toString());
        Statistics.MOST_EXPENSIVE_BOROUGH.setStat(modelThree.calcMostExpensiveBorough());
        Statistics.MOST_POPULAR_HOST.setStat(modelThree.mostPopularHost().toString());
        Statistics.AVERAGE_PRICE.setStat(modelThree.calcAveragePrice().toString());
        Statistics.CHEAPEST_PROPERTY.setStat(modelThree.calcCheapestPropertyID().toString());
        Statistics.MOST_EXPENSIVE_PROPERTY.setStat(modelThree.calcMostExpensivePropertyID().toString());

        showNewStats();
    }

    /**
     * Display newly updated statistics
     */
    public void showNewStats() 
    {
        boxOneStats.stream()
                   .filter(stat -> statisticBox1Title.getText().equals(stat.getTitle()))
                   .forEach(stat -> statisticBox1Data.setText(stat.getStat()));

        boxTwoStats.stream()
                   .filter(stat -> statisticBox2Title.getText().equals(stat.getTitle()))
                   .forEach(stat -> statisticBox2Data.setText(stat.getStat()));

        boxThreeStats.stream()
                     .filter(stat -> statisticBox3Title.getText().equals(stat.getTitle()))
                     .forEach(stat -> statisticBox3Data.setText(stat.getStat()));

        boxFourStats.stream()
                    .filter(stat -> statisticBox4Title.getText().equals(stat.getTitle()))
                    .forEach(stat -> statisticBox4Data.setText(stat.getStat()));
    }

    /**
     * Display the initial statistics
     */
    public void showInitialStats() 
    {
        statisticBox1Title.setText(Statistics.TOTAL_PROPERTY.getTitle());
        statisticBox2Title.setText(Statistics.AVERAGE_REVIEW_PER_PROPERTY.getTitle());
        statisticBox3Title.setText(Statistics.NUM_HOME_AND_APARTMENT.getTitle());
        statisticBox4Title.setText(Statistics.MOST_EXPENSIVE_BOROUGH.getTitle());

        statisticBox1Data.setText(Statistics.TOTAL_PROPERTY.getStat());
        statisticBox2Data.setText(Statistics.AVERAGE_REVIEW_PER_PROPERTY.getStat());
        statisticBox3Data.setText(Statistics.NUM_HOME_AND_APARTMENT.getStat());
        statisticBox4Data.setText(Statistics.MOST_EXPENSIVE_BOROUGH.getStat());
    }

    /**
     * Change and display the next statistic of box 1 when user click on the next button
     */
    public void boxOneNext() 
    {
        String currTitle = statisticBox1Title.getText();
        int newIndex = 0;

        for (Statistics stat : boxOneStats) {
            int currIndex = boxOneStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex < boxOneStats.size() - 1) {
                newIndex = ++currIndex;
            }
        }

        displayStatBoxOne(newIndex);
    }

    /**
     * Change and display the previous statistic of box 1 when user click on the back button
     */
    public void boxOneBack() 
    {
        String currTitle = statisticBox1Title.getText();
        int newIndex = boxOneStats.size() - 1;

        for (Statistics stat : boxOneStats) {
            int currIndex = boxOneStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex > 0) {
                newIndex = --currIndex;
            }
        }

        displayStatBoxOne(newIndex);
    }

    /**
     * Display a specified statistic on box 1
     *
     * @param newIndex The index of the statistic to be displayed
     */
    public void displayStatBoxOne(int newIndex) 
    {
        Statistics newStat = boxOneStats.get(newIndex);
        statisticBox1Title.setText(newStat.getTitle());
        statisticBox1Data.setText(newStat.getStat());
    }

    /**
     * Change and display the next statistic of box 2 when user click on the next button
     */
    public void boxTwoNext() 
    {
        String currTitle = statisticBox2Title.getText();
        int newIndex = 0;

        for (Statistics stat : boxTwoStats) {
            int currIndex = boxTwoStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex < boxTwoStats.size() - 1) {
                newIndex = ++currIndex;
            }
        }

        displayStatBoxTwo(newIndex);
    }

    /**
     * Change and display the previous statistic of box 2 when user click on the back button
     */
    public void boxTwoBack() 
    {
        String currTitle = statisticBox2Title.getText();
        int newIndex = boxTwoStats.size() - 1;

        for (Statistics stat : boxTwoStats) {
            int currIndex = boxTwoStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex > 0) {
                newIndex = --currIndex;
            }
        }

        displayStatBoxTwo(newIndex);
    }

    /**
     * Display a specified statistic on box 2
     *
     * @param newIndex The index of the statistic to be displayed
     */
    public void displayStatBoxTwo(int newIndex) 
    {
        Statistics newStat = boxTwoStats.get(newIndex);
        statisticBox2Title.setText(newStat.getTitle());
        statisticBox2Data.setText(newStat.getStat());
    }

    /**
     * Change and display the next statistic of box 3 when user click on the next button
     */
    public void boxThreeNext() 
    {
        String currTitle = statisticBox3Title.getText();
        int newIndex = 0;

        for (Statistics stat : boxThreeStats) {
            int currIndex = boxThreeStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex < boxThreeStats.size() - 1) {
                newIndex = ++currIndex;
            }
        }

        displayStatBoxThree(newIndex);
    }

    /**
     * Change and display the previous statistic of box 3 when user click on the back button
     */
    public void boxThreeBack() 
    {
        String currTitle = statisticBox3Title.getText();
        int newIndex = boxThreeStats.size() - 1;

        for (Statistics stat : boxThreeStats) {
            int currIndex = boxThreeStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex > 0) {
                newIndex = --currIndex;
            }
        }

        displayStatBoxThree(newIndex);
    }

    /**
     * Display a specified statistic on box 3
     *
     * @param newIndex The index of the statistic to be displayed
     */
    public void displayStatBoxThree(int newIndex) 
    {
        Statistics newStat = boxThreeStats.get(newIndex);
        statisticBox3Title.setText(newStat.getTitle());
        statisticBox3Data.setText(newStat.getStat());
    }

    /**
     * Change and display the next statistic of box 4 when user click on the next button
     */
    public void boxFourNext() 
    {
        String currTitle = statisticBox4Title.getText();
        int newIndex = 0;

        for (Statistics stat : boxFourStats) {
            int currIndex = boxFourStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex < boxFourStats.size() - 1) {
                newIndex = ++currIndex;
            }
        }

        displayStatBoxFour(newIndex);
    }

    /**
     * Change and display the previous statistic of box 4 when user click on the back button
     */
    public void boxFourBack() 
    {
        String currTitle = statisticBox4Title.getText();
        int newIndex = boxFourStats.size() - 1;

        for (Statistics stat : boxFourStats) {
            int currIndex = boxFourStats.indexOf(stat);
            if (currTitle.equals(stat.getTitle()) && currIndex > 0) {
                newIndex = --currIndex;
            }
        }

        displayStatBoxFour(newIndex);
    }

    /**
     * Display a specified statistic on box 4
     *
     * @param newIndex The index of the statistic to be displayed
     */
    public void displayStatBoxFour(int newIndex) 
    {
        Statistics newStat = boxFourStats.get(newIndex);
        statisticBox4Title.setText(newStat.getTitle());
        statisticBox4Data.setText(newStat.getStat());
    }

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

                    updateStats();
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
        resetStats();
    }

    /**
     * Make the current window to transit into the next panel display
     *
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goForward(ActionEvent event) throws IOException 
    {
        URL location = getClass().getResource("panel4.fxml");
        changePanel(event, location);
    }

    /**
     * Make the current window to transit into the previous panel display
     *
     * @param event The event that trigger the panel transition
     * @throws IOException
     */
    public void goBack(ActionEvent event) throws IOException 
    {
        URL location = getClass().getResource("panel2.fxml");
        changePanel(event, location);
    }

    /**
     * Initialise the fxml element for panel 3
     */
    public void initialize() 
    {
        fromPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);
        toPriceOptions = FXCollections.observableArrayList(100, 200, 300, 400);

        fromBox.setItems(fromPriceOptions);
        toBox.setItems(toPriceOptions);

        modelThree = new ModelThree();

        if (getCurrFromSelection() != null && getCurrToSelection() != null) {
            fromBox.getEditor().setText(getCurrFromSelection().toString());
            toBox.getEditor().setText(getCurrToSelection().toString());
        }

        setAvailablePropertiesInRange();
        initialiseStats();
        checkValidRange();
    }
}