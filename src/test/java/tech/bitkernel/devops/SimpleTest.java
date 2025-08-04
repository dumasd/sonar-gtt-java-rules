/*
 * This file is part of Bitkernel GTT Java Rules.
 *
 * Bitkernel GTT Java Rules is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Bitkernel GTT Java Rules is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
