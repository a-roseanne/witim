package com.witim;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddPortfolio extends DialogFragment {

    EditText title;
    EditText desc;
    ImageButton ok, close, add1, add2;
    ImageView add1img, add2img;
    final private int GALLERY_REQUEST_CODE = 1;
    final static String TAG = "dialogaddport";
    RequestQueue requestQueue;
    String titlee = "", descc = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_portfolio, container, false);
        title = view.findViewById(R.id.editTitle);
        desc = view.findViewById(R.id.editDesc);
        ok = view.findViewById(R.id.ok_button);
        close = view.findViewById(R.id.closeButton);
        add1 = view.findViewById(R.id.add1);
        add2 = view.findViewById(R.id.add2);
        add1img = view.findViewById(R.id.add1img);
        add2img = view.findViewById(R.id.add2img);
        add1img.setVisibility(View.INVISIBLE);
        add2img.setVisibility(View.INVISIBLE);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
                add1.setVisibility(View.INVISIBLE);
                add1img.setVisibility(View.VISIBLE);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
                add2.setVisibility(View.INVISIBLE);
                add2img.setVisibility(View.VISIBLE);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                titlee = title.getText().toString();
                descc = desc.getText().toString();
                addPortfolio();
                getDialog().dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        return view;
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_REQUEST_CODE) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    String profilepic = contentURI.getEncodedPath();
//                    pp.setImageURI(contentURI);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
//                    String path = saveImage(bitmap);
//                    Toast.makeText(getActivity(), "Image name : "+path, Toast.LENGTH_SHORT).show();
                    add1img.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void addPortfolio() {
        String url = "https://witim.000webhostapp.com/webservice/addPortfolio.php";
        Map<String, String> params = new HashMap<>();
        params.put("portfolio", titlee);
        params.put("description", descc);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray hasil = null;
                        try {
                            hasil = response.getJSONArray("profile");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (hasil.length() == 0) {

                        } else {
                            try {
                                JSONObject obj = hasil.getJSONObject(0);
                                String msg = "";
                                msg = obj.getString("msg");
                                if (msg.equalsIgnoreCase("Success")) {

                                    Toast.makeText(getActivity(), "Berhasil", Toast.LENGTH_SHORT).show();

                                } else if (msg.equalsIgnoreCase("Failed")) {

                                    Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
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
}
