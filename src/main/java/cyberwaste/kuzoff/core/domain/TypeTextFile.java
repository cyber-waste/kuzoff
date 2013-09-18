package cyberwaste.kuzoff.core.domain;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TypeTextFile extends Type{
    
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
