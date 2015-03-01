package ar.edu.unicen.exa.papilio.core.atl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFExtractor;
import org.eclipse.m2m.atl.core.emf.EMFInjector;
import org.eclipse.m2m.atl.core.emf.EMFModel;
import org.eclipse.m2m.atl.core.emf.EMFModelFactory;
import org.eclipse.m2m.atl.core.emf.EMFReferenceModel;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.engine.emfvm.launch.EMFVMLauncher;

import ar.edu.unicen.exa.papilio.core.diagram.PapilioDiagram;

public class ATLTransformation {

	private static String JAVA_METAMODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/metamodel/java.ecore";
	private static String PARAMETERS_METAMODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/metamodel/parameters.ecore";
	private static String UML_METAMODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/metamodel/UML.ecore";

	private static String INPUT_PARAMETERS_MODEL_PATH = "../ar.edu.unicen.exa.papilio.atl/sourcemodel/sequenceDiagramInputParameters.parameters";

	private static String ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH = "../ar.edu.unicen.exa.papilio.atl/transformation/Java2ClassDiagram.asm";
	private static String ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH = "../ar.edu.unicen.exa.papilio.atl/transformation/Java2SequenceDiagram.asm";

	public IModel runJava2ClassDiagramTransformation(Resource inputModelResource) {

		try {

			/*
			 * Initializations
			 */
			ILauncher transformationLauncher = new EMFVMLauncher();
			ModelFactory modelFactory = new EMFModelFactory();
			EMFInjector injector = new EMFInjector();

			/*
			 * Load metamodels
			 */
			IReferenceModel javaMetamodel = modelFactory.newReferenceModel();
			injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
			IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
			injector.inject(umlMetamodel, UML_METAMODEL_PATH);

			/*
			 * Load models
			 */
			IModel inJavaModel = modelFactory.newModel(javaMetamodel);

			// serialize in memory (because ATL doesn't take a Resource
			// directly)
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			inputModelResource.save(byteArrayOutputStream, null);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					byteArrayOutputStream.toByteArray());

			injector.inject(inJavaModel, inputStream,
					Collections.<String, Object> emptyMap());

			IModel outUmlModel = modelFactory.newModel(umlMetamodel);

			/*
			 * Run "Java2ClassDiagram" transformation
			 */
			transformationLauncher.initialize(new HashMap<String, Object>());
			transformationLauncher.addInModel(inJavaModel, "IN", "java");
			transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
			transformationLauncher.launch(ILauncher.RUN_MODE,
					new NullProgressMonitor(), new HashMap<String, Object>(),
					new FileInputStream(
							ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH));

			return outUmlModel;

		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public IModel runJava2SequenceDiagramTransformation(
			Resource inputModelResource, String inParamsModelPath) {

		try {

			/*
			 * Initializations
			 */
			ILauncher transformationLauncher = new EMFVMLauncher();
			ModelFactory modelFactory = new EMFModelFactory();
			EMFInjector injector = new EMFInjector();

			/*
			 * Load metamodels
			 */
			IReferenceModel javaMetamodel = modelFactory.newReferenceModel();
			injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
			IReferenceModel parametersMetamodel = modelFactory
					.newReferenceModel();
			injector.inject(parametersMetamodel, PARAMETERS_METAMODEL_PATH);
			IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
			injector.inject(umlMetamodel, UML_METAMODEL_PATH);

			/*
			 * Load models
			 */
			IModel inJavaModel = modelFactory.newModel(javaMetamodel);

			// serialize in memory (because ATL doesn't take a Resource
			// directly)
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			inputModelResource.save(byteArrayOutputStream, null);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					byteArrayOutputStream.toByteArray());

			injector.inject(inJavaModel, inputStream,
					Collections.<String, Object> emptyMap());

			IModel inParamsModel = modelFactory.newModel(parametersMetamodel);
			injector.inject(inParamsModel, inParamsModelPath);

			IModel outUmlModel = modelFactory.newModel(umlMetamodel);

			/*
			 * Run "Java2SequenceDiagram" transformation
			 */
			transformationLauncher.initialize(new HashMap<String, Object>());
			transformationLauncher.addInModel(inJavaModel, "IN", "java");
			transformationLauncher.addInModel(inParamsModel, "inputParams",
					"parameters");
			transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
			transformationLauncher.launch(ILauncher.RUN_MODE,
					new NullProgressMonitor(), new HashMap<String, Object>(),
					new FileInputStream(
							ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH));

			return outUmlModel;

		} catch (ATLCoreException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	public void saveOutputModel(IModel outputModel, String outModelPath) {

		try {

			IExtractor extractor = new EMFExtractor();
			extractor.extract(outputModel, outModelPath);

		} catch (ATLCoreException e) {
			e.printStackTrace();
		}
	}
//
//	public static void main(String args[]) {
//
//		ATLTransformation atlTransformation = new ATLTransformation();
//		JavaModelDiscoverer discoverer = new JavaModelDiscoverer();
//
//		Resource javaElibminModelResource = discoverer
//				.discoverJavaModelResourceFromXMIUri(JAVA_ELIBMIN_MODEL_PATH);
//		IModel classDiagramModel = atlTransformation
//				.runJava2ClassDiagramTransformation(javaElibminModelResource);
//		atlTransformation.saveOutputModel(classDiagramModel,
//				UML_CLASSDIAGRAM_ELIBMIN_MODEL_PATH);
//
//		Resource javaObjectElibminModelResource = discoverer
//				.discoverJavaModelResourceFromXMIUri(JAVA_OBJECT_ELIBMIN_MODEL_PATH);
//		IModel sequenceDiagramModel = atlTransformation
//				.runJava2SequenceDiagramTransformation(
//						javaObjectElibminModelResource,
//						INPUT_PARAMETERS_MODEL_PATH);
//		atlTransformation.saveOutputModel(sequenceDiagramModel,
//				UML_SEQUENCEDIAGRAM_ELIBMIN_MODEL_PATH);
//	}

	public void executeTransformation(String workingModelPath,
			PapilioDiagram diagram, IProgressMonitor progressMonitor) {

		ILauncher transformationLauncher = new EMFVMLauncher();
		ModelFactory modelFactory = new EMFModelFactory();
		IInjector injector = new EMFInjector();
		IExtractor extractor = new EMFExtractor();
		try {

			if (diagram.equals(PapilioDiagram.CLASS)) {

				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				URL resourceURL = this.getClass().getResource("java.ecore");
				URL some = FileLocator.toFileURL(resourceURL);
				InputStream in = some.openStream();

				IReferenceModel javaMetamodel = modelFactory
						.newReferenceModel();
				injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
				IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
				injector.inject(umlMetamodel, UML_METAMODEL_PATH);

				IModel inJavaModel = modelFactory.newModel(javaMetamodel);
				IModel outUmlModel = modelFactory.newModel(umlMetamodel);

				injector.inject(inJavaModel, workingModelPath);

				transformationLauncher.initialize(new HashMap<String, Object>());
				transformationLauncher.addInModel(inJavaModel, "IN", "java");
				transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
				transformationLauncher.launch(ILauncher.RUN_MODE,progressMonitor, new HashMap<String, Object>(),
						new FileInputStream(
								ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH));
				
				extractor.extract(outUmlModel,
						"uml.xmi");

				/*
				 * Unload all models and metamodels (EMF-specific)
				 */
				EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
				emfModelFactory.unload((EMFModel) inJavaModel);
				emfModelFactory.unload((EMFModel) outUmlModel);
				emfModelFactory.unload((EMFReferenceModel) javaMetamodel);
				emfModelFactory.unload((EMFReferenceModel) umlMetamodel);
			} else if (diagram.equals(PapilioDiagram.SEQUENCE)) {

				IReferenceModel javaMetamodel = modelFactory
						.newReferenceModel();
				injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
				IReferenceModel parametersMetamodel = modelFactory
						.newReferenceModel();
				injector.inject(parametersMetamodel, PARAMETERS_METAMODEL_PATH);
				IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
				injector.inject(umlMetamodel, UML_METAMODEL_PATH);

				
				IModel javaInModel = modelFactory.newModel(javaMetamodel);
				IModel outUmlModel = modelFactory.newModel(umlMetamodel);

				injector.inject(javaInModel, workingModelPath);

				IModel inParamsModel = modelFactory.newModel(parametersMetamodel);
				injector.inject(inParamsModel, INPUT_PARAMETERS_MODEL_PATH);
				
				transformationLauncher
						.initialize(new HashMap<String, Object>());
				transformationLauncher.addInModel(javaInModel, "IN", "java");
				transformationLauncher.addInModel(inParamsModel,
						"inputParams", "parameters");
				transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
				transformationLauncher.launch(ILauncher.RUN_MODE,
						progressMonitor, new HashMap<String, Object>(),
						new FileInputStream(
								ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH));

				extractor.extract(outUmlModel,
						"Models/Java/sampleCompany_Total.xmi");

				/*
				 * Unload all models and metamodels (EMF-specific)
				 */
				EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
				emfModelFactory.unload((EMFModel) javaInModel);
				emfModelFactory.unload((EMFModel) parametersMetamodel);
				emfModelFactory.unload((EMFModel) outUmlModel);
				emfModelFactory.unload((EMFReferenceModel) javaMetamodel);
				emfModelFactory.unload((EMFReferenceModel) umlMetamodel);
			} else if (diagram.equals(PapilioDiagram.USECASES)) {

			}

		} catch (ATLCoreException e) {
			e.printStackTrace();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String serializeModel(Model model, IJavaProject javaProject) {
		IProject project = javaProject.getProject();
		IPath projectPath = project.getFullPath();
		String modelName = model.getName() + "_object.xmi";
		projectPath = projectPath.append("javamodel");
		projectPath = projectPath.append(modelName);

		URI fileURI = URI.createURI(projectPath.toString());

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
		Resource javamodelResource = resourceSet.createResource(fileURI);

		EList<EObject> contents = javamodelResource.getContents();
		contents.add(model);
		try {
			javamodelResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath.toString();
	}
}
