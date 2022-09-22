package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductDetails extends AppCompatActivity {

    private ImageButton buttonBack;
    private ImageButton buttonForward;
    private int index;
    private ArrayList<Product> products;
    private TextView informationText;
    private TextView catalogText;

    private ImageButton enterAmountButton;
    private ImageButton CancelButton;

    private ImageButton barcode;


    private LinearLayout productAmountLayout;
    private LinearLayout cancelFormLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        informationText = findViewById(R.id.informationText);
        catalogText = findViewById(R.id.catalogText);
        catalogText.setEnabled(false);

        barcode= findViewById(R.id.imageButtonSearchBarcode);

        enterAmountButton = findViewById(R.id.ButtonAmount);
        CancelButton = findViewById(R.id.ButtonCancel);

        productAmountLayout = findViewById(R.id.productAmountLayout);
        productAmountLayout.setEnabled(false);

        cancelFormLayout = findViewById(R.id.CancelFormLayout);
        cancelFormLayout.setEnabled(false);


        buttonBack = findViewById(R.id.ButtonBack);
        buttonForward = findViewById(R.id.buttonForward);


        /*Get intent ile productsları ve tıklanan indexi al*/
        Intent recieverIntent = getIntent();
        index =  recieverIntent.getIntExtra("KEY_INDEX",0);
        products = (ArrayList<Product>) recieverIntent.getSerializableExtra("KEY_PRODUCTS");



        /*Cancel Amount Butonu*/
        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText CancelFormText= findViewById(R.id.CancelFormText);
                products.get(index).setCancelReason(CancelFormText.getText().toString());
                products.get(index).setProcess(2);

                Intent myIntent = new Intent(ProductDetails.this, OrderDetails.class);
                myIntent.putExtra("KEY_PRODUCTS",products);
                startActivity(myIntent);




            }
        });

        /*Enter Amount Butonu*/
        enterAmountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*Button to go back to Order Details*/
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetails.this, OrderDetails.class);
                finish();
                startActivity(intent);
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
                        myIntent.putExtra("KEY_INDEX",index);
                        myIntent.putExtra("KEY_PRODUCTS",products);
                        startActivity(myIntent);
                    }

                }

            }
        });


        /*Barcode doğru olması halinde*/
        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText barcodeText= findViewById(R.id.editTextTextAcceptShipmentBarcode);

                for(int i=0;i<products.size();i++){
                    if(products.get(i).getBarcode()== Integer.parseInt(barcodeText.toString())){
                        catalogText.setEnabled(true);
                         productAmountLayout.setEnabled(true);
                        cancelFormLayout.setEnabled(true);
                         informationText.append(products.get(i).getProductName()+"\n"+products.get(i).getUomName()+"\n"+products.get(i).getProductUomQty()+"\n"+products.get(i).getLocationName()+"\n"+products.get(i).getLocationDestName());
                        break;
                    }
                    for(int j=0;j<products.get(i).getBarcodes().size();j++){
                        if(Integer.parseInt(barcodeText.toString())==products.get(i).getBarcodes().get(j)){
                            catalogText.setEnabled(true);
                            productAmountLayout.setEnabled(true);
                            cancelFormLayout.setEnabled(true);
                            informationText.append(products.get(i).getProductName()+"\n"+products.get(i).getUomName()+"\n"+products.get(i).getProductUomQty()+"\n"+products.get(i).getLocationName()+"\n"+products.get(i).getLocationDestName());
                            break;

                        }
                    }
                }
            }
        });

    }
}