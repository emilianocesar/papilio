package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.modisco.kdm.source.extension.ui.utils.BrowseCodeUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain.FlowPropagationModelChangedListener;

public class ShowFinalModelWizardPage extends WizardPage {

	private TreeViewer originalModelTreeView;
	private TreeViewer derivedModelTreeView;
	private ComposedAdapterFactory adapterFactory;

	private Model originalModel;
	private Model workingModel;
	
	
	public Model getOriginalModel() {
		return originalModel;
	}

	public void setOriginalModel(Model originalModel) {
		this.originalModel = originalModel;
	}

	public Model getWorkingModel() {
		return workingModel;
	}

	public void setWorkingModel(Model workingModel) {
		this.workingModel = workingModel;
	}

	public ShowFinalModelWizardPage(String string) {
		super(string);
		setTitle(string + "Final Models Set");
		setPageComplete(false);
		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite originalModelComposite = new Composite(container, SWT.NONE);
		originalModelComposite.setLayout(new FillLayout(SWT.VERTICAL));

		Composite originalModelTreeComposite = new Composite(
				originalModelComposite, SWT.NONE);
		originalModelTreeComposite.setLayout(new TreeColumnLayout());

		originalModelTreeView = new TreeViewer(originalModelTreeComposite,
				SWT.BORDER);
		
		Button btnNewButton = new Button(originalModelComposite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				serializeModel(getOriginalModel());
			}
		});
		btnNewButton.setText("Save");

		Composite derivedModelComposite = new Composite(container, SWT.NONE);
		derivedModelComposite.setLayout(new FillLayout(SWT.VERTICAL));

		Composite derivedModelTreeComposite = new Composite(
				derivedModelComposite, SWT.NONE);
		derivedModelTreeComposite.setLayout(new TreeColumnLayout());

		derivedModelTreeView = new TreeViewer(derivedModelTreeComposite,
				SWT.BORDER);
		
		Button btnNewButton_1 = new Button(derivedModelComposite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				serializeModel(getWorkingModel());
			}
		});
		btnNewButton_1.setText("Save");

		originalModelTreeView
				.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		originalModelTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));
		derivedModelTreeView.setContentProvider(new AdapterFactoryContentProvider(adapterFactory));
		derivedModelTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));
		originalModelTreeView
				.addDoubleClickListener(new IDoubleClickListener() {

					@Override
					public void doubleClick(DoubleClickEvent event) {
						ISelection selection = event.getSelection();
						if (selection instanceof IStructuredSelection) {
							IStructuredSelection structuredSelection = (IStructuredSelection) selection;
							Object firstElement = structuredSelection
									.getFirstElement();
							if (!(firstElement instanceof ASTranslatorException))
								return;

							ASTranslatorException element = (ASTranslatorException) firstElement;
							EObject eObject = element.getNode();

							if (eObject != null) {
								BrowseCodeUtils
										.openAndSelectEObjectInSourceFile(eObject);
							} else {

							}
						}

					}
				});
		derivedModelTreeView.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					Object firstElement = structuredSelection.getFirstElement();
					if (!(firstElement instanceof ASTranslatorException))
						return;

					ASTranslatorException element = (ASTranslatorException) firstElement;
					EObject eObject = element.getNode();

					if (eObject != null) {
						BrowseCodeUtils
								.openAndSelectEObjectInSourceFile(eObject);
					} else {

					}
				}

			}
		});
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
			onEnterPage();
	}

	private void onEnterPage() {
		Job job = new Job("Generating Abstract Syntax") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				PapilioMain.INSTANCE.propagateChanges(monitor, new FlowPropagationModelChangedListener() {
					
					@Override
					public void onModelPropagationFinished(final Model model, final Model derivedModel) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								originalModelTreeView.setInput(model);
								originalModelTreeView.refresh();
								derivedModelTreeView.setInput(derivedModel);
								derivedModelTreeView.refresh();
								ShowFinalModelWizardPage.this.setOriginalModel(model);
								ShowFinalModelWizardPage.this.setWorkingModel(derivedModel);
							}
						});

					}
				});
				return Status.OK_STATUS;
			}

		};
		job.schedule();
		setPageComplete(true);
		getContainer().updateButtons();
	}
	
	  private void serializeModel(Model model){
		  IJavaProject javaProject = PapilioMain.INSTANCE.getProject();
		  IProject project = javaProject.getProject();
		  
		  IPath projectPath = project.getFullPath();
		  String modelName = model.getName() + "_object.xmi";
		  projectPath = projectPath.append("javamodel");
		  projectPath = projectPath.append(modelName);
		  		  
		  URI fileURI = URI.createURI(projectPath.toString());
		  
		  ResourceSet resourceSet = new ResourceSetImpl();
		  resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", 
				  new XMIResourceFactoryImpl());
		  Resource javamodelResource = resourceSet.createResource(fileURI);
		  
		  EList<EObject> contents = javamodelResource.getContents();
		  contents.add(model);
		  try {
			javamodelResource.save(null);
		} catch (IOException e) { 	
			e.printStackTrace();
		}
		  
	  }

}
