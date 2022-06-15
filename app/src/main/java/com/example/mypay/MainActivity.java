package com.example.mypay;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {

    EditText txt_amount;
    Button btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_amount = findViewById(R.id.txt_amount);
        btnPay = findViewById(R.id.btnPay);

        Checkout.preload(getApplicationContext());

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(txt_amount.getText().toString())) {
                    txt_amount.setError("Please Enter Amount!");
                } else {
                    String amount = txt_amount.getText().toString();
                    double finalAmount = Float.parseFloat(amount) * 100;

                    Checkout checkout = new Checkout();
                    checkout.setKeyID("rzp_test_AOAhiW7vI74tmq");
                    checkout.setImage(R.drawable.razorpay);

                    try {
                        JSONObject object = new JSONObject();
                        object.put("name", "Hashmi");
                        object.put("description", "Reference No. #123456");
                        object.put("image", R.drawable.razorpay);
                        object.put("theme.color", "#3399cc");
                        object.put("currency", "INR");
                        object.put("amount", finalAmount + "");
                        object.put("prefill.email", "hashmi@gmail.com");
                        object.put("prefill.contact", "9106603952");
                        checkout.open(MainActivity.this, object);
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(MainActivity.this, "Payment Success: "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(MainActivity.this, "Payment Fail: "+s, Toast.LENGTH_SHORT).show();

    }
}