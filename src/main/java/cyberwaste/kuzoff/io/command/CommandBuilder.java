package cyberwaste.kuzoff.io.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cyberwaste.kuzoff.core.DatabaseManager;
import cyberwaste.kuzoff.core.domain.Row;
import cyberwaste.kuzoff.core.domain.Table;
import cyberwaste.kuzoff.io.IOManager;

public class CommandBuilder {
    
    private final String commandName;
    
    private DatabaseManager databaseManager;
    private Map<String, String> parameters;
    private Map<String,Class> commands;

    public static CommandBuilder command(String commandName) {
        return new CommandBuilder(commandName);
    }

    private CommandBuilder(String commandName) {
        this.commandName = commandName;
        commands = new HashMap<String, Class>();
        commands.put("lstbl", CommandListTables.class);
        commands.put("mktbl", CommandMakeTable.class);
        commands.put("rwmtbl", CommandRemoveTable.class);
        commands.put("addrw", CommandAddRow.class);
        commands.put("rmvrw", CommandRemoveRow.class);
        commands.put("drpdb", CommandDropDatabase.class);
        commands.put("swtbl", CommandShowTable.class);
        commands.put("untbl", CommandUnionTables.class);
        commands.put("dftbl", CommandDifferenceTables.class);
        commands.put("uqtbl", CommandUniqueTable.class);
    }
    
    public CommandBuilder usingDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        return this;
    }

    public CommandBuilder forDatabase(String databaseFolder) {
        this.databaseManager.forDatabaseFolder(databaseFolder);
        return this;
    }

    public CommandBuilder withParameters(Map<String, String> parameters) {
        this.parameters = new HashMap<String, String>(parameters);
        return this;
    }

    public Command build() throws Exception {
        if (databaseManager.getDatabaseName() == null) {
            throw new RuntimeException("Database folder is not specified");
        }
        
        if(commands.containsKey(commandName)){
            Class commandClass = commands.get(commandName);
            Command command = (Command)commandClass.newInstance();
            command.setState(parameters, databaseManager);
            return command;
        }
        else{
            return new CommandUnknown();
        }
        //if("lstbl")
        //else if ("mktbl".equals(commandName)) {
        //else if ("rmtbl".equals(commandName)) {      
        //else if("addrw".equals(commandName)){        
        //else if("rmvrw".equals(commandName)){
        //else if("drpdb".equals(commandName)){
        //else if("swtbl".equals(commandName)){
        //else if("untbl".equals(commandName)){
        //else if("dftbl".equals(commandName)){
        //else if("uqtbl".equals(commandName)){
    }

    public static String getStringParameter(Map<String, String> parameters, String key) {
        return parameters.get(key);
    }
    
    public static List<String> getListParameter(Map<String, String> parameters, String key) {
        List<String> result = new ArrayList<String>();
        
        int index = 1;
        String columnType;
        while ((columnType = getStringParameter(parameters, key + "-" + index)) != null) {
            result.add(columnType);
            index++;
        }
        
        return result;
    }

    public static Map<Integer, String> getMapParameter(Map<String,String> parameters, String key, int numColumns){
        Map<Integer, String> result = new HashMap<Integer, String>();
        
        String columnData;
        for (int index = 1;index<=numColumns;index++) {
            columnData = getStringParameter(parameters, key + "-" + index);
            if(columnData != null) result.put(index, columnData);
        }
        
        return result;
    }
}
