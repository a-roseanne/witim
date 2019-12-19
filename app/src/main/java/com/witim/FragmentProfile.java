package com.witim;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ArrayAdapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfile extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageButton edit_btn, check_btn, cameraButton;
    EditText editName;
    Spinner editRole;
    TextView name, role;
    String[] spinnerItems = {"Hipster", "Hacker", "Hustler"};
    FloatingActionButton fab_add;
    RequestQueue requestQueue;
    String namee = "", rolee = "";
    ImageView pp;

    AlertDialog dialog;
    View dialogView;
    LayoutInflater dialog_inflater;

    final int GALLERY=1,CAMERA=1, IMAGE_DIRECTORY=1, RESULT_CANCELED = 1;
    final static String TAG = "fragmentprofile";

    ProgressBar loading;


    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        pp = view.findViewById(R.id.pp);
        //button
        edit_btn = view.findViewById(R.id.btn_edit);
        check_btn = view.findViewById(R.id.btn_check);
        cameraButton = view.findViewById(R.id.cameraButton);
        //edit text
        editName = view.findViewById(R.id.editName);
        editRole = view.findViewById(R.id.editRole);
        //fab
        fab_add = view.findViewById(R.id.fab_add);
        loading = view.findViewById(R.id.loading);
        //text
        name = view.findViewById(R.id.name);
        role = view.findViewById(R.id.role);

        editName.setVisibility(View.INVISIBLE);
        editRole.setVisibility(View.INVISIBLE);
        check_btn.setVisibility(View.INVISIBLE);
        cameraButton.setVisibility(View.INVISIBLE);
        requestQueue = Volley.newRequestQueue(getActivity());
        loadProfile();

        //EDIT BUTTON HEREEEEE
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setText(namee);


                name.setVisibility(View.INVISIBLE);
                role.setVisibility(View.INVISIBLE);
                edit_btn.setVisibility(View.INVISIBLE);
                cameraButton.setVisibility(View.VISIBLE);

                editName.setVisibility(View.VISIBLE);
                editRole.setVisibility(View.VISIBLE);
                check_btn.setVisibility(View.VISIBLE);

                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.roles, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editRole.setAdapter(adapter);

//                editRole.setOnItemClickListener(this);
                if (rolee.equalsIgnoreCase("hipster")){
                    editRole.setSelection(0);
                }else if (rolee.equalsIgnoreCase("hacker")){
                    editRole.setSelection(1);
                }else if (rolee.equalsIgnoreCase("hustler")){
                    editRole.setSelection(2);
                }

            }
        });

        //CHECK BUTTON HEREEEE
        check_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editName.setVisibility(View.INVISIBLE);
                editRole.setVisibility(View.INVISIBLE);
                check_btn.setVisibility(View.INVISIBLE);
                cameraButton.setVisibility(View.INVISIBLE);

                namee = editName.getText().toString().trim();
                rolee = role.getText().toString().trim();
                updateProfile();
                name.setText(namee);
                if(rolee.equalsIgnoreCase("hi")){
                    rolee = "Hipster";
                }else if(rolee.equalsIgnoreCase("ha")){
                    rolee = "Hacker";
                }else if(rolee.equalsIgnoreCase("hu")){
                    rolee = "Hustler";
                }
                role.setText(rolee);
                name.setVisibility(View.VISIBLE);
                role.setVisibility(View.VISIBLE);
                edit_btn.setVisibility(View.VISIBLE);

            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPortfolio dialog = new AddPortfolio();
                dialog.show(getFragmentManager(), "dialogaddport");




//                addPortfolioDialog = new AlertDialog.Builder(getActivity()).create();
//                addDialog_inflate = getActivity().getLayoutInflater();
//                addDialogView = addDialog_inflate.inflate(R.layout.add_portfolio, null);
//                addPortfolioDialog.setView(addDialogView);
//                addPortfolioDialog.setCancelable(true);
//
//                EditText title = dialogView.findViewById(R.id.editTitle);
//                EditText desc = dialogView.findViewById(R.id.editDesc);
//                ImageButton ok = dialogView.findViewById(R.id.ok_button);
//
//                ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        String titlee = title.getText().toString();
//                        String descc = desc.getText().toString();
//                        addPortfolio(titlee,descc);
//                        dialog.dismiss();
//                    }
//                });

            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new AlertDialog.Builder(getActivity()).create();
                dialog_inflater = getActivity().getLayoutInflater();
                dialogView = dialog_inflater.inflate(R.layout.dialog_select_camera_or_gallery, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);

                Button takephotoButton = dialogView.findViewById(R.id.takephotoButton);
                Button choosefromgalleryButton  = dialogView.findViewById(R.id.choosefromgalleryBtn);
                Button removePicButton = dialogView.findViewById(R.id.removePicButton);
                takephotoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePhotoFromCamera();
                        dialog.dismiss();
                    }
                });
                choosefromgalleryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosePhotoFromGallery();
                        dialog.dismiss();
                    }
                });
                removePicButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removePic();
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });




        return view;
    }

    public void choosePhotoFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void removePic() {
        pp.setImageResource(R.drawable.profilepicture);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();

                try {
                    String profilepic = contentURI.getEncodedPath();
//                    pp.setImageURI(contentURI);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
//                    Toast.makeText(getActivity(), "Image name : "+path, Toast.LENGTH_SHORT).show();
                    pp.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            pp.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "" +IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    public void loadProfile() {
        String url = "https://witim.000webhostapp.com/webservice/getUser.php";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //get json data from server
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

                                namee = obj.getString("name");
                                rolee = obj.getString("role");
                                loading.setVisibility(View.INVISIBLE);
                                name.setText(namee);
                                if (rolee.equalsIgnoreCase("Hipster")){
                                    role.setText("Hipster");
                                }else if (rolee.equalsIgnoreCase("Hacker")){
                                    role.setText("Hacker");
                                }else if (rolee.equalsIgnoreCase("Hustler")){
                                    role.setText("Hustler");
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

    public void updateProfile() {
        String url = "https://witim.000webhostapp.com/webservice/updateProfile.php";
        Map<String, String> params = new HashMap<>();
        params.put("name", namee);
        if (editRole.getSelectedItem().toString().equalsIgnoreCase("Hipster")){
            rolee = "hi";
        }else if (editRole.getSelectedItem().toString().equalsIgnoreCase("Hacker")){
            rolee = "ha";
        }else if (editRole.getSelectedItem().toString().equalsIgnoreCase("Hustler")){
            rolee = "hu";
        }
        params.put("role_id", rolee);


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

                                if (msg.equalsIgnoreCase("success")) {
                                    Toast.makeText(getActivity(), "role : "+rolee, Toast.LENGTH_SHORT).show();

                                } else if (msg.equalsIgnoreCase("failed")) {

                                    Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                loading.setVisibility(View.VISIBLE);
                                loadProfile();

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



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
