package ar.edu.unicen.exa.papilio.plugin.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import ar.edu.unicen.exa.papilio.plugin.ui.wizard.PapilioWizard;

public class ReverseToUMLHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		IStructuredSelection selection = (IStructuredSelection) sel;

		Object firstElement = selection.getFirstElement();
//		JavaModelDisco]verer discoverer = new JavaModelDiscoverer();
//		Model model = discoverer.discoverModelFromProject((IJavaProject) firstElement, new NullProgressMonitor());
		WizardDialog dialog = new WizardDialog(shell, new PapilioWizard((IJavaProject) firstElement));
		dialog.open(); 
		return null;
	}

}
