package tech.bitkernel.devops;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTest {

    @Test
    public void testPackage() {
        Pattern BASE_PACKAGE_PATTERN = Pattern.compile("^gtt\\.[a-z_][a-z0-9_]*\\.[a-z_][a-z0-9_]*((?:\\.[a-z_][a-z0-9_]*)*)$");
        Assertions.assertTrue(BASE_PACKAGE_PATTERN.matcher("gtt.demo.order").matches());
        Assertions.assertFalse(BASE_PACKAGE_PATTERN.matcher("gtt.demo.order.").matches());
        Matcher matcher = BASE_PACKAGE_PATTERN.matcher("gtt.demo.order");
        if (matcher.matches()) {
            String rest = matcher.group(1);
            Assertions.assertEquals("", rest);
        }


        Pattern v1_package_pattern = Pattern.compile("^gtt\\.[a-z_][a-z0-9_]*\\.[a-z_][a-z0-9_]*\\.(api|app|bean|core)?((?:\\.[a-z_][a-z0-9_]*)*)$");
        matcher = v1_package_pattern.matcher("gtt.demo.order.api");
        if (matcher.matches()) {
            String rest = matcher.group(1);
            Assertions.assertEquals("api", rest);

            String rest2 = matcher.group(2);
            Assertions.assertEquals("", rest2);

        }

        matcher = v1_package_pattern.matcher("gtt.demo.order.apb.bbbaaa");
        Assertions.assertFalse(matcher.matches());


    }

}
