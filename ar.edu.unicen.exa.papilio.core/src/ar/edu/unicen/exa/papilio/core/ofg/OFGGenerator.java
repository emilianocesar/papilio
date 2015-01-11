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

	public void generateOFG() {
		//agrego nodos al ofg a partir de las declaraciones en el asProgram 
		for (ASDeclaration declaration : ASProgram.INSTANCE.getDeclarations().values()) {
			OFGGraph.getInstance().putNodes(declaration.getOFGNodes().values());
		}
		//agrego arcos generados por las declaraciones
		for (ASDeclaration declaration : ASProgram.INSTANCE.getDeclarations().values()) {
			Map<String, Object> edges = declaration.getOFGEdges();
			OFGGraph.getInstance().addEdges(edges);
		}
		//agrego arcos a partir de los statements en el asProgram
		for (ASElement statement : ASProgram.INSTANCE.getStatements()) {
			Map<String, Object> edges = statement.getOFGEdges();
			OFGGraph.getInstance().addEdges(edges);
		}
	}
}
