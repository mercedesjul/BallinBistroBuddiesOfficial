package lulian.ballinbistrobuddies.model;

import java.util.ArrayList;
import java.util.List;

public interface TableModelInterface {

    /**
     * Gets the list of users seated at the table.
     *
     * @return an array of users seated at the table.
     */
    ArrayList<UserModel> getSeatedPeople();

    /**
     * Sets the list of users seated at the table.
     *
     * @param seatedPeople an array of users to set.
     */
    void addSeatedPeople(List<UserModel> seatedPeople);

    void seatUser(UserModel user);

    /**
     *  Unseat a user
     * @param user the user to unseat
     */
    void unseatUser(UserModel user);

    /**
     * Clear all Users
     */
    void clearUsers();
}
