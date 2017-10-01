package jeppestaerk.itsmap17.assignment1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IMAGE;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IS_LAPTOP;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_MEMORY;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_NAME;
import static jeppestaerk.itsmap17.assignment1.Const.REQUEST_DETAILS_ACTIVITY;
import static jeppestaerk.itsmap17.assignment1.Const.REQUEST_IMAGE_CAPTURE;

public class A1 extends AppCompatActivity {

    private static final String TAG = "A1";

    private String computerName;
    private int computerMemory;
    private Boolean computerIsLaptop;
    private Bitmap computerImage;

    private TextView tvComputerName;
    private ImageView ivComputerImage;
    private Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        // Modified from: https://developer.android.com/guide/components/activities/activity-lifecycle.html
        if (savedInstanceState != null) {
            computerName = savedInstanceState.getString(COMPUTER_NAME, getString(R.string.ph_name));
            computerMemory = savedInstanceState.getInt(COMPUTER_MEMORY, Integer.parseInt(getString(R.string.ph_memory)));
            computerIsLaptop = savedInstanceState.getBoolean(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
            computerImage = savedInstanceState.getParcelable(COMPUTER_IMAGE);
        } else {
            computerName = getString(R.string.ph_name);
            computerMemory = Integer.parseInt(getString(R.string.ph_memory));
            computerIsLaptop = Boolean.valueOf(getString(R.string.ph_laptop));
            computerImage = null;
        }

        tvComputerName = (TextView) findViewById(R.id.tvComputerName);
        tvComputerName.setText(computerName);
        ivComputerImage = (ImageView) findViewById(R.id.ivComputer);
        ivComputerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if camera hardware is present
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    // Modified from: https://developer.android.com/training/camera/photobasics.html
                    takePhoto();
                } else {
                    toastText(getText(R.string.toast_alert_no_camera).toString());
                }
            }
        });
        if (computerImage != null)
            ivComputerImage.setImageBitmap(computerImage);

        btnDetails = (Button) findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startA2();
            }
        });
    }

    private void takePhoto() {
        // Modified from: https://developer.android.com/training/camera/photobasics.html
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void startA2() {
        // Modified from: https://developer.android.com/guide/components/activities/intro-activities.html
        Intent intent = new Intent(this, A2.class);
        intent.putExtra(COMPUTER_NAME, computerName);
        intent.putExtra(COMPUTER_MEMORY, computerMemory);
        intent.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
        startActivityForResult(intent, REQUEST_DETAILS_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Modified from: https://developer.android.com/training/camera/photobasics.html
            Bundle extras = data.getExtras();
            computerImage = (Bitmap) extras.get("data");
            ivComputerImage.setImageBitmap(computerImage);
            Log.d("Image", "OK");
        } else if (requestCode == REQUEST_DETAILS_ACTIVITY && resultCode == RESULT_CANCELED) {
            Log.d(TAG, "onActivityResult: REQUEST_DETAILS_ACTIVITY && RESULT_CANCELED");
        } else if (requestCode == REQUEST_DETAILS_ACTIVITY && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: REQUEST_DETAILS_ACTIVITY && RESULT_OK");
            computerName = data.getStringExtra(COMPUTER_NAME);
            computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.parseInt(getString(R.string.ph_memory)));
            computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
            tvComputerName.setText(computerName);
            toastText(getText(R.string.toast_details_updated).toString());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Modified from: https://developer.android.com/guide/components/activities/activity-lifecycle.html
        outState.putString(COMPUTER_NAME, computerName);
        outState.putInt(COMPUTER_MEMORY, computerMemory);
        outState.putBoolean(COMPUTER_IS_LAPTOP, computerIsLaptop);
        outState.putParcelable(COMPUTER_IMAGE, computerImage);
        super.onSaveInstanceState(outState);
    }

    private void toastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
