package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

    private TextView loginMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        loginMessage=findViewById(R.id.LoginMessage);

        Button buttonParse = findViewById(R.id.login);



        buttonParse.setOnClickListener(new View.OnClickListener() { // When Buttton is clicked
            @Override
            public void onClick(View v) {
                //dd

                //dd
                jsonParse();
            }
        });
    }

    private void jsonParse() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://odooapp.swhotel.tech/MrPoundService.php?Operation=UserLogin&Username=" + username.getText().toString() + "&Password=" + password.getText().toString(); // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data
                try {

                    String operation = response.getString("Operation");
                    int result = response.getInt("Result");
                    JSONObject resultData = response.getJSONObject("ResultData");

                    /*1: Success Result*/
                    if(result==1){

                        loginMessage.setText("Login Successful");

                        UserDetails userDetails;
                        userDetails = (UserDetails) getApplicationContext();

                        userDetails.setUsername(username.getText().toString());
                        userDetails.setPassword(password.getText().toString());


                        Intent intent = new Intent(MainActivity.this, OrderListMain.class);
                        finish();
                        startActivity(intent);


                    }
                    /* -5: Invalid Login*/
                    else if(result==-5){

                        loginMessage.setText("-5: Invalid Login");

                    }
                    /*-1: Odoo taraflı dönen error*/
                    else if(result==-1){
                        loginMessage.setText("-1: Odoo error");

                    }
                    /*-10: Credential Missing*/
                    else if(result==-10){
                        loginMessage.setText("-10: Credential Missing");
                    }
                    /*-20: Operation Unknown*/
                    else{
                        loginMessage.setText("-20: Operation Unknown");
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

}