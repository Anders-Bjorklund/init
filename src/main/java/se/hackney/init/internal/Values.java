package se.hackney.init.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Values {

    private String templateName = null;
    private String templateHome = null;
    private List< String > positionalValues = new ArrayList<>();
    private Map< String, String > namedValues = new HashMap<>();
    
}
