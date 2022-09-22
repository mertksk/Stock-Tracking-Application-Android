package com.niyel.mrpoundlast;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class OrderListMain extends AppCompatActivity {

    private String username;
    private String password;

    private ArrayList<Order> orders;

    public ArrayList<Order> getOrders() {
        return orders;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        UserDetails userdetails = (UserDetails) getApplicationContext();
        username = userdetails.getUsername();
        password = userdetails.getPassword();
        jsonParse();



        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new correctOrder()).commit();


    }


    private void jsonParse() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url =" https://odooapp.swhotel.tech/MrPoundService.php?Operation=GetPickings&Username="+username+"&Password="+password; // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data

                try {
                    int result = response.getInt("Result");
                    if(result==1){
                        response=response.getJSONObject("ResultData");
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

                            orders.add(new Order(id,name,ScheduledDate,LocationId,LocationName,LocationDestId,LocationDestName,PickingTypeId,PickingTypeName,false));

                        }
                        if(isManager==1){
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

                                orders.add(new Order(id,name,ScheduledDate,LocationId,LocationName,LocationDestId,LocationDestName,PickingTypeId,PickingTypeName,true));

                            }


                        }

                    }
                    /*Pickings not found+*/
                    else if(result==-1){
                        System.out.println("Hata meydana geldi"); //dddd
                        AlertDialog alertDialog = new AlertDialog.Builder(OrderListMain.this).create();
                        alertDialog.setTitle("Bir Hata Meydana Geldi");
                        alertDialog.setMessage("Ürün Listesi Bulunamadı");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderListMain.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.correctOrders) { //else incorrect
            selectedFragment = new correctOrder();
        }
        else if(itemId== R.id.incorrectOrders){
            selectedFragment=new incorrectOrder();

        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
        }
        return true;
    };
}