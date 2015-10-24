package com.nan.checks;

import com.puppycrawl.tools.checkstyle.api.Check;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class CustomCheckstyleCheck extends Check {
    private int max = 2;

    public int[] getDefaultTokens() {
        return new int[]{TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF};
    }

    public void visitToken(DetailAST ast) {
        // find the OBJBLOCK node below the CLASS_DEF/INTERFACE_DEF
        DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
        // count the number of direct children of the OBJBLOCK
        // that are METHOD_DEFS
        int methodDefs = objBlock.getChildCount(TokenTypes.METHOD_DEF);
        // report error if limit is reached
        if (methodDefs > max) {
            log(ast.getLineNo(),
                    "too many methods, only " + max + " are allowed");
        }
    }
}
