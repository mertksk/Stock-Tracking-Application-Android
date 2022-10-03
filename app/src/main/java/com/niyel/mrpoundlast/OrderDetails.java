package com.niyel.mrpoundlast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OrderDetails extends AppCompatActivity implements EnterAmountDialogue.EnterAmountDialogueListener{

    private Order selectedOrder;
    private String username;
    private String password;
    private ArrayList<Product> products;
    private int hold;
    private UserDetails userDetails;

    private TextView textViewAcceptShipmentProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Intent recieverIntent = getIntent();

        selectedOrder = (Order) recieverIntent.getSerializableExtra("KEY_ORDER");
        if(selectedOrder!=null){

            userDetails = (UserDetails) getApplicationContext();

            userDetails.setOrder(selectedOrder);
        }

        products = (ArrayList<Product>) recieverIntent.getSerializableExtra("KEY_PRODUCTS");

        TextView selectedText = findViewById(R.id.textViewAcceptShipmentProductName);
        ImageButton ButtonDelete= findViewById(R.id.imageButtonDelete);
        ImageButton searchButton = findViewById(R.id.imageButtonSearchBarcode);
        ImageButton ButtonApprove = findViewById(R.id.ButtonApprove);
        EditText searchText = findViewById(R.id.editTextTextAcceptShipmentBarcode);

        int counter=0;
        if(products!=null){
            listVewOperations();

            for(int i=0;i<products.size();i++){
                if(products.get(i).getProcess()==1 && products.get(i).getProcess()==2){
                    counter++;
                    selectedText.setText(counter+" Tane İşlem Uyguladınız, Sisteme Yüklemek yada İptal Etmek için aşağıdaki butonları kullanın");

                }        }     }

        //Servera upload etme butonu
        ButtonApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonPOST();
            }
        });

        //Reset butonu ok
        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<products.size();i++) products.get(i).setProcess(0);
                Intent myIntent = new Intent(OrderDetails.this, OrderDetails.class);
                myIntent.putExtra("KEY_INDEX",0);
                myIntent.putExtra("KEY_PRODUCTS",products);
                startActivity(myIntent);
            }
        });

        //Barkodla arama butonu
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(products!=null){

                for(int i=0;i<products.size();i++){
                    if(Long.toString(products.get(i).getBarcode()).equals(searchText.getText().toString())){
                        if(products.get(i).getProcess()==0 ){
                            openDialog(products.get(i));
                            hold=i;
                            break;
                        }
                        else{
                            messageDisplay("Bu Ürün Üzerinde İşlem Yapılmıştır.Hatalı İşlem Olduğunu Düşünüyorsunuz , Kırmızı Çarpı Butonuna Tıklayarak Girişi Sıfırlayabilirsiniz");
                        }

                    }
                    for(int j=0;j<products.get(i).getBarcodes().size();j++){
                        if(Long.parseLong( searchText.getText().toString())==products.get(i).getBarcodes().get(j)){
                            if(products.get(i).getProcess()==0  ){
                                openDialog(products.get(i));
                                hold=i;
                                break;
                            }
                            else{
                                messageDisplay("Bu Ürün Üzerinde İşlem Yapılmıştır.Hatalı İşlem Olduğunu Düşünüyorsunuz , Kırmızı Çarpı Butonuna Tıklayarak Girişi Sıfırlayabilirsiniz");
                            }


                        }
                    }



                }




                } } });

        UserDetails userdetails = (UserDetails) getApplicationContext();
        username = userdetails.getUsername();
        password = userdetails.getPassword();

        if(selectedOrder!=null && products==null)jsonParse();

        TextView userName= findViewById(R.id.textViewUsername);
        userName.setText(username);
    }



    private void jsonParse() {
        products = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url ="https://odooapp.swhotel.tech/MrPoundService.php?Operation=GetPickingDetail&Username="+username+"&Password="+password+"&PickingId="+userDetails.getOrder().getId(); // Api link

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { //response Holds the data
                 try {
                     int result = response.getInt("Result");
                     /*Sucessfull*/
                     if(result==1){
                         response= response.getJSONObject("ResultData");
                         response= response.getJSONObject("PickingDetail");

                         String ImageURL = response.getString("ImageUrl");
                         JSONArray jsonArray = response.getJSONArray("PickingMoveLines");
                         System.out.println("Size->1"+jsonArray.length());
                         for (int i = 0; i < jsonArray.length(); i++){

                             JSONObject productObject = jsonArray.getJSONObject(i);
                             ArrayList<Long> barcodes = new ArrayList<>();
                             JSONArray barcodeArray=productObject.getJSONArray("Barcodes");

                             if(barcodeArray!=null){
                                 for (int j = 0; j < barcodeArray.length(); j++){

                                     JSONObject barcodeObject = barcodeArray.getJSONObject(j);
                                     barcodes.add(barcodeObject.getLong("Barcode"));
                                 }}

                             products.add(new Product(productObject.getInt("Id"),productObject.getInt("ProductId"),productObject.getString("ProductName")
                                     ,productObject.getLong("Barcode"),
                                     productObject.getString("Tracking"),productObject.getInt("UomId")
                                     ,productObject.getString("UomName"),productObject.getInt("ProductUomQty")
                                     ,productObject.getInt("QtyDone"),productObject.getInt("LocationId"),productObject.getString("LocationName"),
                                     productObject.getInt("LocationDestId"),productObject.getString("LocationDestName"),barcodes,0,ImageURL));

                         //ddd
                             System.out.println("Size->"+products.size());
                         }

                         listVewOperations();
                     }
                     else if(result==-5){
                         messageDisplay("Geçersiz Giriş");
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
    public void listVewOperations(){
        ListView listView = findViewById(R.id.listViewProductList);

        if(products!=null){
            ArrayList<String> names=new ArrayList<>();
            for(int i=0;i<products.size();i++){
                names.add("Ürün Adı-> "+products.get(i).getProductName()+"\n"+"Başlangıç Konumu-> "+ products.get(i).getLocationName()+"\n"+"Varış Konumu-> "+ products.get(i).getLocationDestName()+"\n"+"Miktar-> "+ products.get(i).getProductUomQty()+"\n"+"Miktar Tipi-> "+ products.get(i).getUomName());
            }

            listView.setAdapter(new MyListAdaper(this, R.layout.order_details_list_item, names));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //index'i ve productları gönder Product Details'e gönder
                    // En başta dönen veriyi kontrol edecek bir intent ve diğer sayfaya geçiş
                    if(products.get(position).getProcess()==1 ||products.get(position).getProcess()==2  ){
                        messageDisplay("Bu Ürün Üzerinde İşlem Yapılmıştır.Hatalı İşlem Olduğunu Düşünüyorsanız , Kırmızı Çarpı Butonuna Tıklayarak Girişi Sıfırlayabilirsiniz");
                    }
                    else{
                        Intent myIntent = new Intent(OrderDetails.this, ProductDetails.class);
                        myIntent.putExtra("KEY_INDEX",position);
                        myIntent.putExtra("KEY_PRODUCTS",products);
                        startActivity(myIntent);
                    }
                }
            });
            }

        /*Top Text Control*/
        textViewAcceptShipmentProductName= findViewById(R.id.textViewAcceptShipmentProductName);
        int counter=0;
        if(products!=null){
            for(int i=0;i<products.size();i++){
                if(products.get(i).getProcess()==1 || products.get(i).getProcess()==2 ) counter++;
            }
        }
        if(counter!=0) textViewAcceptShipmentProductName.setText(counter +" tane ürün seçtiniz!");
    }

    public void openDialog(Product index) {

        EnterAmountDialogue exampleDialog = EnterAmountDialogue.newInstance(index.getProductUomQty(),index.getProductName(),index.getUomName(),index.getLocationName(),index.getLocationDestName());
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }

    /*Amount burada geldi*/
    @Override
    public void applyTexts(int amount) {

            products.get(hold).setProcess(1);
            products.get(hold).setRevisedAmount(amount);
            listVewOperations();

    }

    @Override
    public void passProductDetails() {

        Intent myIntent = new Intent(OrderDetails.this, ProductDetails.class);
        myIntent.putExtra("KEY_INDEX",hold);
        myIntent.putExtra("KEY_PRODUCTS",products);
        startActivity(myIntent);

    }

    public void messageDisplay(String response){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Çıktı Mesajı");
        alert.setMessage(response);
        alert.create().show();
    }

    public void JsonPOST(){
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            userDetails = (UserDetails) getApplicationContext();
            StringBuilder URL = new StringBuilder("https://odooapp.swhotel.tech/MrPoundService.php?Operation=PostPicking&Username=" + username + "&Password=" + password + "&PickingId=" + userDetails.getOrder().getId() + "&MoveLines=");

            ArrayList<String> addition = new ArrayList<>();

            ArrayList<Product> moveLineProducts= new ArrayList<>();

            if(products!=null) {
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProcess() == 1 || products.get(i).getProcess() == 2) {
                        moveLineProducts.add(products.get(i));
                    }
                }
                if (moveLineProducts == null) {
                    messageDisplay("Henüz İşlem Yapılmamıştır!");
                } else {
                    for (int i = 0; i < moveLineProducts.size(); i++) {

                        if(products.get(i).getProcess()==1){
                            addition.add(products.get(i).getId()+";"+products.get(i).getRevisedAmount());

                            if(moveLineProducts.get(i+1)!=null) addition.add("-");
                        }
                        else if(products.get(i).getProcess()==2){
                            addition.add(products.get(i).getId()+";"+products.get(i).getProductUomQty()+";"+products.get(i).getCancelReason());

                            if(moveLineProducts.get(i+1)!=null) addition.add("-");
                        }
                    }
                }
            }

            if(addition !=null){
                for(int i=0;i<addition.size();i++){
                    URL.append(addition.get(i));
                }
            }
            //ddd
            System.out.println("D-> " + URL);
            //ddd
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    messageDisplay("Yükleme Başarılı, Sipariş Sayfasına Yönlendiriliyorsunuz!");
                    Intent intent = new Intent(OrderDetails.this, OrderList.class);
                    finish();
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    messageDisplay(error.toString()) ;
                }
            }) {

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }

            };
            requestQueue.add(stringRequest);
} catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ResetPosition(int position){

        for(int i=0;i<products.size();i++){
            if(position==i) products.get(i).setProcess(0);
        }
        Intent myIntent = new Intent(OrderDetails.this, OrderDetails.class);
        myIntent.putExtra("KEY_INDEX",0);
        myIntent.putExtra("KEY_PRODUCTS",products);
        startActivity(myIntent);

    }


    private class MyListAdaper extends ArrayAdapter<String> {
        private int layout;
        private List<String> mObjects;
        private MyListAdaper(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
            mObjects = objects;
            layout = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder mainViewholder = null;
            if(convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(layout, parent, false);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_text);
                viewHolder.button = (Button) convertView.findViewById(R.id.list_item_btn);
                convertView.setTag(viewHolder);
            }
            mainViewholder = (ViewHolder) convertView.getTag();

            if(products.get(position).getProcess()==1)
            {
                convertView.setBackgroundColor (Color.parseColor("#1E821E")); // some color
            }
            else if(products.get(position).getProcess()==2)
            {
                convertView.setBackgroundColor (Color.RED); // some color
            }
            else
            {
                convertView.setBackgroundColor (Color.TRANSPARENT);
            }


            /*Reset Button*/
             mainViewholder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResetPosition(position);
                }
            });
            mainViewholder.title.setText(getItem(position));

            return convertView;
        }
    }

        public class ViewHolder {

            TextView title;
            Button button;
        }

}