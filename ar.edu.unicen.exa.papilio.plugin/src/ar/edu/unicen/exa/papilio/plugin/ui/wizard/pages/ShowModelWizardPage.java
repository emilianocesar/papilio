package ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmt.modisco.java.MethodDeclaration;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.gmt.modisco.java.Package;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.modisco.infra.discovery.core.exception.DiscoveryException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import ar.edu.unicen.exa.papilio.core.diagram.PapilioDiagram;
import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.plugin.ui.provider.java.JavaModelContentProvider;

public class ShowModelWizardPage extends WizardPage {

	private IJavaProject project;
	private TreeViewer modelTreeViewer;
	private ComposedAdapterFactory adapterFactory;
	private PapilioMain papilioMain;
	private WizardPage lastPage;

	public ShowModelWizardPage(String string, IJavaProject project,
			PapilioMain papilioMain, WizardPage lastPage) {
		super(string);
		setTitle("Java Model");
		setDescription("Java Model derived from Project \""
				+ project.getElementName() + "\"");
		this.project = project;
		adapterFactory = new ComposedAdapterFactory(
				ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		setPageComplete(false);
		this.papilioMain = papilioMain;
		this.lastPage = lastPage;
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		setControl(container);

		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite treeComposite = new Composite(composite, SWT.NONE);
		treeComposite.setLayout(new TreeColumnLayout());

		modelTreeViewer = new TreeViewer(treeComposite, SWT.BORDER);
		modelTreeViewer.setContentProvider(new JavaModelContentProvider());
		modelTreeViewer.setLabelProvider(new AdapterFactoryLabelProvider(
				adapterFactory));

		Group grpChooseYourDiagram = new Group(container, SWT.NONE);
		grpChooseYourDiagram.setText("Choose your diagram");
		grpChooseYourDiagram.setLayout(new FillLayout(SWT.VERTICAL));

		final Button btnClassDiagram = new Button(grpChooseYourDiagram,
				SWT.NONE);
		btnClassDiagram.setText("Class Diagram");
		btnClassDiagram.setEnabled(false);
		btnClassDiagram.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getContainer().showPage(getNextPage());
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.CLASS);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				getContainer().showPage(getNextPage());
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.CLASS);
			}
		});

		final Button btnSequenceDiagram = new Button(grpChooseYourDiagram,
				SWT.NONE);
		btnSequenceDiagram.setText("Sequence Diagram");
		btnSequenceDiagram.setEnabled(false);
		btnSequenceDiagram.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				getContainer().showPage(getNextPage());
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.SEQUENCE);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				getContainer().showPage(getNextPage());
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.SEQUENCE);
			}
		});

		final Button btnUseCaseDiagram = new Button(grpChooseYourDiagram,
				SWT.NONE);
		btnUseCaseDiagram.setText("Use Case Diagram");
		btnUseCaseDiagram.setEnabled(false);
		btnUseCaseDiagram.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.USECASES);
				getContainer().showPage(lastPage);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				ShowModelWizardPage.this.papilioMain
						.setDiagram(PapilioDiagram.USECASES);
				getContainer().showPage(lastPage);
			}
		});

		modelTreeViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						ISelection selection = event.getSelection();
						if (selection instanceof IStructuredSelection) {
							IStructuredSelection structuredSelection = (IStructuredSelection) selection;
							Object firstElement = structuredSelection
									.getFirstElement();
							if (firstElement instanceof Model) {
								btnClassDiagram.setEnabled(true);
								btnSequenceDiagram.setEnabled(false);
								btnUseCaseDiagram.setEnabled(true);
							} else if (firstElement instanceof Package) {
								btnClassDiagram.setEnabled(true);
								btnSequenceDiagram.setEnabled(false);
								btnUseCaseDiagram.setEnabled(false);
							} else if (firstElement instanceof MethodDeclaration) {
								btnClassDiagram.setEnabled(false);
								btnSequenceDiagram.setEnabled(true);
								btnUseCaseDiagram.setEnabled(false);
							} else {
								btnClassDiagram.setEnabled(false);
								btnSequenceDiagram.setEnabled(false);
								btnUseCaseDiagram.setEnabled(false);
							}
						}
					}
				});

		Job job = new Job("Discovering model") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				ShowModelWizardPage.this.papilioMain
						.discoverJavaModelFromProject(project, monitor,
								new PapilioMain.DiscoverModelListener() {

									@Override
									public void onModelDiscovered(
											final Model model) {
										Display.getDefault().asyncExec(
												new Runnable() {

													@Override
													public void run() {
														modelTreeViewer
																.setInput(model);
													}
												});
									}

									@Override
									public void onModelDiscoverError(
											DiscoveryException exception) {

									}
								});
				return Status.OK_STATUS;
			}

		};
		job.schedule();
	}
}
