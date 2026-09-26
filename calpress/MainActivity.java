package com.example.calpress;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView screenOutput;

    private LinearLayout historyContainer;

    private RecyclerView recyclerHistory;

    private MaterialButton btnHistory;
    private MaterialButton btnClear;
    private MaterialButton btnClearHistory;

    private MaterialButton btnDelete;
    private MaterialButton btnPercent;
    private MaterialButton btnOpenParen;
    private MaterialButton btnCloseParen;

    private MaterialButton btnDivide;
    private MaterialButton btnMultiply;
    private MaterialButton btnSubtract;
    private MaterialButton btnAdd;

    private MaterialButton btn0;
    private MaterialButton btn1;
    private MaterialButton btn2;
    private MaterialButton btn3;
    private MaterialButton btn4;
    private MaterialButton btn5;
    private MaterialButton btn6;
    private MaterialButton btn7;
    private MaterialButton btn8;
    private MaterialButton btn9;

    private MaterialButton btnDot;
    private MaterialButton btnEquals;

    private CalDatabase calDatabase;
    private HistoryAdapter historyAdapter;

    private boolean showingDeveloperInfo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initializeViews();

        calDatabase = new CalDatabase(this);

        setupHistory();

        setupButtons();

        loadHistory();
    }

    private void initializeViews() {

        screenOutput = findViewById(
                R.id.id_screenOutput
        );

        historyContainer = findViewById(
                R.id.historyContainer
        );

        recyclerHistory = findViewById(
                R.id.recyclerHistory
        );

        btnHistory = findViewById(
                R.id.btnHistory
        );

        btnClear = findViewById(
                R.id.btnClear
        );

        btnClearHistory = findViewById(
                R.id.btnClearHistory
        );

        btnDelete = findViewById(
                R.id.btnDelete
        );

        btnPercent = findViewById(
                R.id.btnPercent
        );

        btnOpenParen = findViewById(
                R.id.btnOpenParen
        );

        btnCloseParen = findViewById(
                R.id.btnCloseParen
        );

        btnDivide = findViewById(
                R.id.btnDivide
        );

        btnMultiply = findViewById(
                R.id.btnMultiply
        );

        btnSubtract = findViewById(
                R.id.btnSubtract
        );

        btnAdd = findViewById(
                R.id.btnAdd
        );

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnDot = findViewById(
                R.id.btnDot
        );

        btnEquals = findViewById(
                R.id.btnEquals
        );
    }

    private void setupHistory() {

        recyclerHistory.setLayoutManager(
                new LinearLayoutManager(this)
        );

        historyAdapter = new HistoryAdapter(
                new ArrayList<>()
        );

        recyclerHistory.setAdapter(
                historyAdapter
        );
    }

    private void setupButtons() {

        btn0.setOnClickListener(
                v -> addToScreen("0")
        );

        btn1.setOnClickListener(
                v -> addToScreen("1")
        );

        btn2.setOnClickListener(
                v -> addToScreen("2")
        );

        btn3.setOnClickListener(
                v -> addToScreen("3")
        );

        btn4.setOnClickListener(
                v -> addToScreen("4")
        );

        btn5.setOnClickListener(
                v -> addToScreen("5")
        );

        btn6.setOnClickListener(
                v -> addToScreen("6")
        );

        btn7.setOnClickListener(
                v -> addToScreen("7")
        );

        btn8.setOnClickListener(
                v -> addToScreen("8")
        );

        btn9.setOnClickListener(
                v -> addToScreen("9")
        );

        btnDot.setOnClickListener(
                v -> addToScreen(".")
        );

        btnAdd.setOnClickListener(
                v -> addToScreen("+")
        );

        btnSubtract.setOnClickListener(
                v -> addToScreen("-")
        );

        btnMultiply.setOnClickListener(
                v -> addToScreen("×")
        );

        btnDivide.setOnClickListener(
                v -> addToScreen("÷")
        );

        btnPercent.setOnClickListener(
                v -> addToScreen("%")
        );

        btnOpenParen.setOnClickListener(
                v -> addToScreen("(")
        );

        btnCloseParen.setOnClickListener(
                v -> addToScreen(")")
        );

        btnDelete.setOnClickListener(
                v -> deleteLastCharacter()
        );

        btnClear.setOnClickListener(
                v -> clearScreen()
        );

        btnEquals.setOnClickListener(
                v -> calculate()
        );

        btnHistory.setOnClickListener(
                v -> toggleHistory()
        );

        btnClearHistory.setOnClickListener(
                v -> clearHistory()
        );
    }

    private void addToScreen(String value) {

        String current =
                screenOutput.getText().toString();

        if (current.equals("Syntax Error")) {
            current = "";
        }

        screenOutput.setText(
                current + value
        );
    }

    private void deleteLastCharacter() {

        String current =
                screenOutput.getText().toString();

        if (showingDeveloperInfo) {

            showingDeveloperInfo = false;

            screenOutput.setText("");

            return;
        }

        if (current.equals("Syntax Error")) {

            screenOutput.setText("");

            return;
        }

        if (!current.isEmpty()) {

            screenOutput.setText(
                    current.substring(
                            0,
                            current.length() - 1
                    )
            );
        }
    }

    private void clearScreen() {

        screenOutput.setText("");
    }

    private void calculate() {

        String expression =
                screenOutput.getText().toString();

        if (expression.isEmpty()) {
            return;
        }

        if (expression.equals("10132001")) {

            showingDeveloperInfo = true;

            screenOutput.setText(
                    "Geoven Rei\n" +
                            "CalPRESS Developer\n" +
                            "Version 1.0"
            );

            return;
        }

        try {

            double result =
                    evaluateExpression(expression);

            String resultText =
                    formatResult(result);

            showingDeveloperInfo = false;

            screenOutput.setText(
                    resultText
            );

            calDatabase.insertHistory(
                    expression,
                    resultText
            );

            loadHistory();

        } catch (Exception e) {

            showingDeveloperInfo = false;

            screenOutput.setText("Syntax Error");
        }
    }

    private String formatResult(double result) {

        if (Double.isNaN(result)
                || Double.isInfinite(result)) {

            throw new ArithmeticException();
        }

        if (result == (long) result) {

            return String.valueOf(
                    (long) result
            );
        }

        return String.valueOf(result);
    }

    private void toggleHistory() {

        if (historyContainer.getVisibility()
                == View.VISIBLE) {

            historyContainer.setVisibility(
                    View.GONE
            );

        } else {

            loadHistory();

            historyContainer.setVisibility(
                    View.VISIBLE
            );
        }
    }

    private void loadHistory() {

        ArrayList<ItemHistory> history =
                calDatabase.getAllHistory();

        historyAdapter.updateData(
                history
        );
    }

    private void clearHistory() {

        calDatabase.deleteAllHistory();

        loadHistory();

        Toast.makeText(
                this,
                "History cleared",
                Toast.LENGTH_SHORT
        ).show();
    }

    private double evaluateExpression(
            String expression
    ) {

        String cleanExpression =
                expression
                        .replace("×", "*")
                        .replace("÷", "/")
                        .replace(" ", "");

        ExpressionParser parser =
                new ExpressionParser(
                        cleanExpression
                );

        double result =
                parser.parse();

        if (!parser.isAtEnd()) {

            throw new IllegalArgumentException();
        }

        return result;
    }

    private static class ExpressionParser {

        private final String expression;

        private int position = 0;

        ExpressionParser(String expression) {

            this.expression = expression;
        }

        double parse() {

            double result =
                    parseExpression();

            skipSpaces();

            return result;
        }

        private double parseExpression() {

            double result =
                    parseTerm();

            while (true) {

                skipSpaces();

                if (match('+')) {

                    result += parseTerm();

                } else if (match('-')) {

                    result -= parseTerm();

                } else {

                    break;
                }
            }

            return result;
        }

        private double parseTerm() {

            double result =
                    parseFactor();

            while (true) {

                skipSpaces();

                if (match('*')) {

                    result *= parseFactor();

                } else if (match('/')) {

                    double divisor =
                            parseFactor();

                    if (divisor == 0) {

                        throw new ArithmeticException();
                    }

                    result /= divisor;

                } else if (isImplicitMultiplication()) {

                    result *= parseFactor();

                } else {

                    break;
                }
            }

            return result;
        }

        private boolean isImplicitMultiplication() {

            if (position >= expression.length()) {
                return false;
            }

            char current =
                    expression.charAt(position);

            return current == '('
                    || Character.isDigit(current)
                    || current == '.';
        }

        private double parseFactor() {

            skipSpaces();

            if (match('+')) {

                return parseFactor();
            }

            if (match('-')) {

                return -parseFactor();
            }

            double result;

            if (match('(')) {

                result =
                        parseExpression();

                if (!match(')')) {

                    throw new IllegalArgumentException();
                }

            } else {

                result =
                        parseNumber();
            }

            skipSpaces();

            if (match('%')) {

                result =
                        result / 100.0;
            }

            return result;
        }

        private double parseNumber() {

            skipSpaces();

            int start =
                    position;

            boolean hasDecimal = false;

            while (
                    position < expression.length()
            ) {

                char c =
                        expression.charAt(
                                position
                        );

                if (Character.isDigit(c)) {

                    position++;

                } else if (
                        c == '.'
                                && !hasDecimal
                ) {

                    hasDecimal = true;

                    position++;

                } else {

                    break;
                }
            }

            if (start == position) {

                throw new IllegalArgumentException();
            }

            return Double.parseDouble(
                    expression.substring(
                            start,
                            position
                    )
            );
        }

        private boolean match(
                char expected
        ) {

            if (
                    position < expression.length()
                            && expression.charAt(
                            position
                    ) == expected
            ) {

                position++;

                return true;
            }

            return false;
        }

        private void skipSpaces() {

            while (
                    position < expression.length()
                            && Character.isWhitespace(
                            expression.charAt(
                                    position
                            )
                    )
            ) {

                position++;
            }
        }

        boolean isAtEnd() {

            skipSpaces();

            return position >=
                    expression.length();
        }
    }
}