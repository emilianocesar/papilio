package ar.edu.unicen.exa.papilio.core.ofg.element;

import java.util.ArrayList;
import java.util.List;

import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class OFGNode {

	private String id;
	private ASElement element;
	
	List<OFGEdge> edges = new ArrayList<OFGEdge>();
	List<OFGEdge> predecesors = new ArrayList<OFGEdge>();
	
	public OFGNode() {
	}
	
	public OFGNode(ASElement element) {
		this.element = element;
	}
	
	public ASElement getASElement() {
		return element;
	}
	
	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public List<OFGEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<OFGEdge> edges) {
		this.edges = edges;
	}
	
	public void addEdge(OFGEdge edge) {
		this.edges.add(edge);
	}
	
	public void addPredecesor(OFGEdge edge) {
		this.predecesors.add(edge);
	}
	
	public List<OFGEdge> getPredecesors() {
		return predecesors;
	}

	public void setPredecesors(List<OFGEdge> predecesors) {
		this.predecesors = predecesors;
	}

	public String toString() {
		return "NODE:"+this.id;
	}
	
	public boolean containsAdjacent(OFGNode node) {
		for (OFGEdge edge : edges) {
			if (edge.targetNode.equals(node))
				return true;
		}
		
		return false;
	}

}
