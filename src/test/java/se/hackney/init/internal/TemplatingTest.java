package se.hackney.init.internal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TemplatingTest {

    @Test
    public void matchingFileName() {
        Values values = new Values();
        values.getNamedValues().put("NAME", "TEST");

        String result = Templating.untemplate( "FIL-¤¤_name_¤¤-1.txt", values);
        assertEquals("FIL-TEST-1.txt", result);
    }
}
