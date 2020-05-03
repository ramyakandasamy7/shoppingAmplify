package com.example.codescannerwithamplify;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Product;
import com.google.gson.Gson;

import java.lang.Runnable;
import java.util.HashSet;
import java.util.Set;

public class scannedProduct extends Fragment {
    TextView productName;
    TextView productQuantity;
    TextView productPrice;
    Product selectedProduct;
    Button addtoCart;

    public scannedProduct() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_scanned_product, container, false);
         String productbarcode = getArguments().getString("productBarcode");
         Log.i ("input string is" , productbarcode);
         String storeID = getArguments().getString("storeID");
         Log.i ("Store ID is" , storeID);
         productName = view.findViewById(R.id.productName);
         productPrice = view.findViewById(R.id.price);
         addtoCart = view.findViewById(R.id.addCart);
         productQuantity = view.findViewById(R.id.productQuantity);
         showProduct(productbarcode, storeID);
         EditText eText = (EditText) view.findViewById(R.id.quantity);


         addtoCart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String quantity = eText.getText().toString();
                 boolean remove = false;
                 Context context = getActivity();
                 SharedPreferences sharedPref = context.getSharedPreferences("Shopping Cart", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor = sharedPref.edit();
                 Set<String> hs = sharedPref.getStringSet("Shopping Cart", new HashSet<String>());
                 Set<String> in = new HashSet<String>(hs);
                 for(String a : in) {
                     if(remove) {
                         if(a.contains(selectedProduct.getName())) {
                             in.remove(a);
                         }
                     }
                 }
                 in.add(selectedProduct.getName() + ":" + selectedProduct.getPrice()+":"+quantity);
                 editor.putStringSet("Shopping Cart", in).commit();
                 Set<String> thecart = sharedPref.getStringSet("Shopping Cart", new HashSet<String>());
                 for(String s : thecart) {
                     Log.d("CART", s);
                 }
                 reducebyOneProduct(selectedProduct, Integer.parseInt(quantity));

                 Bundle bundle = new Bundle();
                 Context context2 = getActivity();
                 SharedPreferences sharedPref2 = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                 SharedPreferences.Editor editor2 = sharedPref2.edit();
                 String storeID = sharedPref2.getString("StoreID", "");
                 String storeName = sharedPref2.getString("StoreName", "");
                 bundle.putString("barcodeID", storeID);
                 Navigation.findNavController(view).navigate(R.id.action_scannedProduct_to_scannedStoreFragment, bundle);
             }
        });
         return view;
    }

    private void showProduct(String barcode, String storeID) {
        Amplify.API.query(Product.class, Product.BARCODE.contains(barcode), new ResultListener<GraphQLResponse<Iterable<Product>>>() {
            @Override
            public void onResult(GraphQLResponse<Iterable<Product>> iterableGraphQLResponse) {
                for (Product product : iterableGraphQLResponse.getData()) {
                    Log.i("Product is", "List result: " + product.getName());
                    if (product.getStore().getId().equals(storeID)) {
                        selectedProduct = product;
                        setText(productQuantity, productName, productPrice, product);
                    }

                }

            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("Was not able to get", throwable.getMessage());
            }
        });

    }

    private void reducebyOneProduct (Product p, int quantity) {
        Amplify.API.mutate(
                Product.builder().quantity(p.getQuantity()-quantity).id(p.getId()).store(p.getStore()).build(),
                MutationType.UPDATE,
                new ResultListener<GraphQLResponse<Product>>() {
                    @Override
                    public void onResult(GraphQLResponse<Product> tGraphQLResponse) {
                        Log.e("Updated quantity", tGraphQLResponse.getData().getQuantity().toString());
                        showProduct(tGraphQLResponse.getData().getBarcode(), tGraphQLResponse.getData().getStore().getId());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("ApiQuickStart", throwable.getMessage());
                    }
                }

        );


    }
    private void setText(final TextView productQuantity,final TextView productName, final TextView productPrice, final Product p){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String price = String.format("$%.2f/unit", p.getPrice());
                String inStock = String.format("%d", Math.round(p.getQuantity()));
                productQuantity.setText(inStock);
                productName.setText(p.getName());
                productPrice.setText(price);
            }
        });
    }

         /*   @Override
            public void onError(Throwable throwable) {
                Log.e("ApiQuickStart", throwable.getMessage());
            }
        });

    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
