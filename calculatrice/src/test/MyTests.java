package test;

import static org.junit.Assert.assertThrows;

import error.ExceptionMessages;
import error.MyException;
import junit.framework.TestCase;
import model.CalculatriceModel;

public class MyTests extends TestCase {

	CalculatriceModel modelToTest = new CalculatriceModel();

	// TODO Compacter tous les tests en une fonction. Ce n'est pas le cas car les tests s'arretent à la première erreur
	// Les différents cas de tests fournis sur le github
	public void testCompute1() throws Exception {
		assertEquals("2", modelToTest.compute("1+1"));
	}

	public void testCompute2() throws Exception {
		assertEquals("3", modelToTest.compute("1 + 2"));
	}

	public void testCompute3() throws Exception {
		assertEquals("0", modelToTest.compute("1 + -1"));
	}

	public void testCompute4() throws Exception {
		assertEquals("0", modelToTest.compute("-1 - -1"));
	}

	public void testCompute5() throws Exception {
		assertEquals("1", modelToTest.compute("5-4"));
	}

	public void testCompute6() throws Exception {
		assertEquals("10", modelToTest.compute("5*2"));
	}

	public void testCompute7() throws Exception {
		assertEquals("21", modelToTest.compute("(2+5)*3"));
	}

	public void testCompute8() throws Exception {
		assertEquals("5", modelToTest.compute("10/2"));
	}

	public void testCompute9() throws Exception {
		assertEquals("17", modelToTest.compute("2+2*5+5"));
	}

	public void testCompute10() throws Exception {
		assertEquals("7.4", modelToTest.compute("2.8*3-1"));
	}

	public void testCompute11() throws Exception {
		assertEquals("256", modelToTest.compute("2^8"));
	}

	public void testCompute12() throws Exception {
		assertEquals("1279", modelToTest.compute("2^8*5-1"));
	}

	public void testCompute13() throws Exception {
		assertEquals("2", modelToTest.compute("sqrt(4)"));
	}

	public void testCompute14() throws Exception {
		testException("1/0", ExceptionMessages.DIVISION_BY_ZERO);
	}

	// Quelques autres cas des tests personnalisés
	// Fonction cosinus (en minuscule)
	public void testOptionalCompute1() throws Exception {
		assertEquals("1", modelToTest.compute("cos(0)"));
	}

	// Fonction sinus (en majuscule)
	public void testOptionalCompute2() throws Exception {
		assertEquals("0", modelToTest.compute("SIN(0)"));
	}

	// Fonction tangente (en minuscule et majuscule)
	public void testOptionalCompute3() throws Exception {
		assertEquals("0", modelToTest.compute("TaN(0)"));
	}

	// Nombre à virgule flottante base 2
	public void testOptionalCompute4() throws Exception {
		assertEquals("14", modelToTest.compute("+1 * 1.75 * 2^3"));
	}

	// Nombre à virgule flottante base 10
	public void testOptionalCompute5() throws Exception {
		assertEquals("-123456.7", modelToTest.compute("-1 * 1.234567 * 10^5"));
	}

	// Ceci n'est pas une expression mathématique, d'où l'erreur
	public void testOptionalCompute6() throws Exception {
		testException("1+theseAreLetters", ExceptionMessages.SYNTAX_ERROR);
	}

	// Il n'y a pas le même nombre de parenthèses ouvertes et fermées, d'où l'erreur
	public void testOptionalCompute7() throws Exception {
		testException("(9*7)+3)", ExceptionMessages.WRONG_NUMBER_OF_PARENTHESIS);
	}

	// La fonction test n'est pas définie, d'où l'erreur
	public void testOptionalCompute8() throws Exception {
		testException("test(1)", ExceptionMessages.UNKNOWN_FUNCTION + "test");
	}

	// Fonction pour tester une exception attendue
	public void testException(String input, String expectedMessage) throws Exception {
		MyException exception = assertThrows(MyException.class, () -> {
			modelToTest.compute(input);
		});

		String resultedMessage = exception.getMyMessage();
		assertTrue(resultedMessage.equals(MyException.PREFIX_MESSAGE + expectedMessage));
	}
}
