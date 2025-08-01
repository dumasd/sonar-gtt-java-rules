package tech.bitkernel.devops.sonar.java.checks;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
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

    protected boolean isValidOpenAPIRequest(VariableTree variableTree) {
        String name = variableTree.type().symbolType().name();
        List<AnnotationTree> annotationTrees = variableTree.modifiers().annotations();
        // RequestBody
        boolean requestBody = annotationTrees.stream().anyMatch(e -> "RequestBody".equals(e.symbolType().name()));
        if (requestBody) {
            return API_REQUEST_TYPES.stream().anyMatch(name::endsWith);
        }
        return true;
    }

    protected abstract void visit();

    protected void visitOpenAPIClass() {
        String simpleName = classTree.simpleName().name();

        if (!classTree.is(Tree.Kind.INTERFACE)) {
            context.reportIssue(javaCheck, classTree.simpleName(), "OpenAPI class must be interface");
            //return;
        }

        if (!API_ENDPOINT_CLASS_PATTERN.matcher(simpleName).matches()) {
            context.reportIssue(javaCheck, classTree.simpleName(), "OpenAPI class name must match regex: " + API_ENDPOINT_CLASS_PATTERN.pattern());
        }

        List<AnnotationTree> annotationTrees = classTree.modifiers().annotations();
        if (annotationTrees.stream().noneMatch(e -> "Tag".equals(e.symbolType().name()))) {
            context.reportIssue(javaCheck, classTree, "OpenAPI class name must hava @Tag annotation");
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
            if (!isValidOpenAPIRequest(variableTree)) {
                String message = String.format("OpenAPI method parameter must be %s", API_REQUEST_TYPES);
                context.reportIssue(javaCheck, variableTree, message);
            }
        }
    }

    protected void visitControllerClass() {
        if (!classTree.is(Tree.Kind.CLASS)) {
            context.reportIssue(javaCheck, classTree, "Controller must be class");
            return;
        }
        String simpleName = classTree.simpleName().name();
        if (!CONTROLLER_CLASS_PATTERN.matcher(simpleName).matches()) {
            context.reportIssue(javaCheck, classTree, "Controller class name must match regex: " + CONTROLLER_CLASS_PATTERN.pattern());
        }
    }

    protected void visitRepoDaoClass() {
        if (!classTree.is(Tree.Kind.INTERFACE)) {
            context.reportIssue(javaCheck, classTree, "Dao must be interface");
            return;
        }
        String simpleName = classTree.simpleName().name();
        if (!simpleName.endsWith("Repository")) {
            context.reportIssue(javaCheck, classTree, "Dao class must end with 'Repository'");
        }
    }

    protected void visitRepoEntityClass() {
        if (!classTree.is(Tree.Kind.CLASS)) {
            context.reportIssue(javaCheck, classTree, "Dao must be class");
            return;
        }
        String simpleName = classTree.simpleName().name();
        if (!simpleName.endsWith("Entity")) {
            context.reportIssue(javaCheck, classTree, "Entity class must end with 'Entity'");
        }
    }

}
