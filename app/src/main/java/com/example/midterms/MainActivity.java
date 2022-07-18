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

public class MainActivity extends AppCompatActivity implements BillDialogFragment.ErrorDialogListener {
    ArrayList<Pipe> pipeTypes;
    ArrayAdapter<Pipe> pipeAdapter;
    ArrayList<Bill> bills;
    BillsAdapter billsAdapter;
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
        Switch swNight = findViewById(R.id.swNight);
        swNight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int[] textviews = {R.id.swNight, R.id.tvTitle, R.id.rbRegular, R.id.rbBasic, R.id.rbPremium, R.id.tvLblPackage, R.id.tvLblPipe, R.id.tvLblPrev, R.id.tvLblNew, R.id.tvLblHistory, R.id.tvLblBill, R.id.etResult, R.id.etPrev, R.id.etNew};
                ConstraintLayout clMain = findViewById(R.id.clMain);
                Spinner spPipe = findViewById(R.id.spPipe);
                TextView tvPipe = (TextView) spPipe.getSelectedView();
                if (swNight.isChecked()) {
                    clMain.setBackgroundColor(Color.BLACK);
                    tvPipe.setTextColor(Color.WHITE);
                    for (int res : textviews) {
                        TextView view1 = findViewById(res);
                        view1.setTextColor(Color.WHITE);
                    }
                } else {
                    clMain.setBackgroundColor(Color.WHITE);
                    tvPipe.setTextColor(Color.BLACK);
                    for (int res : textviews) {
                        TextView view1 = findViewById(res);
                        view1.setTextColor(Color.BLACK);
                    }
                }
            }
        });
    }

    // TODO Milestone B: Show History.
    private void setHistoryAdapter() {
        ListView lvHistory = findViewById(R.id.lvHistory);
        billsAdapter = new BillsAdapter(getBaseContext(), R.layout.bills_layout, bills);
        lvHistory.setAdapter(billsAdapter);
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
                int curr = 0;
                try {
                    curr = Integer.parseInt(etNext.getText().toString());
                } catch (Exception e) {
                    BillDialogFragment dialog = new BillDialogFragment();
                    dialog.show(getSupportFragmentManager(), "vince");
                    return;
                }
                if (prev > curr) {
                    BillDialogFragment dialog = new BillDialogFragment();
                    dialog.show(getSupportFragmentManager(), "vince");
                    return;
                }
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
                bills.add(new_bill);
                last_consumption = curr - prev;
                billsAdapter.notifyDataSetChanged();
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

    @Override
    public void onYesListenerMethod(DialogFragment dialog) {
        EditText etPrev = findViewById(R.id.etPrev);
        int prev = Integer.parseInt(etPrev.getText().toString());
        int read = prev + last_consumption;
        EditText etNext = findViewById(R.id.etNew);
        etNext.setText(read + "");
    }

    @Override
    public void onNoListenerMethod(DialogFragment dialog) {

        EditText etNext = findViewById(R.id.etNew);
        etNext.setText("");
    }
}