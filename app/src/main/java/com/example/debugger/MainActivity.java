package com.example.debugger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * SimpleCalc is the calculator application used for debugging practice.
 * It demonstrates debugging with breakpoints, variable inspection, step execution,
 * Logcat inspection, and error handling for empty inputs and division by zero.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CalculatorActivity";

    private Calculator mCalculator;

    private EditText mOperandOneEditText;
    private EditText mOperandTwoEditText;

    private TextView mResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize the calculator class and all the views
        mCalculator = new Calculator();
        mResultTextView = findViewById(R.id.operation_result_text_view);
        mOperandOneEditText = findViewById(R.id.operand_one_edit_text);
        mOperandTwoEditText = findViewById(R.id.operand_two_edit_text);
    }

    /**
     * OnClick method called when the add Button is pressed.
     */
    public void onAdd(View view) {
        compute(Calculator.Operator.ADD);
    }

    /**
     * OnClick method called when the subtract Button is pressed.
     */
    public void onSub(View view) {
        compute(Calculator.Operator.SUB);
    }

    /**
     * OnClick method called when the divide Button is pressed.
     */
    public void onDiv(View view) {
        try {
            compute(Calculator.Operator.DIV);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException", iae);
            mResultTextView.setText(getString(R.string.computationError));
            mOperandTwoEditText.setError(getString(R.string.div_by_zero_error));
        }
    }

    /**
     * OnClick method called when the multiply Button is pressed.
     */
    public void onMul(View view) {
        compute(Calculator.Operator.MUL);
    }

    private void compute(Calculator.Operator operator) {
        double operandOne;
        double operandTwo;
        try {
            operandOne = getOperand(mOperandOneEditText);
            operandTwo = getOperand(mOperandTwoEditText);
        } catch (NumberFormatException nfe) {
            Log.e(TAG, "NumberFormatException", nfe);
            // Obravnava praznega ali neveljavnega vnosa: opozorilo uporabniku
            if (getOperandText(mOperandOneEditText).trim().isEmpty()) {
                mOperandOneEditText.setError(getString(R.string.empty_operand_error));
            }
            if (getOperandText(mOperandTwoEditText).trim().isEmpty()) {
                mOperandTwoEditText.setError(getString(R.string.empty_operand_error));
            }
            mResultTextView.setText(getString(R.string.computationError));
            return;
        }

        String result;
        try {
            switch (operator) {
                case ADD:
                    result = String.valueOf(
                            mCalculator.add(operandOne, operandTwo));
                    break;
                case SUB:
                    result = String.valueOf(
                            mCalculator.sub(operandOne, operandTwo));
                    break;
                case DIV:
                    result = String.valueOf(
                            mCalculator.div(operandOne, operandTwo));
                    break;
                case MUL:
                    result = String.valueOf(
                            mCalculator.mul(operandOne, operandTwo));
                    break;
                default:
                    result = getString(R.string.computationError);
                    break;
            }
            mResultTextView.setText(result);
        } catch (IllegalArgumentException iae) {
            Log.e(TAG, "IllegalArgumentException: Division by zero", iae);
            mResultTextView.setText(getString(R.string.computationError));
            mOperandTwoEditText.setError(getString(R.string.div_by_zero_error));
        }
    }

    /**
     * @return the operand value entered in an EditText as double.
     */
    private static Double getOperand(EditText operandEditText) {
        String operandText = getOperandText(operandEditText);
        return Double.valueOf(operandText);
    }

    /**
     * @return the operand text which was entered in an EditText.
     */
    private static String getOperandText(EditText operandEditText) {
        return operandEditText.getText().toString();
    }
}
