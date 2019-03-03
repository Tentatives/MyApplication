package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandFragment extends Fragment {

    EditText cmd;
    TextView result;
    String ct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_command, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cmd = (EditText) view.findViewById(R.id.editText3);
        result = (TextView) view.findViewById(R.id.textView1);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmd.clearFocus();
                ct = cmd.getText().toString();
                String st = func(ct);
                result.setText(st);
                cmd.setText("");
            }
        });

    }

    public String func(String cm) {
        String str = "";
        try {
            Process process = Runtime.getRuntime().exec(
                    "/system/bin/" + cm);
            /*Process process = Runtime.getRuntime().exec(
                    "/system/bin/ifconfig ");*/
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    process.getInputStream()));
            int i;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((i = reader.read(buffer)) > 0)
                output.append(buffer, 0, i);
            reader.close();

            // body.append(output.toString()+"\n");
            str = output.toString();
            // Log.d(TAG, str);
        } catch (IOException e) {
            // body.append("Error\n");
            result.setText(e.getMessage().toString());
        }
        return str;
    }

}
