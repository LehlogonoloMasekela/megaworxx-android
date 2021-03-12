package com.doosy.megaworxx.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.doosy.megaworxx.R;
import com.doosy.megaworxx.model.DialogMessage;
import com.doosy.megaworxx.service.listener.OnConfirmDialogListener;

public class ConfirmDialog extends DialogFragment {

    private OnConfirmDialogListener onConfirmDialogListener;

    private static final String ARG_HEADER = "header";
    private static final String ARG_BODY = "body";

    private int icon;
    private String mHeader;
    private String mBody;


    public static ConfirmDialog newInstance(DialogMessage dialogMessage){
        ConfirmDialog fragment = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(ARG_HEADER, dialogMessage.getHeader());
        args.putString(ARG_BODY, dialogMessage.getMessage());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
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
        View view = inflater.inflate(R.layout.dialog_confirm, null);
        Button btnYes = view.findViewById(R.id.btnYes);
        Button btnNo = view.findViewById(R.id.btnNo);

        TextView header = view.findViewById(R.id.tvHeader);
        TextView body = view.findViewById(R.id.tvBody);

        if(mHeader != null && mBody != null) {
            header.setText(mHeader);
            body.setText(mBody);
        }

        onConfirmDialogListener = (OnConfirmDialogListener) getActivity();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmDialogListener.onYesClicked();
                dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmDialogListener.onNoClicked();
            }
        });
        builder.setView(view);
        setCancelable(false);
        return builder.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dialog_confirm, container, false);

        return view;

    }
}
