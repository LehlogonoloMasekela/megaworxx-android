package com.doosy.megaworxx;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private static String paramHeader = "header";
    private static String paramBody = "body";

    private String header;
    private String body;

    public static BottomSheetFragment newInstance(String header, String body){
        BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
        Bundle args = new Bundle();
        args.putString(paramHeader, header);
        args.putString(paramBody, body);
        bottomSheetFragment.setArguments(args);
        return bottomSheetFragment;
    }

    public BottomSheetFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            Bundle bundle = getArguments();
            if(bundle.containsKey(paramHeader) && bundle.containsKey(paramBody)){
                header = bundle.getString(paramHeader);
                body = bundle.getString(paramBody);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_bottom_nav_message, container, false);
        TextView txtHeader = view.findViewById(R.id.txtHeader);
        TextView txtBody = view.findViewById(R.id.txtBody);

        if((header != null) && (body != null)){
            txtHeader.setText(header);
            txtBody.setText(body);
        }

        Button btnOk = view.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }


}