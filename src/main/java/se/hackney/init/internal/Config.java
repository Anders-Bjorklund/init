package se.hackney.init.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Config {
    private String version;
    
    private List< String > parameters;

    @JsonProperty("defaults")
    Map< String, String > defaults = new HashMap<>();
}
