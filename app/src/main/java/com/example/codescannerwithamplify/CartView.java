package com.example.codescannerwithamplify;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Cart;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Store;

import org.w3c.dom.Text;

import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CartView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button backscan;
    Button checkoutBtn;
    Button deleteBtn;

    public CartView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CartView.
     */
    // TODO: Rename and change types and number of parameters
    public static CartView newInstance(String param1, String param2) {
        CartView fragment = new CartView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        View view = inflater.inflate(R.layout.fragment_cart_view, container, false);
        backscan= view.findViewById(R.id.backscan);
        backscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String storeID = sharedPref.getString("StoreID", "");
                bundle.putString("barcodeID", storeID);
                Navigation.findNavController(view).navigate(R.id.action_cartView_to_scannedStoreFragment, bundle);
            }
        });

        deleteBtn= view.findViewById(R.id.btn_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences("Shopping Cart", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putStringSet("Shopping Cart", new HashSet<String>()).commit();

                Bundle bundle = new Bundle();
                SharedPreferences sharedPref2 = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                String storeID = sharedPref2.getString("StoreID", "");
                bundle.putString("barcodeID", storeID);
                Navigation.findNavController(view).navigate(R.id.action_cartView_to_scannedStoreFragment, bundle);


            }
        });



        checkoutBtn= view.findViewById(R.id.btn_checkout);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Bundle bundle = new Bundle();
                Context context = getActivity();
                SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                String storeID = sharedPref.getString("StoreID", "");
                String storeName = sharedPref.getString("StoreName", "");
                bundle.putString("barcodeID", storeID);

                SharedPreferences savedCart = context.getSharedPreferences("Shopping Cart", Context.MODE_PRIVATE);
                Set<String> thecart = savedCart.getStringSet("Shopping Cart", new HashSet<String>());
                for (String s : thecart) {
                    String[] line = s.split(":");
                    String product = line[0];
                    String price = line[1];
                    String quantity = line[2];
                    float total = (float) (Double.parseDouble(price) *  Double.parseDouble(quantity));

                    Cart shoppingCart = Cart.builder()
                            .user(AWSMobileClient.getInstance().getUsername())
                            .paid(total)
                            .storename(storeName)
                            .product(product)
                            .build();

                    Amplify.API.mutate(
                            shoppingCart,
                            MutationType.CREATE,
                            new ResultListener<GraphQLResponse<Cart>>() {
                                @Override
                                public void onResult(GraphQLResponse<Cart> tGraphQLResponse) {
                                    Log.e("Updated quantity", tGraphQLResponse.getData().getUser().toString());
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    Log.e("ApiQuickStart", throwable.getMessage());
                                }
                            }

                    );
                }

                SharedPreferences.Editor editor2 = savedCart.edit();
                editor2.putStringSet("Shopping Cart", new HashSet<String>()).commit();

                Navigation.findNavController(view).navigate(R.id.action_cartView_to_scannedStoreFragment, bundle);
            }
        });

        //TextView textView = (TextView) view.findViewById(R.id.itemText);
        Context context = getActivity();
        //String cart_string = "Shopping Cart:";
        String cart_string = "";
        SharedPreferences sharedPref = context.getSharedPreferences("Shopping Cart", Context.MODE_PRIVATE);
        Set<String> thecart = sharedPref.getStringSet("Shopping Cart", new HashSet<String>());
        System.out.println(thecart);

        //Table Layout Attempt
        TableLayout tl = view.findViewById(R.id.shoppingCartTable);
        tl.setStretchAllColumns(true);

        List<String> prices = new ArrayList<String>();

        for(String s : thecart) {
            String itemName  = s.split(":")[0];
            String itemPrice = s.split(":")[1];
            String itemQty   = s.split(":")[2];
            prices.add(itemPrice);
            //cart_string = cart_string + "\n" + String.format("%s - %s - %s", itemName, itemQty, itemPrice);

            TableRow tr      = new TableRow(context);
            TextView tvName  = new TextView(context);
            TextView tvQty   = new TextView(context);
            TextView tvPrice = new TextView(context);
            TextView xer     = new TextView(context);

            tvQty.setGravity(Gravity.RIGHT);
            tvPrice.setGravity(Gravity.RIGHT);
            xer.setGravity(Gravity.CENTER);

            tvName.setText(itemName);
            tvQty.setText(itemQty);
            tvPrice.setText(itemPrice);
            xer.setText("x");

            tr.addView(tvName);
            tr.addView(tvQty);
            tr.addView(xer);
            tr.addView(tvPrice);

            tl.addView(tr);

        }

        Float tax = 9.25f; //San Jose Sales Tax
        Float taxPaid = 0f;
        Float subtotal = 0f, total = 0f;

        TextView subtxt = new TextView(context);
        subtxt.setGravity(Gravity.RIGHT);
        TextView subval = new TextView(context);
        subval.setGravity(Gravity.RIGHT);
        TextView taxtxt = new TextView(context);
        taxtxt.setGravity(Gravity.RIGHT);
        TextView taxval = new TextView(context);
        taxval.setGravity(Gravity.RIGHT);
        TextView tottxt = new TextView(context);
        tottxt.setGravity(Gravity.RIGHT);
        TextView totval = new TextView(context);
        totval.setGravity(Gravity.RIGHT);

        subtxt.setText("Subtotal");
        taxtxt.setText("Tax 9.25%");
        tottxt.setText("Total");

        for (String p:
             prices) {
            Float price = Float.parseFloat(p);
            subtotal += price;
        }

        taxPaid = ((tax/100) * subtotal);
        total = taxPaid + subtotal;

        subval.setText(String.format("$%.2f",subtotal));
        taxval.setText(String.format("$%.2f",taxPaid));
        totval.setText(String.format("$%.2f",total));

        TableRow trSub = new TableRow(context);
        TableRow trTax = new TableRow(context);
        TableRow trTot = new TableRow(context);

        trSub.addView(subtxt);
        trSub.addView(subval);
        TableRow.LayoutParams sublptxt = (TableRow.LayoutParams) subtxt.getLayoutParams();
        sublptxt.span = 3;
        tl.addView(trSub);

        trTax.addView(taxtxt);
        trTax.addView(taxval);
        TableRow.LayoutParams taxlptxt = (TableRow.LayoutParams) taxtxt.getLayoutParams();
        taxlptxt.span = 3;
        tl.addView(trTax);

        trTot.addView(tottxt);
        trTot.addView(totval);
        TableRow.LayoutParams totlptxt = (TableRow.LayoutParams) tottxt.getLayoutParams();
        totlptxt.span = 3;
        tl.addView(trTot);

        //textView.setText(cart_string);
        /*recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);

        ListAdapter listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);*/

        return view;
    }
}
