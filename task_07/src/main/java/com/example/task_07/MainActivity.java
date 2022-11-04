package com.example.task_07;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //<editor-fold desc="Constants">

    //<editor-fold desc="RUB">
    private final double RUB_TO_RUB_CBR_RATE = 1;
    private final double RUB_TO_RUB_SELLING_RATE = 1;
    private final double RUB_TO_RUB_BUYING_RATE = 1;

    private final double RUB_TO_USD_CBR_RATE       = 0.0162975;
    private final double RUB_TO_USD_SELLING_RATE   = 0.0165480;
    private final double RUB_TO_USD_BUYING_RATE    = 0.0147623;

    private final double RUB_TO_EUR_CBR_RATE       = 0.0162412;
    private final double RUB_TO_EUR_SELLING_RATE   = 0.0169635;
    private final double RUB_TO_EUR_BUYING_RATE    = 0.0148411;
    //</editor-fold desc="RUB">

    //<editor-fold desc="USD">
    private final double USD_TO_USD_CBR_RATE = 1;
    private final double USD_TO_USD_SELLING_RATE = 1;
    private final double USD_TO_USD_BUYING_RATE = 1;

    private final double USD_TO_RUB_CBR_RATE       = 61.359104;
    private final double USD_TO_RUB_SELLING_RATE   = 60.430263;
    private final double USD_TO_RUB_BUYING_RATE    = 67.740121;

    private final double USD_TO_EUR_CBR_RATE       = 1.003469;
    private final double USD_TO_EUR_SELLING_RATE   = 0.896853;
    private final double USD_TO_EUR_BUYING_RATE    = 0.870239;
    //</editor-fold desc="USD">
    
    // <editor-fold desc="EUR">
    private final double EUR_TO_EUR_CBR_RATE = 1;
    private final double EUR_TO_EUR_SELLING_RATE = 1;
    private final double EUR_TO_EUR_BUYING_RATE = 1;

    private final double EUR_TO_RUB_CBR_RATE       = 61.571805;
    private final double EUR_TO_RUB_SELLING_RATE   = 58.950098;
    private final double EUR_TO_RUB_BUYING_RATE    = 67.380450;

    private final double EUR_TO_USD_CBR_RATE       = 0.996542;
    private final double EUR_TO_USD_SELLING_RATE   = 0.870239;
    private final double EUR_TO_USD_BUYING_RATE    = 0.896853;
    //</editor-fold desc="EUR">

    //</editor-fold desc="Constants">

    //<editor-fold desc="Widgets">
    private EditText etAmountCurrency, etResult, etRate;

    private Spinner spinnerCurrencyIn, spinnerCurrencyOut;

    private RadioButton rBtnRCB, rBtnBuying, rBtnSelling;

    private Button btnCalculate;

    private TextView tvInfoCurIn, tvInfoCurOut, tvInfoRate;
    //</editor-fold desc="Widgets">

    private static ArrayList<Map<String, Object>> currencyList;

    private static String currencyIn, currencyOut;
    private static Double rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currencyIn = "RUB";
        currencyOut = "RUB";
        rate = (double) 0;

        //--- Инициализация полей виджитов
        etAmountCurrency = this.findViewById(R.id.etAmountCurrency);
        etResult = this.findViewById(R.id.etResult);
        etRate = this.findViewById(R.id.etRate);

        spinnerCurrencyIn = this.findViewById(R.id.spinnerCurrencyIn);
        spinnerCurrencyOut = this.findViewById(R.id.spinnerCurrencyOut);

        rBtnRCB = this.findViewById(R.id.rBtnRCB);
        rBtnBuying = this.findViewById(R.id.rBtnBuying);
        rBtnSelling = this.findViewById(R.id.rBtnSelling);

        CompoundButton.OnCheckedChangeListener rbOCCL = (buttonView, isChecked) -> setRate();

        rBtnRCB.setOnCheckedChangeListener(rbOCCL);
        rBtnBuying.setOnCheckedChangeListener(rbOCCL);
        rBtnSelling.setOnCheckedChangeListener(rbOCCL);

        btnCalculate = this.findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(v -> calculate());

        tvInfoCurIn = this.findViewById(R.id.tvInfoCurIn);
        tvInfoCurOut = this.findViewById(R.id.tvInfoCurOut);
        tvInfoRate = this.findViewById(R.id.tvInfoRate);


        initApp();

        showApp();



    }

    private void initApp(){
        Currency[] currencies = {
                new Currency("RUB", "рубль"),
                new Currency("USD", "доллар США"),
                new Currency("EUR", "евро")
        };

        currencyList = new ArrayList<>();

        for (Currency currency : currencies) {
            HashMap<String, Object> currencyItem = new HashMap<>();

            currencyItem.put("sCod", currency.sCod);
            currencyItem.put("Name", currency.currencyName);

            currencyList.add(currencyItem);
        }

    }

    private void showApp(){
        SimpleAdapter currencyAdapter = new SimpleAdapter(
                this,
                currencyList,
                R.layout.spiner_currency,
                new String[] {"sCod", "Name"},
                new int[] {R.id.tvCurrencyCodSP, R.id.tvCurrencyNameSP}
        );

        currencyAdapter.setDropDownViewResource(R.layout.spiner_currency);

        spinnerCurrencyIn.setAdapter(currencyAdapter);

        spinnerCurrencyIn.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> currencyItem = (HashMap<String, Object>) parent.getAdapter().getItem(position);

                        currencyIn = (String) currencyItem.get("sCod");
                        tvInfoCurIn.setText("In: " + currencyIn);

                        setRate();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        spinnerCurrencyOut.setAdapter(currencyAdapter);

        spinnerCurrencyOut.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, Object> currencyItem = (HashMap<String, Object>) parent.getAdapter().getItem(position);

                        currencyOut = (String) currencyItem.get("sCod");
                        tvInfoCurOut.setText("Out: " + currencyOut);
                        setRate();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }
        );

    }

    @SuppressLint("SetTextI18n")
    private void setRate(){

        String myRate = String.valueOf(etRate.getText());
        //System.out.println(myRate.length() > 0);
        if (myRate.length() > 0) {
            rate = 1 / Double.valueOf(myRate);
        } else {

            switch (currencyIn) {
                case "RUB":
                    switch (currencyOut) {
                        case "RUB":
                            if (rBtnRCB.isChecked()) rate = RUB_TO_RUB_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = RUB_TO_RUB_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = RUB_TO_RUB_SELLING_RATE;
                            break;
                        case "USD":
                            if (rBtnRCB.isChecked()) rate = RUB_TO_USD_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = RUB_TO_USD_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = RUB_TO_USD_SELLING_RATE;
                            break;
                        case "EUR":
                            if (rBtnRCB.isChecked()) rate = RUB_TO_EUR_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = RUB_TO_EUR_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = RUB_TO_EUR_SELLING_RATE;
                            break;
                    }
                    break;

                case "USD":
                    switch (currencyOut) {
                        case "RUB":
                            if (rBtnRCB.isChecked()) rate = USD_TO_RUB_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = USD_TO_RUB_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = USD_TO_RUB_SELLING_RATE;
                            break;
                        case "USD":
                            if (rBtnRCB.isChecked()) rate = USD_TO_USD_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = USD_TO_USD_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = USD_TO_USD_SELLING_RATE;
                            break;
                        case "EUR":
                            if (rBtnRCB.isChecked()) rate = USD_TO_EUR_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = USD_TO_EUR_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = USD_TO_EUR_SELLING_RATE;
                            break;
                    }
                    break;

                case "EUR":
                    switch (currencyOut) {
                        case "RUB":
                            if (rBtnRCB.isChecked()) rate = EUR_TO_RUB_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = EUR_TO_RUB_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = EUR_TO_RUB_SELLING_RATE;
                            break;
                        case "USD":
                            if (rBtnRCB.isChecked()) rate = EUR_TO_USD_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = EUR_TO_USD_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = EUR_TO_USD_SELLING_RATE;
                            break;
                        case "EUR":
                            if (rBtnRCB.isChecked()) rate = EUR_TO_EUR_CBR_RATE;
                            if (rBtnBuying.isChecked()) rate = EUR_TO_EUR_BUYING_RATE;
                            if (rBtnSelling.isChecked()) rate = EUR_TO_EUR_SELLING_RATE;
                            break;
                    }
                    break;
            }
        }

        tvInfoRate.setText(rate.toString());

    }

    private void calculate(){
        if (etAmountCurrency.getText().length() == 0) return;

        setRate();

        double amount;

        amount = Double.parseDouble(etAmountCurrency.getText().toString());

        etResult.setText(String.valueOf(amount * rate));

    }
}