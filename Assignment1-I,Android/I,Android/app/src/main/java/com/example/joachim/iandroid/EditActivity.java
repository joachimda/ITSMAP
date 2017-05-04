package com.example.joachim.iandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextId;
    private RadioGroup btnsRadio;
    private Button btnSave;
    private Button btnCancel;
    private String name;
    private String id;
    private Boolean isAndroidTicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextId = (EditText) findViewById(R.id.editTextId);
        btnsRadio = (RadioGroup) findViewById(R.id.radioBtns);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        //Check for recreated view
        if (savedInstanceState != null) {
            name = savedInstanceState.getString(getString(R.string.str_name_key));
            id = savedInstanceState.getString(getString(R.string.str_id_key));
            if (savedInstanceState.getBoolean(getString(R.string.android_ticked_key))) {
                btnsRadio.check(R.id.radioBtnYes);
            } else {
                btnsRadio.check(R.id.radioBtnNo);
            }
        } else {
            name = getIntent().getStringExtra(getString(R.string.name_key));
            id = getIntent().getStringExtra(getString(R.string.id_key));

            if (getIntent().getBooleanExtra(getString(R.string.android_ticked_key), false)) {
                btnsRadio.check(R.id.radioBtnYes);
            } else {
                btnsRadio.check(R.id.radioBtnNo);
            }
        }
        editTextId.setText(id);
        editTextName.setText(name);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (btnsRadio.getCheckedRadioButtonId() == R.id.radioBtnYes) {
                    isAndroidTicked = true;
                }
                intent.putExtra(getString(R.string.name_key), editTextName.getText().toString());
                intent.putExtra(getString(R.string.id_key), editTextId.getText().toString());
                intent.putExtra(getString(R.string.android_ticked_key), isAndroidTicked);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(getString(R.string.str_name_key), editTextName.getText().toString());
        bundle.putString(getString(R.string.str_id_key), editTextId.getText().toString());
        bundle.putInt(getString(R.string.android_ticked_key), btnsRadio.getCheckedRadioButtonId());
        super.onSaveInstanceState(bundle);
    }
}

