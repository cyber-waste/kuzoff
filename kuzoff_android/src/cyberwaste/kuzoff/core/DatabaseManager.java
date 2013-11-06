package cyberwaste.kuzoff.core;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

public interface DatabaseManager{
    
    void setDatabaseHome(File file);

    void forDatabaseFolder(String databaseFolder);

    Table loadTable(String tableName) throws IOException;

    Collection<Table> listTables() throws IOException;

    Table createTable(String tableName, List<String> columnTypes) throws IOException;

    void removeTable(String tableName) throws IOException;

    Row addRow(String tableName, List<String> columnData) throws Exception;

    List<Row> removeRow(String tableName, Map<Integer, String> columnData) throws Exception;

    void dropDatabase() throws IOException;

    String getDatabaseName();

    List<Row> loadTableData(String tableName) throws IOException;

    Table unionTable(String tableName1, String tableName2, String newTable) throws Exception;

    Table differenceTable(String tableName1, String tableName2, String newTable) throws Exception;

    Table uniqueTable(String tableName, String newTable) throws Exception;
}
