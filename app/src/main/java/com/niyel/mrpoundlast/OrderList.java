package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderList extends AppCompatActivity {

    private String username;
    private String password;

    private ArrayList<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        UserDetails userdetails = (UserDetails) getApplicationContext();
        username = userdetails.getUsername();
        password = userdetails.getPassword();

        TextView textViewUsername= findViewById(R.id.textViewUsername);
        textViewUsername.setText(username);

        jsonParse();
    }


    private void jsonParse() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = " https://odooapp.swhotel.tech/MrPoundService.php?Operation=GetPickings&Username=" + username + "&Password=" + password; // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data

                try {
                    int result = response.getInt("Result");
                    if (result == 1) {
                        response = response.getJSONObject("ResultData");
                        orders = new ArrayList<Order>();
                        int isManager = response.getInt("isManager");
                        JSONArray jsonArray = response.getJSONArray("Pickings");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject orderlist = jsonArray.getJSONObject(i);

                            int id = orderlist.getInt("Id");
                            String name = orderlist.getString("Name");
                            String ScheduledDate = orderlist.getString("ScheduledDate");
                            int LocationId = orderlist.getInt("LocationId");
                            String LocationName = orderlist.getString("LocationName");
                            int LocationDestId = orderlist.getInt("LocationDestId");
                            String LocationDestName = orderlist.getString("LocationDestName");
                            int PickingTypeId = orderlist.getInt("PickingTypeId");
                            String PickingTypeName = orderlist.getString("PickingTypeName");

                            orders.add(new Order(id, name, ScheduledDate, LocationId, LocationName, LocationDestId, LocationDestName, PickingTypeId, PickingTypeName, false));
                        }
                        if (isManager == 1) {
                            jsonArray = response.getJSONArray("PickingsToCheck");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject orderlist = jsonArray.getJSONObject(i);

                                int id = orderlist.getInt("Id");
                                String name = orderlist.getString("Name");
                                String ScheduledDate = orderlist.getString("ScheduledDate");
                                int LocationId = orderlist.getInt("LocationId");
                                String LocationName = orderlist.getString("LocationName");
                                int LocationDestId = orderlist.getInt("LocationDestId");
                                String LocationDestName = orderlist.getString("LocationDestName");
                                int PickingTypeId = orderlist.getInt("PickingTypeId");
                                String PickingTypeName = orderlist.getString("PickingTypeName");

                                orders.add(new Order(id, name, ScheduledDate, LocationId, LocationName, LocationDestId, LocationDestName, PickingTypeId, PickingTypeName, true));

                            }    }
                                onCreateView();
                    }
                    /*Pickings not found+*/
                    else if (result == -1) {
                        messageDisplay("-1: Ürün Listesi Bulunamadı");
                    }
                    else if(result==-20){
                        messageDisplay("-20: Kimlik Bilgileri Eksik");
                    }
                    else if(result==-5){
                        messageDisplay("-5: Geçersiz Giriş");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void messageDisplay(String response) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Hata Mesajı");
        alert.setMessage(response);

        alert.create().show();
    }

    public void onCreateView()  {

             if (orders != null) {
                ArrayList<String> corrects = new ArrayList<String>();
                for (int i = 0; i < orders.size(); i++) {
                    if (!(orders.get(i).isOrderType())) {
                        corrects.add( "İsim-> " + orders.get(i).getName() + "\n" + "Planlanan Tarih-> " + orders.get(i).getDate() + "\n" + "Konum Adı-> " + orders.get(i).getLocationName() + "\nVarış Yeri-> "+orders.get(i).getLocationDestName()+"\n");
                    }                }

                 ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, corrects);
                ListView lvData = (ListView) findViewById(R.id.listViewOrderList);
                lvData.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                /*Passing into details*/
                lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(OrderList.this, OrderDetails.class);
                        if (orders != null) myIntent.putExtra("KEY_ORDER", orders.get(position));
                        startActivity(myIntent);
                    }
                });
}}}

