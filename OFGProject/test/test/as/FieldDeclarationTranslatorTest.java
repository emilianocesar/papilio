package test.as;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Assert;
import org.easymock.EasyMock;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.ClassDeclaration;
import org.eclipse.gmt.modisco.java.FieldDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.gmt.modisco.java.VariableDeclarationFragment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.as.ASGenerator;
import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.Context;
import ar.edu.unicen.exa.papilio.core.as.element.ASAssignmentStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASConstructorInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.element.ASMethodInvocationStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASStatement;
import ar.edu.unicen.exa.papilio.core.as.element.ASVariableAccess;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.as.translate.AbstractSyntaxTranslator;
import ar.edu.unicen.exa.papilio.core.as.translate.AbstractTranslator;
import ar.edu.unicen.exa.papilio.core.as.translate.FieldDeclarationTranslator;
import ar.edu.unicen.exa.papilio.core.as.translate.VariableDeclarationTranslator;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class FieldDeclarationTranslatorTest {
	
	private ASGenerator asGenerator = new ASGenerator();
	
	/**
	 * Defino una regla para testear excepciones
	 */
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void setUp() {
	
		ASProgram.INSTANCE.removeAllDeclarations();
		ASProgram.INSTANCE.removeAllStatements();
		ASProgram.INSTANCE.removeAllUndeclared();
	}
	
	private Model loadModel(String path) {
		URI baseUri = URI.createURI(Constants.BASE_URI); 
		URI uri = URI.createFileURI(path).resolve(baseUri);
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		return discoverer.loadJavaModel(uri.toString());
	}
	
	/**
	 * Testea el caso de traduccion de una declaracion de atributo que no posee initializaer
	 * @throws ASTranslatorException
	 */
	@Test
	public void translateFieldDeclarationWithNoInitializerSingleFragmentTest() throws ASTranslatorException {

		FieldDeclaration fieldDeclaration = createMock(FieldDeclaration.class);
		VariableDeclarationFragment fragment = createMock(VariableDeclarationFragment.class);
		Context context = createMock(Context.class);
		
		final VariableDeclarationTranslator variableDeclarationTranslatorMock = EasyMock.createMock(VariableDeclarationTranslator.class);
		ASAttributeDeclaration declaration = new ASAttributeDeclaration();
		List<ASElement> variableElements = new ArrayList<ASElement>();
		variableElements.add(declaration);
		expect(variableDeclarationTranslatorMock.translate(fragment)).andReturn(variableElements);
		
		//class under test
		AbstractTranslator translator = new FieldDeclarationTranslator() {
			
			public AbstractSyntaxTranslator getTranslatorForNode(
					ASTNode node) {
					return variableDeclarationTranslatorMock;
			}
		};
		expect(fragment.getInitializer()).andReturn(null);

		EList<VariableDeclarationFragment> fragments = new BasicEList<VariableDeclarationFragment>();
		fragments.add(fragment);
		expect(fieldDeclaration.getFragments()).andReturn(fragments).anyTimes();
		
		replay(variableDeclarationTranslatorMock);
		replay(fieldDeclaration);
		replay(fragment);
		replay(context);
				
		translator.setContext(context);
		List<ASElement> result = translator.translate(fieldDeclaration);
		
		verify(variableDeclarationTranslatorMock);
		verify(fieldDeclaration);
		verify(fragment);
		verify(context);
		
		assertTrue(result.size() == 1);
		ASElement element = result.get(0);
		assertTrue(element instanceof ASAttributeDeclaration);		
	}

	@Test
	/**
	 * Testea el caso de traduccion de un FieldDeclaration con initializer
	 * de tipo ClassInstanceCreation 
	 * @throws ASTranslatorException
	 */
	public void translateFieldDeclarationWithClassInstanceCreationInitializerTest()
			throws ASTranslatorException {
		
		//ejemplo a testear: package.class.variable = new Clase()
		
		FieldDeclaration fieldDeclaration = createMock(FieldDeclaration.class);
		VariableDeclarationFragment fragment = createMock(VariableDeclarationFragment.class);
		Context context = createMock(Context.class);
		
		final VariableDeclarationTranslator variableDeclarationTranslatorMock = EasyMock.createMock(VariableDeclarationTranslator.class);
		ASAttributeDeclaration declaration = new ASAttributeDeclaration();
		List<ASElement> variableElements = new ArrayList<ASElement>();
		variableElements.add(declaration);
		expect(variableDeclarationTranslatorMock.translate(fragment)).andReturn(variableElements);
		
		//class under test
		AbstractTranslator translator = new FieldDeclarationTranslator() {
			
			public AbstractSyntaxTranslator getTranslatorForNode(
					ASTNode node) {
					return variableDeclarationTranslatorMock;
			}
		};
		
		expect(fragment.getInitializer()).andReturn(null);

		EList<VariableDeclarationFragment> fragments = new BasicEList<VariableDeclarationFragment>();
		fragments.add(fragment);
		expect(fieldDeclaration.getFragments()).andReturn(fragments).anyTimes();
		
		replay(variableDeclarationTranslatorMock);
		replay(fieldDeclaration);
		replay(fragment);
		replay(context);
				
		translator.setContext(context);
		List<ASElement> result = translator.translate(fieldDeclaration);
		
		verify(variableDeclarationTranslatorMock);
		verify(fieldDeclaration);
		verify(fragment);
		verify(context);
		
		assertTrue(result.size() == 1);
		ASElement element = result.get(0);
		assertTrue(element instanceof ASAttributeDeclaration);		
	}
	
	@Test
	public void translateFieldDeclarationWithNoInitializerTest() throws ASTranslatorException {
		
		//ejemplo a testear test.TesisTest.testField
		
		String path = Constants.FIELD_DECLARATION_NO_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Collection<ASDeclaration> resultDeclarations = ASProgram.INSTANCE.getDeclarations().values();
		Assert.assertTrue(resultDeclarations.size() == 1);
		
		ASElement element = resultDeclarations.iterator().next();
		assertTrue(element instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)element;
		String expectedName = "test.TesisTest.testField";
		String actualName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedName, actualName);
	}
	
	@Test
	public void translateFieldDeclarationWithClassInstanceCreationInitializerTest1() throws ASTranslatorException {
		
		//ejemplo a testear test.TesisTest.testField1 = new AnotherTestField()
		
		String path = Constants.FIELD_DECLARATION_CLASS_CREATION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		VariableDeclarationFragment testField = ((FieldDeclaration)aClass.getBodyDeclarations().get(0)).getFragments().get(0);
		
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(testField);
		assertTrue(declaration instanceof ASAttributeDeclaration);
		ASAttributeDeclaration asAttribute = (ASAttributeDeclaration)declaration;
		
		String expectedAttributeName = "test.TesisTest.testField1";
		String actualAttributeName = asAttribute.getFullyScopedName();
		Assert.assertEquals(expectedAttributeName, actualAttributeName);
		
		List<ASStatement>resultStatements = ASProgram.INSTANCE.getStatements();
		assertTrue(resultStatements.size() == 1);
		ASElement assignment = resultStatements.get(0);
		assertTrue(assignment instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)assignment;
		
		assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertNotNull(leftSide.getVariableDeclaration());
		Assert.assertEquals(expectedAttributeName, leftSide.getFullyScopedName());
		
		assertTrue(initializer.getRightSide() instanceof ASConstructorInvocationStatement);
		ASConstructorInvocationStatement rightSide = (ASConstructorInvocationStatement)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getConstructorDeclaration());
		String expectedLeftSide = "test.AnotherTestClass.AnotherTestClass.this";
		Assert.assertEquals(expectedLeftSide, rightSide.getFullyScopedName());

	}

	@Test
	public void translateFieldDeclarationWithMultipleFragments(){
		
		String path = Constants.FIELD_DECLARATION_MULTIPLE_FRAGMENTS_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//verifico que no se realiza la traduccion del atributo
		assertTrue(ASProgram.INSTANCE.getDeclarations().isEmpty());
		assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
		
	}
	
	@Test
	public void translateFieldDeclarationWithMethodInvocationInitializerTest()
			throws ASTranslatorException {

		//ejemplo a testear: test.TesisTest.testField1 = test.TesisTest.this.initializer()
		
		String path = Constants.FIELD_DECLARATION_METHOD_INVOCATION_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//obtengo el nodo para acceder a la declaracion
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		VariableDeclarationFragment testField = ((FieldDeclaration)aClass.getBodyDeclarations().get(0)).getFragments().get(0);
		
		//obtengo la declaracion desde el asProgram
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(testField);
		
		assertTrue(declaration instanceof ASAttributeDeclaration);
		String expectedDeclaration = "test.TesisTest.testField1";
		String actualDeclaration = declaration.getFullyScopedName();
		Assert.assertEquals(expectedDeclaration, actualDeclaration);
		
		//obtengo el statement para el initializer
		ASStatement assignment = ASProgram.INSTANCE.getStatements().get(0);
		
		assertTrue(assignment instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)assignment;

		//obtengo el lado izquierdo del initializer
		assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertEquals(expectedDeclaration, leftSide.getFullyScopedName());

		//obtengo el lado derecho del initializer
		assertTrue(initializer.getRightSide() instanceof ASMethodInvocationStatement);
		ASMethodInvocationStatement rightSide = (ASMethodInvocationStatement)initializer.getRightSide();
		assertTrue(rightSide.getTarget() instanceof ASAttributeDeclaration);
		String expectedTargetName = "test.TesisTest.this";
		String actualTargetName = rightSide.getTarget().getFullyScopedName();
		Assert.assertEquals(expectedTargetName, actualTargetName);
		Assert.assertNotNull(rightSide.getDeclaration());
		Assert.assertTrue(rightSide.getArguments().isEmpty());
	}

	@Test
	public void translateFieldDeclarationWithSingleVariableAccessInitializerTest()
			throws ASTranslatorException {

		//ejemplo a testear: test.TesisTest.testField1 = test.TesisTest.VAR_ACCESS_INITIALIZER
		
		String path = Constants.FIELD_DECLARATION_VAR_ACCESS_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		Package aPackage = (Package)javaModel.getOwnedElements().get(0);
		ClassDeclaration aClass = (ClassDeclaration)aPackage.getOwnedElements().get(0);
		VariableDeclarationFragment testField = ((FieldDeclaration)aClass.getBodyDeclarations().get(1)).getFragments().get(0);
		
		//obtengo la declaracion
		ASDeclaration declaration = ASProgram.INSTANCE.getDeclaration(testField);
		
		assertTrue(declaration instanceof ASAttributeDeclaration);
		String expectedDeclaration = "test.TesisTest.testField1";
		String actualDeclaration = declaration.getFullyScopedName();
		Assert.assertEquals(expectedDeclaration, actualDeclaration);
		
		//obtengo el statement para el initializer
		ASStatement assignment = ASProgram.INSTANCE.getStatements().get(0);
		
		assertTrue(assignment instanceof ASAssignmentStatement);
		ASAssignmentStatement initializer = (ASAssignmentStatement)assignment;

		//obtengo el lado izquierdo del initializer
		assertTrue(initializer.getLeftSide() instanceof ASVariableAccess);		
		ASVariableAccess leftSide = (ASVariableAccess)initializer.getLeftSide();
		Assert.assertEquals(expectedDeclaration, leftSide.getFullyScopedName());

		//obtengo el lado derecho del initializer
		assertTrue(initializer.getRightSide() instanceof ASVariableAccess);
		ASVariableAccess rightSide = (ASVariableAccess)initializer.getRightSide();
		Assert.assertNotNull(rightSide.getVariableDeclaration());
		String expectedRightSide = "test.TesisTest.VAR_ACCESS_INITIALIZER";
		String actualRightSide = rightSide.getFullyScopedName();
		Assert.assertEquals(expectedRightSide, actualRightSide);
	}

	@Test
	public void translateFieldDeclarationWithNonTranslatableInitializerTest()
			throws ASTranslatorException {

		//ejemplo a testear: test.TestClass.testField = null
		
		String path = Constants.FIELD_DECLARATION_NULL_INITIALIZER_MODEL_PATH;
		Model javaModel = this.loadModel(path);
		asGenerator.iterate(javaModel);
		
		//verifico que se tradujo la declaracion pero no el initializer
		assertTrue(ASProgram.INSTANCE.getDeclarations().size() == 1);
		assertTrue(ASProgram.INSTANCE.getStatements().isEmpty());
	}
	
}
