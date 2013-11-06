package cyberwaste.kuzoff.core.domain;

import java.io.Serializable;

public class TypeReal extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeReal(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        try{
            Double.parseDouble(val);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getValue(String val) {
        return val;
    }
}
