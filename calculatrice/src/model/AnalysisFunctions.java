package model;

import error.ExceptionMessages;
import error.MyException;
import utils.Utils;

public class AnalysisFunctions {

	private String input;

	// Fonction g�n�rale de la classe qui prend en param�tre un String et renvoie un
	// double ou une exception
	public double analysis(final String input) {
		this.input = input;
		return globalAnalysis();
	}

	int position = -1;
	// character est un int et pas un char car on lui fixe comme valeur d'erreur -1
	// et '-1' ne peut pas �tre une valeur de char. On utilise donc la valeur
	// d�cimale des chars de la table ASCII (de 48 � 57 pour les chiffres et de 97 �
	// 122 pour les lettres minuscules, 40 = ( , 41 = ) , 46 = .)
	// Cette comparaison est automatique entre un int et un char
	int character = -1;

	// On avance au caract�re suivant si la position du caract�re courant +1 ne
	// d�passe pas la taille de l'expression en entr�e
	private void nextCharacter() {
		character = (++position < input.length()) ? input.charAt(position) : -1;
	}

	// On compare la valeur d�cimale du caract�re courant avec la valeur d�cimale
	// du caract�re en param�tre
	// Si c'est la m�me on renvoie true et on avance d'un caract�re dans la chaine
	// Sinon, on renvoie false et on continue l'algorithme en cours
	private boolean compareCurrentChar(int comparatedChar) {
		if (character == comparatedChar) {
			nextCharacter();
			return true;
		}
		return false;
	}

	// Renvoie true si le caract�re courant est un chiffre (le '.' sert de
	// s�parateur dans un double)
	private boolean isNumber() {
		return ((character >= '0' && character <= '9') || character == '.');
	}

	// Renvoie true si le caract�re courant est une lettre minuscule ou majuscule
	private boolean isLetter() {
		return ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z'));
	}

	// M�thode d'analyse g�n�rale de l'expression en entr�e
	private double globalAnalysis() {
		// On analyse le premier caract�re de la chaine
		nextCharacter();
		double x = expressionAnalysis();
		// On v�rifie qu'il n'y a pas de terme restant qu'on ne peut pas analyser
		// (exemple '!')
		if (position < input.length()) {
			throw new MyException(ExceptionMessages.SYNTAX_ERROR);
		}
		return x;
	}

	// M�thode d'analyse au plus haut niveau (2). X sera le premier terme � gauche
	// On additionne et on soustrait X � son terme de droite ici
	private double expressionAnalysis() {
		double x = termAnalysis();
		while (true) {
			if (compareCurrentChar(Utils.OPERATOR.ADDITION.getValue())) {
				x = x + termAnalysis(); // on ajoute x et son terme de droite
			} else if (compareCurrentChar(Utils.OPERATOR.SOUSTRACTION.getValue())) {
				x = x - termAnalysis(); // on soustrait le terme de droite � x
			} else {
				return x; // fin du calcul de la sous-expression ou de l'expression totale
			}
		}
	}

	// M�thode d'analyse au niveau (1). X est encore le terme � gauche et on
	// multiplie et on divise X � son terme de droite ici (niveau 1 car * et / ont
	// la priorit� sur + et -)
	private double termAnalysis() {
		double x = factorAnalysis();
		while (true) {
			// TODO Utiliser BigDecimal pour ne pas perdre en pr�cision lors de la multiplication ou la division
			if (compareCurrentChar(Utils.OPERATOR.MULTIPLICATION.getValue())) {
				x = x * factorAnalysis(); // On multiplie x et son terme de droite
			} else if (compareCurrentChar(Utils.OPERATOR.DIVISION.getValue())) {
				x = x / (factorAnalysis()); // On divise x par son terme de droite
			}
			// Si le r�sultat est �gal � l'infini (positive ou n�gative), c'est qu'on a
			// divis� par z�ro
			else if (Double.isInfinite(x)) {
				throw new MyException(ExceptionMessages.DIVISION_BY_ZERO);
			} else {
				return x; // fin de calcul du terme
			}
		}
	}

	// M�thode d'analyse du plus bas niveau (0)
	private double factorAnalysis() {
		// On cherche si l'expression g�n�rale ou la sous-expression commence par '+'
		// ou '-'
		if (compareCurrentChar(Utils.OPERATOR.ADDITION.getValue())) {
			return factorAnalysis(); // Il y a un '+' devant l'expression g�n�rale ou la sous-expression
		}
		if (compareCurrentChar(Utils.OPERATOR.SOUSTRACTION.getValue())) {
			return -factorAnalysis(); // Il y a un '-' devant l'expression g�n�rale ou la sous-expression
		}

		double x;
		int startPosition = this.position;
		if (compareCurrentChar(Utils.PARENTHESE.PARENTHESE_OUVERTE.getValue())) {
			// C'est une sous-expression entre parenth�ses qu'il faut calculer avant de
			// passer a la suite
			x = expressionAnalysis();
			compareCurrentChar(Utils.PARENTHESE.PARENTHESE_FERMEE.getValue());
		} else if (isNumber()) { // V�rifie si le caract�re courant est un chiffre
			while (isNumber()) {
				// On d�file tant qu'on a un chiffre en analyse
				nextCharacter();
			}
			// On r�cup�re le double analys�
			x = Double.parseDouble(input.substring(startPosition, this.position));
		} else if (isLetter()) { // V�rifie si le caract�re courant est une lettre
			while (isLetter()) {
				// On d�file tant qu'on a une lettre en analyse. Cette suite de lettres sera
				// notre fonction
				nextCharacter();
			}
			// Passage en minuscule car les fonctions math�matiques java sont en minuscules
			String function = input.substring(startPosition, this.position).toLowerCase();
			// On d�termine quelle variable num�rique est pass�e en param�tre de la fonction
			// obtenue
			x = factorAnalysis();
			// On applique la fonction correspondante � x
			if (function.equals(Utils.FUNCTION.RACINE_CARREE.getValue())) {
				x = Math.sqrt(x);
			} else if (function.equals(Utils.FUNCTION.SINUS.getValue())) {
				x = Math.sin(Math.toRadians(x));
			} else if (function.equals(Utils.FUNCTION.COSINUS.getValue())) {
				x = Math.cos(Math.toRadians(x));
			} else if (function.equals(Utils.FUNCTION.TANGENTE.getValue())) {
				x = Math.tan(Math.toRadians(x));
			}
			// Si la fonction n'est pas d�finie dans la classe Utils, on renvoie une erreur
			else {
				throw new MyException(ExceptionMessages.UNKNOWN_FUNCTION + function);
			}
		}
		// Il reste un op�rateur qu'on peut analyser sans terme ensuite (exemple 2^ ou
		// 1+) ou avec un terme non attendu (exemple 2^t)
		else {
			throw new MyException(ExceptionMessages.SYNTAX_ERROR);
		}

		// Le calcul de la puissance fait suite � une analyse de chiffre o� on a
		// r�cup�r� x
		if (compareCurrentChar(Utils.OPERATOR.PUISSANCE.getValue())) {
			x = Math.pow(x, factorAnalysis()); // x devient x � la puissance terme suivant
		}

		return x; // On renvoie x qui est le terme de plus bas niveau
	}

}
