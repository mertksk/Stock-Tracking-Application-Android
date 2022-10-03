package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private ProgressBar progressBar;
    private Button buttonParse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);


        progressBar = findViewById(R.id.progressBar2);
        buttonParse = findViewById(R.id.login);

        buttonParse.setOnClickListener(new View.OnClickListener() { // When Buttton is clicked
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        progressBar.setVisibility(View.VISIBLE);
        buttonParse.setVisibility(View.INVISIBLE);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://odooapp.swhotel.tech/MrPoundService.php?Operation=UserLogin&Username=" + username.getText().toString() + "&Password=" + password.getText().toString(); // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data
                try {

                    int result = response.getInt("Result");

                    /*1: Success Result*/
                    if(result==1){

                        UserDetails userDetails;
                        userDetails = (UserDetails) getApplicationContext();

                        userDetails.setUsername(username.getText().toString());
                        userDetails.setPassword(password.getText().toString());

                        Intent intent = new Intent(MainActivity.this, OrderList.class);
                        finish();
                        startActivity(intent);

                    }else{

                        /* -5: Invalid Login*/
                        if(result==-5){

                            messageDisplay("-5: Geçersiz Giriş");
                            //  loginMessage.setText("-5: Invalid Login");

                        }
                        /*-1: Odoo taraflı dönen error*/
                        else if(result==-1){
                            messageDisplay("-1: Odoo Hatası");
                            // loginMessage.setText("-1: Odoo error");

                        }
                        /*-10: Credential Missing*/
                        else if(result==-10){
                            messageDisplay("-10:Kimlik Bigisi Eksik");
                            //   loginMessage.setText("-10: Credential Missing");
                        }
                        /*-20: Operation Unknown*/
                        else{
                            messageDisplay("-20: İşlem Bulunamıyor");
                            //   loginMessage.setText("-20: Operation Unknown");
                        }

                        progressBar.setVisibility(View.INVISIBLE);
                        buttonParse.setVisibility(View.VISIBLE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void messageDisplay(String response){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hata Mesajı");
        alert.setMessage(response);

        alert.create().show();
    }

}