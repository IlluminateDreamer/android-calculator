package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private String currentInput = "";
    private String expression = "";
    private String operator = "";
    private double operand1 = 0;
    private boolean lastWasOperator = false;
    private boolean hasDecimal = false;
    private boolean newCalculation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize display
        display = findViewById(R.id.txtInput);
        display.setText("0.0");

        // Initialize all buttons
        Button button0 = findViewById(R.id.btnZero);
        Button button1 = findViewById(R.id.btnOne);
        Button button2 = findViewById(R.id.btnTwo);
        Button button3 = findViewById(R.id.btnThree);
        Button button4 = findViewById(R.id.btnFour);
        Button button5 = findViewById(R.id.btnFive);
        Button button6 = findViewById(R.id.btnSix);
        Button button7 = findViewById(R.id.btnSeven);
        Button button8 = findViewById(R.id.btnEight);
        Button button9 = findViewById(R.id.btnNine);
        Button addButton = findViewById(R.id.btnAdd);
        Button subtractButton = findViewById(R.id.btnSubtract);
        Button multiplyButton = findViewById(R.id.btnMultiply);
        Button divideButton = findViewById(R.id.btnDivide);
        Button decimalButton = findViewById(R.id.btnDecimal);
        Button equalsButton = findViewById(R.id.btnEquals);
        Button clearButton = findViewById(R.id.btnClear);
        Button backspaceButton = findViewById(R.id.btnBack);

        // Number button click listener
        View.OnClickListener numberClickListener = v -> {
            Button b = (Button) v;
            String digit = b.getText().toString();

            if (newCalculation) {
                clearCalculator();
                newCalculation = false;
            }

            // Start fresh after operator
            if (lastWasOperator) {
                currentInput = digit;
                lastWasOperator = false;
            } else {
                currentInput += digit;
            }

            if (expression.isEmpty() || lastWasOperator) {
                expression += digit;
            } else {
                if (!operator.isEmpty() && currentInput.length() == 1) {
                    expression += digit;
                } else {
                    expression = expression + digit;
                }
            }
            display.setText(expression);
        };

        // Assign number button listeners
        button0.setOnClickListener(numberClickListener);
        button1.setOnClickListener(numberClickListener);
        button2.setOnClickListener(numberClickListener);
        button3.setOnClickListener(numberClickListener);
        button4.setOnClickListener(numberClickListener);
        button5.setOnClickListener(numberClickListener);
        button6.setOnClickListener(numberClickListener);
        button7.setOnClickListener(numberClickListener);
        button8.setOnClickListener(numberClickListener);
        button9.setOnClickListener(numberClickListener);

        // Decimal point listener
        decimalButton.setOnClickListener(v -> {
            if (newCalculation) {
                clearCalculator();
                newCalculation = false;
            }

            if (!hasDecimal) {
                if (currentInput.isEmpty() || lastWasOperator) {
                    currentInput = "0.";
                    expression += "0.";
                } else {
                    currentInput += ".";
                    expression += ".";
                }
                display.setText(expression);
                hasDecimal = true;
                lastWasOperator = false;
            }
        });

        // Operator click listener
        View.OnClickListener operatorClickListener = v -> {
            if (!currentInput.isEmpty()) {
                Button b = (Button) v;
                String newOperator = b.getText().toString();

                if (lastWasOperator) {
                    // Replace the last operator
                    expression = expression.substring(0, expression.length() - 1) + newOperator;
                    operator = newOperator;
                } else {
                    try {
                        if (!operator.isEmpty()) {
                            calculateResult();
                            expression = currentInput + newOperator;
                        } else {
                            operand1 = Double.parseDouble(currentInput);
                            expression += newOperator;
                        }
                        operator = newOperator;
                    } catch (NumberFormatException e) {
                        Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                lastWasOperator = true;
                hasDecimal = false;
                newCalculation = false;
                display.setText(expression);
            }
        };

        // Assign operator button listeners
        addButton.setOnClickListener(operatorClickListener);
        subtractButton.setOnClickListener(operatorClickListener);
        multiplyButton.setOnClickListener(operatorClickListener);
        divideButton.setOnClickListener(operatorClickListener);

        // Equals button listener
        equalsButton.setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
                calculateResult();
                newCalculation = true;
            }
        });

        // Clear button listener
        clearButton.setOnClickListener(v -> clearCalculator());

        // Backspace button listener
        backspaceButton.setOnClickListener(v -> {
            if (!expression.isEmpty()) {
                char lastChar = expression.charAt(expression.length() - 1);
                expression = expression.substring(0, expression.length() - 1);

                if ("+-*/".indexOf(lastChar) >= 0) {
                    operator = "";
                    lastWasOperator = false;
                } else {
                    if (!currentInput.isEmpty()) {
                        currentInput = currentInput.substring(0, currentInput.length() - 1);
                        if (lastChar == '.') {
                            hasDecimal = false;
                        }
                    }
                }

                display.setText(expression.isEmpty() ? "0.0" : expression);
            }
        });
    }

    private void calculateResult() {
        try {
            double operand2 = Double.parseDouble(currentInput);
            double result = 0;

            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    if (operand2 != 0) {
                        result = operand1 / operand2;
                    } else {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                        clearCalculator();
                        return;
                    }
                    break;
            }

            // Format the result to avoid scientific notation and trailing zeros
            String resultStr = String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            currentInput = resultStr;
            expression = resultStr;
            operand1 = result;
            operator = "";
            hasDecimal = resultStr.contains(".");
            lastWasOperator = false;
            display.setText(resultStr);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            clearCalculator();
        }
    }

    private void clearCalculator() {
        currentInput = "";
        expression = "";
        operator = "";
        operand1 = 0;
        hasDecimal = false;
        lastWasOperator = false;
        display.setText("0.0");
    }
}