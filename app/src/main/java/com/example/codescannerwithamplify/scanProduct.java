package com.example.codescannerwithamplify;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class scanProduct extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    //Product selectedProduct;
    String storeID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        storeID = getArguments().getString("storeID");
        return mScannerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(getActivity(), "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(scanProduct.this);
            }
        }, 2000);
        Bundle bundle = new Bundle();
        bundle.putString("productBarcode", rawResult.getText());
        bundle.putString("storeID", storeID);
        Navigation.findNavController(getView()).navigate(R.id.action_scanProduct_to_scannedProduct, bundle);
       // Navigation.findNavController(getView()).navigate(R.id.action_scanCodeFragment_to_scanProductFragment, bundle);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

}