package com.example.codescannerwithamplify;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.HashSet;
import java.util.Set;


public class scanStore extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
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
                mScannerView.resumeCameraPreview(scanStore.this);
            }
        }, 2000);
        Bundle bundle = new Bundle();
        bundle.putString("barcodeID", rawResult.getText());


        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("StoreID", rawResult.getText()).commit();


        Navigation.findNavController(getView()).navigate(R.id.action_scanCodeFragment_to_scanProductFragment, bundle);

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