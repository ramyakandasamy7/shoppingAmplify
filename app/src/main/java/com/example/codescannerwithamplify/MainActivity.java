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
        productScannedText=findViewById(R.id.product);
        scan_btn= findViewById(R.id.btn_scan);
        scan_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ScanCodeActivity.class);
                startActivityForResult(intent, 2);
            }
        });
        erase_btn=(Button) findViewById(R.id.button);
        erase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productScannedText.setText("");
            }
        });
        confirm_btn = findViewById(R.id.confirm);


        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("AmplifyGetStarted", "Amplify is all setup and ready to go!");
        } catch (AmplifyException exception) {
            Log.e("AmplifyGetStarted", exception.getMessage());
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //createStore();
        //createProduct();
       // reducebyOneProduct("e9047c0a-3f57-487a-bfcd-cc9a0bb3aee7");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void createStore() {
        Store store = Store.builder().name("Whole Foods - Amplify").location("330 Ballymore Circle").build();
        Amplify.API.mutate(
                store,
                MutationType.CREATE,
                new ResultListener<GraphQLResponse<Store>>() {
                    @Override
                    public void onResult(GraphQLResponse<Store> response) {
                        Log.i("ApiQuickStart", "Added Store with id: " + response.getData().getId());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("ApiQuickStart", throwable.getMessage());
                    }
                }
        );
    }
    private void createProduct() {
        Store s = Store.justId("15d08a00-4bde-49da-afed-182363621fad");
        Product product = Product.builder().barcodeNumber("099482438982").quantity(10).store(s).price(3.50F).name("Bran Flakes").build();
        Amplify.API.mutate(
                product,
                MutationType.CREATE,
                new ResultListener<GraphQLResponse<Product>>() {
                    @Override
                    public void onResult(GraphQLResponse<Product> response) {
                        Log.i("ApiQuickStart", "Added product with id: " + response.getData().getId());

                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e("ApiQuickStart", error.getMessage());

                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String message = data.getStringExtra("MyData");
                Product p= showProduct(message);


            }

        }
    }
    private Product showProduct(String barcode) {
        Amplify.API.query(
                Product.class,
                Product.BARCODE_NUMBER.contains(barcode),
                new ResultListener<GraphQLResponse<Iterable<Product>>>(){
                    @Override
                    public void onResult(GraphQLResponse<Iterable<Product>> iterableGraphQLResponse) {
                        for(Product product : iterableGraphQLResponse.getData()) {
                            Log.i("ApiQuickstart", "List result: " + product.getName());
                            setText(productScannedText, "Name of Product " + product.getName() +" Price: " +product.getPrice() +" Quantity: " + product.getQuantity());
                            setConfirm_btn(confirm_btn, product);
                        }

                   //    confirm_btn.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("ApiQuickStart", throwable.getMessage());
                        selectedProduct = null;
                    }
                }
        );
        return selectedProduct;

    }
    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
    private void setConfirm_btn(Button button, Product product) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (button.getVisibility() == View.INVISIBLE || button.getVisibility() == View.GONE) {
                    button.setVisibility(View.VISIBLE);
                }
                else {
                    button.setVisibility(View.INVISIBLE);
                }
                if (erase_btn.getVisibility()== View.INVISIBLE || erase_btn.getVisibility() == View.GONE) {
                    erase_btn.setVisibility(View.VISIBLE);
                }
                else {
                    erase_btn.setVisibility(View.INVISIBLE);
                }
            }
        });
        Log.e("Product ID IS", product.getId());
        confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              reducebyOneProduct(product);
            }
        });
    }

    private void reducebyOneProduct (Product p) {
        Product product = Product.builder().barcodeNumber("099482438982").quantity(p.getQuantity()-1).store(p.getStore()).id(p.getId()).build();
                Amplify.API.mutate(
                        product,
                        MutationType.UPDATE,
                        new ResultListener<GraphQLResponse<Product>>() {
                            @Override
                            public void onResult(GraphQLResponse<Product> tGraphQLResponse) {
                                Log.e("Updated quantity", tGraphQLResponse.getData().getQuantity().toString());
                                showProduct(tGraphQLResponse.getData().getBarcodeNumber());
                                // Do nothing - watch it come in the subscription
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Log.e("ApiQuickStart", throwable.getMessage());
                            }
                        }

                );


            }

         /*   @Override
            public void onError(Throwable throwable) {
                Log.e("ApiQuickStart", throwable.getMessage());
            }
        });

    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
