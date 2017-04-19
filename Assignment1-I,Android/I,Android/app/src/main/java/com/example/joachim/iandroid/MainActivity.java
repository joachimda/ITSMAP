package com.example.joachim.iandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT_ACTIVITY = 100;
    public static final int REQUEST_CODE_CAPTURE_IMAGE = 101;

    private FloatingActionButton floatingActionButtonEdit;
    private EditText editTextName;
    private EditText editTextId;
    private CheckBox checkBoxAndroid;
    private ImageView imageView;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButtonEdit = (FloatingActionButton) findViewById(R.id.btnFloatEdit);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        checkBoxAndroid = (CheckBox) findViewById(R.id.checkBoxAndroid);
        imageView = (ImageView) findViewById(R.id.imageView);

        checkBoxAndroid.setTextIsSelectable(false);
        editTextName.setTextIsSelectable(false);
        editTextId.setClickable(false);


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
                    editTextName.setText(intent.getStringExtra("name"));
                    editTextId.setText(intent.getStringExtra("id"));
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
        intent.putExtra("name", editTextName.getText().toString());
        intent.putExtra("id", editTextId.getText().toString());
        intent.putExtra(getString(R.string.android_ticked_key), checkBoxAndroid.isChecked());
        startActivityForResult(intent, REQUEST_CODE_EDIT_ACTIVITY);
    }

    private void startImageCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_IMAGE);
        } else {
            Toast.makeText(this, getString(R.string.image_capture_failed), Toast.LENGTH_SHORT).show();
        }
    }


}
