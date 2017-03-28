package com.wakeup.forever.wakeup.widget;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wakeup.forever.wakeup.R;


/**
 * Created by Wesly186 on 2016/11/12.
 */

public class EditDialogBuilder {

    private Context context;
    private String dialogTitle;

    private PositiveButtonClickListener PositiveButtonListener;


    public EditDialogBuilder(Context context) {
        this.context = context;
    }

    public AlertDialog build() {
        View dialogView = View.inflate(context, R.layout.dialog_modify, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();

        TextView tvTitle = (TextView) dialogView.findViewById(R.id.dialog_title);
        tvTitle.setText(TextUtils.isEmpty(dialogTitle) ? "标题" : dialogTitle);

        final EditText etFeedback = (EditText) dialogView.findViewById(R.id.et_feedback);
        TextView tvCancel = (TextView) dialogView.findViewById(R.id.btn_cancel);
        TextView tvConfirm = (TextView) dialogView.findViewById(R.id.btn_confirm);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PositiveButtonListener != null) {
                    PositiveButtonListener.onClick(etFeedback.getText().toString().trim());
                }
                dialog.dismiss();
            }
        });
        return dialog;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public EditDialogBuilder setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        return this;
    }

    public EditDialogBuilder setPositiveButtonListener(PositiveButtonClickListener positiveButtonListener) {
        PositiveButtonListener = positiveButtonListener;
        return this;
    }

    public interface PositiveButtonClickListener {
        void onClick(String content);
    }
}
