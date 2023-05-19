package se.hackney.init.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Templating {
    
    private static Pattern pattern = Pattern.compile("(¤¤_)|(_¤¤)|([^¤_]+)");

    public static String untemplate( String input, Values values ) {

        Matcher matcher = pattern.matcher( input );

        int counter = 0;
        int position = 0;

        StringBuffer buffer = new StringBuffer();
        boolean substituting = false;

        while( matcher.find( position ) ) {
            counter++;

            if( counter > 1000 ) {
                System.exit(1);
            }

            String start =  matcher.group( 1 );
            String end =  matcher.group( 2 );
            String other = matcher.group( 3 );

            if( start != null ) {
                substituting = true;
            }

            if( end != null ) {
                substituting = false;
            }

            if( other != null ) {

                if( substituting ) {
                    String replacement = values.getNamedValues().get(other.trim().toUpperCase());

                    if( replacement == null ) {
                        System.out.println( "Could not find value for parameter [ " + other + " ]");
                        System.exit(0);
                    }

                    buffer.append(replacement);
                } else {
                    buffer.append( other );
                }
            }

            position += stringLength(start) + stringLength(end) + stringLength(other);
        }

        return buffer.toString();
    }

    private static int stringLength( String input ) {

        if ( input == null ) return 0;
        return input.length();

    }
}
