package ar.edu.unicen.exa.papilio.core.ofg.element;

/**
 * @author Belen Rolandi
 * 
 */
public class OFGEdge {

	OFGNode sourceNode;
	OFGNode targetNode;
	Object value;
	
	public OFGEdge () {}
	
	public OFGEdge (OFGNode source, OFGNode target) {
		
		sourceNode = source;
		targetNode = target;
	}
	

	public OFGNode getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(OFGNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public OFGNode getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(OFGNode targetNode) {
		this.targetNode = targetNode;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return sourceNode.getId() +", "+ targetNode.getId();
	}
}
