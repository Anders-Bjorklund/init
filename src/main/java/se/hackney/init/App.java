package se.hackney.init;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import se.hackney.init.internal.Type;

public class App {

    public static void main(String[] args) {

        // Validate input format

        String currentDir = System.getProperty("user.dir");
        System.out.println( "CURRENT: " + currentDir );

        for (String string : args) {
            System.out.println(string);
        }

        String home = System.getProperty("user.home");
        System.out.println("[ home ] = " + home);

        String typeHome = home + File.separator + ".init" + File.separator + "types";
        System.out.println("[ types ] = " + typeHome);

        Map<String, Type> types = getTypes(typeHome);
        String target = args[0].toUpperCase();

        System.out.println("TARGET: " + target);
        Type matchedtype = types.get(target);

        System.out.println("MATCHED: " + matchedtype.getPath());


        // Find ( case insensitive ) type to use

        // Recurse into type, recreating directory structure from type inside current
        // director
        // Copy all files at current level. Replace all template values in the files
        // using values from parameters ( or secondarily using values from .defaults in
        // type root )
        // Only write files if not already present in directory. Use --force flag in
        // order to overwrite already existing files.

    }

    private static Map<String, Type> getTypes(String... paths) {

        File[] directories = new File(paths[0]).listFiles(File::isDirectory);
        Map<String, Type> map = new HashMap<>();

        // Find all available types
        for (File file : directories) {
            String path = file.toString();
            String name = path.substring(path.lastIndexOf(File.separator) + 1).toUpperCase();
            System.out.println( name );

            Type type = new Type();
            type.setName(name);
            type.setPath(path);

            map.put(name, type);
        }

        return map;

    }

}
