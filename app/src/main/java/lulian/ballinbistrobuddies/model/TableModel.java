package lulian.ballinbistrobuddies.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a table within the application, including its position, quadrant, and seated users.
 * This class implements the TableModelInterface, providing functionality to manage the users seated at the table.
 */
public class TableModel implements TableModelInterface {
    private Position position;
    private int quadrant;
    private ArrayList<UserModel> seatedPeople = new ArrayList<>();

    public TableModel(Position position, int quadrant) {
        this.position = position;
        this.quadrant = quadrant;
    }

    @Override
    public ArrayList<UserModel> getSeatedPeople() {
        return seatedPeople;
    }

    @Override
    public void addSeatedPeople(List<UserModel> seatedPeople) {
        this.seatedPeople.addAll(seatedPeople);
    }

    @Override
    public void seatUser(UserModel user) {
        seatedPeople.add(user);
    }

    public void unseatUser(UserModel user) {
        seatedPeople.remove(user);
    }

    public void clearUsers() {
        seatedPeople.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Table (%d|%d)", position.getX(), position.getY());
    }

    public int getQuadrant() {
        return quadrant;
    }

    public Position getPosition() {
        return position;
    }
}
