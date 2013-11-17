package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Value implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String value;
    private String typeName;
    
    public Value() {
    }
    
    public Value(String val, Type type){
        this.value = val;
        this.typeName = type.getName();
    }
    
    public String getTypeName() {
        return typeName;
    }
    
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getValue(){
        return Type.createType(typeName).getValue(value);
    }
    
    public String data(){
        return value;
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
