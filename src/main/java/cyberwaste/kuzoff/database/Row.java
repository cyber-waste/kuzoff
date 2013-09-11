package cyberwaste.kuzoff.database;

import java.util.List;

public class Row {
    
    private List<Value> row;
    
    public Row(List<Value> row){
        this.row = row;
    }
    
    public int length(){
        return row.size();
    }
    
    public Value getElement(int index){
        return row.get(index);
    }
    
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<row.size();i++){
            if(builder.length() > 0)
                builder.append(' ');
            builder.append(row.get(i).getValue());
        }
        return builder.toString();
    }
}
