package com.example.midterms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Pipe> pipeTypes;
    ArrayAdapter<Pipe> pipeAdapter;
    ArrayList<Bill> bills;
    int month;
    int last_consumption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bills = new ArrayList<>();
        month = 1;
        last_consumption = 0;
        setPipeAdapter();
        btnCalculateListenerMethod();
        setHistoryAdapter();
        nightModeListenerMethod();
    }

    // TODO Milestone A: Use Day-Night mode.
    private void nightModeListenerMethod() {

    }

    // TODO Milestone B: Show History.
    private void setHistoryAdapter() {

    }

    // // TODO Milestone 3: Calculate bill.
    private void btnCalculateListenerMethod() {
        Button btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etPrev = findViewById(R.id.etPrev);
                int prev = Integer.parseInt(etPrev.getText().toString());
                EditText etNext = findViewById(R.id.etNew);
                int curr = Integer.parseInt(etNext.getText().toString());
                Spinner spPipe = findViewById(R.id.spPipe);
                Pipe type = (Pipe) spPipe.getSelectedItem();
                RadioButton rbBasic = findViewById(R.id.rbBasic);
                RadioButton rbRegular = findViewById(R.id.rbRegular);
                RadioButton rbPremium = findViewById(R.id.rbPremium);
                int pack = 0;
                if (rbBasic.isChecked()) {
                    pack = 1; // Basic Package
                } else if (rbRegular.isChecked()) {
                    pack = 2; // Regular Package
                } else {
                    pack = 3; // Premium Package
                }
                Bill new_bill = new Bill(prev, curr, type, pack, month);
                month++;
                double bill = new_bill.get_bill();
                EditText etResult = findViewById(R.id.etResult);
                etResult.setText(bill + "");
                etPrev.setText(curr + "");
                etNext.setText("");
            }
        });
    }

    /**
     * The pipe list is already initialized. There is no need to revise this.
     */
    private void setPipeAdapter() {
        pipeTypes = new ArrayList<>();
        pipeTypes.add(new Pipe("Arad", 0.5));
        pipeTypes.add(new Pipe("Arad", 0.7));
        pipeTypes.add(new Pipe("Arad", 0.2));
        pipeTypes.add(new Pipe("Ace", 0.5));
        pipeTypes.add(new Pipe("Ace", 0.2));
        Spinner spPipe = findViewById(R.id.spPipe);
        pipeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pipeTypes);
        spPipe.setAdapter(pipeAdapter);
    }
}