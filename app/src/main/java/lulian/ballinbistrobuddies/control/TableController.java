package lulian.ballinbistrobuddies.control;

import static lulian.ballinbistrobuddies.control.ConnectionManager.SERVICE_NAME;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import lulian.ballinbistrobuddies.model.Position;
import lulian.ballinbistrobuddies.model.TableModel;
import lulian.ballinbistrobuddies.model.UserModel;

/**
 * The TableController class manages the operations related to table management within the application.
 * It implements the TableControllerInterface to ensure all required table management functionalities are provided.
 * This class follows the Singleton design pattern to ensure that only one instance of the TableController exists
 * within the application at any time, providing a centralized point of access to the table management functionalities.
 */
public class TableController implements TableControllerInterface {
    private static TableController instance;

    private final HashMap<Integer, ArrayList<TableModel>> quadrants = new HashMap<>();
    UserModel user;
    private TableModel currentTable;

    private TableController(UserModel user) {
        this.user = user;
        for (int i = 1; i <= 8; i++) {
            quadrants.put(i, generateTablesByQuadrant(i));
        }
    }

    /**
     * Provides a global point of access to the {@code TableController} instance and creates it if it doesn't already exist.
     * This method implements the Singleton design pattern to ensure that only one instance of {@code TableController} is
     * created and used throughout the application. It checks if the {@code instance} is {@code null}, and if so, creates
     * a new {@code TableController} object and assigns it to {@code instance}. If {@code instance} is not {@code null},
     * it simply returns the existing instance.
     *
     * @return The single instance of {@code TableController}.
     */
    public static TableController getInstance(UserModel user) {
        if (instance == null) {
            return instance = new TableController(user);
        }

        return instance;
    }

    /**
     * Retrieves the names of all users seated at a specific table.
     * This method utilizes Java Streams to map each UserModel to its name, collecting the results into an ArrayList.
     *
     * @param table The TableModel instance for which user names are to be retrieved.
     * @return An ArrayList<String> containing the names of all users seated at the specified table.
     */
    public ArrayList<String> getUserNamesByTable(TableModel table) {
        return table
                .getSeatedPeople()
                .stream()
                .map(UserModel::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Retrieves the names of all users seated at any table across all quadrants.
     * This method aggregates the names of all seated users by flattening the structure of quadrants and tables,
     * then mapping each UserModel to its name. The result is collected into an ArrayList.
     * Utilizes Java Streams to efficiently process the nested collections.
     *
     * @return An ArrayList<String> containing the names of all users seated at tables across all quadrants.
     */
    public ArrayList<String> getAllUserNames() {
        return quadrants
                .values()
                .stream()
                .flatMap(Collection::stream)
                .flatMap(table -> table.getSeatedPeople().stream())
                .map(UserModel::getName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Generates a list of tables for a specified quadrant.
     * This method creates a grid of tables based on predefined dimensions (4x3) for each quadrant.
     * Each table is instantiated with a unique position within the quadrant and the quadrant number itself.
     *
     * @param quadrant The quadrant number for which the tables are to be generated.
     * @return An ArrayList of TableModel objects representing the tables in the specified quadrant.
     */
    private ArrayList<TableModel> generateTablesByQuadrant(int quadrant) {
        ArrayList<TableModel> tables = new ArrayList<>();
        for (int x = 1; x <= 4; x++) {
            for (int y = 1; y <= 3; y++) {
                tables.add(new TableModel(new Position(x,y), quadrant));
            }
        }
        return tables;
    }

    /**
     * Retrieves the tables in a specific quadrant.
     * This method returns the list of tables in the specified quadrant by querying the {@code quadrants} HashMap.
     *
     * @param quadrant The quadrant number for which the tables are to be retrieved.
     * @return An ArrayList of TableModel objects representing the tables in the specified quadrant.
     */
    public ArrayList<TableModel> getTablesByQuadrant(int quadrant) {
        return quadrants.get(quadrant);
    }

    /**
     * Retrieves all tables across all quadrants.
     * This method flattens the structure of the {@code quadrants} HashMap to retrieve all tables across all quadrants.
     *
     * @return An ArrayList of TableModel objects representing all tables across all quadrants.
     */
    public void seatCurrentUserAtTable(UserModel user, TableModel table) {
        unseatCurrentUser(user);
        table.seatUser(user);
        currentTable = table;
    }

    /**
     * Unseats the current user from all tables.
     * This method iterates over all tables in all quadrants and unseats the specified user from each table.
     *
     * @param user The UserModel instance representing the user to be unseated from all tables.
     */
    public void unseatCurrentUser(UserModel user) {
        currentTable = null;
        quadrants
                .values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(table -> table.unseatUser(user));
    }

    public Map<String, String> getUserRecord(){
        if (currentTable == null) {
            return new HashMap<>(){{put("Empty", "Empty");}};
        }
        Map<String, String> map = new HashMap<>();
        map.put("service", SERVICE_NAME);
        map.put("name", user.getName());
        map.put("quadrant", String.valueOf(currentTable.getQuadrant()));
        map.put("x", String.valueOf(currentTable.getPosition().getX()));
        map.put("y", String.valueOf(currentTable.getPosition().getY()));
        return map;
    }

    public TableModel getTableByQuadrantAndPosition(int quadrant, Position position) {
        return quadrants.get(quadrant)
                .stream()
                .filter(tableModel -> tableModel.getPosition().equals(position)).findFirst()
                .get();
    }

    public void clearAllTables() {
        quadrants
                .values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(TableModel::clearUsers);
    }

    public void addUsers(HashMap<String, Map<String, String>> users) {
        clearAllTables();
        if (currentTable != null) {
            seatCurrentUserAtTable(user, currentTable);

        }
        users.values().forEach(uRecord -> {
            getTableByQuadrantAndPosition(Integer.parseInt(uRecord.get("quadrant")), new Position(
                    Integer.parseInt(uRecord.get("x")),
                    Integer.parseInt(uRecord.get("y"))
            )).seatUser(new UserModel(uRecord.get("name"), ""));
        });
    }
}