package se.hackney.init;

import java.io.File;

public class App {

    public static void main(String[] args) {

        for (String string : args) {
            System.out.println( string );
        }

        String home = System.getProperty("user.home");
        System.out.println("[ home ] = " + home);

        String types = home + File.separator + ".init" + File.separator + "types";
        System.out.println("[ types ] = " + types);

        File[] directories = new File( types ).listFiles(File::isDirectory);
        
        // Find all available types
        for (File type : directories) {
            System.out.println( type );
        }

        // Find ( case insensitive ) type to use

        // Recurse into type, recreating directory structure from type inside current director
        // Copy all files at current level. Replace all template values in the files using values from parameters ( or secondarily using values from .defaults in type root )
        // Only write files if not already present in directory. Use --force flag in order to overwrite already existing files.

    }


}
