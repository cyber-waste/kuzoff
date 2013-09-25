package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeReal extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeReal(String name){
        super(name);
    }
    
    public boolean isValid(Value val){
        String valString = val.getValue();
        try{
            Double.parseDouble(valString);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
