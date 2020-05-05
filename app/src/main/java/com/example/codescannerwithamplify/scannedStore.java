
package com.example.codescannerwithamplify;

        import android.content.Context;
        import android.content.SharedPreferences;
        import android.os.Bundle;
        import android.os.Handler;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.amplifyframework.api.graphql.GraphQLResponse;
        import com.amplifyframework.core.Amplify;
        import com.amplifyframework.core.ResultListener;
        import com.amplifyframework.datastore.generated.model.Product;
        import com.amplifyframework.datastore.generated.model.Store;
        import com.google.zxing.Result;

        import me.dm7.barcodescanner.zxing.ZXingScannerView;

        import androidx.fragment.app.Fragment;
        import androidx.navigation.Navigation;


public class scannedStore extends Fragment {
    public String inputText;
    Store selectedStore;
    TextView storeName;
    Button btn;
    Button transactionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanned_store, container, false);
        inputText = getArguments().getString("barcodeID");
        storeName = (TextView) view.findViewById(R.id.textView);
        showStore(inputText);
        btn = view.findViewById(R.id.scanProduct);
        btn.setVisibility(View.INVISIBLE);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("storeID", inputText);
                Navigation.findNavController(view).navigate(R.id.action_scanProductFragment_to_scanProduct, bundle);
            }
        });

        transactionButton = view.findViewById(R.id.transaction_btn);
        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scannedStoreFragment_to_transaction);
            }
        });

        Button cart_btn;
        cart_btn= view.findViewById(R.id.buttoncart);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_scannedStoreFragment_to_cartView);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void showStore(String barcode) {

        Amplify.API.query(Store.class, Store.ID.contains(barcode), new ResultListener<GraphQLResponse<Iterable<Store>>>() {
            @Override
            public void onResult(GraphQLResponse<Iterable<Store>> iterableGraphQLResponse) {
                if (iterableGraphQLResponse == null) {
                    storeName.setText("There is no store by that name, please try again");
                }
                else {
                    for (Store store : iterableGraphQLResponse.getData()) {
                        Log.i("Store is", "List result: " + store.getName());
                        selectedStore = store;
                        Context context = getActivity();
                        SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("StoreName", store.getName()).commit();
                        setText(store.getName());
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                //btn = view.findViewById(R.id.scanProduct);
                Log.e("Was not able to get", throwable.getMessage());
                btn.setVisibility(View.INVISIBLE);
                setText("There is no store by that name, please try again");
            }
        });

    }
    private void setText(String message){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btn.setVisibility(View.VISIBLE);
                storeName.setText(message);
            }
        });
    }
}
