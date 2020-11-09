package model;

import error.ExceptionMessages;
import error.MyException;
import utils.Utils;

public class CommonFunctions {

	public static String doublePlus = "++";
	public static String doubleMinus = "--";
	public static String plusAndMinus = "+-";
	public static String minusAndPlus = "-+";
	public static String multiAndPlus = "*+";
	public static String divAndPlus = "/+";
	public static String decimalSeparator = ".";

	// Retire tous les espaces de l'input
	public static String removeSpaceFromInput(String input) {
		String res = input.replace(" ", "");
		return res;
	}

	// Remplace les suites d'opérations par l'opération la plus simple possible
	public static String concatenateOperators(String input) {
		String res = input.replace(doublePlus, "+");
		res = res.replace(doubleMinus, "+");
		res = res.replace(plusAndMinus, "-");
		res = res.replace(minusAndPlus, "-");
		res = res.replace(multiAndPlus, "*");
		res = res.replace(divAndPlus, "/");
		return res;
	}

	// Convertit le résultat obtenu sous la forme d'un double en integer s'il y a
	// seulement un zéro après la virgule (rendant celle-ci inutile à l'affichage)
	public static String convertDoubleOutputToIntegerIfPossible(Double doubleOutput) {
		String uselessZeroRegex = "-?\\d+(\\.0)?";
		String doubleOutputString = String.valueOf(doubleOutput);
		String output = "";
		if (doubleOutputString.matches(uselessZeroRegex)) {
			int indexOfCut = doubleOutputString.indexOf(decimalSeparator);
			output = doubleOutputString.substring(0, indexOfCut);
		} else {
			output = doubleOutputString;
		}
		return output;
	}

	// Compare le nombre de parenthèses ouvertes et le nombre de parenthèses fermées
	// Si ces nombres sont inégaux, on renvoie une erreur, sinon on continue
	public static String compareOpenedParenthesisAndClosedParenthesis(String input) {
		int counterOpenedParenthesis = 0;
		int counterClosedParenthesis = 0;
		char[] inputChars = input.toCharArray();
		for (char c : inputChars) {
			if (c == Utils.PARENTHESE.PARENTHESE_OUVERTE.getValue()) {
				counterOpenedParenthesis++;
			}
			if (c == Utils.PARENTHESE.PARENTHESE_FERMEE.getValue()) {
				counterClosedParenthesis++;
			}
		}

		if (counterOpenedParenthesis == counterClosedParenthesis) {
			return input;
		} else {
			throw new MyException(ExceptionMessages.WRONG_NUMBER_OF_PARENTHESIS);
		}
	}

}
