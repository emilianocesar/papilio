package ar.edu.unicen.exa.papilio.core.atl;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class ATLResources {

//	private static String PARAMETERS_METAMODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/metamodel/parameters.ecore";

//	private static String INPUT_PARAMETERS_MODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/sourcemodel/sequenceDiagramInputParameters.parameters";

//	private static String ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH = "../ar.edu.unicen.exa.papilio.atl/transformation/Java2SequenceDiagram.asm";
	
	private Bundle bundle;

	public ATLResources() {
		bundle = Platform.getBundle("ar.edu.unicen.exa.papilio.atl"); //$NON-NLS-1$
	}

	public String getUMLMetamodelPath() {
		return bundle.getEntry("metamodel/UML.ecore").toString();
	}

	public String getJavaMetamodelPath() {
		return bundle.getEntry("metamodel/java.ecore").toString();
	}

	public URL getClassDiagramTransformationURL() {
		return bundle.getEntry("transformation/Java2ClassDiagram.asm");
	}

	public URL getSequenceDiagramTransformationURL() {
		return bundle.getEntry("transformation/Java2SequenceDiagram.asm");
	}

	public URL getUseCaseDiagramTransformationURL() {
		return bundle.getEntry("transformation/Java2UseCaseDiagram.asm");
	}

	public String getParametersMetamodelPath() {
		return bundle.getEntry("metamodel/parameters.ecore").toString();
	}

}
