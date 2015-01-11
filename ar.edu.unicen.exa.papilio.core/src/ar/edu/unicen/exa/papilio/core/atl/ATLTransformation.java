package ar.edu.unicen.exa.papilio.core.atl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;


public class ATLTransformation {

	
//	private static String JAVA_METAMODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/metamodel/java.ecore";
//	private static String PARAMETERS_METAMODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/metamodel/parameters.ecore";
//	private static String UML_METAMODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/metamodel/UML.ecore";
//	private static String JAVA_ELIBMIN_MODEL_PATH = "file:C:/Users/Rolandis/Documents/Bel/Tesis/tWorkspace/ar.edu.unicen.exa.hefesto.atl/sourcemodel/elibmin_java.java";
//    private static String UML_CLASSDIAGRAM_ELIBMIN_MODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/targetmodel/elibmin_class_uml.uml";
//    private static String JAVA_OBJECT_ELIBMIN_MODEL_PATH = "C:/Users/Rolandis/Documents/Bel/Tesis/tWorkspace/ar.edu.unicen.exa.hefesto.atl/sourcemodel/object_elibmin_java.java";
//    private static String INPUT_PARAMETERS_MODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/sourcemodel/sequenceDiagramInputParameters.parameters";
//    private static String UML_SEQUENCEDIAGRAM_ELIBMIN_MODEL_PATH = "../ar.edu.unicen.exa.hefesto.atl/targetmodel/elibmin_sequence_uml.uml";
//	private static String ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH = "../ar.edu.unicen.exa.hefesto.atl/transformation/Java2ClassDiagram.asm";
//	private static String ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH = "../ar.edu.unicen.exa.hefesto.atl/transformation/Java2SequenceDiagram.asm";
//	
//	public IModel runJava2ClassDiagramTransformation(Resource inputModelResource) {
//		
//		try {
//		
//		/*
//		 * Initializations
//		 */
//		ILauncher transformationLauncher = new EMFVMLauncher();
//		ModelFactory modelFactory = new EMFModelFactory();
//		EMFInjector injector = new EMFInjector();
//
//		/*
//		 * Load metamodels
//		 */
//		IReferenceModel javaMetamodel = modelFactory.newReferenceModel();
//		injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
//		IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
//		injector.inject(umlMetamodel, UML_METAMODEL_PATH);
//	
//		/*
//		 * Load models
//		 */
//		IModel inJavaModel = modelFactory.newModel(javaMetamodel);
//		
//		// serialize in memory (because ATL doesn't take a Resource
//		// directly)
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		inputModelResource.save(byteArrayOutputStream, null);
//		ByteArrayInputStream inputStream = new ByteArrayInputStream(
//				byteArrayOutputStream.toByteArray());
//		
//		injector.inject(inJavaModel, inputStream, Collections.<String, Object> emptyMap());
//		
//		IModel outUmlModel = modelFactory.newModel(umlMetamodel);
//				
//		/*
//		 * Run "Java2ClassDiagram" transformation
//		 */
//		transformationLauncher.initialize(new HashMap<String,Object>());
//		transformationLauncher.addInModel(inJavaModel, "IN", "java");
//		transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
//		transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String,Object>(),
//			new FileInputStream(ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH));
//		
//		return outUmlModel;
//		
//	} catch (ATLCoreException e) {
//		e.printStackTrace();
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	 } catch (IOException e) {
//		e.printStackTrace();
//	}
//	
//	return null;
//}
//	
//	
//	public IModel runJava2SequenceDiagramTransformation(Resource inputModelResource, String inParamsModelPath) {
//		
//		try {
//		
//		/*
//		 * Initializations
//		 */
//		ILauncher transformationLauncher = new EMFVMLauncher();
//		ModelFactory modelFactory = new EMFModelFactory();
//		EMFInjector injector = new EMFInjector();
//		
//
//		/*
//		 * Load metamodels
//		 */
//		IReferenceModel javaMetamodel = modelFactory.newReferenceModel();
//		injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
//		IReferenceModel parametersMetamodel = modelFactory.newReferenceModel();
//		injector.inject(parametersMetamodel, PARAMETERS_METAMODEL_PATH);
//		IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
//		injector.inject(umlMetamodel, UML_METAMODEL_PATH);
//		
//		/*
//		 * Load models
//		 */
//		IModel inJavaModel = modelFactory.newModel(javaMetamodel);
//		
//		// serialize in memory (because ATL doesn't take a Resource
//		// directly)
//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		inputModelResource.save(byteArrayOutputStream, null);
//		ByteArrayInputStream inputStream = new ByteArrayInputStream(
//				byteArrayOutputStream.toByteArray());
//		
//		injector.inject(inJavaModel, inputStream, Collections.<String, Object> emptyMap());
//		
//		IModel inParamsModel = modelFactory.newModel(parametersMetamodel);
//		injector.inject(inParamsModel, inParamsModelPath);
//		
//		IModel outUmlModel = modelFactory.newModel(umlMetamodel);
//		
//		/*
//		 * Run "Java2SequenceDiagram" transformation
//		 */
//				transformationLauncher.initialize(new HashMap<String,Object>());
//		transformationLauncher.addInModel(inJavaModel, "IN", "java");
//		transformationLauncher.addInModel(inParamsModel, "inputParams", "parameters");
//		transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
//		transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String,Object>(),
//			new FileInputStream(ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH));
//		
//		return outUmlModel;
//		
//	} catch (ATLCoreException e) {
//		e.printStackTrace();
//	} catch (FileNotFoundException e) {
//		e.printStackTrace();
//	 } catch (IOException e) {
//		e.printStackTrace();
//	}
//		return null;
//	
//	}
//	
//	public void saveOutputModel(IModel outputModel, String outModelPath) {
//		
//		try {
//			
//		IExtractor extractor = new EMFExtractor();
//		extractor.extract(outputModel, outModelPath);
//		
//		} catch (ATLCoreException e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	public static void main (String args[]) {
//		
//		ATLTransformation atlTransformation = new ATLTransformation();
//		JavaModelDiscoverer discoverer = new JavaModelDiscoverer();
//		
//		Resource javaElibminModelResource = discoverer.discoverJavaModelResourceFromXMIUri(JAVA_ELIBMIN_MODEL_PATH);
//		IModel classDiagramModel = atlTransformation.runJava2ClassDiagramTransformation(javaElibminModelResource);
//		atlTransformation.saveOutputModel(classDiagramModel, UML_CLASSDIAGRAM_ELIBMIN_MODEL_PATH);
//		
//		Resource javaObjectElibminModelResource = discoverer.discoverJavaModelResourceFromXMIUri(JAVA_OBJECT_ELIBMIN_MODEL_PATH);
//		IModel sequenceDiagramModel = atlTransformation.runJava2SequenceDiagramTransformation(javaObjectElibminModelResource, INPUT_PARAMETERS_MODEL_PATH);
//		atlTransformation.saveOutputModel(sequenceDiagramModel, UML_SEQUENCEDIAGRAM_ELIBMIN_MODEL_PATH);
//	}
//	
//	public void runTransformation() {
//		try {
//			/*
//			 * Initializations
//			 */
//			ILauncher transformationLauncher = new EMFVMLauncher();
//			ModelFactory modelFactory = new EMFModelFactory();
//			IInjector injector = new EMFInjector();
//			IExtractor extractor = new EMFExtractor();
//			
//			/*
//			 * Load metamodels
//			 */
//			IReferenceModel companyMetamodel = modelFactory.newReferenceModel();
//			injector.inject(companyMetamodel, "Metamodels/Company.ecore");
//			IReferenceModel totalMetamodel = modelFactory.newReferenceModel();
//			injector.inject(totalMetamodel, "Metamodels/Total.ecore");
//			
//			/*
//			 * Run "Cut" transformation
//			 */
//			IModel companyModel = modelFactory.newModel(companyMetamodel);
//			injector.inject(companyModel,"Models/sampleCompany.xmi");
//			
//			transformationLauncher.initialize(new HashMap<String,Object>());
//			transformationLauncher.addInOutModel(companyModel, "IN", "Company");
//			IReferenceModel refiningTraceMetamodel = modelFactory.getBuiltInResource("RefiningTrace.ecore");
//			IModel refiningTraceModel = modelFactory.newModel(refiningTraceMetamodel);
//			transformationLauncher.addOutModel(refiningTraceModel, "refiningTrace", "RefiningTrace");
//			transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String,Object>(),
//				new FileInputStream("Transformations/Cut.asm"));
//			
//			IModel companyModel_Cut = companyModel;
//			extractor.extract(companyModel_Cut, "Models/Java/sampleCompany_Cut.xmi");
//			
//			/*
//			 * Run "ComputeTotal" transformation
//			 */
//			IModel companyModel_Total = modelFactory.newModel(totalMetamodel);
//			
//			transformationLauncher.initialize(new HashMap<String,Object>());
//			transformationLauncher.addInModel(companyModel_Cut, "IN", "Company");
//			transformationLauncher.addOutModel(companyModel_Total, "OUT", "Total");
//			transformationLauncher.launch(ILauncher.RUN_MODE, new NullProgressMonitor(), new HashMap<String,Object>(),
//				new FileInputStream("Transformations/ComputeTotal.asm"));
//			
//			extractor.extract(companyModel_Total, "Models/Java/sampleCompany_Total.xmi");
//			
//			/*
//			 * Unload all models and metamodels (EMF-specific)
//			 */
//			EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
//			emfModelFactory.unload((EMFModel) companyModel_Cut);
//			emfModelFactory.unload((EMFModel) companyModel_Total);
//			emfModelFactory.unload((EMFReferenceModel) companyMetamodel);
//			emfModelFactory.unload((EMFReferenceModel) totalMetamodel);
//			
//		} catch (ATLCoreException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

	public void transform() throws ATLCoreException {
		ILauncher transformationLauncher = new EMFVMLauncher();
		ModelFactory modelFactory = new EMFModelFactory();
		IInjector injector = new EMFInjector();
		IExtractor extractor = new EMFExtractor();

		IReferenceModel MMA = modelFactory.newReferenceModel();
		injector.inject(MMA, "...");
		IReferenceModel MMB = modelFactory.newReferenceModel();
		injector.inject(MMB, "...");

		IModel mA = modelFactory.newModel(MMA);
		injector.inject(mA, "path to transformation input file (xmi)");

		transformationLauncher.initialize(new HashMap<String,Object>());
		transformationLauncher.addInModel(MMA, "IN", "name of IN model in MMA2MMB.atl");
		transformationLauncher.addOutModel(MMB, "OUT", "name of OUT model in MMA2MMB.atl");
		try {
			transformationLauncher.launch(
			    ILauncher.RUN_MODE, 
			    new NullProgressMonitor(), 
			    new HashMap<String,Object>(),
			    new FileInputStream("path to MMA2MMB.asm file"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		extractor.extract(MMA, "name of output file for transformation");

		EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
		emfModelFactory.unload((EMFReferenceModel) MMA);
		emfModelFactory.unload((EMFReferenceModel) MMB);
	}
}
