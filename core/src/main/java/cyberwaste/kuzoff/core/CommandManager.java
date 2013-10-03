package cyberwaste.kuzoff.core;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import cyberwaste.kuzoff.core.command.Command;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.core.domain.Type;
import cyberwaste.kuzoff.core.domain.Value;

public abstract class CommandManager {
    
    protected abstract Command getNextCommand() throws IOException;
    
    protected abstract boolean hasMoreCommands();
    
    protected abstract void outputMessage(String message);

    protected abstract void outputResult(String message);
    
    public void start() {
        while (hasMoreCommands()) {
            try {
                Command command = getNextCommand();
                
                if (command != null) {
                    command.execute(this);
                }
            } catch (Exception e) {
                outputError(e);
            }
        }
    }
    
    public void outputListTables(Collection<Table> tables) {
        outputMessage("Found " + tables.size() + " table(s):");
        for (Table table : tables) {
            outputTableInfo(table);
        }
    }

    public void outputTableCreated(Table table) {
        outputMessage("Table created:");
        outputTableInfo(table);
    }

    public void outputTableRemoved(String tableName) {
        outputMessage("Table " + tableName + " removed");
    }

    public void outputRowAdded(Row row) {
        outputMessage("Row added:");
        outputRow(row);
    }

    public void outputRowDeleted(List<Row> rows) {
        outputMessage("Deleted " + rows.size() + " row(s):");
        for (Row row : rows) {
            outputRow(row);
        }
    }

    public void outputDatabaseDropped(String databaseName) {
        outputMessage("Database " + databaseName + " removed");
    }

    public void outputTableData(Table table, List<Row> tableData) {
        outputMessage("Table " + table.name() + " - " + tableData.size() + " row(s):");
        for (Row row : tableData) {
            outputRow(row);
        }
    }
    
    public void outputTableInfo(Table table) {
        StringBuilder tableInfo = new StringBuilder();
        tableInfo.append("table ").append(table.name()).append(" (");
        
        boolean needComaBefore = false;
        for (Type columnType : table.columnTypes()) {
            if (needComaBefore) {
                tableInfo.append(", ");
            } else {
                needComaBefore = true;
            }
            
            tableInfo.append(columnType.name());
        }
        
        tableInfo.append(")");
        
        outputResult(tableInfo.toString());
    }

    private void outputRow(Row row) {
        StringBuilder rowData = new StringBuilder();
        rowData.append("{");
        
        boolean needComaBefore = false;
        for (int i = 0; i < row.length(); i++) {
            Value value = row.getElement(i);
            
            if (needComaBefore) {
                rowData.append(", ");
            } else {
                needComaBefore = true;
            }
            
            rowData.append(value.getValue());
        }
        
        rowData.append("}");
        
        outputResult(rowData.toString());
    }
    
    private void outputError(Exception e) {
        outputMessage("ERROR: "+ e.getMessage());
    }
}
