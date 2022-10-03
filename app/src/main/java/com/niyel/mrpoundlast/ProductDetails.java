package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity {

    private int index;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        onCreateView();
        ImageView productImage = findViewById(R.id.productPicture);
        TextView informationText = findViewById(R.id.informationText);
        ImageButton enterAmountButton = findViewById(R.id.ButtonAmount);
    //    ImageButton cancelButton = findViewById(R.id.ButtonCancel);
        ImageButton buttonBack = findViewById(R.id.ButtonBack);
        ImageButton buttonForward = findViewById(R.id.buttonForward);

        /*Get intent ile productsları ve tıklanan indexi al*/
        Intent recieverIntent = getIntent();
        index =  recieverIntent.getIntExtra("KEY_INDEX",0);
        products = (ArrayList<Product>) recieverIntent.getSerializableExtra("KEY_PRODUCTS");

        Picasso.get().load(products.get(index).getImageURL()).into(productImage);
        informationText.append("Ürün Adı-> "+products.get(index).getProductName()+"\n"+"Miktar Tipi-> "+products.get(index).getUomName()+"\n"+"Miktar-> "+products.get(index).getProductUomQty()+"\n"+"Başlangıç Konumu-> "+products.get(index).getLocationName()+"\n"+"Varış Konumu-> "+products.get(index).getLocationDestName());



        /*Enter Amount Butonu*/
        enterAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText EnterAmountText = findViewById(R.id.amountText);
                    products.get(index).setProcess(1);
                    backToOrderDetails();
                    products.get(index).setRevisedAmount(Integer.parseInt(EnterAmountText.getText().toString()));


            }  });

        /*Button to go back to Order Details*/
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    backToOrderDetails();
            }
        });

        /*Bir sonraki product, text view'ı index productu değiştir*/
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(ProductDetails.this, ProductDetails.class);
                index++;
                while(index<products.size()){
                    if(products.get(index).getProcess()==0){
                        System.out.println("Gridi");
                        myIntent.putExtra("KEY_INDEX",index);
                        myIntent.putExtra("KEY_PRODUCTS",products);
                        finish();
                        startActivity(myIntent);
                        break;
                    }
                }            }
        });
    }

    /*Function to return Order Details*/
    public void backToOrderDetails(){
        Intent intent = new Intent(ProductDetails.this, OrderDetails.class);
        finish();
        intent.putExtra("KEY_PRODUCTS",products);
        startActivity(intent);
    }

    public void onCreateView()  {


            ArrayList<String> errors = new ArrayList<String>();
            errors.add("Ürün Bulunamadı");
            errors.add("Yanlış Adres");
            errors.add("Barkodsuz Ürün");
            errors.add("Hasarlı Ürün");
            errors.add("Yanlış Miktar");
            errors.add("İnceleme Gerekiyor");
            errors.add("Diğer");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, errors);
            ListView lvData = (ListView) findViewById(R.id.cancelReasonsForm);
            lvData.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            /*Cancel Form Doldurma*/
            lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    products.get(index).setProcess(2);
                    products.get(index).setCancelReason(position+1);
                    backToOrderDetails();

                }
            });
        }
}
