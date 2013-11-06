package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Row implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
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
    
    public List<Value> getRow() {
        return row;
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
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Row)) return false;
        
        Row other = (Row) obj;
        
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.row, other.row);
        
        return equalsBuilder.isEquals();
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(row);
        
        return hashCodeBuilder.toHashCode();
    }
}
