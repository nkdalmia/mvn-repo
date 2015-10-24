package com.nan.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.checks.CheckUtils;

/**
 * Created by ndalmia on 10/23/15.
 */
public class MethodParamNoObjectCheck extends Check {


    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.METHOD_DEF};
    }

    public void visitToken(DetailAST ast) {

        // return if no parameters
        if (!ast.branchContains(TokenTypes.PARAMETER_DEF)) {
            return;
        }

        // we can now be sure that there is at least one parameter
        final DetailAST parameters = ast.findFirstToken(TokenTypes.PARAMETERS);
        DetailAST child = parameters.getFirstChild();
        while (child != null) {
            // children are PARAMETER_DEF and COMMA
            if (child.getType() == TokenTypes.PARAMETER_DEF) {
                final DetailAST paramType = child.findFirstToken(TokenTypes.TYPE);
                final DetailAST firstNode = CheckUtils.getFirstNode(paramType);
                log(firstNode.getLineNo(), firstNode.getColumnNo(), "Parameter type", paramType.getText());
                /*if (paramType.getText())

                    log(firstNode.getLineNo(), firstNode.getColumnNo(),
                            MSG_KEY, paramName.getText());
                }*/
            }
            child = child.getNextSibling();
        }
    }
}
