package ar.edu.unicen.exa.papilio.core.atl;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.m2m.atl.core.ATLCoreException;
import org.eclipse.m2m.atl.core.IExtractor;
import org.eclipse.m2m.atl.core.IInjector;
import org.eclipse.m2m.atl.core.IModel;
import org.eclipse.m2m.atl.core.IReferenceModel;
import org.eclipse.m2m.atl.core.ModelFactory;
import org.eclipse.m2m.atl.core.launch.ILauncher;
import org.eclipse.m2m.atl.core.service.CoreService;

import ar.edu.unicen.exa.papilio.core.diagram.PapilioDiagram;
import parameters.*;

public class ATLTransformation {


	private ATLResources atlResources;

	public ATLTransformation(ATLResources atlResources) {
		this.atlResources = atlResources;
	}

//	public IModel runJava2SequenceDiagramTransformation(
//			Resource inputModelResource, String inParamsModelPath) {
//
//		try {
//
//			/*
//			 * Initializations
//			 */
//			ILauncher transformationLauncher = new EMFVMLauncher();
//			ModelFactory modelFactory = new EMFModelFactory();
//			EMFInjector injector = new EMFInjector();
//
//			/*
//			 * Load metamodels
//			 */
//			IReferenceModel javaMetamodel = modelFactory.newReferenceModel();
//			injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
//			IReferenceModel parametersMetamodel = modelFactory
//					.newReferenceModel();
//			injector.inject(parametersMetamodel, PARAMETERS_METAMODEL_PATH);
//			IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
//			injector.inject(umlMetamodel, UML_METAMODEL_PATH);
//
//			/*
//			 * Load models
//			 */
//			IModel inJavaModel = modelFactory.newModel(javaMetamodel);
//
//			// serialize in memory (because ATL doesn't take a Resource
//			// directly)
//			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//			inputModelResource.save(byteArrayOutputStream, null);
//			ByteArrayInputStream inputStream = new ByteArrayInputStream(
//					byteArrayOutputStream.toByteArray());
//
//			injector.inject(inJavaModel, inputStream,
//					Collections.<String, Object> emptyMap());
//
//			IModel inParamsModel = modelFactory.newModel(parametersMetamodel);
//			injector.inject(inParamsModel, inParamsModelPath);
//
//			IModel outUmlModel = modelFactory.newModel(umlMetamodel);
//
//			/*
//			 * Run "Java2SequenceDiagram" transformation
//			 */
//			transformationLauncher.initialize(new HashMap<String, Object>());
//			transformationLauncher.addInModel(inJavaModel, "IN", "java");
//			transformationLauncher.addInModel(inParamsModel, "inputParams",
//					"parameters");
//			transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
//			transformationLauncher.launch(ILauncher.RUN_MODE,
//					new NullProgressMonitor(), new HashMap<String, Object>(),
//					new FileInputStream(
//							ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH));
//
//			return outUmlModel;
//
//		} catch (ATLCoreException e) {
//			e.printStackTrace();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//
//	}
//
	public void executeTransformation(PapilioDiagram diagram, IProgressMonitor progressMonitor, Model model, IJavaProject javaProject, MethodDeclaration selectedElement) throws ATLCoreException, IOException, CoreException {
		
		String workingModelPath = serializeModel(model, javaProject, diagram);
		IInjector injector = null;
		IExtractor extractor = null;
		try {
			injector = CoreService.getInjector("EMF"); //$NON-NLS-1$
			extractor = CoreService.getExtractor("EMF"); //$NON-NLS-1$			
		} catch (ATLCoreException e) {
			e.printStackTrace();
		}

		
		ModelFactory factory = CoreService.getModelFactory("EMF"); //$NON-NLS-1$

		// Metamodels
		IReferenceModel umlMetamodel = factory.newReferenceModel();
		IReferenceModel javaMetamodel = factory.newReferenceModel();
		
		injector.inject(umlMetamodel, atlResources.getUMLMetamodelPath());
		injector.inject(javaMetamodel, atlResources.getJavaMetamodelPath());
		
		// Getting launcher
		ILauncher launcher = null;
		launcher = CoreService.getLauncher("EMF-specific VM"); //$NON-NLS-1$
		launcher.initialize(Collections.<String, Object> emptyMap());

		// Creating models
		IModel javaModel = factory.newModel(javaMetamodel);
		IModel umlModel = factory.newModel(umlMetamodel);

		// Loading existing model
		injector.inject(javaModel, workingModelPath);

		URL transformationURL = null;
		if (diagram.equals(PapilioDiagram.CLASS)) {
			transformationURL = atlResources.getClassDiagramTransformationURL();
		} else if (diagram.equals(PapilioDiagram.SEQUENCE)) {
			assert selectedElement != null;
			transformationURL = atlResources.getSequenceDiagramTransformationURL();
			IReferenceModel parametersMetamodel = factory.newReferenceModel();
			injector.inject(parametersMetamodel, atlResources.getParametersMetamodelPath());
			IModel inParamsModel = factory.newModel(parametersMetamodel);
			String parametersPath = createModelFromParameters(selectedElement, javaProject);
			injector.inject(inParamsModel, parametersPath);
			launcher.addInModel(inParamsModel, "inputParams",
			"parameters");

		} else if (diagram.equals(PapilioDiagram.USECASES)) {
			transformationURL = atlResources.getUseCaseDiagramTransformationURL();
		}
		// Launching
		launcher.addOutModel(umlModel, "OUT", "UML"); //$NON-NLS-1$ //$NON-NLS-2$
		launcher.addInModel(javaModel, "IN", "java"); //$NON-NLS-1$ //$NON-NLS-2$

		launcher.launch(ILauncher.RUN_MODE, progressMonitor, Collections
				.<String, Object> emptyMap(), transformationURL.openStream());

		// Saving model
		IProject project = javaProject.getProject();
		IPath projectPath = project.getFullPath();
		String modelName = model.getName() + "_"+  diagram.getName() + ".uml";
		projectPath = projectPath.append("javamodel");
		projectPath = projectPath.append(modelName);

		extractor.extract(umlModel, projectPath.toString());

		// Refresh workspace
		project.refreshLocal(IProject.DEPTH_INFINITE, null);

	}
	
//	public void executeTTransformation(String workingModelPath,
//			PapilioDiagram diagram, IProgressMonitor progressMonitor) {
//
//		ILauncher transformationLauncher = new EMFVMLauncher();
//		ModelFactory modelFactory = new EMFModelFactory();
//		IInjector injector = new EMFInjector();
//		IExtractor extractor = new EMFExtractor();
//		try {
//
//			if (diagram.equals(PapilioDiagram.CLASS)) {
//
//				ClassLoader loader = Thread.currentThread().getContextClassLoader();
//				URL resourceURL = this.getClass().getResource("java.ecore");
//				URL some = FileLocator.toFileURL(resourceURL);
//				InputStream in = some.openStream();
//
//				IReferenceModel javaMetamodel = modelFactory
//						.newReferenceModel();
//				injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
//				IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
//				injector.inject(umlMetamodel, UML_METAMODEL_PATH);
//
//				IModel inJavaModel = modelFactory.newModel(javaMetamodel);
//				IModel outUmlModel = modelFactory.newModel(umlMetamodel);
//
//				injector.inject(inJavaModel, workingModelPath);
//
//				transformationLauncher.initialize(new HashMap<String, Object>());
//				transformationLauncher.addInModel(inJavaModel, "IN", "java");
//				transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
////				transformationLauncher.launch(ILauncher.RUN_MODE,progressMonitor, new HashMap<String, Object>(),
////						new FileInputStream(
////								ATL_JAVA2CLASSDIAGRAM_TRANSFORMATION_PATH));
//				
//				extractor.extract(outUmlModel,
//						"uml.xmi");
//
//				/*
//				 * Unload all models and metamodels (EMF-specific)
//				 */
//				EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
//				emfModelFactory.unload((EMFModel) inJavaModel);
//				emfModelFactory.unload((EMFModel) outUmlModel);
//				emfModelFactory.unload((EMFReferenceModel) javaMetamodel);
//				emfModelFactory.unload((EMFReferenceModel) umlMetamodel);
//			} else if (diagram.equals(PapilioDiagram.SEQUENCE)) {
//
//				IReferenceModel javaMetamodel = modelFactory
//						.newReferenceModel();
//				injector.inject(javaMetamodel, JAVA_METAMODEL_PATH);
//				IReferenceModel parametersMetamodel = modelFactory
//						.newReferenceModel();
//				injector.inject(parametersMetamodel, PARAMETERS_METAMODEL_PATH);
//				IReferenceModel umlMetamodel = modelFactory.newReferenceModel();
//				injector.inject(umlMetamodel, UML_METAMODEL_PATH);
//
//				
//				IModel javaInModel = modelFactory.newModel(javaMetamodel);
//				IModel outUmlModel = modelFactory.newModel(umlMetamodel);
//
//				injector.inject(javaInModel, workingModelPath);
//
//				IModel inParamsModel = modelFactory.newModel(parametersMetamodel);
//				injector.inject(inParamsModel, INPUT_PARAMETERS_MODEL_PATH);
//				
//				transformationLauncher
//						.initialize(new HashMap<String, Object>());
//				transformationLauncher.addInModel(javaInModel, "IN", "java");
//				transformationLauncher.addInModel(inParamsModel,
//						"inputParams", "parameters");
//				transformationLauncher.addOutModel(outUmlModel, "OUT", "UML");
//				transformationLauncher.launch(ILauncher.RUN_MODE,
//						progressMonitor, new HashMap<String, Object>(),
//						new FileInputStream(
//								ATL_JAVA2SEQUENCEDIAGRAM_TRANSFORMATION_PATH));
//
//				extractor.extract(outUmlModel,
//						"Models/Java/sampleCompany_Total.xmi");
//
//				/*
//				 * Unload all models and metamodels (EMF-specific)
//				 */
//				EMFModelFactory emfModelFactory = (EMFModelFactory) modelFactory;
//				emfModelFactory.unload((EMFModel) javaInModel);
//				emfModelFactory.unload((EMFModel) parametersMetamodel);
//				emfModelFactory.unload((EMFModel) outUmlModel);
//				emfModelFactory.unload((EMFReferenceModel) javaMetamodel);
//				emfModelFactory.unload((EMFReferenceModel) umlMetamodel);
//			} else if (diagram.equals(PapilioDiagram.USECASES)) {
//
//			}
//
//		} catch (ATLCoreException e) {
//			e.printStackTrace();
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private String createModelFromParameters(MethodDeclaration selectedElement, IJavaProject javaProject) {
		parameterContainer container = ParametersFactory.eINSTANCE.createparameterContainer();
		param parameter = ParametersFactory.eINSTANCE.createparam();
		parameter.setName("methodName");
		parameter.setValue(getMethodIdentifier(selectedElement));
		container.getParameters().add(parameter);
		return serializeParameters(container, javaProject);
	}
	
