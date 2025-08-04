/*
 * Bitkernel GTT Java Rules
 * Copyright (C) 2009-2025 dumasd
 * wukai213@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Sonar Source-Available License Version 1, as published by SonarSource SA.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the Sonar Source-Available License for more details.
 *
 * You should have received a copy of the Sonar Source-Available License
 * along with this program; if not, see https://sonarsource.com/license/ssal/
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
