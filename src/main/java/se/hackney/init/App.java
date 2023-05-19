package se.hackney.init;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;


public class App {

    public static void main(String[] args) {

        // Validate input format
        if (args == null || args.length == 0) {
            System.out.println("Init without arguments. [ Exiting ]");
            System.exit(0);
        }

        String currentDir = System.getProperty("user.dir");
        System.out.println("CURRENT: " + currentDir);

        for (String string : args) {
            System.out.println(string);
        }

        String home = System.getProperty("user.home");
        System.out.println("[ current ] = " + home);

        String templateHome = home + File.separator + ".init" + File.separator + "types";
        System.out.println("[ templateHome ] = " + templateHome);

        init(currentDir, templateHome, args[0]);
        // Recurse into type, recreating directory structure from type inside current
        // director
        // Copy all files at current level. Replace all template values in the files
        // using values from parameters ( or secondarily using values from .defaults in
        // type root )
        // Only write files if not already present in directory. Use --force flag in
        // order to overwrite already existing files.

    }

    private static void init(String currentDir, String templateHome, String template) {

        // List all termplate directories, find selected template using case-insensitive
        // comparison
        List<File> files = Arrays.asList(new File(templateHome).listFiles(File::isDirectory));
        String templateDir = null;

        for (File file : files) {
            String filePath = file.toString();

            System.out.println("FILE: " + filePath);

            if (filePath.toUpperCase().endsWith(template.toUpperCase())) {
                templateDir = filePath;
                System.out.println("[ TEMPLATE-DIR ] : " + templateDir);
            }
        }

        if (templateDir == null) {
            System.out.println("Unable to find a matching init template.");
            System.exit(0);
        }

        init(currentDir, templateDir);

    }

    private static void init(String currentDir, String templateDir) {

        // Read all files, insert any template values and store to files in target
        // directory while potentially setting file names based on template values
        List<File> files = Arrays.asList(new File(templateDir).listFiles(File::isFile));

        for (File file : files) {
            System.out.println("KJHKJHK " + file.getName());

            String targetPath = currentDir + File.separator + file.getName();
            String content = getContent(file.getAbsolutePath());

            putContent(targetPath, content);
        }

        // For each directory in template, create directory in target

    }

    private static String getContent(String path) {

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)), Charset.defaultCharset());
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(101);
        }

        return null;
    }

    private static void putContent(String path, String content) {
        
        try {
            BufferedWriter writer = new BufferedWriter( new FileWriter( path ) );
            writer.write( content );
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}
