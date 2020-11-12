package com.gymwn.vertretungsplan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class Fragment_Plan extends Fragment {
    private ProgressDialog mainloaderdialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan, container,false);
        try {
            HTMLgetter("https://h2903870.stratoserver.net/schueler/schueler.htm", view);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
    public class myAsyncTask extends AsyncTask<String, String, String> {
        public String html;

        @Override
        public String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(params[0]);
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String html = "";
            InputStream in = null;
            try {
                in = response.getEntity().getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while(true)
            {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                str.append(line);
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            html = str.toString();
           // Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();
            mainloaderdialog.dismiss();
            return html;
        }

    }
    public void HTMLgetter(final String surl, final View view) throws IOException {

        if(isNetworkAvailable()) {
            mainloaderdialog = new ProgressDialog(getActivity(), R.style.black_dialog);
            mainloaderdialog.setMessage("Empfange Daten");
            mainloaderdialog.setCancelable(false);
            mainloaderdialog.show();


            myAsyncTask task= new myAsyncTask();
            try {

               // Toast.makeText(getActivity(), task.execute(surl).get(), Toast.LENGTH_LONG).show();
            //    Log.v("", task.execute(surl).get());
                TextView tv = (TextView)view.findViewById(R.id.text1);
                tv.setText(task.execute(surl).get());

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();

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
                            try {
                                HTMLgetter(surl, view);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
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
