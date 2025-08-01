package tech.bitkernel.devops.sonar.java.checks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.net.URI;

public class GttBackendNamingV2Rule extends AbstractGttBackendNamingRule {

    private static final Logger LOGGER = LoggerFactory.getLogger(GttBackendNamingV2Rule.class);

    protected GttBackendNamingV2Rule(JavaCheck javaCheck, ClassTree classTree, JavaFileScannerContext context) {
        super(javaCheck, classTree, context);
    }


    @Override
    public void visit() {
        URI fileUri = context.getInputFile().uri();
        if (fileUri.getPath().contains("/api/endpoint/")) {
            visitOpenAPIClass();
        } else if (fileUri.getPath().contains("/api/controller")) {
            visitControllerClass();
        } else if (fileUri.getPath().contains("/repo/entity/")) {
            visitRepoEntityClass();
        } else if (fileUri.getPath().contains("/repo/dao/")) {
            visitRepoDaoClass();
        }
    }

}
