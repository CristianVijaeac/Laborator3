package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class PhoneDialerActivity extends AppCompatActivity {

    private final int PERMISSION_REQUEST_CALL_PHONE = 100;

    private EditText etNumber;
    private ImageButton btBackspace;
    private ImageButton btHangup;
    private ImageButton btCall;
    private ArrayList<Button> numberButtons = new ArrayList<>();
    private ListenButtonEvents listener;


    class ListenButtonEvents implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btDelete):
                    Editable number = etNumber.getText();
                    if (!TextUtils.isEmpty(number)) {
                        etNumber.getText().delete(number.length() - 1, number.length());
                    } else {
                        Toast.makeText(PhoneDialerActivity.this, "Nu ati introdus niciun numar de telefon", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.btHangUp:
                    finish();
                    break;
                case R.id.btCall:
                    if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(
                                PhoneDialerActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                PERMISSION_REQUEST_CALL_PHONE);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + etNumber.getText().toString()));
                        startActivity(intent);
                    }
                    break;
                default:
                    if (!TextUtils.isEmpty(etNumber.getText())) {
                        etNumber.setText(etNumber.getText().toString() + ((Button) view).getText());
                    } else {
                        etNumber.setText(((Button) view).getText());
                    }
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        initViews();
    }

    private void initViews() {
        etNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btBackspace = (ImageButton) findViewById(R.id.btDelete);
        btHangup = (ImageButton) findViewById(R.id.btHangUp);
        btCall = (ImageButton) findViewById(R.id.btCall);

        numberButtons.add((Button) findViewById(R.id.button_0));
        numberButtons.add((Button) findViewById(R.id.button_1));
        numberButtons.add((Button) findViewById(R.id.button_2));
        numberButtons.add((Button) findViewById(R.id.button_3));
        numberButtons.add((Button) findViewById(R.id.button_4));
        numberButtons.add((Button) findViewById(R.id.button_5));
        numberButtons.add((Button) findViewById(R.id.button_6));
        numberButtons.add((Button) findViewById(R.id.button_7));
        numberButtons.add((Button) findViewById(R.id.button_8));
        numberButtons.add((Button) findViewById(R.id.button_9));
        numberButtons.add((Button) findViewById(R.id.button_star));
        numberButtons.add((Button) findViewById(R.id.button_hashtag));

        listener = new ListenButtonEvents();

        btBackspace.setOnClickListener(listener);
        btHangup.setOnClickListener(listener);
        btCall.setOnClickListener(listener);
        for (Button button : numberButtons) {
            button.setOnClickListener(listener);
        }

    }
}
