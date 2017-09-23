package jeppestaerk.itsmap17.assignment1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IMAGE;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_IS_LAPTOP;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_MEMORY;
import static jeppestaerk.itsmap17.assignment1.Const.COMPUTER_NAME;
import static jeppestaerk.itsmap17.assignment1.Const.REQUEST_DETAILS;
import static jeppestaerk.itsmap17.assignment1.Const.REQUEST_TAKE_PHOTO;

public class A1 extends AppCompatActivity {

    String computerName;
    int computerMemory;
    Boolean computerIsLaptop;

    String computerImage;

    TextView tvName;
    ImageView ivImage;
    Button btnDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a1);

        if (savedInstanceState == null) {
            computerName = getString(R.string.ph_name);
            computerMemory = Integer.parseInt(getString(R.string.ph_memory));
            computerIsLaptop = Boolean.valueOf(getString(R.string.ph_laptop));
        }

        tvName = (TextView) findViewById(R.id.tvComputerName);
        tvName.setText(getText(R.string.tv_name));
        ivImage = (ImageView) findViewById(R.id.ivComputer);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
                    takePhoto();
                } else {
                    toastText("You need a camera for this function"); //TODO translate
                }
            }
        });

        btnDetails = (Button) findViewById(R.id.btnDetails);
        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startA2();
            }
        });
    }

    private void toastText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        computerImage = image.getAbsolutePath();
        return image;
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "jeppestaerk.itsmap17.assignment1.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void setPic() {
        int targetW = ivImage.getWidth();
        int targetH = ivImage.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(computerImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(computerImage, bmOptions);
        ivImage.setImageBitmap(bitmap);
    }

    private void startA2() {
        Intent intent = new Intent(this, A2.class);
        intent.putExtra(COMPUTER_NAME, computerName);
        intent.putExtra(COMPUTER_MEMORY, computerMemory);
        intent.putExtra(COMPUTER_IS_LAPTOP, computerIsLaptop);
        startActivityForResult(intent, REQUEST_DETAILS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            setPic();
        } else if (requestCode == REQUEST_DETAILS && resultCode == RESULT_CANCELED) {
            toastText("A2 Canceled"); //TODO translate
        } else if (requestCode == REQUEST_DETAILS && resultCode == RESULT_OK) {
            computerName = data.getStringExtra(COMPUTER_NAME);
            computerMemory = data.getIntExtra(COMPUTER_MEMORY, Integer.parseInt(getString(R.string.ph_memory)));
            computerIsLaptop = data.getBooleanExtra(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));

            tvName.setText(computerName);

            if (computerImage != null)
                setPic();

            toastText("A3 OK");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(COMPUTER_NAME, computerName);
        outState.putInt(COMPUTER_MEMORY, computerMemory);
        outState.putBoolean(COMPUTER_IS_LAPTOP, computerIsLaptop);
        outState.putString(COMPUTER_IMAGE, computerImage);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        computerName = savedInstanceState.getString(COMPUTER_NAME);
        computerMemory = savedInstanceState.getInt(COMPUTER_MEMORY, Integer.parseInt(getString(R.string.ph_memory)));
        computerIsLaptop = savedInstanceState.getBoolean(COMPUTER_IS_LAPTOP, Boolean.valueOf(getString(R.string.ph_laptop)));
        computerImage = savedInstanceState.getString(COMPUTER_IMAGE);

        tvName.setText(computerName);

        if (computerImage != null)
            setPic();
    }
}
