package com.example.to_do_list;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends Fragment {

SearchView searchView;
RecyclerView recyclerView;
HashMap<String,String>hashMap;
ArrayList<HashMap<String,String>>arrayList=new ArrayList<>();
DatabaseHelper helper;
    String m;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);
        searchView=view.findViewById(R.id.searchView);
        recyclerView=view.findViewById(R.id.recyclerView);
          helper=new DatabaseHelper(getContext());

            loadData(helper.GetallData());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                m = s;
                arrayList.clear();
                loadData(helper.searchAllData(m));
                return false;
            }
        });

        return view;
    }


public class XAdapter extends RecyclerView.Adapter<XAdapter.Holder>{



    public class Holder extends RecyclerView.ViewHolder{

        TextView tvTime;
        ImageView update;
        LinearLayout layout;


        public Holder(@NonNull View itemView) {
            super(itemView);

            tvTime=itemView.findViewById(R.id.tvTime);

            layout=itemView.findViewById(R.id.layout);



        }
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=getLayoutInflater();
        View view=inflater.inflate(R.layout.item,parent,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        hashMap = arrayList.get(position);
        String id=hashMap.get("id");
        String t = hashMap.get("title");
         m = hashMap.get("msg");
        String d = hashMap.get("date");

        holder.tvTime.setText("Date:" + d + "\n" + "Title:" + t + "\n" + "Massage:" + m);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AddData addData = new AddData();
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                bundle.putString("msg", m);
                bundle.putString("title",t);
                addData.setArguments(bundle);



                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.replace(R.id.frameLayout,addData);
                transaction.addToBackStack(null);
                transaction.commit();


            }
        });








    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



}

   public void loadData(Cursor cursor) {

       if (cursor != null && cursor.getCount() > 0) {
           while (cursor.moveToNext()) {
               int i=cursor.getInt(0);
               String ti = cursor.getString(1);
               String ms = cursor.getString(2);
               String da = cursor.getString(3);


               hashMap = new HashMap<>();
               hashMap.put("id",""+i);
               hashMap.put("title", ti);
               hashMap.put("msg", ms);
               hashMap.put("date", da);
               arrayList.add(hashMap);

           }
           recyclerView.setAdapter(new XAdapter());
           recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       }





    }






}