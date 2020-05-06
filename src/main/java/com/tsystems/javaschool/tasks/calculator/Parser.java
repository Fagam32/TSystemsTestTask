package com.tsystems.javaschool.tasks.calculator;

import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;


public class Parser {
    private Stack<String> operationsStack;
    private Stack<String> notationStack;


    public void compile(String statement) {
        if (statement == null)
            return;

        operationsStack = new Stack<>();
        notationStack = new Stack<>();

        statement = statement.replace(" ", "");

        if (statement.length() == 0)
            return;

        if (statement.charAt(0) == '-')
            statement = "0" + statement;

        StringTokenizer tokenizer = new StringTokenizer(statement, Constants.DELIMITERS, true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            try {
                parseToken(token);
            } catch (Exception ignored) {
            }
        }

        //push all operations from operationsStack to output
        while (!operationsStack.isEmpty()) {
            if (operationsStack.peek().equals("(")) {
                notationStack = null;
                return;
            }
            notationStack.push(operationsStack.pop());
        }
        Collections.reverse(operationsStack);
    }

    public String evaluate() {
        Stack<Double> calculationStack = new Stack<>();
        if (notationStack == null || notationStack.isEmpty())
            return null;

        for (String s : notationStack) {
            if (isDigit(s))
                calculationStack.push(Double.valueOf(s));
            else {
                if (calculationStack.size() < 2)
                    return null;
                double a = calculationStack.pop();
                double b = calculationStack.pop();
                Double operationResult = getOperationResult(b, a, s);
                if (operationResult == null)
                    return null;
                else
                    calculationStack.push(operationResult);

            }
        }
        if (calculationStack.peek().intValue() - calculationStack.peek() == 0.0)
            return String.valueOf(calculationStack.pop().intValue());
        return calculationStack.pop().toString();
    }

    private void parseToken(String token) {
        if (isDigit(token)) {
            proceedDigit(token);
            return;
        }
        if (isOperator(token)) {
            proceedOperator(token);
            return;
        }
        if (isOpenBracket(token)) {
            proceedOpenBracket(token);
            return;
        }
        if (isCloseBracket(token)) {
            proceedCloseBracket(token);
            return;
        }
    }


    private void proceedOpenBracket(String token) {
        operationsStack.push(token);
    }

    //push all operations from operationStack to output until open bracket is found
    private void proceedCloseBracket(String token) {
        while (!operationsStack.isEmpty()) {
            String stackOperation = operationsStack.peek();
            if (stackOperation.equals("(")) {
                operationsStack.pop();
                break;
            } else {
                notationStack.push(operationsStack.pop());
            }
        }
    }


    //push all operations from the top of operationStack with higher or the same priority as current operation
    private void proceedOperator(String operation) {
        while (!operationsStack.isEmpty()) {
            String stackOperation = operationsStack.peek();
            if (isOperator(stackOperation) && priorityOf(operation) <= priorityOf(stackOperation))
                notationStack.push(operationsStack.pop());
            else break;
        }
        operationsStack.push(operation);
    }

    private int priorityOf(String operation) {
        if (operation.equals("(") || operation.equals(")"))
            return 0;
        else if (operation.equals("+") || operation.equals("-"))
            return 1;
        return 2;
    }

    private void proceedDigit(String token) {
        notationStack.push(token);
    }

    private boolean isOpenBracket(String token) {
        return token.equals("(");
    }

    private boolean isCloseBracket(String token) {
        return token.equals(")");
    }

    private boolean isOperator(String token) {
        return Constants.OPERATORS.contains(token);
    }

    private Double getOperationResult(Double value1, Double value2, String s) {
        switch (s) {
            case "+":
                return value1 + value2;
            case "-":
                return value1 - value2;
            case "*":
                return value1 * value2;
            case "/":
                if (value2 != 0.0) return value1 / value2;
                else return null;
        }
        return null;
    }

    //Control flow but it is necessary
    private boolean isDigit(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException ignored) {
        }
        return false;
    }
}
