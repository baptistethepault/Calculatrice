package utils;

public abstract class Utils {

	public enum OPERATOR {
		ADDITION, SOUSTRACTION, MULTIPLICATION, DIVISION, PUISSANCE;

		public Character getValue() {
			Character value = null;
			switch (this) {
			case ADDITION:
				value = '+';
				break;
			case SOUSTRACTION:
				value = '-';
				break;
			case MULTIPLICATION:
				value = '*';
				break;
			case DIVISION:
				value = '/';
				break;
			case PUISSANCE:
				value = '^';
				break;
			}
			return value;
		}
	}

	public enum FUNCTION {
		RACINE_CARREE, SINUS, COSINUS, TANGENTE;

		public String getValue() {
			String value = null;
			switch (this) {
			case RACINE_CARREE:
				value = "sqrt";
				break;
			case SINUS:
				value = "sin";
				break;
			case COSINUS:
				value = "cos";
				break;
			case TANGENTE:
				value = "tan";
				break;
			}
			return value;
		}
	}

	public enum PARENTHESE {
		PARENTHESE_OUVERTE, PARENTHESE_FERMEE;

		public Character getValue() {
			Character value = null;
			switch (this) {
			case PARENTHESE_OUVERTE:
				value = '(';
				break;
			case PARENTHESE_FERMEE:
				value = ')';
				break;
			}
			return value;
		}
	}
}
