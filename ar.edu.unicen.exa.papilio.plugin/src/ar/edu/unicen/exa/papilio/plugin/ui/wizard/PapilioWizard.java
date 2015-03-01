package ar.edu.unicen.exa.papilio.plugin.ui.wizard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.wizard.Wizard;

import ar.edu.unicen.exa.papilio.core.main.PapilioMain;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowAbstractSyntaxWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowFinalModelWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowModelWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowOFGWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowPropagationAlgorithmGenSetWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowPropagationAlgorithmOutSetWizardPage;

public class PapilioWizard extends Wizard {

	private PapilioMain papilioMain;
	
	protected ShowModelWizardPage pageOne;
	private IJavaProject project;
	private ShowAbstractSyntaxWizardPage pageTwo;
	private ShowOFGWizardPage pageThree;
	private ShowPropagationAlgorithmGenSetWizardPage pageFour;
	private ShowPropagationAlgorithmOutSetWizardPage pageFive;
	private ShowFinalModelWizardPage pageSix;

	public PapilioWizard(IJavaProject project) {
		super();
		this.project = project;
		this.papilioMain = new PapilioMain();
	}

	@Override
	public void addPages() {
		pageOne = new ShowModelWizardPage("Choose diagram", project, this.papilioMain);
		pageTwo = new ShowAbstractSyntaxWizardPage("Abstract Syntax", this.papilioMain);
		pageThree = new ShowOFGWizardPage("OFG", this.papilioMain);
		pageFour = new ShowPropagationAlgorithmGenSetWizardPage("", this.papilioMain);
		pageFive = new ShowPropagationAlgorithmOutSetWizardPage("", this.papilioMain);
		pageSix = new ShowFinalModelWizardPage("", this.papilioMain);
		addPage(pageOne);
		addPage(pageTwo);
		addPage(pageThree);
		addPage(pageFour);
		addPage(pageFive);
		addPage(pageSix);

	}

	@Override
	public boolean canFinish() {
		return this.papilioMain.canFinish();
	}

	@Override
	public boolean performFinish() {
		Job job = new Job("Executing Transformation") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				PapilioWizard.this.papilioMain.finish(monitor);
				return Status.OK_STATUS;
			}

		};
		job.schedule();
		return true;
	}
}
