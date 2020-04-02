package com.example.codescannerwithamplify;

import android.content.Intent;
import android.os.Bundle;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.GraphQLResponse;
import com.amplifyframework.api.graphql.MutationType;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.ResultListener;
import com.amplifyframework.datastore.generated.model.Product;
import com.amplifyframework.datastore.generated.model.Store;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentTransaction;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {
    private AWSAppSyncClient mAWSAppSyncClient;
    static TextView productScannedText;
    static Button confirm_btn;
    Button scan_btn;
    Button erase_btn;
    Product selectedProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyGetStarted", "Amplify is all setup and ready to go!");
        } catch (AmplifyException exception) {
            Log.e("AmplifyGetStarted", exception.getMessage());
        }

    }
}
