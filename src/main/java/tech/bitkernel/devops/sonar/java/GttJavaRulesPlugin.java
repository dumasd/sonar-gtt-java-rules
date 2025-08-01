package tech.bitkernel.devops.sonar.java;

import org.sonar.api.Plugin;

public class GttJavaRulesPlugin implements Plugin {
    @Override
    public void define(Context context) {
        // server extensions -> objects are instantiated during server startup
        context.addExtension(GttJavaRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(GttJavaFileCheckRegistrar.class);
    }
}
