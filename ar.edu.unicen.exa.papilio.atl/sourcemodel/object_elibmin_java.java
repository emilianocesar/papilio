<?xml version="1.0" encoding="ASCII"?>
<java:Model xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:java="http://www.eclipse.org/MoDisco/Java/0.2.incubation/java" name="Elibmin">
  <ownedElements name="samples">
    <ownedPackages name="elib">
      <ownedElements xsi:type="java:ClassDeclaration" originalCompilationUnit="//@compilationUnits.0" name="Document" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.2/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0/@initializer/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@parameters.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.4/@returnType">
        <modifier visibility="public"/>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.0">
          <modifier/>
          <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.0" name="title" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.2/@body/@statements.0/@expression/@leftHandSide"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.0">
          <modifier/>
          <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
          <fragments originalCompilationUnit="//@compilationUnits.0" name="loan" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.3/@body/@statements.0/@expression/@leftOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5/@body/@statements.0/@expression/@leftHandSide"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:ConstructorDeclaration" originalCompilationUnit="//@compilationUnits.0" name="Document" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0/@initializer">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.0">
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.0">
              <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.0">
                <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.0/@fragments.0"/>
                <rightHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.2/@parameters.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.0" name="tit" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.2/@body/@statements.0/@expression/@rightHandSide">
            <modifier/>
            <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.0" name="isAvailable" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@rightOperand">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.0">
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.0">
              <expression xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.0" operator="==">
                <rightOperand xsi:type="java:NullLiteral" originalCompilationUnit="//@compilationUnits.0"/>
                <leftOperand xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.1/@fragments.0"/>
              </expression>
            </statements>
          </body>
          <returnType type="//@orphanTypes.4"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.0" name="authorizedLoan" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@rightOperand">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.0">
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.0">
              <expression xsi:type="java:BooleanLiteral" originalCompilationUnit="//@compilationUnits.0" value="true"/>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.0" name="user">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
          </parameters>
          <returnType type="//@orphanTypes.4"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.0" name="addLoan" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.5/@expression">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.0">
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.0">
              <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.0">
                <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.1/@fragments.0"/>
                <rightHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5/@parameters.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.0" name="ln" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5/@body/@statements.0/@expression/@rightHandSide">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
          </parameters>
          <returnType type="//@orphanTypes.5"/>
        </bodyDeclarations>
        <instances name="Document1" instanceClass="//@ownedElements.0/@ownedPackages.0/@ownedElements.0" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0/@initializer //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@rightOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@rightOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.5/@expression" creationExpression="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0/@initializer"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" originalCompilationUnit="//@compilationUnits.1" name="Library" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@fragments.0/@initializer/@type">
        <annotations originalCompilationUnit="//@compilationUnits.1">
          <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.5"/>
          <values>
            <value xsi:type="java:StringLiteral" originalCompilationUnit="//@compilationUnits.1" escapedValue="&quot;all&quot;"/>
          </values>
        </annotations>
        <modifier visibility="public"/>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.1">
          <modifier inheritance="final"/>
          <type type="//@orphanTypes.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.1" name="MAX_NUMBER_OF_LOANS" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@leftOperand/@rightOperand">
            <initializer xsi:type="java:NumberLiteral" originalCompilationUnit="//@compilationUnits.1" tokenValue="20"/>
          </fragments>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.1">
          <modifier/>
          <type type="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.1" name="loans" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.3/@expression/@expression">
            <initializer xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.1/@ownedPackages.2/@ownedElements.1/@bodyDeclarations.0">
              <type type="//@ownedElements.1/@ownedPackages.2/@ownedElements.1"/>
            </initializer>
          </fragments>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:ConstructorDeclaration" originalCompilationUnit="//@compilationUnits.1" name="Library" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@fragments.0/@initializer">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.1"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.1" name="borrowDocument" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.3/@expression">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.1">
            <statements xsi:type="java:IfStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="||">
                <rightOperand xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="==">
                  <rightOperand xsi:type="java:NullLiteral" originalCompilationUnit="//@compilationUnits.1"/>
                  <leftOperand xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.1"/>
                </rightOperand>
                <leftOperand xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="==">
                  <rightOperand xsi:type="java:NullLiteral" originalCompilationUnit="//@compilationUnits.1"/>
                  <leftOperand xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.0"/>
                </leftOperand>
              </expression>
              <thenStatement xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.1">
                <expression xsi:type="java:BooleanLiteral" originalCompilationUnit="//@compilationUnits.1"/>
              </thenStatement>
            </statements>
            <statements xsi:type="java:IfStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="&amp;&amp;">
                <rightOperand xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.4">
                  <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.0"/>
                  <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.1"/>
                </rightOperand>
                <leftOperand xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="&amp;&amp;">
                  <rightOperand xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.3">
                    <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.1"/>
                  </rightOperand>
                  <leftOperand xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="&lt;">
                    <rightOperand xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.0/@fragments.0"/>
                    <leftOperand xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.3">
                      <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.0"/>
                    </leftOperand>
                  </leftOperand>
                </leftOperand>
              </expression>
              <thenStatement xsi:type="java:Block" originalCompilationUnit="//@compilationUnits.1">
                <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
                  <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
                  <fragments originalCompilationUnit="//@compilationUnits.1" name="loan" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@leftHandSide //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.2/@expression/@arguments.0"/>
                  <modifier/>
                </statements>
                <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
                  <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.1">
                    <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.0/@fragments.0"/>
                    <rightHandSide xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2">
                      <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.0"/>
                      <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.1"/>
                      <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
                    </rightHandSide>
                  </expression>
                </statements>
                <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
                  <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4">
                    <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.0/@fragments.0"/>
                  </expression>
                </statements>
                <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.1">
                  <expression xsi:type="java:BooleanLiteral" originalCompilationUnit="//@compilationUnits.1" value="true"/>
                </statements>
              </thenStatement>
            </statements>
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:BooleanLiteral" originalCompilationUnit="//@compilationUnits.1"/>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.1" name="user" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.0/@expression/@leftOperand/@leftOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@leftOperand/@leftOperand/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@rightOperand/@arguments.0 //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide/@arguments.0">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
          </parameters>
          <parameters originalCompilationUnit="//@compilationUnits.1" name="doc" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.0/@expression/@rightOperand/@leftOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@rightOperand/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@rightOperand/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide/@arguments.1">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
          <returnType type="//@orphanTypes.4"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.1" name="addLoan" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.2/@expression">
          <modifier visibility="private"/>
          <body originalCompilationUnit="//@compilationUnits.1">
            <statements xsi:type="java:IfStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:InfixExpression" originalCompilationUnit="//@compilationUnits.1" operator="==">
                <rightOperand xsi:type="java:NullLiteral" originalCompilationUnit="//@compilationUnits.1"/>
                <leftOperand xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
              </expression>
              <thenStatement xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.1"/>
            </statements>
            <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
              <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
              <fragments originalCompilationUnit="//@compilationUnits.1" name="user" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.4/@expression/@expression">
                <initializer xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.3">
                  <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
                </initializer>
              </fragments>
              <modifier/>
            </statements>
            <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
              <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
              <fragments originalCompilationUnit="//@compilationUnits.1" name="doc" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.5/@expression/@expression">
                <initializer xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.4">
                  <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
                </initializer>
              </fragments>
              <modifier/>
            </statements>
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.1/@ownedPackages.2/@ownedElements.0/@bodyDeclarations.0">
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.1/@fragments.0"/>
              </expression>
            </statements>
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4">
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.1/@fragments.0"/>
              </expression>
            </statements>
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5">
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0"/>
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.2/@fragments.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.1" name="loan" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.0/@expression/@leftOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.1/@fragments.0/@initializer/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.2/@fragments.0/@initializer/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.3/@expression/@arguments.0 //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.4/@expression/@arguments.0 //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.5/@expression/@arguments.0">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
          </parameters>
          <returnType type="//@orphanTypes.5"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.1" name="main">
          <modifier visibility="public" static="true"/>
          <body originalCompilationUnit="//@compilationUnits.1">
            <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
              <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
              <fragments originalCompilationUnit="//@compilationUnits.1" name="me" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.3/@expression/@arguments.0">
                <initializer xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.2">
                  <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@parameters.0"/>
                  <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
                </initializer>
              </fragments>
              <modifier/>
            </statements>
            <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
              <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
              <fragments originalCompilationUnit="//@compilationUnits.1" name="doc" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.3/@expression/@arguments.1">
                <initializer xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.2">
                  <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@parameters.1"/>
                  <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
                </initializer>
              </fragments>
              <modifier/>
            </statements>
            <statements xsi:type="java:VariableDeclarationStatement" originalCompilationUnit="//@compilationUnits.1">
              <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.1"/>
              <fragments originalCompilationUnit="//@compilationUnits.1" name="myLibrary" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.3/@expression/@expression">
                <initializer xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.2">
                  <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.1"/>
                </initializer>
              </fragments>
              <modifier/>
            </statements>
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.1">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.1" method="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3">
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0"/>
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0"/>
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@fragments.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.1" name="userName" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0/@initializer/@arguments.0">
            <modifier/>
            <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
          <parameters originalCompilationUnit="//@compilationUnits.1" name="docName" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.1/@fragments.0/@initializer/@arguments.0">
            <modifier/>
            <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
          <returnType type="//@orphanTypes.5"/>
        </bodyDeclarations>
        <instances name="Library1" instanceClass="//@ownedElements.0/@ownedPackages.0/@ownedElements.1" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@fragments.0/@initializer //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.2/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.3/@expression" creationExpression="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.2/@fragments.0/@initializer"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" originalCompilationUnit="//@compilationUnits.2" name="Loan" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@parameters.0/@type">
        <modifier visibility="public"/>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.2">
          <modifier/>
          <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
          <fragments originalCompilationUnit="//@compilationUnits.2" name="user" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@body/@statements.0/@expression/@leftHandSide //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.3/@body/@statements.0/@expression"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.2">
          <modifier/>
          <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.2" name="document" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@body/@statements.1/@expression/@leftHandSide //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.4/@body/@statements.0/@expression"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:ConstructorDeclaration" originalCompilationUnit="//@compilationUnits.2" name="Loan" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.2">
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.2">
              <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.2">
                <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.0/@fragments.0"/>
                <rightHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@parameters.0"/>
              </expression>
            </statements>
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.2">
              <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.2">
                <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.1/@fragments.0"/>
                <rightHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@parameters.1"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.2" name="usr" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@body/@statements.0/@expression/@rightHandSide">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
          </parameters>
          <parameters originalCompilationUnit="//@compilationUnits.2" name="doc" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@body/@statements.1/@expression/@rightHandSide">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.2" name="getUser" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.1/@fragments.0/@initializer">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.2">
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.2">
              <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.0/@fragments.0"/>
            </statements>
          </body>
          <returnType type="//@ownedElements.0/@ownedPackages.0/@ownedElements.3"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.2" name="getDocument" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.2/@fragments.0/@initializer">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.2">
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.2">
              <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.1/@fragments.0"/>
            </statements>
          </body>
          <returnType type="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
        </bodyDeclarations>
        <instances name="Loan1" instanceClass="//@ownedElements.0/@ownedPackages.0/@ownedElements.2" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.1/@fragments.0/@initializer //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.2/@fragments.0/@initializer" creationExpression="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@thenStatement/@statements.1/@expression/@rightHandSide"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" originalCompilationUnit="//@compilationUnits.3" name="User" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.4/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0/@initializer/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.2/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.2/@bodyDeclarations.3/@returnType">
        <annotations originalCompilationUnit="//@compilationUnits.3">
          <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.5"/>
          <values>
            <value xsi:type="java:StringLiteral" originalCompilationUnit="//@compilationUnits.3" escapedValue="&quot;all&quot;"/>
          </values>
        </annotations>
        <modifier visibility="public"/>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.3">
          <modifier/>
          <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.3" name="fullName" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.2/@body/@statements.0/@expression/@leftHandSide"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:FieldDeclaration" originalCompilationUnit="//@compilationUnits.3">
          <modifier/>
          <type type="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
          <fragments originalCompilationUnit="//@compilationUnits.3" name="loans" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.3/@body/@statements.0/@expression/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@body/@statements.0/@expression/@expression">
            <initializer xsi:type="java:ClassInstanceCreation" originalCompilationUnit="//@compilationUnits.3" method="//@ownedElements.1/@ownedPackages.2/@ownedElements.1/@bodyDeclarations.0">
              <type type="//@ownedElements.1/@ownedPackages.2/@ownedElements.1"/>
            </initializer>
          </fragments>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:ConstructorDeclaration" originalCompilationUnit="//@compilationUnits.3" name="User" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0/@initializer">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.3">
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.3">
              <expression xsi:type="java:Assignment" originalCompilationUnit="//@compilationUnits.3">
                <leftHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.0/@fragments.0"/>
                <rightHandSide xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.2/@parameters.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.3" name="name" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.2/@body/@statements.0/@expression/@rightHandSide">
            <modifier/>
            <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.0"/>
          </parameters>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.3" name="numberOfLoans" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@leftOperand/@leftOperand">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.3">
            <statements xsi:type="java:ReturnStatement" originalCompilationUnit="//@compilationUnits.3">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.3" method="//@ownedElements.1/@ownedPackages.2/@ownedElements.0/@bodyDeclarations.1">
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.1/@fragments.0"/>
              </expression>
            </statements>
          </body>
          <returnType type="//@orphanTypes.0"/>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" originalCompilationUnit="//@compilationUnits.3" name="addLoan" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.4/@expression">
          <modifier visibility="public"/>
          <body originalCompilationUnit="//@compilationUnits.3">
            <statements xsi:type="java:ExpressionStatement" originalCompilationUnit="//@compilationUnits.3">
              <expression xsi:type="java:MethodInvocation" originalCompilationUnit="//@compilationUnits.3" method="//@ownedElements.1/@ownedPackages.2/@ownedElements.0/@bodyDeclarations.0">
                <arguments xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@parameters.0"/>
                <expression xsi:type="java:SingleVariableAccess" variable="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.1/@fragments.0"/>
              </expression>
            </statements>
          </body>
          <parameters originalCompilationUnit="//@compilationUnits.3" name="loan" usageInVariableAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@body/@statements.0/@expression/@arguments.0">
            <modifier/>
            <type type="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
          </parameters>
          <returnType type="//@orphanTypes.5"/>
        </bodyDeclarations>
        <instances name="User1" instanceClass="//@ownedElements.0/@ownedPackages.0/@ownedElements.3" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0/@initializer //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@body/@statements.1/@expression/@leftOperand/@leftOperand/@leftOperand //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.4/@expression" creationExpression="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@body/@statements.0/@fragments.0/@initializer"/>
      </ownedElements>
    </ownedPackages>
  </ownedElements>
  <ownedElements name="java" proxy="true">
    <ownedPackages name="lang" proxy="true">
      <ownedElements xsi:type="java:ClassDeclaration" name="String" proxy="true" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.2/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@parameters.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@parameters.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.2/@parameters.0/@type">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.1/@ownedElements.0"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.0/@ownedElements.1"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.0/@ownedElements.2"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Comparable" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.0/@ownedElements.0/@superInterfaces.1">
        <typeParameters name="T" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="CharSequence" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.0/@ownedElements.0/@superInterfaces.2"/>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Iterable" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.0/@superInterfaces.0">
        <typeParameters name="T" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Cloneable" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.1/@superInterfaces.2"/>
      <ownedElements xsi:type="java:AnnotationTypeDeclaration" name="SuppressWarnings" proxy="true" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@annotations.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@annotations.0/@type"/>
      <ownedElements xsi:type="java:ClassDeclaration" name="Object" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.0/@bodyDeclarations.0/@parameters.0/@type"/>
    </ownedPackages>
    <ownedPackages name="io" proxy="true">
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Serializable" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.0/@ownedElements.0/@superInterfaces.0 //@ownedElements.1/@ownedPackages.2/@ownedElements.1/@superInterfaces.3"/>
    </ownedPackages>
    <ownedPackages name="util" proxy="true">
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Collection" proxy="true" usagesInImports="//@compilationUnits.1/@imports.0 //@compilationUnits.3/@imports.0" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.4/@superInterfaces.0 //@ownedElements.1/@ownedPackages.2/@ownedElements.5/@superInterfaces.0 //@ownedElements.1/@ownedPackages.2/@ownedElements.7/@superInterfaces.0 //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.1/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.1/@type">
        <bodyDeclarations xsi:type="java:MethodDeclaration" name="add" proxy="true" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@body/@statements.3/@expression //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@body/@statements.0/@expression">
          <parameters name="arg0" proxy="true">
            <type type="//@ownedElements.1/@ownedPackages.0/@ownedElements.6"/>
          </parameters>
        </bodyDeclarations>
        <bodyDeclarations xsi:type="java:MethodDeclaration" name="size" proxy="true" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.3/@body/@statements.0/@expression"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.0/@ownedElements.3"/>
        <typeParameters name="E" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" name="LinkedList" proxy="true" usagesInImports="//@compilationUnits.1/@imports.1 //@compilationUnits.3/@imports.1" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.1/@fragments.0/@initializer/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.1/@fragments.0/@initializer/@type">
        <bodyDeclarations xsi:type="java:ConstructorDeclaration" name="LinkedList" proxy="true" usages="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.1/@fragments.0/@initializer //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.1/@fragments.0/@initializer"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.5"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.6"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.0/@ownedElements.4"/>
        <superInterfaces type="//@ownedElements.1/@ownedPackages.1/@ownedElements.0"/>
        <typeParameters name="E" proxy="true"/>
        <superClass type="//@ownedElements.1/@ownedPackages.2/@ownedElements.2"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" name="AbstractSequentialList" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.1/@superClass">
        <typeParameters name="E" proxy="true"/>
        <superClass type="//@ownedElements.1/@ownedPackages.2/@ownedElements.3"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" name="AbstractList" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.2/@superClass">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.5"/>
        <typeParameters name="E" proxy="true"/>
        <superClass type="//@ownedElements.1/@ownedPackages.2/@ownedElements.4"/>
      </ownedElements>
      <ownedElements xsi:type="java:ClassDeclaration" name="AbstractCollection" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.3/@superClass">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
        <typeParameters name="E" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="List" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.3/@superInterfaces.0 //@ownedElements.1/@ownedPackages.2/@ownedElements.1/@superInterfaces.0">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
        <typeParameters name="E" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Deque" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.1/@superInterfaces.1">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.7"/>
        <typeParameters name="E" proxy="true"/>
      </ownedElements>
      <ownedElements xsi:type="java:InterfaceDeclaration" name="Queue" proxy="true" usagesInTypeAccess="//@ownedElements.1/@ownedPackages.2/@ownedElements.6/@superInterfaces.0">
        <superInterfaces type="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
        <typeParameters name="E" proxy="true"/>
      </ownedElements>
    </ownedPackages>
  </ownedElements>
  <orphanTypes xsi:type="java:PrimitiveTypeInt" name="int" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.0/@type //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.3/@returnType"/>
  <orphanTypes xsi:type="java:PrimitiveTypeLong" name="long"/>
  <orphanTypes xsi:type="java:PrimitiveTypeFloat" name="float"/>
  <orphanTypes xsi:type="java:PrimitiveTypeDouble" name="double"/>
  <orphanTypes xsi:type="java:PrimitiveTypeBoolean" name="boolean" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.3/@returnType //@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.4/@returnType //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.3/@returnType"/>
  <orphanTypes xsi:type="java:PrimitiveTypeVoid" name="void" usagesInTypeAccess="//@ownedElements.0/@ownedPackages.0/@ownedElements.0/@bodyDeclarations.5/@returnType //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.4/@returnType //@ownedElements.0/@ownedPackages.0/@ownedElements.1/@bodyDeclarations.5/@returnType //@ownedElements.0/@ownedPackages.0/@ownedElements.3/@bodyDeclarations.4/@returnType"/>
  <orphanTypes xsi:type="java:PrimitiveTypeChar" name="char"/>
  <orphanTypes xsi:type="java:PrimitiveTypeShort" name="short"/>
  <orphanTypes xsi:type="java:PrimitiveTypeByte" name="byte"/>
  <compilationUnits name="Document.java" originalFilePath="C:\Users\Rolandis\Documents\Bel\Tesis\tWorkspace\Elibmin\src\samples\elib\Document.java" package="//@ownedElements.0/@ownedPackages.0" types="//@ownedElements.0/@ownedPackages.0/@ownedElements.0"/>
  <compilationUnits name="Library.java" originalFilePath="C:\Users\Rolandis\Documents\Bel\Tesis\tWorkspace\Elibmin\src\samples\elib\Library.java" package="//@ownedElements.0/@ownedPackages.0" types="//@ownedElements.0/@ownedPackages.0/@ownedElements.1">
    <imports originalCompilationUnit="//@compilationUnits.1" importedElement="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
    <imports originalCompilationUnit="//@compilationUnits.1" importedElement="//@ownedElements.1/@ownedPackages.2/@ownedElements.1"/>
  </compilationUnits>
  <compilationUnits name="Loan.java" originalFilePath="C:\Users\Rolandis\Documents\Bel\Tesis\tWorkspace\Elibmin\src\samples\elib\Loan.java" package="//@ownedElements.0/@ownedPackages.0" types="//@ownedElements.0/@ownedPackages.0/@ownedElements.2"/>
  <compilationUnits name="User.java" originalFilePath="C:\Users\Rolandis\Documents\Bel\Tesis\tWorkspace\Elibmin\src\samples\elib\User.java" package="//@ownedElements.0/@ownedPackages.0" types="//@ownedElements.0/@ownedPackages.0/@ownedElements.3">
    <imports originalCompilationUnit="//@compilationUnits.3" importedElement="//@ownedElements.1/@ownedPackages.2/@ownedElements.0"/>
    <imports originalCompilationUnit="//@compilationUnits.3" importedElement="//@ownedElements.1/@ownedPackages.2/@ownedElements.1"/>
  </compilationUnits>
</java:Model>
