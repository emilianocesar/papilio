package test.model;

import junit.framework.TestCase;

import org.eclipse.gmt.modisco.java.Model;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import test.util.Constants;
import ar.edu.unicen.exa.papilio.core.model.JavaXMIModelLoader;

public class EMFModelLoadingTest extends TestCase {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Instancia el metamodelo en el archivo indicado por modelURI como un metamodelo Java
	 * El test resulta exitoso si la instancia obtenida es distinta de null,
	 * falla en caso contrario. 
	 */
	@Test
	public void testDiscoverModel() {
		
		JavaXMIModelLoader discoverer = new JavaXMIModelLoader();
		
		Model javaModel = discoverer.loadJavaModel(Constants.MODEL_URI);
		
		assertNotNull(javaModel);
				
	}

}
	