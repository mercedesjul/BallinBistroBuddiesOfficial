package lulian.ballinbistrobuddies;

import lulian.ballinbistrobuddies.control.TableController;
import lulian.ballinbistrobuddies.model.TableModel;
import lulian.ballinbistrobuddies.model.UserModel;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for TableController class.
 */
public class TableControllerTest {

    private TableController tableController;

    /**
     * Sets up the TableController instance before each test case.
     */
    @Before
    public void setUp() {
        tableController = TableController.getInstance();
    }

    /**
     * Tests seating a user at a specific table.
     */
    @Test
    public void testSeatUserAtTable() {
        // Choose a table (for example, quadrant 1, table 1)
        int quadrantNumber = 1;
        int tableIndex = 0;
        TableModel table = tableController.getTablesByQuadrant(quadrantNumber).get(tableIndex);

        // Create a user
        UserModel user = new UserModel("John Doe", "Light");

        // Seat the user at the table
        tableController.seatCurrentUserAtTable(user, table);

        // Check if the user is seated at the table
        assertTrue(table.getSeatedPeople().contains(user));
    }

    /**
     * Tests unseating a user from all tables.
     */
    @Test
    public void testUnseatUser() {
        // Choose a table (for example, quadrant 1, table 1)
        int quadrantNumber = 1;
        int tableIndex = 0;
        TableModel table = tableController.getTablesByQuadrant(quadrantNumber).get(tableIndex);

        // Create a user
        UserModel user = new UserModel("John Doe", "Light");

        // Seat the user at the table
        tableController.seatCurrentUserAtTable(user, table);

        // Unseat the user from all tables
        tableController.unseatCurrentUser(user);

        // Check if the user is unseated from all tables
        assertFalse(table.getSeatedPeople().contains(user));
    }

    /**
     * Tests retrieving tables from a specific quadrant.
     */
    @Test
    public void testGetTablesByQuadrant() {
        // Choose a quadrant (for example, quadrant 1)
        int quadrantNumber = 1;

        // Get tables from the chosen quadrant
        ArrayList<TableModel> tables = tableController.getTablesByQuadrant(quadrantNumber);

        // Check if the returned list is not null and contains expected number of tables
        assertNotNull(tables);
        assertEquals(12, tables.size()); // Assuming each quadrant has 12 tables (4 rows x 3 columns)
    }
}