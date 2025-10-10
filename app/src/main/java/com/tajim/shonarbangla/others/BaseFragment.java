package com.tajim.shonarbangla.others;

import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    AlertDialog loadingAlert;

    protected void startLoading() {
        ProgressBar progressBar = new ProgressBar(getContext());

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setView(progressBar)
                .setCancelable(false)
                .create();

        dialog.show();

        dialog.getWindow().setLayout(175, 175);

        loadingAlert = dialog;
    }

    protected void endLoading() {
        if (loadingAlert != null && loadingAlert.isShowing()) {
            loadingAlert.dismiss();
        }
    }
}
