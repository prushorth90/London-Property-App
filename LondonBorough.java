/**
 * An enumeration class represent different boroughs in London.
 * Each borough has an area code (i.e. the abbreviation of the borough name)
 *
 * @author Hieu Trung Nguyen, Manivannan Prushorth, and Alexander Baker
 * @version 2021
 */
public enum LondonBorough {
    ENFI("enfi", "Enfield"),
    BARN("barn", "Barnet"),
    HRGY("hrgy", "Haringey"),
    WALT("walt", "Waltham Forest"),
    HRRW("hrrw", "Harrow"),
    BREN("bren", "Brent"),
    CAMD("camd", "Camden"),
    ISLI("isli", "Islington"),
    HACK("hack", "Hackney"),
    REDB("redb", "Redbridge"),
    HAVE("have", "Havering"),
    HILL("hill", "Hillingdon"),
    EALI("eali", "Ealing"),
    KENS("kens", "Kensington and Chelsea"),
    WSTM("wstm", "Westminster"),
    TOWH("towh", "Tower Hamlets"),
    NEWH("newh", "Newham"),
    BARK("bark", "Barking and Dagenham"),
    HOUN("houn", "Hounslow"),
    HAMM("hamm", "Hammersmith and Fulham"),
    WAND("wand", "Wandsworth"),
    CITY("city", "City of London"),
    GWCH("gwch", "Greenwich"),
    BEXL("bexl", "Bexley"),
    RICH("rich", "Richmond upon Thames"),
    MERT("mert", "Merton"),
    LAMB("lamb", "Lambeth"),
    STHW("sthw", "Southwark"),
    LEWS("lews", "Lewisham"),
    KING("king", "Kingston upon Thames"),
    SUTT("sutt", "Sutton"),
    CROY("croy", "Croydon"),
    BROM("brom", "Bromley");

    private String areaCode;
    private String areaName;

    /**
     * The constructor for Enum class LondonBorough. which
     * create a borough enum with an area code and name.
     *
     * @param areaCode The abbreviation of borough name.
     * @param areaName The full name of the borough.
     */
    LondonBorough(String areaCode, String areaName) {
        this.areaCode = areaCode;
        this.areaName = areaName;
    }

    /**
     * Get the area code (i.e. the abbreviation of borough name).
     *
     * @return The abbreviation of borough name.
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     *Get the full name of a borough.
     *
     * @return The full name of a borough
     */
    public String getAreaName() {
        return areaName;
    }
}
