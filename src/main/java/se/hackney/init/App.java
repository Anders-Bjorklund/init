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

import se.hackney.init.internal.ArgumentParser;
import se.hackney.init.internal.Values;

public class App {

    public static void main(String[] args) {

        // Validate input format
        if (args == null || args.length == 0) {
            System.out.println("Init without arguments. [ Exiting ]");
            System.exit(0);
        }

        Values values = ArgumentParser.parse(args);

        String home = System.getProperty("user.home");
        String templateHome = home + File.separator + ".init" + File.separator + "types";
        values.setTemplateHome(templateHome);

        String currentDir = System.getProperty("user.dir");
        // System.out.println("CURRENT: " + currentDir);

        init(currentDir, getTemplateDir(values), values);
        // Recurse into type, recreating directory structure from type inside current
        // director
        // Copy all files at current level. Replace all template values in the files
        // using values from parameters ( or secondarily using values from .defaults in
        // type root )
        // Only write files if not already present in directory. Use --force flag in
        // order to overwrite already existing files.

    }


    private static void init(String currentDir, String templateDir, Values values) {

        // Read all files, insert any template values and store to files in target
        // directory while potentially setting file names based on template values
        List<File> files = Arrays.asList(new File(templateDir).listFiles(File::isFile));

        for (File file : files) {
            // System.out.println("[ FILE ] : " + file.getName());

            String targetPath = currentDir + File.separator + file.getName();
            String content = getContent(file.getAbsolutePath());

            putContent(targetPath, content);
        }

        // For each directory in template, create directory in target and recursively
        // init them.
        List<File> directories = Arrays.asList(new File(templateDir).listFiles(File::isDirectory));

        for (File directory : directories) {
            String localDirectory = directory.getName();

            String absoluteDirectory = currentDir + File.separator + localDirectory;
            new File(absoluteDirectory).mkdirs();

            String nextTemplateDirectory = templateDir + File.separator + localDirectory;
            init(absoluteDirectory, nextTemplateDirectory, values);
        }

    }


    private static String getTemplateDir(Values values) {

        // List all termplate directories, find selected template using case-insensitive comparison
        List<File> files = Arrays.asList(new File(values.getTemplateHome()).listFiles(File::isDirectory));

        for (File file : files) {
            String filePath = file.toString();

            if (filePath.toUpperCase().endsWith(values.getTemplateName().toUpperCase())) {
                return filePath;
            }
        }

        System.out.println("Unable to find a matching init template.");
        System.exit(0);

        return null;
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
