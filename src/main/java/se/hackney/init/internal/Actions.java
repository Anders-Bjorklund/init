package se.hackney.init.internal;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Actions {

    public static void act(Values values) {

        if (values.isHelp()) {

            if (values.getTemplateName() == null) {
                System.out.println("Usage:\n" + "  init [template-name]\n  init --list");
            } else {
                String arguments = "";
                for (String argumentName : values.getPositionalNames()) {
                    arguments += " " + "<" + argumentName + ">";
                }
                System.out.println("Usage:\n" + "  init " + values.getTemplateName() + arguments + "\n  init --list");
            }

            System.exit(0);
        }

        if (values.isVersion())

        {
            System.out.println("Version:\n  1.0");
            System.exit(0);
        }

        if (values.isList()) {
            List<File> directories = Arrays.asList(new File(values.getHome()).listFiles(File::isDirectory));

            for (File directory : directories) {
                String localDirectory = directory.getName();
                System.out.println(localDirectory);
            }

            System.exit(0);
        }

    }

}
