package cyberwaste.kuzoff.core.domain;

import java.io.IOException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Value {
    
    private String val;
    private Type type;
    
    public Value(String val,Type type){
        this.val = val;
        this.type = type;
    }
    
    public String getValue(){
        return type.getValue(val);
    }
    
    public String getData(){
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
