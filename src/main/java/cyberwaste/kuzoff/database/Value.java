package cyberwaste.kuzoff.database;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Value {
    
    private String val;
    
    public Value(String val){
        this.val = val;
    }
    
    public String getValue(){
        return val;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Value)) return false;
        
        Value other = (Value) obj;
        
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.val, other.val);
        
        return equalsBuilder.isEquals();
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(val);
        
        return hashCodeBuilder.toHashCode();
    }
}
