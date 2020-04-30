package com.example.codescannerwithamplify;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Cart;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Store;

import java.util.HashSet;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link transaction#newInstance} factory method to
 * create an instance of this fragment.
 */
public class transaction extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView textView;
    Button back;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public transaction() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment transaction.
     */
    // TODO: Rename and change types and number of parameters
    public static transaction newInstance(String param1, String param2) {
        transaction fragment = new transaction();
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
        View rootView = inflater.inflate(R.layout.fragment_transaction,container,false);
        textView= (TextView) rootView.findViewById(R.id.transactionText);
        showTransaction();

        back= rootView.findViewById(R.id.backscan);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String storeID = sharedPref.getString("StoreID", "");
                bundle.putString("barcodeID", storeID);
                Navigation.findNavController(view).navigate(R.id.action_transaction_to_scannedStoreFragment, bundle);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void showTransaction() {

        Amplify.API.query(Cart.class, Cart.USER.contains(AWSMobileClient.getInstance().getUsername()), new ResultListener<GraphQLResponse<Iterable<Cart>>>() {
            @Override
            public void onResult(GraphQLResponse<Iterable<Cart>> iterableGraphQLResponse) {
                String t = "";
                for (Cart cart :iterableGraphQLResponse.getData()) {
                    t = t + "Store: " +cart.getStorename() + ", Product: " + cart.getProduct() +", Paid: " + cart.getPaid() + "\n";
                }
                Log.d("HELLO", t);
                setText(t);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("Was not able to get", throwable.getMessage());
            }
        });

    }
    private void setText(String message){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(message);
            }
        });
    }

}
