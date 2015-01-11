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

public class ShowPropagationAlgorithmOutSetWizardPage extends WizardPage {

	private TreeViewer outSetOFGTreeView;
	private TreeViewer outSetASTTreeView;
	private ComposedAdapterFactory adapterFactory;

	public ShowPropagationAlgorithmOutSetWizardPage(String string) {
		super(string);
		setTitle(string + "Out Set");
		setPageComplete(false);
		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);

	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite outSetOFGComposite = new Composite(container, SWT.NONE);
		outSetOFGComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite outSetOFGTreeComposite = new Composite(outSetOFGComposite,
				SWT.NONE);
		outSetOFGTreeComposite.setLayout(new TreeColumnLayout());

		outSetOFGTreeView = new TreeViewer(outSetOFGTreeComposite, SWT.BORDER);

		Composite outSetASTComposite = new Composite(container, SWT.NONE);
		outSetASTComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite outSetASTTreeComposite = new Composite(outSetASTComposite,
				SWT.NONE);
		outSetASTTreeComposite.setLayout(new TreeColumnLayout());

		outSetASTTreeView = new TreeViewer(outSetASTTreeComposite, SWT.BORDER);

		outSetOFGTreeView
				.setContentProvider(new PropagationMapContentProvider());
		outSetOFGTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));
		outSetASTTreeView
				.setContentProvider(new PropagationSetContentProvider());
		outSetASTTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));

		outSetOFGTreeView
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
								outSetASTTreeView.setInput(firstElement);
								outSetASTTreeView.refresh();
							}
						});
					}
				});

		outSetASTTreeView.addDoubleClickListener(new IDoubleClickListener() {

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
				PapilioMain.INSTANCE.generateOutSet(monitor,
						new FlowPropagationAlgorithmStepListener() {

							@Override
							public <T extends ASTNode> void onStepPerformed(
									final Map<OFGNode, Set<T>> out) {
								Display.getDefault().asyncExec(new Runnable() {

									@Override
									public void run() {

										outSetOFGTreeView.setInput(out);
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
