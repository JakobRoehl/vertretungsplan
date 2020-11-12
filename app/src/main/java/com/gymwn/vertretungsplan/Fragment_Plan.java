package com.gymwn.vertretungsplan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;



public class Fragment_Plan extends Fragment {
    private ProgressDialog mainloaderdialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container,false);
        HTMLgetter("https://h2903870.stratoserver.net/schueler/schueler.htm");

        return view;
    }
    public void HTMLgetter(final String url) {

        if(isNetworkAvailable()) {
            mainloaderdialog = new ProgressDialog(getActivity(), R.style.black_dialog);
            mainloaderdialog.setMessage("Empfange Daten");
            mainloaderdialog.setCancelable(false);
            mainloaderdialog.show();
            Ion.with(getActivity()).load(url).asString().setCallback(new FutureCallback<String>() {
                @Override
                public void onCompleted(Exception e, String result) {
                    mainloaderdialog.dismiss();
                }
            });
        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("Es besteht keine Internetverbindung!");
            builder1.setTitle("Keine Verbindung");
            builder1.setIcon(R.drawable.ic_baseline_wifi_off_24);

            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Erneut versuchen",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            HTMLgetter(url);
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            ImageView imageView = alert11.findViewById(android.R.id.icon);
            if (imageView != null)
                imageView.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
