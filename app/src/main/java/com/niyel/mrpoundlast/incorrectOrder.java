package com.niyel.mrpoundlast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link incorrectOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class incorrectOrder extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public incorrectOrder() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment incorrectOrder.
     */
    // TODO: Rename and change types and number of parameters
    public static incorrectOrder newInstance(String param1, String param2) {
        incorrectOrder fragment = new incorrectOrder();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    protected void onPostExecute (long Result){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_correct_order,container,false);


        OrderListMain orderlistActivity = (OrderListMain) getActivity();


        ArrayList<Order> orders = orderlistActivity.getOrders();


        if(orders!=null){

            ArrayList<String> incorrects= new ArrayList<String>();


            for(int i=0;i< orders.size();i++){
                if(orders.get(i).isOrderType()){
                    incorrects.add("ID-> "+orders.get(i).getId()+"\n"+"Name-> "+orders.get(i).getName()+"\n"+"Scheduled Date-> "+orders.get(i).getDate()+"\n"+"Location Name-> "+orders.get(i).getLocationName()+"\n");
                }

            }
            if(incorrects.size()!=0){

                //ddd
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        incorrects

                );
                ListView lvData=(ListView) view.findViewById(R.id.incorrectOrderListView);
                lvData.setAdapter(adapter);


                /*Passing into details*/
                lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent myIntent = new Intent(getActivity(), OrderDetails.class);
                        //dd
                        if(orders!=null)myIntent.putExtra("KEY_ORDER",  orders.get(position));
                        startActivity(myIntent);
                    }
                });

            }



        }

        // Inflate the layout for this fragment
        return view;
    }
}