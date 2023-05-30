package se.hackney.init.internal;

public class ArgumentParser {

    public static Values parse(Values values, String... arguments) {

        int argumentIndex = 0;

        for (int index = 0; index < arguments.length; index++) {

            if( arguments[ index ].equals("--list") || arguments[ index ].equals("-l") ) {
                values.setList( true );
                continue;
            }

            if (arguments[index].equals("--help")) {
                values.setHelp( true );
                continue;
            }

            if (arguments[index].equals("--version")) {
                values.setVersion( true );
                continue;
            }

            // First argument should be the template name, unless user needes help or wants to know version.
            if (index == 0) {
                values.setTemplateName(arguments[index]);

                // If not recognized, just assue it is the template name to be used and continue
                // handling remaining arguments.
                continue;
            }

            int positionOfEquals = arguments[index].indexOf("=");

            if (positionOfEquals > 0) {
                String name = arguments[index].substring(0, positionOfEquals);
                String value = arguments[index].substring(positionOfEquals + 1);

                values.getNamedValues().put(name.toUpperCase(), value);
                continue;
            }

            if( positionOfEquals == -1 ) {
                if( argumentIndex < values.getPositionalNames().size() ) {
                    values.getNamedValues().put(values.getPositionalNames().get( argumentIndex++ ).toUpperCase(), arguments[ index ]);
                }
            }

        }

        return values;
    }

}
