package com.niyel.mrpound.MainComponents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.niyel.mrpound.R;
import com.niyel.mrpound.globalVariables.Order;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CorrectOrders#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CorrectOrders extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CorrectOrders() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CorrectOrders.
     */
    // TODO: Rename and change types and number of parameters
    public static CorrectOrders newInstance(String param1, String param2) {
        CorrectOrders fragment = new CorrectOrders();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListView orderListView = (ListView) getView().findViewById(R.id.correctOrderListView);


        OrderListMain orderlistActivity = (OrderListMain) getActivity();
        ArrayList<Order> orders;
        ArrayList<Order> corrects= new ArrayList<Order>();
        orders= orderlistActivity.getOrders();

        for(int i=0;i< orders.size();i++){
             if(!(orders.get(i).isOrderType())){
                 corrects.add(orders.get(i));
             }

        }

        ArrayAdapter<Order> adapter =new ArrayAdapter<Order>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1,corrects);
        orderListView.setAdapter(adapter);

        orderListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //next
                System.out.println(position);
            }
        });


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_correct_orders, container, false);
    }
}