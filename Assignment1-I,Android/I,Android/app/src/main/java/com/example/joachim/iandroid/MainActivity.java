package com.example.joachim.iandroid;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_EDIT_ACTIVITY = 100;

    private FloatingActionButton floatingActionButtonEdit;
    private EditText editTextName;
    private EditText editTextId;
    private CheckBox checkBoxAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        floatingActionButtonEdit = (FloatingActionButton) findViewById(R.id.btnFloatEdit);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        checkBoxAndroid = (CheckBox) findViewById(R.id.checkBoxAndroid);

        checkBoxAndroid.setTextIsSelectable(false);
        editTextName.setTextIsSelectable(false);
        editTextId.setClickable(false);

        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEditActivity();
            }
        });
    }

    @Override
    public void onActivityResult(int request, int result, Intent intent) {
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

    private void startEditActivity() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("name", editTextName.getText().toString());
        intent.putExtra("id", editTextId.getText().toString());
        intent.putExtra(getString(R.string.android_ticked_key), checkBoxAndroid.isChecked());
        startActivityForResult(intent, REQUEST_CODE_EDIT_ACTIVITY);
    }
}
