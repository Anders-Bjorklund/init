package se.hackney.init.internal;

public class ArgumentParser {

    public static Values parse(Values values, String... arguments) {

        for (int index = 0; index < arguments.length; index++) {

            if( arguments[ index ].equals("--list") || arguments[ index ].equals("-l") ) {
                values.setList( true );
            }

            if (arguments[index].equals("--help")) {
                values.setHelp( true );
            }

            if (arguments[index].equals("--version")) {
                values.setVersion( true );
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
                String value = arguments[index].substring(positionEquals + 1);

                values.getNamedValues().put(name.toUpperCase(), value);
                continue;
            }

        }

        return values;
    }

}
