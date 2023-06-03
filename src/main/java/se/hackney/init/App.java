package se.hackney.init;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import se.hackney.init.internal.Actions;
import se.hackney.init.internal.ArgumentParser;
import se.hackney.init.internal.Config;
import se.hackney.init.internal.Templating;
import se.hackney.init.internal.Values;

public class App {

    public static void main(String[] args) {

        // Validate input format
        if (args == null || args.length == 0) {
            System.out.println("Init without arguments. [ Exiting ]");
            System.exit(0);
        }

        Values values = new Values();
        values.setHome(System.getProperty("user.home") + File.separator + ".init");
        
        if (args[0].indexOf("-") == -1) {
            values.setTemplateName(args[0]);
            
            String templateHome = values.getHome() + File.separator + "templates" + File.separator + values.getTemplateName()
                    + File.separator + "template";
            String settingsHome = values.getHome() + File.separator + "templates" + File.separator + values.getTemplateName();

            values.setTemplateHome(templateHome);
            values.setSettingsHome(settingsHome);
        }

        applyConfiguration(values);
        ArgumentParser.parse(values, args);

        Actions.act(values);

        String currentDir = System.getProperty("user.dir");

        init(currentDir, values.getTemplateHome(), values);

    }

    private static void applyConfiguration(Values values) {

        try {
            Config config = new ObjectMapper(new YAMLFactory())
                    .readValue(new File(values.getSettingsHome() + File.separator + "settings.yaml"), Config.class);

            for (String name : config.getDefaults().keySet()) {
                values.getNamedValues().put(name.toUpperCase(), config.getDefaults().get(name).toString());
            }

            values.setPositionalNames(config.getParameters());

        } catch (NullPointerException e) {

        } catch (IOException e) {
            // No settings file found. This is normal
        }

    }

    private static void init(String currentDir, String templateDir, Values values) {

        // Read all files, insert any template values and store to files in target
        // directory while potentially setting file names based on template values
        List<File> files = Arrays.asList(new File(templateDir).listFiles(File::isFile));

        for (File file : files) {
            String targetPath = currentDir + File.separator + Templating.untemplate(file.getName(), values);
            String content = Templating.untemplate(getContent(file.getAbsolutePath()), values);

            putContent(targetPath, content);
        }

        // For each directory in template, create directory in target and recursively
        // init them.
        List<File> directories = Arrays.asList(new File(templateDir).listFiles(File::isDirectory));

        for (File directory : directories) {
            String localDirectory = directory.getName();

            String absoluteDirectory = Templating.untemplate(currentDir + File.separator + localDirectory, values);
            new File(absoluteDirectory).mkdirs();

            String nextTemplateDirectory = templateDir + File.separator + localDirectory;
            init(absoluteDirectory, nextTemplateDirectory, values);
        }

    }

    private static String getContent(String path) {

        try {
            String content = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
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
