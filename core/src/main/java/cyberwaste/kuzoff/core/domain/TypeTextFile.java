package cyberwaste.kuzoff.core.domain;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;

public class TypeTextFile extends Type implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public TypeTextFile(String name){
        super(name);
    }
    
    public boolean isValid(String val){
        return new File(val).exists();

    }

    @Override
    public String getValue(String val) {
        try {
            return FileUtils.readFileToString(new File(val));
        } catch (IOException e) {
            return "NOT ACCESIBLE";
        }
    }
    
}
