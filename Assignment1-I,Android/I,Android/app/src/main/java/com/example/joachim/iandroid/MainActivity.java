package com.example.joachim.iandroid;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT_ACTIVITY = 100;
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 101;
    public static final int PERMISSION_REQUEST_CAMERA = 528;

    private EditText editTextName;
    private EditText editTextId;
    private CheckBox checkBoxAndroid;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButtonEdit = (FloatingActionButton) findViewById(R.id.btnFloatEdit);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        checkBoxAndroid = (CheckBox) findViewById(R.id.checkBoxAndroid);
        imageView = (ImageView) findViewById(R.id.imageView);

        disableUi();

        if (savedInstanceState != null) {
            bitmap = savedInstanceState.getParcelable(getString(R.string.bitmap_saved_key));
            imageView.setImageBitmap(bitmap);
            editTextId.setText(savedInstanceState.getString(getString(R.string.str_name_key)));
            editTextId.setText(savedInstanceState.getString(getString(R.string.str_id_key)));
        }
        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditActivity();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startImageCapture();
            }
        });
    }

    @Override
    public  void onSaveInstanceState(Bundle bundle){
        bundle.putParcelable(getString(R.string.bitmap_saved_key), bitmap);
        bundle.putString(getString(R.string.str_name_key), editTextName.getText().toString());
        bundle.putString(getString(R.string.str_id_key), editTextId.getText().toString());
        bundle.putBoolean(getString(R.string.android_ticked_key), checkBoxAndroid.isChecked());
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onActivityResult(int request, int result, Intent intent) {
        if (request == REQUEST_CODE_CAPTURE_IMAGE) {
            switch (result) {
                case RESULT_OK: {
                    if (intent != null) {
                        Bundle bundle = intent.getExtras();
                        bitmap = (Bitmap)bundle.get("data");
                        imageView.setImageBitmap(bitmap);
                    }
                }
                case RESULT_CANCELED: {
                    //not implemented
                }
            }
        }

        if (request == REQUEST_CODE_EDIT_ACTIVITY) {
            switch (result) {
                case RESULT_OK: {
                    editTextName.setText(intent.getStringExtra(getString(R.string.name_key)));
                    editTextId.setText(intent.getStringExtra(getString(R.string.id_key)));
                    checkBoxAndroid.setChecked(intent.getBooleanExtra(getString(R.string.android_ticked_key), false));
                }
                case RESULT_CANCELED: {
                    //not implemented
                }
            }
        }
    }

    private void startEditActivity() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(getString(R.string.name_key), editTextName.getText().toString());
        intent.putExtra(getString(R.string.id_key), editTextId.getText().toString());
        intent.putExtra(getString(R.string.android_ticked_key), checkBoxAndroid.isChecked());
        startActivityForResult(intent, REQUEST_CODE_EDIT_ACTIVITY);
    }

    private void startImageCapture() {

        if (!hasPermissions()){
            return;
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
        } else {
            Toast.makeText(this, getString(R.string.image_capture_failed), Toast.LENGTH_SHORT).show();
        }
    }

    // The following code is taken from:
    // https://developer.android.com/training/permissions/requesting.html
    // used with a few changes.
    private boolean hasPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
        else {
            return true;
        }

        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void disableUi(){
        editTextId.setFocusable(false);
        editTextId.setEnabled(false);
        editTextId.setCursorVisible(false);
        editTextId.setKeyListener(null);

        editTextName.setFocusable(false);
        editTextName.setEnabled(false);
        editTextName.setCursorVisible(false);
        editTextName.setKeyListener(null);

        checkBoxAndroid.setFocusable(false);
        checkBoxAndroid.setEnabled(false);
        checkBoxAndroid.setCursorVisible(false);
        checkBoxAndroid.setKeyListener(null);
    }
}
