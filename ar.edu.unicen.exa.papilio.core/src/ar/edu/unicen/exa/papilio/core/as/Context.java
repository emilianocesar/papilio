package ar.edu.unicen.exa.papilio.core.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.AbstractMethodDeclaration;
import org.eclipse.gmt.modisco.java.AbstractTypeDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.VariableDeclaration;

import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclarationProxy;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASNamedElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;

public class Context {

	private ASProgram program;
	private String packagePath = "";
	private List<AbstractTypeDeclaration> currentClassDeclaration = new ArrayList<AbstractTypeDeclaration>();
	// private String currentClassPath = "";
	private String classScope = "";
	private String currentScope = "";
	private CompilationUnit currentCompilationUnit;
	private List<ASNamedElement> returnElements = new ArrayList<ASNamedElement>();
	private Map<String, Integer> varDeclarationIds = new HashMap<String, Integer>();
	private Map<String, Integer> methodDeclarationIds = new HashMap<String, Integer>();

	public Context(ASProgram asProgram) {
		this.program = asProgram;
	}

	public void addPackageToPath(Package aPackage) {
		StringBuilder packagePath = new StringBuilder(this.packagePath);
		if (packagePath.length() > 0) {
			packagePath.append(".");
		}
		packagePath.append(aPackage.getName());
		this.packagePath = packagePath.toString();
	}

	public void removePackageFromPath(Package aPackage) {
		int lastDotIndex = this.packagePath.lastIndexOf(".");
		if (lastDotIndex != -1) {
			this.packagePath = this.packagePath.substring(0, lastDotIndex);
		} else {
			this.packagePath = "";
		}
	}

	/**
	 * Obtiene el id de variable para la declaracion pasada como parametro El id
	 * de variable esta dado por el scope actual mas el nombre de la variable Si
	 * ya existe una variable con ese mismo id, se agrega a la misma un sufijo
	 * entero incremental
	 * 
	 * @param declaration
	 *            La declaracion para la que se desea obtener el nombre completo
	 * @return El nombre completo para la variable
	 */
	public String getFullVarDeclarationName(VariableDeclaration declaration) {

		StringBuilder fullVariableName = new StringBuilder(
				this.getCurrentScope());
		if (fullVariableName.length() > 0) {
			fullVariableName.append(".");
		}
		fullVariableName.append(declaration.getName());

		Integer sufix;
		// verifico si ya existe una variable con ese id
		if (varDeclarationIds.containsKey(fullVariableName.toString())) {
			sufix = varDeclarationIds.get(fullVariableName.toString());
			sufix++;
			varDeclarationIds.put(fullVariableName.toString(), sufix);
			fullVariableName.append(sufix);
		} else {
			sufix = new Integer("0");
			varDeclarationIds.put(fullVariableName.toString(), sufix);
		}

		return fullVariableName.toString();
	}

	public String getFullMethodDeclarationName(
			AbstractMethodDeclaration declaration) {

		// el nombre completo del method esta dado por el currentscope, leerlo
		// de ah?

		StringBuilder fullMethodName = new StringBuilder(this.getCurrentScope());
		Integer sufix;
		// verifico si ya existe un metodo con ese id
		if (methodDeclarationIds.containsKey(this.getCurrentScope())) {
			sufix = methodDeclarationIds.get(this.getCurrentScope());
			sufix++;
			methodDeclarationIds.put(this.getCurrentScope(), sufix);
			fullMethodName.append(sufix);
		} else {
			sufix = new Integer("0");
			methodDeclarationIds.put(this.getCurrentScope(), sufix);
		}

		return fullMethodName.toString();
	}

	public void setCurrentClassDeclaration(
			AbstractTypeDeclaration classDeclaration) {
		this.currentClassDeclaration.add(classDeclaration);
	}

	public void addToClassScope(NamedElement scopeElement) {
		this.classScope = this.addToScope(this.classScope, scopeElement);
	}

	public void addToCurrentScope(NamedElement scopeElement) {
		this.currentScope = this.addToScope(this.currentScope, scopeElement);
	}

	public void removeFromClassScope(NamedElement scopeElement) {
		this.classScope = this.removeFromScope(this.classScope, scopeElement);
	}

	public void removeFromCurrentScope(NamedElement scopeElement) {
		this.currentScope = this.removeFromScope(this.currentScope,
				scopeElement);
	}

	public void removeCurrentClassDeclaration() {
		int lastIndex = currentClassDeclaration.size() - 1;
		currentClassDeclaration.remove(lastIndex);
	}

	private String addToScope(String scope, NamedElement scopeElement) {
		StringBuilder scopeBuilder = new StringBuilder(scope);
		if (scope.length() > 0) {
			scopeBuilder.append(".");
		}
		scopeBuilder.append(scopeElement.getName());
		return scopeBuilder.toString();
	}

	private String removeFromScope(String scope, NamedElement scopeElement) {
		int lastDotIndex = scope.lastIndexOf(".");
		if (lastDotIndex != -1) {
			return scope.substring(0, lastDotIndex);
		} else {
			return "";
		}
	}

	public String getCurrentScope() {
		return this.currentScope;
	}

	public String getClassScope() {
		return this.classScope;
	}

	public String getFullPackageName() {
		return this.packagePath;
	}

	// public String getFullClassName() {
	// return this.currentClassPath;
	// }

	public void setCurrentCompilationUnit(CompilationUnit compilationUnit) {
		this.currentCompilationUnit = compilationUnit;
	}

	public CompilationUnit getCurrentCompilationUnit() {
		return this.currentCompilationUnit;
	}

	public AbstractTypeDeclaration getCurrentClassDeclaration() {
		int lastIndex = currentClassDeclaration.size() - 1;
		return currentClassDeclaration.get(lastIndex);
	}

	public List<ASNamedElement> getReturnElements() {
		return returnElements;
	}

	public void clearReturnElements() {
		returnElements = new ArrayList<ASNamedElement>();
	}

	public void addError(ASTranslatorException asTranslatorException) {
		this.program.getErrors().add(asTranslatorException);
	}

	public ASDeclaration getDeclaration(ASTNode node) {
		return this.program.getDeclaration(node);
	}

	public ASDeclarationProxy addUndeclaredMethod(ASTNode method) {
		return this.program.addUndeclaredMethod(method);
	}

	public ASDeclaration getDeclaration(String string) {
		return this.program.getDeclaration(string);
	}

	public void addElements(List<ASElement> result) {
		for (ASElement asElement : result) {
			this.program.addElement(asElement);
		}
	}

	public void addElement(ASElement element) {
		this.program.addElement(element);
	}

	public ASDeclarationProxy addUndeclaredConstructor(ASTNode node) {
		return this.program.addUndeclaredConstructor(node);
	}

	public ASDeclarationProxy addUndeclaredVariable(
			ASTNode node) {
		return this.program.addUndeclaredVariable(node);
	}

	public ASProgram getProgram() {
		return this.program;
	}

}
