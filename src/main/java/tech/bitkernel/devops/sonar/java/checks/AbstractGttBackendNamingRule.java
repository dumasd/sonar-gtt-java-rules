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
package tech.bitkernel.devops.sonar.java.checks;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ModifiersTree;
import org.sonar.plugins.java.api.tree.ParameterizedTypeTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeTree;
import org.sonar.plugins.java.api.tree.VariableTree;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractGttBackendNamingRule {

    protected static final List<String> API_RESPONSE_TYPES = Arrays.asList(
            "ApiResponse",
            "ApiPagingResponse",
            "ApiSubQueryResponse",
            "ApiSubQueryCountResponse",
            "PageResult"
    );

    protected static final List<String> API_REQUEST_TYPES = Arrays.asList(
            "BO",
            "DTO",
            "Command",
            "Query",
            "ApiSubQueryRequest",
            "ApiSubQueryCountRequest"
    );

    protected static final List<String> API_MAPPING_TYPES = Arrays.asList(
            "GetMapping",
            "PostMapping",
            "PutMapping",
            "DeleteMapping",
            "PatchMapping",
            "RequestMapping"
    );

    protected static final Pattern API_ENDPOINT_CLASS_PATTERN
            = Pattern.compile("^I.*Controller$");

    protected static final Pattern CONTROLLER_CLASS_PATTERN
            = Pattern.compile(".*Controller$");

    protected final JavaCheck javaCheck;
    protected final ClassTree classTree;
    protected final JavaFileScannerContext context;

    protected AbstractGttBackendNamingRule(JavaCheck javaCheck, ClassTree classTree, JavaFileScannerContext context) {
        this.javaCheck = javaCheck;
        this.classTree = classTree;
        this.context = context;
    }

    protected boolean isValidOpenAPIResponse(TypeTree returnType) {
        String name = returnType.symbolType().name();
        return API_RESPONSE_TYPES.stream().anyMatch(name::equals);
    }

    protected void visitOpenAPIRequest(VariableTree variableTree) {
        String name = variableTree.type().symbolType().name();
        List<AnnotationTree> annotationTrees = variableTree.modifiers().annotations();
        // RequestBody
        boolean requestBody = annotationTrees.stream().anyMatch(e -> "RequestBody".equals(e.symbolType().name()));
        if (requestBody) {
            if (variableTree.type().is(Tree.Kind.PARAMETERIZED_TYPE)) {
                ParameterizedTypeTree parameterizedTypeTree = (ParameterizedTypeTree) variableTree.type();
                if (parameterizedTypeTree.typeArguments().isEmpty()) {
                    context.reportIssue(javaCheck, variableTree, "Raw use of OpenAPI method parameter");
                } else {
                    String parameterizedTypeName = parameterizedTypeTree.typeArguments().get(0).symbolType().name();
                    if (API_REQUEST_TYPES.stream().noneMatch(parameterizedTypeName::endsWith)) {
                        context.reportIssue(javaCheck, variableTree, String.format("OpenAPI method parameter's generic type must be %s", API_REQUEST_TYPES));
                    }
                }
            } else if (API_REQUEST_TYPES.stream().noneMatch(name::endsWith)) {
                context.reportIssue(javaCheck, variableTree, String.format("OpenAPI method parameter must be %s", API_REQUEST_TYPES));
            }
        }
    }

    protected abstract void visit();

    protected void visitOpenAPIClass() {
        IdentifierTree simpledNameTree = classTree.simpleName();
        ModifiersTree modifiersTree = classTree.modifiers();

        if (!classTree.is(Tree.Kind.INTERFACE)) {
            context.reportIssue(javaCheck, classTree, "OpenAPI class must be interface");
        }

        if (simpledNameTree != null) {
            if (!API_ENDPOINT_CLASS_PATTERN.matcher(simpledNameTree.name()).matches()) {
                context.reportIssue(javaCheck, simpledNameTree, "OpenAPI class name must match regex: " + API_ENDPOINT_CLASS_PATTERN.pattern());
            }
        }

        if (modifiersTree.annotations().stream().noneMatch(e -> "Tag".equals(e.symbolType().name()))) {
            context.reportIssue(javaCheck, modifiersTree, "OpenAPI class name must hava @Tag annotation");
        }

        for (Tree member : classTree.members()) {
            if (member.is(Tree.Kind.METHOD)) {
                MethodTree methodTree = (MethodTree) member;
                visitOpenAPIMethod(methodTree);
            }
        }

    }


    protected void visitOpenAPIMethod(MethodTree methodTree) {
        TypeTree returnType = methodTree.returnType();
        if (returnType != null && !isValidOpenAPIResponse(returnType)) {
            String message = String.format("OpenAPI method's return type must be %s", API_RESPONSE_TYPES);
            context.reportIssue(javaCheck, returnType, message);
        }
        List<AnnotationTree> annotationTrees = methodTree.modifiers().annotations();
        if (annotationTrees.stream().noneMatch(e -> "Operation".equals(e.symbolType().name()))) {
            context.reportIssue(javaCheck, methodTree, "OpenAPI method must hava @Operation annotation");
        }
        if (annotationTrees.stream().noneMatch(e -> API_MAPPING_TYPES.contains(e.symbolType().name()))) {
            context.reportIssue(javaCheck, methodTree, "OpenAPI method must hava @xxxMapping annotation");
        }

        List<VariableTree> parameters = methodTree.parameters();
        for (VariableTree variableTree : parameters) {
            visitOpenAPIRequest(variableTree);
        }
    }

    protected void visitControllerClass() {
        if (!classTree.is(Tree.Kind.CLASS)) {
            context.reportIssue(javaCheck, classTree, "Controller must be class");
            return;
        }
        IdentifierTree simpleNameTree = classTree.simpleName();
        if (simpleNameTree != null) {
            if (!CONTROLLER_CLASS_PATTERN.matcher(simpleNameTree.name()).matches()) {
                context.reportIssue(javaCheck, classTree, "Controller class name must match regex: " + CONTROLLER_CLASS_PATTERN.pattern());
            }
        }
    }

    protected void visitRepoDaoClass() {
        if (!classTree.is(Tree.Kind.INTERFACE)) {
            context.reportIssue(javaCheck, classTree, "Dao must be interface");
            return;
        }
        IdentifierTree simpleNameTree = classTree.simpleName();
        if (simpleNameTree != null) {
            if (!simpleNameTree.name().endsWith("Repository")) {
                context.reportIssue(javaCheck, classTree, "Dao class must end with 'Repository'");
            }
        }
    }

    protected void visitRepoEntityClass() {
        if (!classTree.is(Tree.Kind.CLASS)) {
            context.reportIssue(javaCheck, classTree, "Dao must be class");
            return;
        }
        IdentifierTree simpleNameTree = classTree.simpleName();
        if (simpleNameTree != null) {
            if (!simpleNameTree.name().endsWith("Entity")) {
                context.reportIssue(javaCheck, classTree, "Entity class must end with 'Entity'");
            }
        }
    }

}
