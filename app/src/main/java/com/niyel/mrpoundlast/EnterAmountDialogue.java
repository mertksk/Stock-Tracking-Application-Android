package com.niyel.mrpoundlast;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EnterAmountDialogue extends AppCompatDialogFragment  {

    private EditText EnterAmountEditText;
    private EnterAmountDialogueListener listener;

    private static final String ARG_PRODUCTNAME = "argProductName";
    private static final String ARG_AMOUNT = "argNumber";
    private static final String ARG_UOMNAME = "arUomName";
    private static final String ARG_LOCATION = "argLocation";
    private static final String ARG_DESTINATION = "argDestination";




    public static EnterAmountDialogue newInstance(int number,String ProductName,String UomName,String Location, String Destination) {
        EnterAmountDialogue fragment = new EnterAmountDialogue();
        Bundle args = new Bundle();

        args.putString(ARG_PRODUCTNAME, ProductName);
        args.putString(ARG_UOMNAME, UomName);
        args.putString(ARG_LOCATION, Location);
        args.putString(ARG_DESTINATION, Destination);
        args.putInt(ARG_AMOUNT, number);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        if (getArguments() != null) {

            int requiredAmount = getArguments().getInt(ARG_AMOUNT);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_amount_dialogue,null);
        EnterAmountEditText = view.findViewById(R.id.EnterAmountEditText);
        TextView detailsText = view.findViewById(R.id.DetailsText);

        detailsText.setText("Ürün Adı:"+getArguments().getString(ARG_PRODUCTNAME)+"\n"+"Gerekli Miktar: "+getArguments().getInt(ARG_AMOUNT)+"\n"+"Ölçü Birimi: "+getArguments().getString(ARG_UOMNAME)+"\n"+"Başlangıç Konumu: "+getArguments().getString(ARG_LOCATION)+"\n"+"Varış Konumu: "+getArguments().getString(ARG_DESTINATION)+"\n");

        builder.setView(view)
                .setTitle("Miktar Girişi")

                .setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int amount = Integer.parseInt(EnterAmountEditText.getText().toString());
                        listener.applyTexts(amount);
                    }
                })
                .setNegativeButton("İptal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNeutralButton("Detaylar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.passProductDetails();

                    }
                })    ;


        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (EnterAmountDialogueListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(context.toString()+"Dialog Listener Gerekiyor");
        }
    }


    /*To send and get parameters to activity*/
    public interface EnterAmountDialogueListener{
        void applyTexts(int amount);
        void passProductDetails();

    }

}
