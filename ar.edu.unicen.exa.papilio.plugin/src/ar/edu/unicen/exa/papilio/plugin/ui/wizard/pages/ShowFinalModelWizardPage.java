package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.modisco.kdm.source.extension.ui.utils.BrowseCodeUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
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
	private PapilioMain papilioMain;

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

	public ShowFinalModelWizardPage(String string, PapilioMain papilioMain) {
		super(string);
		setTitle(string + "Final Models Set");
		setPageComplete(false);
		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		this.papilioMain = papilioMain;
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

		Composite derivedModelComposite = new Composite(container, SWT.NONE);
		derivedModelComposite.setLayout(new FillLayout(SWT.VERTICAL));

		Composite derivedModelTreeComposite = new Composite(
				derivedModelComposite, SWT.NONE);
		derivedModelTreeComposite.setLayout(new TreeColumnLayout());

		derivedModelTreeView = new TreeViewer(derivedModelTreeComposite,
				SWT.BORDER);

		originalModelTreeView
				.setContentProvider(new AdapterFactoryContentProvider(
						adapterFactory));
		originalModelTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));
		derivedModelTreeView
				.setContentProvider(new AdapterFactoryContentProvider(
						adapterFactory));
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
				ShowFinalModelWizardPage.this.papilioMain.propagateChanges(
						monitor, new FlowPropagationModelChangedListener() {

							@Override
							public void onModelPropagationFinished(
									final Model model, final Model derivedModel) {
								Display.getDefault().asyncExec(new Runnable() {

									@Override
									public void run() {
										originalModelTreeView.setInput(model);
										originalModelTreeView.refresh();
										derivedModelTreeView
												.setInput(derivedModel);
										derivedModelTreeView.refresh();
										ShowFinalModelWizardPage.this
												.setOriginalModel(model);
										ShowFinalModelWizardPage.this
												.setWorkingModel(derivedModel);
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
}
