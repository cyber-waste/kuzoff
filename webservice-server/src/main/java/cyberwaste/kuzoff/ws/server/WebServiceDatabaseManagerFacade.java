package cyberwaste.kuzoff.ws.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;

@Controller
@RequestMapping("/api")
public class WebServiceDatabaseManagerFacade implements DatabaseManager {
    
    @Autowired
    private DatabaseManager delegate;
    
    @Override
    public void forDatabaseFolder(String databaseFolder) throws RemoteException {
        delegate.forDatabaseFolder(databaseFolder);
    }

    @Override
    public Table loadTable(String tableName) throws IOException {
        return delegate.loadTable(tableName);
    }

    @Override
    public Collection<Table> listTables() throws IOException {
        return delegate.listTables();
    }

    @Override
    public Table createTable(String tableName, List<String> columnTypes) throws IOException {
        return delegate.createTable(tableName, columnTypes);
    }

    @Override
    public void removeTable(String tableName) throws IOException {
        delegate.removeTable(tableName);
    }

    @Override
    public Row addRow(String tableName, List<String> columnData) throws Exception {
        return delegate.addRow(tableName, columnData);
    }

    @Override
    public List<Row> removeRow(String tableName, Map<Integer, String> columnData) throws Exception {
        return delegate.removeRow(tableName, columnData);
    }

    @Override
    public void dropDatabase() throws IOException {
        delegate.dropDatabase();
    }

    @Override
    public String getDatabaseName() throws RemoteException {
        return delegate.getDatabaseName();
    }

    @Override
    public List<Row> loadTableData(String tableName) throws IOException {
        return delegate.loadTableData(tableName);
    }

    @Override
    public Table unionTable(String tableName1, String tableName2) throws Exception {
        return delegate.unionTable(tableName1, tableName2);
    }

    @Override
    public Table differenceTable(String tableName1, String tableName2) throws Exception {
        return delegate.differenceTable(tableName1, tableName2);
    }

    @Override
    public Table uniqueTable(String tableName) throws Exception {
        return delegate.uniqueTable(tableName);
    }
    
    public void setDelegate(DatabaseManager delegate) {
        this.delegate = delegate;
    }
}
