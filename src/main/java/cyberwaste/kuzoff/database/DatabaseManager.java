package cyberwaste.kuzoff.database;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class DatabaseManager {
    
    private static final String METADATA_FILE_NAME = "metadata";
    
    private final File databaseFolder;
    
    public DatabaseManager(String databaseFolderName){
        this.databaseFolder = new File(databaseFolderName);
        initDatabaseFolder(this.databaseFolder);
    }

    private final static void initDatabaseFolder(File databaseFolder) {
        databaseFolder.mkdirs();
    }

    public Table createTable(String tableName, List<String> columnTypeNames) throws IOException {
        File tableDirectory = new File(databaseFolder, tableName);
        tableDirectory.mkdir();
        
        List<Type> columnTypes = new ArrayList<Type>();
        for (String columnTypeName : columnTypeNames) {
            columnTypes.add(new Type(columnTypeName));
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
            columnTypes.add(new Type(columnTypeName));
        }
        
        Table result = new Table(tableName, columnTypes);
        return result;
    }

    public void removeTable(String tableName) throws IOException {
        FileUtils.deleteDirectory(new File(databaseFolder, tableName));
    }
}
