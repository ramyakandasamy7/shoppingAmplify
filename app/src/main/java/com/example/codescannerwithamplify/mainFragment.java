package com.example.codescannerwithamplify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.auth.core.IdentityManager;
import com.amazonaws.mobile.client.AWSMobileClient;


public class mainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    Button scan_btn;
    Button cart_btn;

    //private OnFragmentInteractionListener mListener;

    public mainFragment() {
        // Required empty public constructor
    }

    Button signout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences("StoreID", Context.MODE_PRIVATE);
        String storeID = sharedPref.getString("StoreID", "");
        Button skip = view.findViewById(R.id.button3);
        if(storeID != "") {
            skip.setVisibility(View.VISIBLE);
            skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("barcodeID", storeID);
                    //Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_scannedStoreFragment, bundle);
                }
            });
        }

        scan_btn= view.findViewById(R.id.btn_scan);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_scanCodeFragment);
            }
        });
        TextView textView = (TextView) view.findViewById(R.id.textView3);
//        Log.i("name is", AWSMobileClient.getInstance().getUsername().toString());
        textView.setText(AWSMobileClient.getInstance().getUsername());
        signout = (Button) view.findViewById(R.id.button2);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AWSMobileClient.getInstance().signOut();
                IdentityManager.getDefaultIdentityManager().signOut();
               // Navigation.findNavController(view).navigate(R.id.)
            }
        });
        return view;
    }

}
