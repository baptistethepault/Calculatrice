package model;

import error.MyException;

public class CalculatriceModel {

	// Calcule l'opération en input et renvoie un string contenant le résultat
	public String compute(String input) throws MyException {

		// Retire tous les espaces de l'input
		String inputWithoutSpaces = CommonFunctions.removeSpaceFromInput(input);

		// Simplifie au maximum toutes les suites d'opérations référencées dans
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

		// Compare le nombre de parenthèses ouvertes et le nombre de parenthèses fermées
		// Si ces nombres sont inégaux, on renvoie une erreur, sinon on continue
		// Le faire avant l'analyse de l'expression générale permet de gagner en temps
		// lors de l'exécution
		try {
			simpleOperatorsInput = CommonFunctions.compareOpenedParenthesisAndClosedParenthesis(simpleOperatorsInput);
		} catch (Exception e) {
			throw e;
		}

		double doubleOutput;
		try {
			// Analyse la chaine de caractères un par un en respectant la priorité des
			// opérateurs et en réalisant les opérations successives
			AnalysisFunctions analysisFunctions = new AnalysisFunctions();
			doubleOutput = analysisFunctions.analysis(simpleOperatorsInput);
		} catch (Exception e) {
			throw e;
		}

		// Convertit le résultat obtenu sous la forme d'un double en integer s'il y a
		// seulement un zero après la virgule (rendant celle-ci inutile à l'affichage)
		String output = CommonFunctions.convertDoubleOutputToIntegerIfPossible(doubleOutput);

		return output;
	}

}