	private String getMethodIdentifier(MethodDeclaration element) {
		StringBuilder methodValue = new StringBuilder();
		String methodName = element.getName();
		String className = element.getAbstractTypeDeclaration().getName();
		String packages = getPackageString(element.getAbstractTypeDeclaration().getPackage());
		methodValue.append(packages);
		methodValue.append(className);
		methodValue.append(".");
		methodValue.append(methodName);
		return methodValue.toString();
	}
	
	
	private String getPackageString(Package aPackage) {
		if (aPackage == null) {
			return "";
		}
		return getPackageString(aPackage.getPackage()) + aPackage.getName() + ".";
	}

	public String serializeParameters(parameterContainer container, IJavaProject javaProject) {
		IProject project = javaProject.getProject();
		IPath projectPath = project.getFullPath();
		String modelName = "sequence_diagram_parameters.xmi";
		projectPath = projectPath.append("parameters");
		projectPath = projectPath.append(modelName);

		URI fileURI = URI.createURI(projectPath.toString());

		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put("xmi", new XMIResourceFactoryImpl());
		Resource javamodelResource = resourceSet.createResource(fileURI);

		EList<EObject> contents = javamodelResource.getContents();
		contents.add(container);
		try {
			javamodelResource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return projectPath.toString();
	}

	public String serializeModel(Model model, IJavaProject javaProject, PapilioDiagram diagram) {
		IProject project = javaProject.getProject();
		IPath projectPath = project.getFullPath();
		String modelName = model.getName() + "_"+  diagram.getName() + ".xmi";
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
