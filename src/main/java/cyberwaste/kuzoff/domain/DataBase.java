package cyberwaste.kuzoff.domain;

import java.util.Collection;
import java.util.ArrayList;

public class DataBase {
	
	public DataBase(){	
		tables = new ArrayList<Table>();
	}
	
	public void addTable(Table table){
		tables.add(table);
	}
	
	public boolean removeTable(String name){
		for(Table curTable : tables){
			if(curTable.getName().equals(name)){
				tables.remove(curTable);
				return true;
			}
		}
		return false;
	}
	
	public Collection<String> getTableNames(){
		Collection<String> names = new ArrayList<String>();
		for(Table curTable : tables){
			names.add(curTable.getName());
		}
		return names;
	}
	
	private Collection<Table> tables;

}
