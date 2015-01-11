package ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg.propagation;

import java.util.Map;
import java.util.Set;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class PropagationMapContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] elements;
		@SuppressWarnings("unchecked")
		Map<OFGNode, Set<ASTNode>> element = (Map<OFGNode, Set<ASTNode>>) inputElement;
		elements = element.entrySet().toArray();
		return elements;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return null;
	}

	@Override
	public Object getParent(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		// TODO Auto-generated method stub
		return false;
	}

}
