package se.hackney.init.internal;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Directory {

    private Map< String, Directory > directories = new HashMap<>();
    private Map< String, File > files = new HashMap<>();
    
}
