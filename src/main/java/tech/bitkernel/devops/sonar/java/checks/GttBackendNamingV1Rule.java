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
