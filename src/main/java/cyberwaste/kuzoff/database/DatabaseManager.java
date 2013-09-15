package cyberwaste.kuzoff.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class DatabaseManager {
    
    private static final String METADATA_FILE_NAME = "metadata";
    private static final String TABLE_FILE_NAME = "table";
    private static final String TABLE_FILE_NAME_TEMP = "table_temp";
    
    private final File databaseFolder;
    
    public DatabaseManager(String databaseFolderName){
        this.databaseFolder = new File(databaseFolderName);
        initDatabaseFolder(this.databaseFolder);
    }

    private final static void initDatabaseFolder(File databaseFolder) {
        databaseFolder.mkdirs();
    }
    
    public String getDatabaseName(){
        return databaseFolder.getName();
    }
    
    public void dropDatabase() throws IOException{
        FileUtils.deleteDirectory(databaseFolder);
    }
    
    public Table createTable(String tableName, List<String> columnTypeNames) throws IOException {
        File tableDirectory = new File(databaseFolder, tableName);
        tableDirectory.mkdir();
        
        List<Type> columnTypes = new ArrayList<Type>();
        for (String columnTypeName : columnTypeNames) {
            columnTypes.add(Type.createType(columnTypeName));
        }
        
        StringBuilder metadata = new StringBuilder();
        for (Type columnType : columnTypes) {
            if (metadata.length() > 0) {
                metadata.append("|");
            }
            metadata.append(columnType.name());
        }
        
        File metadataFile = new File(tableDirectory, METADATA_FILE_NAME);
        FileUtils.writeStringToFile(metadataFile, metadata.toString());
        
        return new Table(tableName, columnTypes);
    }
    
    public Row addRow(String tableName, List<String> columnData) throws IOException{
        File tableDir = new File(databaseFolder, tableName);
        
        List<Value> values = new ArrayList<Value>();
        for(String val : columnData){
            values.add(new Value(val));
        }
        Row row = new Row(values);
        
        StringBuilder stringRow = new StringBuilder();
        for(int i=0;i<row.length();i++){
            if(stringRow.length() > 0)
                stringRow.append(' ');
            stringRow.append(row.getElement(i).getValue());
        }
        if(stringRow.length() > 0) stringRow.append('\n');
        File tableFile = new File(tableDir, TABLE_FILE_NAME);
        FileUtils.writeStringToFile(tableFile, stringRow.toString(), true);
        
        return row;
    }
    
    public List<Row> removeRow(String tableName, Map<Integer,String> columnData) throws IOException{
        File tableFile = new File(new File(databaseFolder,tableName), TABLE_FILE_NAME);
        File tableFileTemp = new File(new File(databaseFolder,tableName), TABLE_FILE_NAME_TEMP);
        
        BufferedReader reader = new BufferedReader(new FileReader(tableFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tableFileTemp));
        
        String currentLine;
        List<Row> res = new ArrayList<Row>();;
        boolean toRemove = true;
        while((currentLine = reader.readLine()) != null){
            toRemove = true;
            String[] curLineData = currentLine.split(" ");
            for(int curKey : columnData.keySet()){
                if(!curLineData[curKey-1].equals(columnData.get(curKey))){
                    toRemove = false;
                    break;
                }
            }
            if(toRemove == false) writer.write(currentLine+"\n");
            else{
                List<Value> valueList = new ArrayList<Value>();
                for(String val : curLineData){
                    valueList.add(new Value(val));
                }
                res.add(new Row(valueList));
            }
        }
        boolean success = tableFileTemp.renameTo(tableFile);
        reader.close();
        writer.close();
        return res;
    }
    
    public Collection<Table> listTables() throws IOException{
        String[] dirList = databaseFolder.list(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                return dir.equals(databaseFolder) && new File(dir,name).isDirectory();
            }
        });
        
        Collection<Table> result = new ArrayList<Table>();
        for(String dir : dirList){
            result.add(loadTable(dir));
        }
        
        return result;
    }
    
    public Table loadTable(String tableName) throws IOException {
        File tableDirectory = new File(databaseFolder, tableName);
        
        List<Type> columnTypes = new ArrayList<Type>();
        
        String metadata = FileUtils.readFileToString(new File(tableDirectory, METADATA_FILE_NAME));
        String[] columnTypeNames = metadata.split("\\|");
        for (String columnTypeName : columnTypeNames) {
            columnTypes.add(Type.createType(columnTypeName));
        }
        
        Table result = new Table(tableName, columnTypes);
        return result;
    }
    
    public List<Row> loadTableData(String tableName) throws IOException{
        List<Row> result = new ArrayList<Row>();
        
        File tableFile = new File(new File(databaseFolder,tableName), TABLE_FILE_NAME);
        BufferedReader reader = new BufferedReader(new FileReader(tableFile));
        
        String currentLine;
        while((currentLine = reader.readLine()) != null){
            String[] curLineData = currentLine.split(" ");
            List<Value> valueList = new ArrayList<Value>();
            for(String val : curLineData) valueList.add(new Value(val));
            result.add(new Row(valueList));
            
        }
        reader.close();
        return result;
    }
    
    public void removeTable(String tableName) throws IOException {
        FileUtils.deleteDirectory(new File(databaseFolder, tableName));
    }
}
