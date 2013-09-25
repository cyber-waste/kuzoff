package cyberwaste.kuzoff.core;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager {

    void forDatabaseFolder(String databaseFolder);

    Table loadTable(String tableName) throws IOException;

    Collection<Table> listTables() throws IOException;

    Table createTable(String tableName, List<String> columnTypes) throws IOException;

    void removeTable(String tableName) throws IOException;

    Row addRow(String tableName, List<String> columnData) throws IOException;

    List<Row> removeRow(String tableName, Map<Integer, String> columnData) throws IOException;

    void dropDatabase() throws IOException;

    String getDatabaseName();

    List<Row> loadTableData(String tableName) throws IOException;

    Table unionTable(String tableName1, String tableName2) throws Exception;

    Table differenceTable(String tableName1, String tableName2) throws Exception;

    Table uniqueTable(String tableName) throws Exception;
}
