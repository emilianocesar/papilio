package ar.edu.unicen.exa.papilio.core.as.visitor.adapter;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Assignment;
import org.eclipse.gmt.modisco.java.CatchClause;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.ConstructorDeclaration;
import org.eclipse.gmt.modisco.java.EnhancedForStatement;
import org.eclipse.gmt.modisco.java.EnumConstantDeclaration;
import org.eclipse.gmt.modisco.java.EnumDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.InterfaceDeclaration;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.MethodInvocation;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.ReturnStatement;
import org.eclipse.gmt.modisco.java.SingleVariableDeclaration;
import org.eclipse.gmt.modisco.java.VariableDeclarationExpression;
import org.eclipse.gmt.modisco.java.VariableDeclarationStatement;



public enum ASTNodeAdapterFactory {

	INSTANCE;
	
	
	public ASTNodeAdapter adapt(Model model) {
		return new ModelAdapter(model);
	}
	
	public ASTNodeAdapter adapt(ASTNode node) {

		if (node instanceof Assignment) {
			return new AssignmentAdapter((Assignment)node);
		} else if (node instanceof ClassDeclaration) {
			return new ClassDeclarationAdapter((ClassDeclaration)node);
		} else if (node instanceof Package) {
			return new PackageAdapter((Package)node);
		} else if (node instanceof ConstructorDeclaration) {
			return new ConstructorDeclarationAdapter((ConstructorDeclaration)node);
		} else if (node instanceof EnhancedForStatement) {
			return new EnhancedForStatementAdapter((EnhancedForStatement)node);
		} else if (node instanceof FieldDeclaration) {
			return new FieldDeclarationAdapter((FieldDeclaration)node);
		} else if (node instanceof MethodDeclaration) {
			return new MethodDeclarationAdapter((MethodDeclaration)node);
		} else if (node instanceof MethodInvocation) {
			return new MethodInvocationAdapter((MethodInvocation)node);
		} else if (node instanceof VariableDeclarationStatement) {
			return new VariableDeclarationStatementAdapter((VariableDeclarationStatement)node);
		} else if (node instanceof EnumDeclaration) {
			return new EnumDeclarationAdapter((EnumDeclaration)node);
		} else if (node instanceof EnumConstantDeclaration) {
			return new EnumConstantDeclarationAdapter((EnumConstantDeclaration)node);
		} else if (node instanceof SingleVariableDeclaration) {
			return new SingleVariableDeclarationAdapter((SingleVariableDeclaration)node);
		} if(node instanceof InterfaceDeclaration) {
			return new InterfaceDeclarationAdapter((InterfaceDeclaration)node);
		} if (node instanceof VariableDeclarationExpression) {
			return new VariableDeclarationExpressionAdapter((VariableDeclarationExpression)node);
		} if (node instanceof CatchClause) {
			return new CatchClauseAdapter((CatchClause)node);
		} if (node instanceof ReturnStatement) {
			return new ReturnStatementAdapter((ReturnStatement)node);
		} else {
			return new DefaultAdapter(node);
		}
	}
}
