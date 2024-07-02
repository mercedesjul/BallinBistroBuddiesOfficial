package lulian.ballinbistrobuddies.control;

import lulian.ballinbistrobuddies.model.Position;
import lulian.ballinbistrobuddies.model.TableModel;
import lulian.ballinbistrobuddies.model.UserModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface TableControllerInterface {

    ArrayList<String> getUserNamesByTable(TableModel table);

    ArrayList<String> getAllUserNames();

    ArrayList<TableModel> getTablesByQuadrant(int quadrant);

    void seatCurrentUserAtTable(UserModel user, TableModel table);

    void unseatCurrentUser(UserModel user);

    Map<String, String> getUserRecord();

    TableModel getTableByQuadrantAndPosition(int quadrant, Position position);

    void clearAllTables();

    void addUsers(HashMap<String, Map<String, String>> users);
}
