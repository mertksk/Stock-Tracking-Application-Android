package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class IncorrectOrderDetails extends AppCompatActivity {

    private Order selectedOrder;
    private String username;
    private String password;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incorrect_order_details);

        Intent recieverIntent = getIntent();

        selectedOrder = (Order) recieverIntent.getSerializableExtra("KEY_ORDER");

        TextView textDetails = findViewById(R.id.textDetails);
        textDetails.setText(selectedOrder.getName()+"\n"+selectedOrder.getDate()+"\n"+selectedOrder.getLocationName()+"\n"+selectedOrder.getLocationDestName()+"\n"+selectedOrder.getPickingTypeName());

        ImageButton buttonTODO,buttonCancel,buttonDONE;
        buttonTODO = findViewById(R.id.ButtonTODO);
        buttonDONE = findViewById(R.id.ButtonDONE);
        buttonCancel = findViewById(R.id.ButtonCancel);

        /*To do*/
        buttonTODO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*Cancel*/
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*Done*/
        buttonDONE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}