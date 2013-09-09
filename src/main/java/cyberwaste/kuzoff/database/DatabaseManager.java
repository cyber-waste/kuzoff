package cyberwaste.kuzoff.database;

import java.io.File;
import java.io.FilenameFilter;

import cyberwaste.kuzoff.outputmanager.OutputManager;;

public class DatabaseManager {
    
    private File databaseFolder;
    private OutputManager output;
    
    public DatabaseManager(String folderName,OutputManager output){
        databaseFolder = new File(folderName);
        this.output = output;
    }
    
    public void createTable(String tableName){
        new File(databaseFolder,tableName).mkdir();
    }
    
    public void listTables(){
        String[] dirList = databaseFolder.list(new FilenameFilter() {
            
            @Override
            public boolean accept(File dir, String name) {
                return dir.equals(databaseFolder) && new File(dir,name).isDirectory();
            }
        });
        for(String curDir : dirList){
            output.show(loadTable(curDir));
        }
    }
    
    private Table loadTable(String tableDir){
        Table res = new Table(new File(tableDir).getName());
        // load headers
        return res;
    }
}
