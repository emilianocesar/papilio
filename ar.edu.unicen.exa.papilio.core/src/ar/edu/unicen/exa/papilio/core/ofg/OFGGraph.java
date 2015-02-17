package ar.edu.unicen.exa.papilio.core.ofg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ar.edu.unicen.exa.papilio.core.as.exception.OFGGenerationException;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGEdge;
import ar.edu.unicen.exa.papilio.core.ofg.element.OFGNode;

public class OFGGraph {
	
	private Map<String,OFGNode> ofg = new LinkedHashMap<String,OFGNode>();
	private List<OFGGenerationException> errors = new ArrayList<OFGGenerationException>();


	public Set<OFGNode> getNodes() {
		return new LinkedHashSet<OFGNode>(ofg.values());
	}

	public void addEdges(Map<String, Object> edges) {
		
		for (String edgeId : edges.keySet()) {
			
			String sourceNodeId = this.getSourceNodeId(edgeId);
			OFGNode sourceNode = ofg.get(sourceNodeId);
			String targetNodeId = this.getTargetNodeId(edgeId);
			OFGNode targetNode = ofg.get(targetNodeId);
			
			if (sourceNode != null && targetNode != null) {
				Object value = edges.get(edgeId);
				addEdge(sourceNode, targetNode, value);
			} else 
				if (sourceNode == null) {
					String message = "Unable to generate edge. SourceNode is null " + sourceNodeId;
					OFGGenerationException exception = new OFGGenerationException(message);
					errors.add(exception);
				} else
					if (targetNode == null) {
						String message = "Unable to generate edge. TargetNode is null " + targetNodeId;
						OFGGenerationException exception = new OFGGenerationException(message);
						errors.add(exception);
					}
		}		
	}
	
	public void addEdge(OFGNode sourceNode, OFGNode targetNode, Object value) {
				
		OFGEdge edge = new OFGEdge();
		edge.setSourceNode(sourceNode);
		edge.setTargetNode(targetNode);
		edge.setValue(value);
		sourceNode.addEdge(edge);
		targetNode.addPredecesor(edge);
	}
	
	public void putNodes(Collection<OFGNode>nodes) {
		
		for (OFGNode node: nodes) {
			this.putNode(node);
		}
	}
	
	public Boolean containsNode(String nodeId) {
		return ofg.containsKey(nodeId);
	}
	
	public Boolean containsEdge(String nodeId1, String nodeId2) {
		Boolean result = false;
		if (this.containsNode(nodeId1)) {
			OFGNode node1 = this.getNode(nodeId1);
			if (this.containsNode(nodeId2)) {
				OFGNode node2 = this.getNode(nodeId2);
				result = node1.containsAdjacent(node2);
			}
		}
		return result;
	}

	public OFGNode getNode(String key) {
		return ofg.get(key);
	}
	
	public void putNode(OFGNode node){
		ofg.put(node.getId(), node);
	}
	
	public void removeAllNodes() {
		
		if (!ofg.isEmpty()){
			ofg = new HashMap<String, OFGNode>();
		}
	}
	
	/**
	 * Obtiene el id del nodo source de un arco a partir del edgeId pasado como parametro
	 * Un edgeId es un string formado por la secuencia sourceNodeId,targetNodeId
	 * @param edgeId El identificador del arco que se desea agregar al ofg
	 * @return El id del nodo source del arco
	 */
	public String getSourceNodeId(String edgeId) {
		int commaIndex = edgeId.indexOf(',');
		return edgeId.substring(0, commaIndex);
	}
	
	public String getTargetNodeId(String edgeId) {
		int commaIndex = edgeId.indexOf(',');
		return edgeId.substring(commaIndex+1, edgeId.length());
	}

	public void clear() {
		ofg = new LinkedHashMap<String,OFGNode>();		
	}

	public List<OFGGenerationException> getErrors() {
		return errors;
	}
}
