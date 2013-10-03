package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeChar extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeChar(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        if(val.length() == 1) return true;
        else return false;
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
