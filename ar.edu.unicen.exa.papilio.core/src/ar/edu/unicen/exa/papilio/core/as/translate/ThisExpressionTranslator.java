package ar.edu.unicen.exa.papilio.core.as.translate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gmt.modisco.java.ASTNode;

import ar.edu.unicen.exa.papilio.core.as.ASProgram;
import ar.edu.unicen.exa.papilio.core.as.element.ASAttributeDeclaration;
import ar.edu.unicen.exa.papilio.core.as.element.ASElement;

public class ThisExpressionTranslator extends AbstractTranslator {

	@Override
	public List<ASElement> translate(ASTNode node) {
		
		List<ASElement> result = new ArrayList<ASElement>();
		String thisExpressionName = this.getThisExpressionName();
		
		//Verifico si existe el atributo para el objeto this
		ASAttributeDeclaration thisInstance = (ASAttributeDeclaration)ASProgram.INSTANCE.getDeclaration(thisExpressionName.toString());
		if (thisInstance == null) {
			thisInstance = new ASAttributeDeclaration();
			thisInstance.setFullyScopedName(this.getThisExpressionName());
			thisInstance.setThisTarget(true);
			thisInstance.setNode(context.getCurrentClassDeclaration());
			//agrego el elemento al ASProgram
			ASProgram.INSTANCE.addElement(thisInstance);
		}
		result.add(thisInstance);
		return result;
	}
	

}
