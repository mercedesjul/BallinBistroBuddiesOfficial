package lulian.ballinbistrobuddies.model;

public interface UserModelInterface {
    /**
     * Returns the name of the user.
     *
     * @return the name of the user.
     */
    String getName();

    /**
     * Sets the name of the user.
     *
     * @param name the name to set.
     */
    void setName(String name);

    /**
     * Gets the theme preference of the user.
     *
     * @return the theme preference of the user.
     */
    String getTheme();

    /**
     * Sets the theme preference of the user.
     *
     * @param theme the theme to set.
     */
    void setTheme(String theme);
}