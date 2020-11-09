package model;

import error.MyException;

public class CalculatriceModel {

	// Calcule l'op�ration en input et renvoie un string contenant le r�sultat
	public String compute(String input) throws MyException {

		// Retire tous les espaces de l'input
		String inputWithoutSpaces = CommonFunctions.removeSpaceFromInput(input);

		// Simplifie au maximum toutes les suites d'op�rations r�f�renc�es dans
		// CommonFunctions
		String simpleOperatorsInput = inputWithoutSpaces;
		while (simpleOperatorsInput.contains(CommonFunctions.doubleMinus)
				|| simpleOperatorsInput.contains(CommonFunctions.doublePlus)
				|| simpleOperatorsInput.contains(CommonFunctions.minusAndPlus)
				|| simpleOperatorsInput.contains(CommonFunctions.plusAndMinus)
				|| simpleOperatorsInput.contains(CommonFunctions.multiAndPlus)
				|| simpleOperatorsInput.contains(CommonFunctions.divAndPlus)) {
			simpleOperatorsInput = CommonFunctions.concatenateOperators(simpleOperatorsInput);
		}

		// Compare le nombre de parenth�ses ouvertes et le nombre de parenth�ses ferm�es
		// Si ces nombres sont in�gaux, on renvoie une erreur, sinon on continue
		// Le faire avant l'analyse de l'expression g�n�rale permet de gagner en temps
		// lors de l'ex�cution
		try {
			simpleOperatorsInput = CommonFunctions.compareOpenedParenthesisAndClosedParenthesis(simpleOperatorsInput);
		} catch (Exception e) {
			throw e;
		}

		double doubleOutput;
		try {
			// Analyse la chaine de caract�res un par un en respectant la priorit� des
			// op�rateurs et en r�alisant les op�rations successives
			AnalysisFunctions analysisFunctions = new AnalysisFunctions();
			doubleOutput = analysisFunctions.analysis(simpleOperatorsInput);
		} catch (Exception e) {
			throw e;
		}

		// Convertit le r�sultat obtenu sous la forme d'un double en integer s'il y a
		// seulement un zero apr�s la virgule (rendant celle-ci inutile � l'affichage)
		String output = CommonFunctions.convertDoubleOutputToIntegerIfPossible(doubleOutput);

		return output;
	}

}
