package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Value implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String value;
    private Type type;
    
    public Value(String val,Type type){
        this.value = val;
        this.type = type;
    }
    
    public String getValue(){
        return type.getValue(value);
    }
    
    public String getData(){
        return value;
    }
    
    public Type getType() {
        return type;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        
        if (!(obj instanceof Value)) return false;
        
        Value other = (Value) obj;
        
        EqualsBuilder equalsBuilder = new EqualsBuilder();
        equalsBuilder.append(this.value, other.value);
        
        return equalsBuilder.isEquals();
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(value);
        
        return hashCodeBuilder.toHashCode();
    }
}
