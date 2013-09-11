package cyberwaste.kuzoff.outputmanager;

import java.util.Collection;

import cyberwaste.kuzoff.database.Table;

public interface OutputManager {
    
    void output(Collection<Table> collection);

    void output(Table createTable);
}
