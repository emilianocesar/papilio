package ar.edu.unicen.exa.papilio.plugin.ui.provider.java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;
import org.eclipse.gmt.modisco.java.Model;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class JavaModelContentProvider implements ITreeContentProvider {

	
	private Model model;

	@Override
	public void dispose() {
		model = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Model && model == null) {
			this.model = (Model) inputElement;
			List<Object> model = new ArrayList<Object>();
			model.add(inputElement);
			return model.toArray();
		} else if (inputElement instanceof Model && model != null) {
				return ((Model)inputElement).getOwnedElements().toArray();
		}	else if (inputElement instanceof ASTNode) {
			return ((ASTNode)inputElement).eContents().toArray();
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Model) {
			return ((Model)parentElement).getOwnedElements().toArray();
		} else if (parentElement instanceof ASTNode) {
			return ((ASTNode)parentElement).eContents().toArray();
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
