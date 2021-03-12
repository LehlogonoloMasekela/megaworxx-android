package com.doosy.megaworxx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.model.DialogMessage;
import com.doosy.megaworxx.service.listener.OnCheckDialogListener;

public class CheckDialog extends DialogFragment {

    private OnCheckDialogListener onCheckDialogListener;

    private static final String ARG_HEADER = "header";
    private static final String ARG_BODY = "body";
    private static final String ARG_ICON= "icon";

    private int icon;
    private String mHeader;
    private String mBody;


    public static CheckDialog newInstance(DialogMessage dialogMessage){
        CheckDialog fragment = new CheckDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_ICON, dialogMessage.getIcon());
        args.putString(ARG_HEADER, dialogMessage.getHeader());
        args.putString(ARG_BODY, dialogMessage.getMessage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            icon = getArguments().getInt(ARG_ICON);
            mHeader = getArguments().getString(ARG_HEADER);
            mBody = getArguments().getString(ARG_BODY);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_check_in_out, null);
        Button btnYes = view.findViewById(R.id.btnOk);

        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        TextView header = view.findViewById(R.id.tvHeader);
        TextView body = view.findViewById(R.id.tvMessage);

        imgIcon.setImageResource(icon);

        if(mHeader != null && mBody != null) {
            header.setText(mHeader);
            body.setText(mBody);
        }

        onCheckDialogListener = (OnCheckDialogListener) getActivity();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckDialogListener.onOkClicked();
                dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_check_in_out, container, false);


        return view;

    }
}
