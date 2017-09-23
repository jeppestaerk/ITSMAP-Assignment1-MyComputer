package jeppestaerk.itsmap17.assignment1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IS_LAPTOP;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_MEMORY;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_NAME;
import static jeppestaerk.itsmap17.assignment1.Const.REQUEST_EDIT;

public class A2 extends AppCompatActivity {

    String computerName;
    int computerMemory;
    Boolean computerIsLaptop;

    TextView tvName;
    TextView tvMemory;
    TextView tvLaptop;
    Button btnCancel;
    Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2);

        Intent data = getIntent();
        computerName = data.getStringExtra(COMPUTER_NAME);
        computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
        computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));

        tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(computerName);
        tvMemory = (TextView) findViewById(R.id.tvMemory);
        tvMemory.setText(String.valueOf(computerMemory));
        tvLaptop = (TextView) findViewById(R.id.tvLaptop);
        if (computerIsLaptop) {
            tvLaptop.setText(getText(R.string.rb_yes));
        } else {
            tvLaptop.setText(getText(R.string.rb_no));

        }
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startA3();
            }
        });

    }

    private void toastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void startA3() {
        Intent intent = new Intent(this, A3.class);
        intent.putExtra(COMPUTER_NAME, computerName);
        intent.putExtra(COMPUTER_MEMORY, computerMemory);
        intent.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
        startActivityForResult(intent, REQUEST_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT && resultCode == RESULT_CANCELED) {
            toastText("A3 Canceled"); //TODO translate
        } else if (requestCode == REQUEST_EDIT && resultCode == RESULT_OK) {
            computerName = data.getStringExtra(COMPUTER_NAME);
            computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
            computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));

            Intent intent = new Intent();
            intent.putExtra(COMPUTER_NAME, computerName);
            intent.putExtra(COMPUTER_MEMORY, computerMemory);
            intent.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(COMPUTER_NAME, computerName);
        outState.putInt(COMPUTER_MEMORY, computerMemory);
        outState.putBoolean(COMPUTER_IS_LAPTOP, computerIsLaptop);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        computerName = savedInstanceState.getString(COMPUTER_NAME, getString(R.string.ph_name));
        computerMemory = savedInstanceState.getInt(COMPUTER_MEMORY, Integer.valueOf(getString(R.string.ph_memory)));
        computerIsLaptop = savedInstanceState.getBoolean(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
    }
}
