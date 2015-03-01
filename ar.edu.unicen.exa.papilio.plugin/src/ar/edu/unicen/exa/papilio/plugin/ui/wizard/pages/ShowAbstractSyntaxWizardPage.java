package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
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

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;
import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.as.ASTranslatorExceptionContentProvider;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.as.AbstractSyntaxContentProvider;

public class ShowAbstractSyntaxWizardPage extends WizardPage {

	private TreeViewer abstractSyntaxTreeView;
	private TreeViewer asExceptionTreeView;
	private ComposedAdapterFactory adapterFactory;
	private PapilioMain papilioMain;

	public ShowAbstractSyntaxWizardPage(String string, PapilioMain papilioMain) {
		super(string);
		setTitle("Abstract Syntax Tree");
		setDescription("Derived from selected model");
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

		Composite abstractSyntaxComposite = new Composite(container, SWT.NONE);
		abstractSyntaxComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite abstractSyntaxTreeComposite = new Composite(
				abstractSyntaxComposite, SWT.NONE);
		abstractSyntaxTreeComposite.setLayout(new TreeColumnLayout());

		abstractSyntaxTreeView = new TreeViewer(abstractSyntaxTreeComposite,
				SWT.BORDER);

		Composite asExceptionComposite = new Composite(container, SWT.NONE);
		asExceptionComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite asExceptionTreeComposite = new Composite(
				asExceptionComposite, SWT.NONE);
		asExceptionTreeComposite.setLayout(new TreeColumnLayout());

		asExceptionTreeView = new TreeViewer(asExceptionTreeComposite,
				SWT.BORDER);

		abstractSyntaxTreeView
				.setContentProvider(new AbstractSyntaxContentProvider());
		abstractSyntaxTreeView
				.setLabelProvider(new AdapterFactoryLabelProvider(
						adapterFactory));
		asExceptionTreeView
				.setContentProvider(new ASTranslatorExceptionContentProvider());
		asExceptionTreeView.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));

		abstractSyntaxTreeView
				.addDoubleClickListener(new IDoubleClickListener() {

					@Override
					public void doubleClick(DoubleClickEvent event) {
						ISelection selection = event.getSelection();
						if (selection instanceof IStructuredSelection) {
							IStructuredSelection structuredSelection = (IStructuredSelection) selection;
							Object firstElement = structuredSelection
									.getFirstElement();
							if (!(firstElement instanceof ASElement))
								return;

							ASElement element = (ASElement) firstElement;
							EObject eObject = element.getNode();

							if (eObject != null) {
								BrowseCodeUtils
										.openAndSelectEObjectInSourceFile(eObject);
							} else {

							}
						}

					}
				});

		asExceptionTreeView.addDoubleClickListener(new IDoubleClickListener() {

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
				ShowAbstractSyntaxWizardPage.this.papilioMain
						.generateASProgram(monitor,
								new PapilioMain.GenerateASProgramListener() {

									@Override
									public void onASProgramGenerated(
											final ASProgram program) {
										Display.getDefault().asyncExec(
												new Runnable() {

													@Override
													public void run() {

														abstractSyntaxTreeView
																.setInput(program);
														asExceptionTreeView
																.setInput(program
																		.getErrors());
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
