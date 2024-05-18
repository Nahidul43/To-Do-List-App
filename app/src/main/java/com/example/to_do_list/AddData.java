package com.example.to_do_list;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddData extends Fragment {
    ImageView addButton, upDate,delete;
    TextInputEditText edTitle, edMsg;
    DatabaseHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_data, container, false);

        edTitle = view.findViewById(R.id.edTitle);
        edMsg = view.findViewById(R.id.edMsg);
        upDate = view.findViewById(R.id.upDate);
        delete = view.findViewById(R.id.delete);
        addButton = view.findViewById(R.id.addButton);

        helper = new DatabaseHelper(getContext());

        if (getArguments() != null) {
            String msg = getArguments().getString("msg");
            String title = getArguments().getString("title");
            String id = getArguments().getString("id");
            edTitle.setText(title);
            edMsg.setText(msg);
            upDate.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

            upDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ID = getArguments().getString("id");
                    int updateId = Integer.parseInt(ID);

                    String updatedTitle = edTitle.getText().toString();
                    String updatedMsg = edMsg.getText().toString();
                    edTitle.setText(updatedTitle);
                    edMsg.setText(updatedMsg);

                    helper.updateData(updatedTitle, updatedMsg, updateId);

                    Toast.makeText(getContext(), "Data updated", Toast.LENGTH_LONG).show();
                }
            });


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String ID = getArguments().getString("id");
                    int updateId = Integer.parseInt(ID);


                    helper.Delete(updateId);

                    Toast.makeText(getContext(), "Delete Data", Toast.LENGTH_LONG).show();





                }
            });






        } else {
            addButton.setVisibility(View.VISIBLE);
            Calendar calendar = Calendar.getInstance();
            String date = DateFormat.getDateInstance().format(calendar.getTime());
            String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            final String finalTime = date + " " + currentTime;

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String msg = edMsg.getText().toString();
                    String title = edTitle.getText().toString();
                    if (msg.isEmpty() || title.isEmpty()) {
                        edTitle.setError("Input Your Title");
                        edMsg.setError("Input Your Message");
                    } else {
                        helper.InsertedData(title, msg, finalTime);
                        Toast.makeText(getContext(), "Data inserted", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        return view;
    }
}