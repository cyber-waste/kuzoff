package cyberwaste.kuzoff.database;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

public class DatabaseManager {
    
    private final File databaseFolder;
    
    public DatabaseManager(String databaseFolderName){
        this.databaseFolder = new File(databaseFolderName);
        initDatabaseFolder(this.databaseFolder);
    }

    private final static void initDatabaseFolder(File databaseFolder) {
        databaseFolder.mkdirs();
    }

    public Table createTable(String tableName){
        new File(databaseFolder, tableName).mkdir();
        return new Table(tableName);
    }
    
    public Collection<Table> listTables(){
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
    
    public Table loadTable(String tableDirectory){
        Table result = new Table(new File(tableDirectory).getName());
        return result;
    }

    public void removeTable(String tableName) {
        FileUtils.deleteQuietly(new File(databaseFolder, tableName));
    }
}
