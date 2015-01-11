package ar.edu.unicen.exa.papilio.plugin.ui.provider.as;

import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.as.exception.ASTranslatorException;

public class ASTranslatorExceptionContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List<?>) {
			return ((List<?>)inputElement).toArray();
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof ASTranslatorException) {
			return new Object[]{((ASTranslatorException)parentElement).getNode()};
		}
		return Collections.EMPTY_LIST.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

}
