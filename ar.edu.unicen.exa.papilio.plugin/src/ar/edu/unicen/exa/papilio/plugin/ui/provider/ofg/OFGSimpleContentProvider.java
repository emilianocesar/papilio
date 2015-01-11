package ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class OFGSimpleContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof OFGNode)
			return new Object[] { inputElement };
		else if (inputElement instanceof List) {
			return ((List<OFGNode>)inputElement).toArray();
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

}
