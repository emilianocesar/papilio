package ar.edu.unicen.exa.papilio.plugin.ui.wizard;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.wizard.Wizard;

import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowAbstractSyntaxWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowFinalModelWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowModelWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowOFGWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowPropagationAlgorithmGenSetWizardPage;
import ar.edu.unicen.exa.papilio.plugin.ui.wizard.pages.ShowPropagationAlgorithmOutSetWizardPage;

public class HefestoWizard extends Wizard {

	protected ShowModelWizardPage pageOne;
	private IJavaProject project;
	private ShowAbstractSyntaxWizardPage pageTwo;
	private ShowOFGWizardPage pageThree;
	private ShowPropagationAlgorithmGenSetWizardPage pageFour;
	private ShowPropagationAlgorithmOutSetWizardPage pageFive;
	private ShowFinalModelWizardPage pageSix;

	public HefestoWizard(IJavaProject project) {
		super();
		this.project = project;
	}

	@Override
	public void addPages() {
		pageOne = new ShowModelWizardPage("Choose diagram", project);
		pageTwo = new ShowAbstractSyntaxWizardPage("Abstract Syntax");
		pageThree = new ShowOFGWizardPage("OFG");
		pageFour = new ShowPropagationAlgorithmGenSetWizardPage("");
		pageFive = new ShowPropagationAlgorithmOutSetWizardPage("");
		pageSix = new ShowFinalModelWizardPage("");
		addPage(pageOne);
		addPage(pageTwo);
		addPage(pageThree);
		addPage(pageFour);
		addPage(pageFive);
		addPage(pageSix);

	}

	@Override
	public boolean canFinish() {
		return pageTwo.isPageComplete() && pageThree.isPageComplete();
	}

	@Override
	public boolean performFinish() {
		return canFinish();
	}
	
	

}
