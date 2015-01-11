package ar.edu.unicen.exa.papilio.core.model;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.emf.JavaPackage;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.modisco.java.composition.discoverer.DiscoverKDMSourceAndJavaModelFromJavaProject;
import org.eclipse.modisco.java.composition.javaapplication.JavaApplication;

public class JavaModelDiscoverer {

	public Resource discoverModelResourceFromProject(IJavaProject project,
			IProgressMonitor progressMonitor) throws DiscoveryException {
		DiscoverKDMSourceAndJavaModelFromJavaProject javaDiscoverer = new DiscoverKDMSourceAndJavaModelFromJavaProject();
			javaDiscoverer.discoverElement(project, progressMonitor);
		Resource javaResource = javaDiscoverer.getTargetModel();
		return javaResource;
	}

	public Model discoverModelFromProject(IJavaProject project,
			IProgressMonitor progressMonitor) throws DiscoveryException {
		Resource javaResource = this.discoverModelResourceFromProject(project,
				progressMonitor);
		JavaApplication app = (JavaApplication) javaResource.getContents().get(0);
		return app.getJavaModel();
	}

	public Model discoverJavaModelFromXMIUri(String modelURI) {
		Resource resource = this.discoverJavaModelResourceFromXMIUri(modelURI);
		return (Model) resource.getContents().get(0);
	}

	public Resource discoverJavaModelResourceFromXMIUri(String modelURI) {

		// Create a resource set to hold the resources.
		//
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the appropriate resource factory to handle all file
		// extensions.
		//
		resourceSet
				.getResourceFactoryRegistry()
				.getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION,
						new XMIResourceFactoryImpl());

		// Register the package to ensure it is available during loading.
		//
		resourceSet.getPackageRegistry().put(JavaPackage.eNS_URI,
				JavaPackage.eINSTANCE);

		File file = new File(modelURI);
		URI uri = file.isFile() ? URI.createFileURI(file.getAbsolutePath())
				: URI.createURI(modelURI);

		Resource resource = resourceSet.getResource(uri, true);

		return resource;
	}
}
