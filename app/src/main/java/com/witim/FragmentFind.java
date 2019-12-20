package com.witim;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import  android.widget.AdapterView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFind extends Fragment {


    public FragmentFind() {
        // Required empty public constructor
    }


    AlertDialog dialog;
    LayoutInflater dialog_inflater;
    View dialogView;
    ProgressDialog pd;
    RequestQueue requestQueue; // nembak data secara online
    ArrayList<Model> listModel = new ArrayList<>();
    RecyclerView rv_find;
    String nama="", email="", password="", category="", role="";
    Integer id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_find, container, false);
        rv_find = view.findViewById(R.id.rv_find);
        requestQueue = Volley.newRequestQueue(getActivity());
        pd = new ProgressDialog(getActivity());
        return dialogView;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMahasiswa();
    }

    public void loadMahasiswa(){
        listModel.clear();
        rv_find.setAdapter(null);
        String url = "https://witim.000webhostapp.com/webservice/getUser.php";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray hasil = null;
                        try {
                            hasil = response.getJSONArray("mhs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (hasil.length() == 0){

                        }else{
                            for (int i = 0; i < hasil.length(); i++) {
                                try {
                                    JSONObject obj = hasil.getJSONObject(i);
                                    id = obj.getInt("id");
                                    email = obj.getString("email");
                                    nama = obj.getString("nama");
                                    password = obj.getString("password");
                                    category = obj.getString("category");
                                    role = obj.getString("role");

                                    Model mhs = new Model(id, nama, email, password, category, role);
                                    listModel.add(mhs);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (getActivity() != null){
                                showMahasiswa(listModel);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jor);
    }


    ArrayList<Model> listPassMhs = new ArrayList<>();

    public void showMahasiswa(final ArrayList<Model> list) {
        rv_find.setLayoutManager(new LinearLayoutManager(getActivity()));
        com.witim.Adapter adapter = new com.witim.Adapter(getContext());
        adapter.setListModels(list);
//        rv_find.setAdapter(adapter);

//        ItemClickSupport.addTo(rv_find).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
//                final String id_mhs = list.get(position).getId();
//                dialog = new AlertDialog.Builder(getActivity()).create();
//                dialog_inflater = getActivity().getLayoutInflater();
//                dialogView = dialog_inflater.inflate(R.layout.dialog_edit_del_mhs, null);
//                dialog .setView(dialogView);
//                dialog.setCancelable(true);
//                Button btn_del = dialogView.findViewById(R.id.btn_del);
//                Button btn_edit = dialogView.findViewById(R.id.btn_edit);
//                btn_del.setOnClickListener(new View.OnClickListener() {
//                                               @Override
//                                               public void onClick(View view) {
//                                                   pd.setCancelable(false);
//                                                   pd.setTitle("In Progress");
//                                                   pd.setMessage("Deleting data, please wait...");
//                                                   pd.show();
//                                                   Runnable r = new Runnable() {
//                                                       @Override
//                                                       public void run() {
//                                                           del_mhs(id_mhs);
//                                                       }
//                                                   };
//                                                   Handler cancel = new Handler();
//                                                   cancel.postDelayed(r, 2000);
////                    pd.cancel();
//                                                   dialog.dismiss();
//                                               }
//                                           }
//                );
//                btn_edit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        listPassMhs.clear();
//                        Intent intent = new Intent(getActivity(), EditMahasiswa.class);
//                        Model m = list.get(position);
//                        listPassMhs.add(m);
//                        intent.putExtra("listPassMhs", listPassMhs);
//                        startActivity(intent);
//                        dialog.dismiss();
//                    }
//                });
//                dialog.show();
//                return false;
//            }
//        });
    }
}

//    public void del_mhs(String id_mhs){
//        String url = "https://natasya-mad.000webhostapp.com/webservice/del_one_mhs.php";
//        Map<String, String> params = new HashMap<>();
//        params.put("id", id_mhs);
//        JSONObject parameters = new JSONObject(params);
//        final JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>(){
//            @Override
//            public void onResponse(JSONObject response) {
//                String message = "";
//                try {
//                    message = response.getString("msg");
//
//                    if (message.equals("Success!")) {
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                        onStart();
//                    } else {
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                pd.cancel();
//            }
//        },
//                new Response.ErrorListener(){
//                    public void onErrorResponse(VolleyError error){
//                        Log.e("Volley", String.valueOf(error));
//                    }
//                }
//        );
//        requestQueue.add(jor);
//    }
//
//
//}
