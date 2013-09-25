package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeInt extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeInt(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            Integer.parseInt(val);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
