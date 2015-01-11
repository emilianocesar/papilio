package ar.edu.unicen.exa.papilio.plugin.ui.provider.ofg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import ar.edu.unicen.exa.papilio.core.ofg.OFGGraph;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class OFGContentProvider implements ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof OFGGraph) {
			OFGGraph graph = (OFGGraph) inputElement;
			return graph.getNodes().toArray();
		} else if (inputElement instanceof OFGNode) {
			OFGNode node = (OFGNode) inputElement;
			return new Object[] { node.getASElement() };
		}
		return Collections.EMPTY_SET.toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof OFGGraph) {
			OFGGraph graph = (OFGGraph) parentElement;
			return graph.getNodes().toArray();
		} else if (parentElement instanceof OFGNode) {
			OFGNode node = (OFGNode) parentElement;
			List<OFGNode> adjacent = new ArrayList<OFGNode>();
			for (OFGEdge edge : node.getEdges()) {
				adjacent.add(edge.getTargetNode());
			}
			return adjacent.toArray();
		}
		return null;
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
