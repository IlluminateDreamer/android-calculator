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
    private String Query = "";
    private String operator = "";
    private double operand1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference to the display TextView
        display = findViewById(R.id.txtInput);
        display.setText("0.0");

        // Number buttons
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

        // Operator buttons
        Button addButton = findViewById(R.id.btnAdd);
        Button subtractButton = findViewById(R.id.btnSubtract);
        Button multiplyButton = findViewById(R.id.btnMultiply);
        Button divideButton = findViewById(R.id.btnDivide);
        Button decimalButton = findViewById(R.id.btnDecimal);
        Button equalsButton = findViewById(R.id.btnEquals);
        Button clearButton = findViewById(R.id.btnClear);
        Button backspaceButton = findViewById(R.id.btnBack);

        // Set click listeners for number buttons
        View.OnClickListener numberClickListener = v -> {
            Button b = (Button) v;
            currentInput += b.getText();
            Query += currentInput;
            display.setText(Query);
        };

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
        decimalButton.setOnClickListener(numberClickListener);

        // Operator click listeners
        View.OnClickListener operatorClickListener = v -> {
            if (!currentInput.isEmpty()) {
                operand1 = Double.parseDouble(currentInput);
                System.out.println(operand1);
                Button b = (Button) v;
                operator = b.getText().toString();
                Query += operator;
                currentInput = "";
                display.setText(Query);
            }
        };

        addButton.setOnClickListener(operatorClickListener);
        subtractButton.setOnClickListener(operatorClickListener);
        multiplyButton.setOnClickListener(operatorClickListener);
        divideButton.setOnClickListener(operatorClickListener);

        // Equals button
        equalsButton.setOnClickListener(v -> {
            if (!currentInput.isEmpty() && !operator.isEmpty()) {
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
                            return;
                        }
                        break;
                }
                display.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                operator = "";
                Query = currentInput;
            }
        });

        // Clear button
        clearButton.setOnClickListener(v -> {
            currentInput = "";
            operator = "";
            operand1 = 0;
            Query = "";
            display.setText("0.0");
        });

        // Backspace button
        backspaceButton.setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                display.setText(currentInput);
            }
        });
    }
}
