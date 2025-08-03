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
package tech.bitkernel.devops.sonar.java;

import org.sonar.plugins.java.api.JavaCheck;
import tech.bitkernel.devops.sonar.java.checks.GttBackendNamingDispatcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class RulesList {

  private RulesList() {
  }

  public static List<Class<? extends JavaCheck>> getChecks() {
    List<Class<? extends JavaCheck>> checks = new ArrayList<>();
    checks.addAll(getJavaChecks());
    checks.addAll(getJavaTestChecks());
    return Collections.unmodifiableList(checks);
  }

  /**
   * These rules are going to target MAIN code only
   */
  public static List<Class<? extends JavaCheck>> getJavaChecks() {
    return Collections.singletonList(
            GttBackendNamingDispatcher.class);
  }

  /**
   * These rules are going to target TEST code only
   */
  public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
    return Collections.emptyList();
  }
}
