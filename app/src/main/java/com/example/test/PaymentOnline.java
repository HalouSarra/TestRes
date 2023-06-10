package com.example.test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentOnline extends AppCompatActivity {

    Button payOnline;
    String publishableKey="pk_test_51NH6kyBBnkAegnwIGMmoq39IGeSjYiAKdgE2XpwAmuWkoO1JB0K88qJwojLNVHD9kljvdoOgb2VbvOOO9ibVvnxb00ERUtOAHc";
    String secretKey ="sk_test_51NH6kyBBnkAegnwIzOOClSkdJ03qqhaOo66xwCg2WXGTFAAWW7ckK3w1O08Xn3hcwhzhi8j9My0HqTkcvarKGg6j00UbTntY60";
    String customerId;
    String ephericalKey;
    String clientSecret;
    PaymentSheet paymentSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_online);


        payOnline =findViewById(R.id.payOnline);
        PaymentConfiguration.init(this,publishableKey);


        paymentSheet =new PaymentSheet(this,paymentSheetResult -> {
            onPaymentResult(paymentSheetResult);
        });

        payOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                paymentFlow();
            }
        });


        StringRequest request= new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object =new JSONObject(response);
                            customerId=object.getString("id");
                            Toast.makeText(PaymentOnline.this, customerId, Toast.LENGTH_SHORT).show();
                            getEmphericalKey();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentOnline.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header =new HashMap<>();
                header.put("Autorisation","Bearer "+secretKey);

                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void paymentFlow() {
        paymentSheet.presentWithPaymentIntent(clientSecret, new PaymentSheet.Configuration("Blasti",new PaymentSheet.CustomerConfiguration(
                customerId,ephericalKey
        )));
    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if(paymentSheetResult instanceof PaymentSheetResult.Completed){
            Toast.makeText(this, "Succès du paiement", Toast.LENGTH_SHORT).show();
        }
    }

    private void getEmphericalKey() {
        StringRequest request= new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object =new JSONObject(response);
                            ephericalKey=object.getString("id");
                            Toast.makeText(PaymentOnline.this, customerId, Toast.LENGTH_SHORT).show();
                            getClientSecret(customerId,ephericalKey);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentOnline.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers =new HashMap<>();
                headers.put("Autorisation","Bearer "+secretKey);
                headers.put("Stripe-Version","2022-11-15");
                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",customerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void getClientSecret(String costumerId, String ephericalKey) {
        StringRequest request= new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject object =new JSONObject(response);
                            clientSecret= object.getString("client_secret");
                            Toast.makeText(PaymentOnline.this, clientSecret, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PaymentOnline.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + secretKey);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("customer",costumerId);
                params.put("amount","10000");
                params.put("currency","DZD");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }
}