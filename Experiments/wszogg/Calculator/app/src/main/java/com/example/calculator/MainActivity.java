package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ZeroButPress(View view) {
        updateDisplay("0");
    }

    public void OneButPress(View view) {
        updateDisplay("1");
    }

    public void TwoButPress(View view) {
        updateDisplay("2");
    }

    public void ThreeButPress(View view) {
        updateDisplay("3");
    }

    public void FourButPress(View view) {
        updateDisplay("4");
    }

    public void FiveButPress(View view) {
        updateDisplay("5");
    }

    public void SixButPress(View view) {
        updateDisplay("6");
    }

    public void SevenButPress(View view) {
        updateDisplay("7");
    }

    public void EightButPress(View view) {
        updateDisplay("8");
    }

    public void NineButPress(View view) {
        updateDisplay("9");
    }

    public void DecButPress(View view) {
        updateDisplay(".");
    }

    public void EnterButPress(View view) {

    }

    public void ClearButPress(View view) {
        StringVals.displayMessage = "";
        updateDisplay("");
    }

    public void ModButPress(View view) {
        updateDisplay("%");
    }

    public void AddButPress(View view) {
        updateDisplay("+");
    }

    public void SubButPress(View view) {
        updateDisplay("-");
    }

    public void DivButPress(View view) {
        updateDisplay("/");
    }

    public void MultButPress(View view) {
        updateDisplay("x");
    }

    public void ExpButPress(View view) {
        updateDisplay("^");
    }

    public void BackButPress(View view) {
        if (StringVals.displayMessage.length() != 0) {
            StringVals.displayMessage = StringVals.displayMessage.subSequence(0, StringVals.displayMessage.length() - 1).toString();
        }
        updateDisplay("");
    }

    public void eButPress(View view) {
        updateDisplay("e");
    }

    public void LnButPress(View view) {
        updateDisplay("ln");
    }

    public void LeftParButPress(View view) {
        updateDisplay("(");
    }

    public void RightParButPress(View view) {
        updateDisplay(")");
    }

    public void LogButPress(View view) {
        updateDisplay("log");
    }

    public void InverseButPress(View view) {
        updateDisplay("^(-1)");
    }

    public void CosButPress(View view) {
        updateDisplay("cos");
    }

    public void SinButPress(View view) {
        updateDisplay("sin");
    }

    public void TanButPress(View view) {
        updateDisplay("tan");
    }

    public void ArcCosButPress(View view) {
        updateDisplay("acos");
    }

    public void ArcSinButPress(View view) {
        updateDisplay("asin");
    }

    public void ArcTanButPress(View view) {
        updateDisplay("atan");
    }

    protected void updateDisplay(String toAdd) {
        TextView display = (TextView) findViewById(R.id.ResultDisplay);
        StringVals.displayMessage += toAdd;
        display.setText(StringVals.displayMessage);
    }
}