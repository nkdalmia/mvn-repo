package com.nan.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

public class MethodParamNotObjectTypeCheck extends Check {

    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF};
    }

    public void visitToken(DetailAST ast) {

        // return if no parameters
        if (!ast.branchContains(TokenTypes.PARAMETER_DEF)) {
            return;
        }

        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);
        DetailAST child = parameters.getFirstChild();
        while (child != null) {
            // children are PARAMETER_DEF and COMMA
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST paramType = child.findFirstToken(TokenTypes.TYPE);
                if ("Object".equals(paramType.getFirstChild().getText())) {
                    final DetailAST firstNode = CheckUtils.getFirstNode(child);
                    log(firstNode.getLineNo(), firstNode.getColumnNo(), "Method parameter cannot be of type Object. Use Generic instead.");
                }
            }
            child = child.getNextSibling();
        }
    }
}
