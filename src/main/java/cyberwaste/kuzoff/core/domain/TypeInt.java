package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeInt extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeInt(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            Integer.parseInt(valString);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}
