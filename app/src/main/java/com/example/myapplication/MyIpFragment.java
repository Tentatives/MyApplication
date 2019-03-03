package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

public class MyIpFragment extends Fragment {

    TextView myip;
    TextView pip,iip,ms,ifn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_myip, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pip = (TextView) view.findViewById(R.id.txtView2);
        iip = (TextView) view.findViewById(R.id.txtView4);
        ms = (TextView) view.findViewById(R.id.txtView6);
        ifn = (TextView) view.findViewById(R.id.txtView8);

        myip = (TextView) view.findViewById(R.id.textView6);
        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/curl ifconfig.me");
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
              myip.setText(output.toString());
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            myip.setText(e.getMessage().toString());
        }

        ExampleAsyncTask task = new ExampleAsyncTask();
        task.execute("rmnet_data0");
    }

    private class ExampleAsyncTask extends AsyncTask<String,String,String>{

        int msize;
        String internal, ifname,ifaddr,mac;
        NetworkInterface nic;
        InetAddress ia;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String interfaceName = strings[0];
            try {
                ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface iface : interfaces) {
                    // isUp() method used for checking whether the interface in process
                    // is up and running or not.
                    if (iface.isUp()) {

                        // getName() method
                        ifname = iface.getName().toString();
                        for (InterfaceAddress addr : iface.getInterfaceAddresses().subList(0,1))
                        {
                           ifaddr = addr.getAddress().toString();
                        }
                        msize = iface.getMTU();

                    }
                }

                    } catch (SocketException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            pip.setText(myip.getText().toString());
            iip.setText(ifaddr);
            ms.setText(Integer.toString(msize));
            ifn.setText(ifname);

        }
    }

}
