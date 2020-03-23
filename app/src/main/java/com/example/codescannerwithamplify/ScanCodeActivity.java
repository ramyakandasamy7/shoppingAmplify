package com.example.codescannerwithamplify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Product;
import com.google.zxing.Result;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;

import javax.annotation.Nonnull;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    ZXingScannerView ScannerView;
    //Product scannedProduct;
    String scannedProductBarcode;
    int MY_PERMISSIONS_REQUEST_CAMERA=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
        //queryByBarcode(result.getText());
    }


    @Override
    public void handleResult(Result result) {
        //queryByBarcode(result.getText());
        scannedProductBarcode = result.getText();
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("MyData", scannedProductBarcode);
       // setResult(resultcode, intent);
        setResult(RESULT_OK, intent);
        finish();
    }
    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
    private void queryByBarcode(String barcode) {
        Amplify.API.query(
                Product.class,
                Product.BARCODE_NUMBER.contains(barcode),
                new ResultListener<GraphQLResponse<Iterable<Product>>>(){
                    @Override
                    public void onResult(GraphQLResponse<Iterable<Product>> iterableGraphQLResponse) {
                        for(Product product : iterableGraphQLResponse.getData()) {
                            Log.i("ApiQuickstart", "List result: " + product.getName());
                            MainActivity.productScannedText.setText("Name of Product " + product.getName() +" Price: " +product.getPrice());
                        }

                        MainActivity.confirm_btn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("ApiQuickStart", throwable.getMessage());
                    }
                }
        );


    }

    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}

