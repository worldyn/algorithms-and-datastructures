import java.util.EmptyStackException;

/**
 * The Postfix class implements an evaluator for integer postfix expressions.
 *
 * Postfix notation is a simple way to define and write arithmetic expressions
 * without the need for parentheses or priority rules. For example, the postfix
 * expression "1 2 - 3 4 + *" corresponds to the ordinary infix expression
 * "(1 - 2) * (3 + 4)". The expressions may contain decimal 32-bit integer
 * operands and the four operators +, -, *, and /. Operators and operands must
 * be separated by whitespace.
 *
 * @author  Adam Jacobs
 * @version Jan 2017
 */
public class Postfix {
	private static class ExpressionException extends Exception {
		public ExpressionException(String message) {
			super(message);
		}
	}

	/**
	 * Evaluates the given postfix expression.
	 * 
	 * @param expr  Arithmetic expression in postfix notation
	 * @return      The value of the evaluated expression
	 * @throws      ExpressionException if the expression is wrong
	 */
	public static int evaluate(String expr) throws ExpressionException {
        if(expr == null || expr.length() == 0) {
            throw new ExpressionException("The expression is invalid.");
        }

        StackImplementation<Integer> stack = new StackImplementation<>();

        // remove unnecessary white space
        expr = expr.trim();

        for (String current : expr.split("\\s+")) {
            if (isOperator(current)) {
                Integer first;
                Integer second;

                try {
                    first = stack.pop();
                    second = stack.pop();
                } catch (Exception e) {
                    throw new ExpressionException("The expression is invalid. Did not find two numbers before operator");
                }

                switch(current) {
                    case "+":
                        stack.push(second + first);
                        break;
                    case "-":
                        stack.push(second - first);
                        break;
                    case "*":
                        stack.push(second * first);
                        break;
                    case "/":
                        if(first == 0) {
                            throw new ExpressionException("The expression is invalid. Division by zero");
                        }
                        stack.push(second / first);
                        break;
                }
            } else if(isInteger(current)) {
                try {
                    stack.push(Integer.parseInt(current));
                } catch(NumberFormatException e) {
                    throw new ExpressionException("The expression is invalid. Could not parse number in expression");
                }
            } else {
                throw new ExpressionException("Expression is invalid. Found a non-integer or non-operator in expression.");
            }
        }

        if(stack.size() == 1) {
            try {
                return stack.pop();
            } catch (Exception e) {
                throw new ExpressionException("Expression is invalid");
            }
        } else {
            throw new ExpressionException("Expression is invalid");
        }

	}
	/**
	 * Returns true if s is an operator.
	 *
	 * A word of caution on using the String.matches method: it returns true
	 * if and only if the whole given string matches the regex. Therefore
	 * using the regex "[0-9]" is equivalent to "^[0-9]$".
	 *
	 * An operator is one of '+', '-', '*', '/'.
	 */
	private static boolean isOperator(String s) {
        return s.matches("^[+\\-*\\/]$");
	}
	
	/**
	 * Returns true if s is an integer.
	 *
	 * A word of caution on using the String.matches method: it returns true
	 * if and only if the whole given string matches the regex. Therefore
	 * using the regex "[0-9]" is equivalent to "^[0-9]$".
	 *
	 * We accept two types of integers:
	 *
	 * - the first type consists of an optional '-' 
	 *   followed by a non-zero digit
	 *   followed by zero or more digits,
	 *
	 * - the second type consists of an optional '-'
	 *   followed by a single '0'.
	 */
	private static boolean isInteger(String s) {
        return s.matches("^-?(0|[1-9][0-9]*)$");
	}
	
	/**
	 * Unit test. Run with "java -ea Postfix".
	 */
	public static void main(String[] args) throws ExpressionException {
		assert evaluate("0") == 0;
        assert evaluate("-0") == -0;
		assert evaluate("1234567890") == 1234567890;
		assert evaluate("-1234567890") == -1234567890;
		assert evaluate("1 23 +") == 1 + 23;
		assert evaluate("1	23	+") == 1 + 23; // tabs instead of spaces
		assert evaluate("0 1 /") == 0 / 1;
		assert evaluate("1 2 + -3 *") == (1 + 2) * -3;
		assert evaluate("12 34 - 56 -78 + *") == (12 - 34) * (56 + -78);
		assert evaluate("1 2 + 3 * 4 - 5 /") == (((1 + 2) * 3) - 4) / 5;
		assert evaluate("2 3 4 -0 + - *") == 2 * (3 - (4 + -0));
		assert evaluate("  		1 	-2	 + ") == 1 - 2; // tabs and spaces

		assert explodes("");
		assert explodes("+");
		assert explodes("--1");
		assert explodes("-1-0");
		assert explodes("-0-1");
		assert explodes("1 +");
		assert explodes("1 2 ,");
		assert explodes("1 2 .");
		assert explodes("1 2 3 +");
		assert evaluate("4") == 4;
		assert explodes("1 2 + +");
		assert explodes("017");
		assert explodes("0x17");
		assert explodes("-03");
		assert explodes("x");
		assert explodes("1234L");
		assert explodes("9876543210"); // larger than maxint
		assert explodes("1 0 /");
		assert explodes("1 2+");
		assert explodes("1 2 3 +*");
	}
	
	/**
	 * Returns true if <code>evaluate(expr)</code> throws
	 * a subclass of RuntimeException.
	 */
	private static boolean explodes(String expr) {
		try {
			evaluate(expr);
		} catch (ExpressionException e) {
			return true;
		}
		return false;
	}
}
