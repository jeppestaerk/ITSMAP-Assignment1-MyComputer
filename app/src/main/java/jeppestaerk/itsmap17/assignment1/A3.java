package jeppestaerk.itsmap17.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IS_LAPTOP;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_MEMORY;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_NAME;

public class A3 extends AppCompatActivity {

    String computerName;
    int computerMemory;
    Boolean computerIsLaptop;

    EditText etComputerName;
    EditText etComputerMemory;
    RadioButton rbYes;
    RadioButton rbNo;
    Button btnCancel;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);

        if (savedInstanceState != null) {
            computerName = savedInstanceState.getString(COMPUTER_NAME, getString(R.string.ph_name));
            computerMemory = savedInstanceState.getInt(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
            computerIsLaptop = savedInstanceState.getBoolean(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
        } else {
            final Intent data = getIntent();
            computerName = data.getStringExtra(COMPUTER_NAME);
            computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
            computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
        }

        etComputerName = (EditText) findViewById(R.id.ptName);
        etComputerName.setText(computerName);

        etComputerMemory = (EditText) findViewById(R.id.numMemory);
        etComputerMemory.setText(String.valueOf(computerMemory));

        rbYes = (RadioButton) findViewById(R.id.rbYes);
        rbYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computerIsLaptop = true;
            }
        });

        rbNo = (RadioButton) findViewById(R.id.rbNo);
        rbNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                computerIsLaptop = false;
            }
        });

        if (computerIsLaptop) {
            rbYes.setChecked(true);
        } else {
            rbNo.setChecked(true);
        }

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra(COMPUTER_NAME, etComputerName.getText().toString());
                data.putExtra(COMPUTER_MEMORY, Integer.valueOf(etComputerMemory.getText().toString()));
                data.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(COMPUTER_NAME, computerName);
        outState.putInt(COMPUTER_MEMORY, computerMemory);
        outState.putBoolean(COMPUTER_IS_LAPTOP, computerIsLaptop);
    }
}
