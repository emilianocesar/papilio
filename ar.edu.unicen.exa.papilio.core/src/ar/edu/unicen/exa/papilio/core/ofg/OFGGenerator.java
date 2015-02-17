package ar.edu.unicen.exa.papilio.core.ofg;

import java.util.Map;

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

/**
 * @author Belen Rolandi
 * 
 */
public class OFGGenerator {

	public OFGGraph generateOFG(ASProgram program) {
		OFGGraph graph = new OFGGraph();
		// agrego nodos al ofg a partir de las declaraciones en el asProgram
		for (ASDeclaration declaration : program.getDeclarations().values()) {
			graph.putNodes(declaration.getOFGNodes().values());
		}
		// agrego arcos generados por las declaraciones
		for (ASDeclaration declaration : program.getDeclarations().values()) {
			Map<String, Object> edges = declaration.getOFGEdges();
			graph.addEdges(edges);
		}
		// agrego arcos a partir de los statements en el asProgram
		for (ASElement statement : program.getStatements()) {
			Map<String, Object> edges = statement.getOFGEdges();
			graph.addEdges(edges);
		}
		return graph;
	}
}
