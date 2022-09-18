/**
 * An enum class that list all the statistics used in the application
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public enum Statistics 
{
    TOTAL_PROPERTY("Total Number of Properties"),
    AVERAGE_REVIEW_PER_PROPERTY("Average Reviews Per Property"),
    NUM_HOME_AND_APARTMENT("Number of Homes and Apartments"),
    MOST_EXPENSIVE_BOROUGH("Most Expensive Borough"),
    MOST_POPULAR_HOST("Most Popular Host ID"),
    AVERAGE_PRICE("Average Price"),
    CHEAPEST_PROPERTY("Cheapest Property ID"),
    MOST_EXPENSIVE_PROPERTY("Most Expensive property ID");

    private String title;
    private String stat;

    /**
     * Constructor of Statistics
     */
    Statistics(String title) {
        this.title = title;
        this.stat = "Not available";
    }

    /**
     * @return The title of the statistic
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return The statistic value
     */
    public String getStat() {
        return stat;
    }

    /**
     * @param stat The statistic value to be set
     */
    public void setStat(String stat) {
        this.stat = stat;
    }

    /**
     * Reset the statistic to its default value
     */
    public void resetStat() {
        this.stat = "Not available";
    }
}