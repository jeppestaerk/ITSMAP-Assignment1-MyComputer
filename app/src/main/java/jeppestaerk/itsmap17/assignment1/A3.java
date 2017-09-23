package jeppestaerk.itsmap17.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IS_LAPTOP;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_MEMORY;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_NAME;

public class A3 extends AppCompatActivity {

    String computerName;
    int computerMemory;
    Boolean computerIsLaptop;

    EditText etName;
    EditText etMemory;
    RadioButton rbYes;
    RadioButton rbNo;
    Button btnCancel;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);

        final Intent data = getIntent();
        computerName = data.getStringExtra(COMPUTER_NAME);
        computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
        computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));

        etName = (EditText) findViewById(R.id.ptName);
        etName.setText(computerName);

        etMemory = (EditText) findViewById(R.id.numMemory);
        etMemory.setText(String.valueOf(computerMemory));

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
                Intent intent = new Intent();
                intent.putExtra(COMPUTER_NAME, etName.getText().toString());
                intent.putExtra(COMPUTER_MEMORY, Integer.valueOf(etMemory.getText().toString()));
                intent.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void toastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
