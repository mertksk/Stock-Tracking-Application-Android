package com.niyel.mrpoundlast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
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


public class OrderDetails extends AppCompatActivity {

    private Order selectedOrder;
    private String username;
    private String password;

    private ArrayList<Product> products;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent recieverIntent = getIntent();

        selectedOrder = (Order) recieverIntent.getSerializableExtra("KEY_ORDER");
        if(selectedOrder!=null){
            UserDetails userDetails;
            userDetails = (UserDetails) getApplicationContext();

            userDetails.setOrderId(selectedOrder.getId());
        }
        products = (ArrayList<Product>) recieverIntent.getSerializableExtra("KEY_PRODUCTS");

        TextView selectedText = findViewById(R.id.textViewAcceptShipmentProductName);
        ImageButton ButtonDelete= findViewById(R.id.imageButtonDelete);
        ImageButton searchButton = findViewById(R.id.imageButtonSearchBarcode);
        ImageButton ButtonApprove = findViewById(R.id.ButtonApprove);
        EditText searchText = findViewById(R.id.editTextTextAcceptShipmentBarcode);


        //Servera upload etme butonu
        ButtonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDetails userDetails =(UserDetails) getApplicationContext();
                /*Will be updated ddd*/
                String url =" https://odooapp.swhotel.tech/MrPoundService.php?Operation=PostPicking&Username="+username+"&Password="+password+"&PickingId="+userDetails.getOrderId()+"&MoveLines=10605561;10;-15025732;5;";
                RequestQueue queue = Volley.newRequestQueue(OrderDetails.this);


            }
        });

        //Reset butonu ok
        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<products.size();i++) products.get(i).setProcess(0);
                selectedText.setText( "Listenizde Hiç Ürün Yok");
            }
        });

        //Barkodla arama butonu
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(products!=null){

                for(int i=0;i<products.size();i++){
                    if(Integer.toString(products.get(i).getBarcode()) ==searchText.toString()){

                        Intent myIntent = new Intent(OrderDetails.this, ProductDetails.class);
                        myIntent.putExtra("KEY_INDEX",i);
                        myIntent.putExtra("KEY_PRODUCTS",products);
                        startActivity(myIntent);

                    }
                }}
            }
        });

        TextView userName= findViewById(R.id.textViewUsername);
        userName.setText(username);

        UserDetails userdetails = (UserDetails) getApplicationContext();
        username = userdetails.getUsername();
        password = userdetails.getPassword();

        if(selectedOrder!=null)jsonParse();

        /*ListView operations*/
        listView=(ListView)findViewById(R.id.listViewProductList);

        if(products!=null){
            ArrayList<String> names=new ArrayList<>();
            for(int i=0;i<products.size();i++){
                names.add("Ürün Adı-> "+products.get(i).getProductName()+"\n"+"Konum-> "+ products.get(i).getLocationName());
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, names);
            listView.setAdapter(adapter);

            for(int i=0;i<products.size();i++){
                if(products.get(i).getProcess()==1){

                    listView.findViewById(i).setBackgroundColor(Color.GRAY);

                }
                else if(products.get(i).getProcess()==2){

                    listView.findViewById(i).setBackgroundColor(Color.RED);
                }


            }


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // TODO Auto-generated method stub

                    //index'i ve productları gönder Product Details'e gönder
                    // En başta dönen veriyi kontrol edecek bir intent ve diğer sayfaya geçiş

                    Intent myIntent = new Intent(OrderDetails.this, ProductDetails.class);
                    myIntent.putExtra("KEY_INDEX",position);
                    myIntent.putExtra("KEY_PRODUCTS",products);
                    startActivity(myIntent);


                }
            });

        }


    }

    private void jsonParse() {
        products = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url =" https://odooapp.swhotel.tech/MrPoundService.php?Operation=GetPickingDetail&Username="+username+"&Password="+password+"&PickingId="+selectedOrder.getId(); // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data

                       try {

                    JSONArray jsonArray = response.getJSONArray("PickingMoveLines");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject productObject = jsonArray.getJSONObject(i);

                        ArrayList<Integer> barcodes = new ArrayList<>();
                        JSONArray barcodeArray=productObject.getJSONArray("Barcodes");
                        for (int j = 0; j < barcodeArray.length(); j++){
                            JSONObject barcodeObject = jsonArray.getJSONObject(j);
                            barcodes.add(barcodeObject.getInt("Barcode"));
                        }

                        products.add(new Product(productObject.getInt("Id"),productObject.getInt("ProductId"),productObject.getString("ProductName")
                                ,productObject.getInt("Barcode"),
                                productObject.getString("Tracking"),productObject.getInt("UomId")
                                ,productObject.getString("UomName"),productObject.getInt("ProductUomQty")
                        ,productObject.getInt("QtyDone"),productObject.getInt("LocationId"),productObject.getString("LocationName"),
                                productObject.getInt("LocationDestId"),productObject.getString("LocationDestName"),barcodes,0));
                    }   }
                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(OrderDetails.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}