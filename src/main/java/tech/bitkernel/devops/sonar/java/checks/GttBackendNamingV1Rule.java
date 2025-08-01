package tech.bitkernel.devops.sonar.java.checks;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.net.URI;

public class GttBackendNamingV1Rule extends AbstractGttBackendNamingRule {

    protected GttBackendNamingV1Rule(JavaCheck javaCheck, ClassTree classTree, JavaFileScannerContext context) {
        super(javaCheck, classTree, context);
    }

    public void visit() {
        URI fileUri = context.getInputFile().uri();
        if (fileUri.getPath().contains("/api/")) {
            visitOpenAPIClass();
        } else if (fileUri.getPath().contains("/app/controller/")) {
            visitControllerClass();
        } else if (fileUri.getPath().contains("/core/dao/")) {
            visitRepoDaoClass();
        } else if (fileUri.getPath().contains("/core/dao/")) {
            visitRepoDaoClass();
        }
    }

}
