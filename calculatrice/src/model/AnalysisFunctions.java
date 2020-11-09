package model;

import error.ExceptionMessages;
import error.MyException;
import utils.Utils;

public class AnalysisFunctions {

	private String input;

	// Fonction générale de la classe qui prend en paramètre un String et renvoie un
	// double ou une exception
	public double analysis(final String input) {
		this.input = input;
		return globalAnalysis();
	}

	int position = -1;
	// character est un int et pas un char car on lui fixe comme valeur d'erreur -1
	// et '-1' ne peut pas être une valeur de char. On utilise donc la valeur
	// décimale des chars de la table ASCII (de 48 à 57 pour les chiffres et de 97 à
	// 122 pour les lettres minuscules, 40 = ( , 41 = ) , 46 = .)
	// Cette comparaison est automatique entre un int et un char
	int character = -1;

	// On avance au caractère suivant si la position du caractère courant +1 ne
	// dépasse pas la taille de l'expression en entrée
	private void nextCharacter() {
		character = (++position < input.length()) ? input.charAt(position) : -1;
	}

	// On compare la valeur décimale du caractère courant avec la valeur décimale
	// du caractère en paramètre
	// Si c'est la même on renvoie true et on avance d'un caractère dans la chaine
	// Sinon, on renvoie false et on continue l'algorithme en cours
	private boolean compareCurrentChar(int comparatedChar) {
		if (character == comparatedChar) {
			nextCharacter();
			return true;
		}
		return false;
	}

	// Renvoie true si le caractère courant est un chiffre (le '.' sert de
	// séparateur dans un double)
	private boolean isNumber() {
		return ((character >= '0' && character <= '9') || character == '.');
	}

	// Renvoie true si le caractère courant est une lettre minuscule ou majuscule
	private boolean isLetter() {
		return ((character >= 'a' && character <= 'z') || (character >= 'A' && character <= 'Z'));
	}

	// Méthode d'analyse générale de l'expression en entrée
	private double globalAnalysis() {
		// On analyse le premier caractère de la chaine
		nextCharacter();
		double x = expressionAnalysis();
		// On vérifie qu'il n'y a pas de terme restant qu'on ne peut pas analyser
		// (exemple '!')
		if (position < input.length()) {
			throw new MyException(ExceptionMessages.SYNTAX_ERROR);
		}
		return x;
	}

	// Méthode d'analyse au plus haut niveau (2). X sera le premier terme à gauche
	// On additionne et on soustrait X à son terme de droite ici
	private double expressionAnalysis() {
		double x = termAnalysis();
		while (true) {
			if (compareCurrentChar(Utils.OPERATOR.ADDITION.getValue())) {
				x = x + termAnalysis(); // on ajoute x et son terme de droite
			} else if (compareCurrentChar(Utils.OPERATOR.SOUSTRACTION.getValue())) {
				x = x - termAnalysis(); // on soustrait le terme de droite à x
			} else {
				return x; // fin du calcul de la sous-expression ou de l'expression totale
			}
		}
	}

	// Méthode d'analyse au niveau (1). X est encore le terme à gauche et on
	// multiplie et on divise X à son terme de droite ici (niveau 1 car * et / ont
	// la priorité sur + et -)
	private double termAnalysis() {
		double x = factorAnalysis();
		while (true) {
			// TODO Utiliser BigDecimal pour ne pas perdre en précision lors de la multiplication ou la division
			if (compareCurrentChar(Utils.OPERATOR.MULTIPLICATION.getValue())) {
				x = x * factorAnalysis(); // On multiplie x et son terme de droite
			} else if (compareCurrentChar(Utils.OPERATOR.DIVISION.getValue())) {
				x = x / (factorAnalysis()); // On divise x par son terme de droite
			}
			// Si le résultat est égal à l'infini (positive ou négative), c'est qu'on a
			// divisé par zéro
			else if (Double.isInfinite(x)) {
				throw new MyException(ExceptionMessages.DIVISION_BY_ZERO);
			} else {
				return x; // fin de calcul du terme
			}
		}
	}

	// Méthode d'analyse du plus bas niveau (0)
	private double factorAnalysis() {
		// On cherche si l'expression générale ou la sous-expression commence par '+'
		// ou '-'
		if (compareCurrentChar(Utils.OPERATOR.ADDITION.getValue())) {
			return factorAnalysis(); // Il y a un '+' devant l'expression générale ou la sous-expression
		}
		if (compareCurrentChar(Utils.OPERATOR.SOUSTRACTION.getValue())) {
			return -factorAnalysis(); // Il y a un '-' devant l'expression générale ou la sous-expression
		}

		double x;
		int startPosition = this.position;
		if (compareCurrentChar(Utils.PARENTHESE.PARENTHESE_OUVERTE.getValue())) {
			// C'est une sous-expression entre parenthèses qu'il faut calculer avant de
			// passer a la suite
			x = expressionAnalysis();
			compareCurrentChar(Utils.PARENTHESE.PARENTHESE_FERMEE.getValue());
		} else if (isNumber()) { // Vérifie si le caractère courant est un chiffre
			while (isNumber()) {
				// On défile tant qu'on a un chiffre en analyse
				nextCharacter();
			}
			// On récupère le double analysé
			x = Double.parseDouble(input.substring(startPosition, this.position));
		} else if (isLetter()) { // Vérifie si le caractère courant est une lettre
			while (isLetter()) {
				// On défile tant qu'on a une lettre en analyse. Cette suite de lettres sera
				// notre fonction
				nextCharacter();
			}
			// Passage en minuscule car les fonctions mathématiques java sont en minuscules
			String function = input.substring(startPosition, this.position).toLowerCase();
			// On détermine quelle variable numérique est passée en paramètre de la fonction
			// obtenue
			x = factorAnalysis();
			// On applique la fonction correspondante à x
			if (function.equals(Utils.FUNCTION.RACINE_CARREE.getValue())) {
				x = Math.sqrt(x);
			} else if (function.equals(Utils.FUNCTION.SINUS.getValue())) {
				x = Math.sin(Math.toRadians(x));
			} else if (function.equals(Utils.FUNCTION.COSINUS.getValue())) {
				x = Math.cos(Math.toRadians(x));
			} else if (function.equals(Utils.FUNCTION.TANGENTE.getValue())) {
				x = Math.tan(Math.toRadians(x));
			}
			// Si la fonction n'est pas définie dans la classe Utils, on renvoie une erreur
			else {
				throw new MyException(ExceptionMessages.UNKNOWN_FUNCTION + function);
			}
		}
		// Il reste un opérateur qu'on peut analyser sans terme ensuite (exemple 2^ ou
		// 1+) ou avec un terme non attendu (exemple 2^t)
		else {
			throw new MyException(ExceptionMessages.SYNTAX_ERROR);
		}

		// Le calcul de la puissance fait suite à une analyse de chiffre où on a
		// récupéré x
		if (compareCurrentChar(Utils.OPERATOR.PUISSANCE.getValue())) {
			x = Math.pow(x, factorAnalysis()); // x devient x à la puissance terme suivant
		}

		return x; // On renvoie x qui est le terme de plus bas niveau
	}

}
