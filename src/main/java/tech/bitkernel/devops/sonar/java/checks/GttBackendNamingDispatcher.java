package tech.bitkernel.devops.sonar.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Rule(key = "GttBackendNaming")
public class GttBackendNamingDispatcher extends IssuableSubscriptionVisitor {

    private static final List<String> V1_BASE_PACKAGES = Arrays.asList(
            "/api/",
            "/app/config/",
            "/app/controller/",
            "/app/filter/",
            "/bean/bo/",
            "/bean/command/",
            "/bean/dto",
            "/core/util",
            "/core/constant",
            "/core/dao",
            "/core/entity",
            "/core/service"
    );

    private static final List<String> V2_BASE_PACKAGES = Arrays.asList(
            "/api/endpoint",
            "/api/controller",
            "/api/service/",
            "/api/model/command",
            "/api/model/dto",
            "/api/model/mapper",
            "/app/config/",
            "/app/filter/",
            "/core/service",
            "/core/bo",
            "/core/constant",
            "/core/util",
            "/core/mapper",
            "/repo/dao",
            "/repo/entity"
    );

    @Override
    public List<Tree.Kind> nodesToVisit() {
        return Arrays.asList(Tree.Kind.CLASS, Tree.Kind.INTERFACE);
    }

    @Override
    public void visitNode(Tree tree) {
        ClassTree classTree = (ClassTree) tree;

        URI fileUri = context.getInputFile().uri();
        String path = fileUri.getPath();

        if (V2_BASE_PACKAGES.stream().anyMatch(path::contains)) {
            GttBackendNamingV2Rule rule = new GttBackendNamingV2Rule(this, classTree, context);
            rule.visit();
        } else if (V1_BASE_PACKAGES.stream().anyMatch(path::contains)) {
            GttBackendNamingV1Rule rule = new GttBackendNamingV1Rule(this, classTree, context);
            rule.visit();
        } else {
            context.reportIssue(this, tree, "The package of the class does not comply with either the v1 or v2 conventions.");
        }
        super.visitNode(tree);
    }
}
