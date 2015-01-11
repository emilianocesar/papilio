package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.modisco.kdm.source.extension.ui.utils.BrowseCodeUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain.FlowPropagationAlgorithmStepListener;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.propagation.PropagationMapContentProvider;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.propagation.PropagationSetContentProvider;

public class ShowPropagationAlgorithmGenSetWizardPage extends WizardPage {

	private TreeViewer genSetOFGTreeView;
	private TreeViewer genSetASTTreeView;
	private ComposedAdapterFactory adapterFactory;

	public ShowPropagationAlgorithmGenSetWizardPage(String string) {
		super(string);
		setTitle(string + "Gen Set");
		// setDescription("Derived from selected model");
		setPageComplete(false);
		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite genSetOFGComposite = new Composite(container, SWT.NONE);
		genSetOFGComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite genSetOFGTreeComposite = new Composite(genSetOFGComposite,
				SWT.NONE);
		genSetOFGTreeComposite.setLayout(new TreeColumnLayout());

		genSetOFGTreeView = new TreeViewer(genSetOFGTreeComposite, SWT.BORDER);

		Composite genSetASTComposite = new Composite(container, SWT.NONE);
		genSetASTComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite genSetASTTreeComposite = new Composite(genSetASTComposite,
				SWT.NONE);
		genSetASTTreeComposite.setLayout(new TreeColumnLayout());

		genSetASTTreeView = new TreeViewer(genSetASTTreeComposite, SWT.BORDER);

		genSetOFGTreeView
				.setContentProvider(new PropagationMapContentProvider());
		genSetOFGTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));
		genSetASTTreeView
				.setContentProvider(new PropagationSetContentProvider());
		genSetASTTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));

		genSetOFGTreeView
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						final ISelection selection = event.getSelection();
						IStructuredSelection structuredSelection = (IStructuredSelection) selection;
						final Object firstElement = structuredSelection
								.getFirstElement();
						if (!(firstElement instanceof Entry<?, ?>))
							return;
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								genSetASTTreeView.setInput(firstElement);
								genSetASTTreeView.refresh();
							}
						});
					}
				});

		genSetASTTreeView.addDoubleClickListener(new IDoubleClickListener() {

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
				PapilioMain.INSTANCE.generateGenSet(monitor,
						new FlowPropagationAlgorithmStepListener() {

							@Override
							public <T extends ASTNode> void onStepPerformed(
									final Map<OFGNode, Set<T>> gen) {
								Display.getDefault().asyncExec(new Runnable() {

									@Override
									public void run() {

										genSetOFGTreeView.setInput(gen);
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
