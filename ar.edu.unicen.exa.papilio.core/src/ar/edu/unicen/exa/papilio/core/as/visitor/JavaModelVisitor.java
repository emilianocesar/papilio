package ar.edu.unicen.exa.papilio.core.as.visitor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Assignment;
import org.eclipse.gmt.modisco.java.CatchClause;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.CompilationUnit;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.EnhancedForStatement;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.NamedElement;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.ReturnStatement;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.VariableDeclarationExpression;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;

import ar.edu.unicen.exa.papilio.core.as.Context;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodDeclaration;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.translate.AbstractSyntaxTranslator;
import ar.edu.unicen.exa.papilio.core.as.translate.TranslatorFactory;

public class JavaModelVisitor {

	private Context context;

	private List<ASTranslatorException> errors = new ArrayList<ASTranslatorException>();

	public JavaModelVisitor(Context context) {
		this.context = context;
	}
	public Context getContext() {
		return context;
	}

	public List<ASTranslatorException> getErrors() {
		return errors;
	}

	public void translateNode(ASTNode node, Context context) {

		List<ASElement> result = null;
		AbstractSyntaxTranslator translator;
		translator = TranslatorFactory.INSTANCE.getTranslator(node, context);
		translator.setContext(context);
		result = translator.translate(node);
		for (ASElement asElement : result) {
			context.addElement(asElement);
		}
	}

	public boolean isProxy(ASTNode node) {
		if (node instanceof NamedElement) {
			return ((NamedElement) node).isProxy();
		}
		return false;
	}

	public boolean visit(Model node) {
		return true;
	}

	public boolean visit(Package node) {
		return true;
	}

	public boolean visit(ClassDeclaration node) {
		return true;
	}
	
	public boolean visit(InterfaceDeclaration node) {
		return true;
	}
	
	public boolean visit(EnumDeclaration node) {
		return true;
	}
	
	public boolean visit(CatchClause node) {
		return false;
	}

	public boolean visit(EnumConstantDeclaration node) {
		translateNode(node, context);
		return false;
	}
	
	public boolean visit(FieldDeclaration node) {

		translateNode(node, context);
		return false;
	}
	
	public boolean visit(VariableDeclarationExpression node) {

		translateNode(node, context);
		return false;
	}
	
	
	public boolean visit(SingleVariableDeclaration node) {
		translateNode(node, context);
		return false;
	}

	public boolean visit(MethodDeclaration node) {

		translateNode(node, context);
		// visito el cuerpo del metodo solo si el metodo no es proxy
		Boolean visitChildren = !node.isProxy();
		return visitChildren;
	}
	
	public boolean visit(ReturnStatement node) {
		translateNode(node, context);
		return false;
	}

	public boolean visit(ConstructorDeclaration node) {
		translateNode(node, context);
		return true;
	}

	public boolean visit(VariableDeclarationStatement node) {
		translateNode(node, context);
		return false;
	}

	public boolean visit(Assignment node) {
		translateNode(node, context);
		return false;
	}

	public boolean visit(MethodInvocation node) {
		translateNode(node, context);
		return false;
	}

	public boolean visit(EnhancedForStatement node) {
		translateNode(node, context);
		return true;
	}

	public boolean defaultVisit(ASTNode node) {
		return true;
	}

	public void beforeVisit(Model node) {

	}

	public void beforeVisit(Package node) {
		context.addPackageToPath(node);
		context.addToClassScope(node);
		context.addToCurrentScope(node);
	}

	public void beforeVisit(ClassDeclaration node) {
		context.setCurrentClassDeclaration(node);
		context.addToClassScope(node);
		context.addToCurrentScope(node);
	}
	
	public void beforeVisit(InterfaceDeclaration node) {
		context.addToCurrentScope(node);
	}


	public void beforeVisit(FieldDeclaration node) {}
	
	public void beforeVisit(VariableDeclarationExpression node) {}
	
	public void beforeVisit(SingleVariableDeclaration node) {}
	
	public void beforeVisit(EnumConstantDeclaration node) {}

	public void beforeVisit(MethodDeclaration node) {
		context.addToCurrentScope(node);
	}

	public void beforeVisit(ConstructorDeclaration node) {
		context.addToCurrentScope(node);
	}
	
	public void beforeVisit(EnumDeclaration node) {
		context.addToCurrentScope(node);
		context.addToClassScope(node);
		context.setCurrentClassDeclaration(node);
	}

	public void beforeVisit(VariableDeclarationStatement node) {

	}

	public void beforeVisit(Assignment node) {

	}

	public void beforeVisit(MethodInvocation node) {

	}

	public void beforeVisit(EnhancedForStatement node) {

	}
	
	public void beforeVisit(CatchClause node) {

	}

	public void beforeVisit(CompilationUnit compilationUnit) {
		System.out.println(compilationUnit.getOriginalClassFile()
				.getAttachedSource().toString());
		context.setCurrentCompilationUnit(compilationUnit);
	}
	
	public void beforeVisit(ReturnStatement returnStatement) {}

	public void afterVisit(CompilationUnit compilationUnit) {
		context.setCurrentCompilationUnit(null);
	}

	public void afterVisit(Model node) {

	}

	public void afterVisit(Package node) {
		context.removePackageFromPath(node);
		context.removeFromClassScope(node);
		context.removeFromCurrentScope(node);
	}

	public void afterVisit(ClassDeclaration node) {
		context.removeCurrentClassDeclaration();
		context.removeFromClassScope(node);
		context.removeFromCurrentScope(node);
	}
	
	public void afterVisit(InterfaceDeclaration node) {
		context.removeFromCurrentScope(node);
	}

	public void afterVisit(FieldDeclaration node) {}
	
	public void afterVisit(VariableDeclarationExpression node) {}
	
	public void afterVisit(EnumConstantDeclaration node) {}
	
	public void afterVisit(SingleVariableDeclaration node) {}

	public void afterVisit(MethodDeclaration node) {
		
		//obtengo el ASElement generado para la declaracion de metodo
		ASMethodDeclaration declaration = (ASMethodDeclaration)context.getDeclaration(node);
		if (declaration != null && declaration.returnsObject()) {
			//agrego return elements
			declaration.getReturnObjects().addAll(context.getReturnElements());
		}
		
		context.removeFromCurrentScope(node);
		context.clearReturnElements();
	}
	
	public void afterVisit(ReturnStatement returnStatement) {}

	public void afterVisit(ConstructorDeclaration node) {
		context.removeFromCurrentScope(node);
	}
	
	public void afterVisit(EnumDeclaration node) {
		context.removeFromCurrentScope(node);
		context.removeFromClassScope(node);
		context.removeCurrentClassDeclaration();
	}

	public void afterVisit(VariableDeclarationStatement node) {}

	public void afterVisit(Assignment node) {}

	public void afterVisit(MethodInvocation node) {}

	public void afterVisit(EnhancedForStatement node) {}
	
	public void afterVisit(CatchClause node) {}

	public boolean visit(CompilationUnit node) {
		return false;
	}

}
