package se.hackney.init.internal;

public class ArgumentParser {

    public static Values parse(String... arguments) {

        Values values = new Values();

        for (int index = 0; index < arguments.length; index++) {

            if (arguments[index].equals("--help")) {
                System.out.println("Usage:\n" + "  init [template-name]\n  init --list");
                System.exit(0);
            }

            if (arguments[index].equals("--version")) {
                System.out.println("Version:\n  1.0");
                System.exit(0);
            }

            // First argument should be the template name, unless user needes help or wants to know version.
            if (index == 0) {
                values.setTemplateName(arguments[index]);

                // If not recognized, just assue it is the template name to be used and continue
                // handling remaining arguments.
                continue;
            }

            int positionEquals = arguments[index].indexOf("=");

            if (positionEquals > 0) {
                String name = arguments[index].substring(0, positionEquals);
                out("NAME: " + name);

                String value = arguments[index].substring(positionEquals + 1);
                out("VALUE: " + value);

                values.getNamedValues().put(name, value);

                continue;
            }

        }

        return values;
    }

    private static void out(String out) {
        System.out.println(out);
    }

}
