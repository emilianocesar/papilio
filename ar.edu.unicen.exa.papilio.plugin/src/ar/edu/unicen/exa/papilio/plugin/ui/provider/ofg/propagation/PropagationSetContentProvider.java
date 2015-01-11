package ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.propagation;

import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class PropagationSetContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] elements;
		@SuppressWarnings("unchecked")
		Entry<OFGNode, Set<ASTNode>> element = (Entry<OFGNode, Set<ASTNode>>) inputElement;
		if (element.getValue() != null)
			elements = element.getValue().toArray();
		else
			elements = new Object[]{};
		return elements;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
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
