package com.apolongo.apolongo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.apolongo.apolongo.Fragments.DatePickerFragment;

public class NewPurchaseActivity extends AppCompatActivity {

    private EditText mEditPurchaseName;
    private EditText mEditPurchasePrice;
    private EditText mEditPurchaseDate;
    private EditText mEditPurchaseDesc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_purchase);

        //Initializes the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditPurchaseName = findViewById(R.id.edit_purchase_name);
        mEditPurchasePrice = findViewById(R.id.edit_purchase_price);
        mEditPurchaseDate = findViewById(R.id.edit_purchase_date);
        mEditPurchaseDesc = findViewById(R.id.edit_purchase_desc);

        mEditPurchaseDate.setOnClickListener(new View.OnClickListener() {
            private void showDatePickerDialog() {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // +1 because january is zero
                        final String selectedDate = day + " / " + (month+1) + " / " + year;
                        mEditPurchaseDate.setText(selectedDate);
                    }
                });
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }

            @Override
            public void onClick(View view){
                switch (view.getId()){
                    case R.id.edit_purchase_date:
                        showDatePickerDialog();
                        break;
                }
            }
        });

        final Button button = findViewById((R.id.button_save));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditPurchaseName.getText()) || TextUtils.isEmpty(mEditPurchaseDate.getText()) || TextUtils.isEmpty(mEditPurchasePrice.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }else{
                    String purchaseName = mEditPurchaseName.getText().toString();
                    String purchaseDate = mEditPurchaseDate.getText().toString();
                    String purchasePrice = mEditPurchasePrice.getText().toString();
                    String purchaseDesc = mEditPurchaseDesc.getText().toString();

                    replyIntent.putExtra("purchaseName", purchaseName);
                    replyIntent.putExtra("purchaseDate", purchaseDate);
                    replyIntent.putExtra("purchasePrice", purchasePrice);
                    replyIntent.putExtra("purchaseDesc", purchaseDesc);

                    setResult(RESULT_OK, replyIntent);
                    //It goes back to PurchaseListActivity
                    finish();
                }
            }
        });

    }
}
