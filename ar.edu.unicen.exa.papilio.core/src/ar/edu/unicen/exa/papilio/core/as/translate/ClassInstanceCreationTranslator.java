package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ClassInstanceCreation;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.Expression;

import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASEmptyElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException.ASTranslatorExceptionLevel;

public class ClassInstanceCreationTranslator extends AbstractTranslator {

	/**
	 * Traduce un elemento de tipo ClassInstanceCreation a su sintaxis abstracta
	 * Se crea un elemento ASConstructorInvocationStatement y se asigna como
	 * lado derecho el nombre completo de la clase del objeto creado, agregando
	 * una lista con la traduccion de los parametros (si posee) El lado
	 * izquierdo del statement de creacion se deja para ser completado por el
	 * metodo invocante
	 * 
	 * @param node
	 *            El elemento ClassInstanceCreation a traducir
	 * @return Una lista con el elemento ASConstructorInvocationStatement
	 *         generado
	 * 
	 */
	@Override
	public List<ASElement> translate(ASTNode node) {
		ClassInstanceCreation constructorInvocation = (ClassInstanceCreation) node;

		if (constructorInvocation.getMethod().getAbstractTypeDeclaration() == null
				|| constructorInvocation.getMethod()
						.getAbstractTypeDeclaration().isProxy()) {
			// si el constructor corresponde a una clase proxy no realizo
			// traduccion
			this.context
					.addError(new ASTranslatorException(
							"Proxy constructor invocation "
									+ constructorInvocation.getMethod()
											.getName()
									+ ". Proxy constructor invocations are not translated",
							constructorInvocation,
							ASTranslatorExceptionLevel.INFO));
			return Collections.emptyList();
		}

		List<ASElement> resultElements = new ArrayList<ASElement>();
		ASConstructorInvocationStatement asConstructorInvocationStatement = new ASConstructorInvocationStatement();
		asConstructorInvocationStatement.setNode(constructorInvocation);

		ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) constructorInvocation
				.getMethod();
		ASConstructorDeclaration asConstructorDeclaration = (ASConstructorDeclaration) this.context
				.getDeclaration(constructorDeclaration);
		if (asConstructorDeclaration == null) {
			asConstructorDeclaration = (ASConstructorDeclaration) this.context
					.addUndeclaredConstructor(constructorDeclaration);
		}
		asConstructorInvocationStatement
				.setConstructorDeclaration(asConstructorDeclaration);

		for (Expression argument : constructorInvocation.getArguments()) {

			AbstractSyntaxTranslator argumentTranslator = this
					.getTranslatorForNode(argument);
			List<ASElement> result = argumentTranslator.translate(argument);
			if (result.isEmpty()) {
				// agrego un elemento vacio para conservar el lugar
				asConstructorInvocationStatement.getArguments().add(
						new ASEmptyElement());
				this.context.addError(new ASTranslatorException(
						"Argument not translatable "
								+ argument.getClass().getSimpleName()
								+ ". The element was ignored", argument,
						ASTranslatorExceptionLevel.INFO));
			} else {
				ASNamedElement asArgument = (ASNamedElement) result.get(0);
				asConstructorInvocationStatement.getArguments().add(asArgument);
			}

		}

		resultElements.add(asConstructorInvocationStatement);
		return resultElements;
	}

}
