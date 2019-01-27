package com.apolongo.apolongo;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewCardActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.apolongo.android.cardlistsql.REPLY";

    private EditText mEditCardView;
    private ApolongoViewModel mApolongoViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);
        mEditCardView = findViewById(R.id.edit_card);
        mApolongoViewModel = ViewModelProviders.of(this).get(ApolongoViewModel.class);

        final Button button = findViewById((R.id.button_save));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditCardView.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                }
                else{
                    String cardName = mEditCardView.getText().toString();
                    int number = mApolongoViewModel.alreadyExist(cardName);
                    if(number == 0) {
                        replyIntent.putExtra(EXTRA_REPLY, cardName);
                        setResult(RESULT_OK, replyIntent);
                        //It goes back to MainActivity
                        finish();
                    }
                    else {
                        setResult(RESULT_CANCELED, replyIntent);
                        Toast.makeText(getApplicationContext(), R.string.alreadyExist_not_saved, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
