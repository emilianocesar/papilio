package ar.edu.unicen.exa.papilio.plugin.ui.provider.as;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class AbstractSyntaxContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ASProgram) {
			ASProgram program = (ASProgram)inputElement;
			List<Object> elements = new ArrayList<Object>();
			elements.addAll(program.getDeclarations().values());
			elements.addAll(program.getStatements());
			elements.addAll(program.getUndeclaredElement().values());
			return elements.toArray();
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ASProgram) {
			ASProgram program = (ASProgram)parentElement;
			List<Object> elements = new ArrayList<Object>();
			elements.addAll(program.getStatements());
			elements.addAll(program.getDeclarations().values());
			elements.addAll(program.getUndeclaredElement().values());
			return elements.toArray();
		} else {
			if (parentElement instanceof ASElement) {
				ASElement element = (ASElement)parentElement;
				return new Object[]{element.getNode()};

			}
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length >0;
	}

}
