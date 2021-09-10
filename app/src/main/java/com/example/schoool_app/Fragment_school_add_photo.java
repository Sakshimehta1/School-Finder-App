package com.example.schoool_app;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.println;


public class Fragment_school_add_photo extends Fragment {

    Button uploadPhoto;
    RecyclerView recyclerView;
    RecViewSchoolPhotos adapter;
    private StorageReference mStorageRef;
    String uid;
    ProgressBar progressBar;
    String filename;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference;
    int counter;
    StorageReference photosdocref;
    List<String> downloadUriList;
    List<String> availablePhotos;
    ImageView imageView;
    data_schools data;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_school_add_photo, container, false);
        uploadPhoto=view.findViewById(R.id.uploadPhoto);
        recyclerView=view.findViewById(R.id.recViewSchoolPhotos);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        uid=currentUser.getUid();
        documentReference=db.collection("schools").document(uid);

        photosdocref=mStorageRef.child("photos/").child(uid);
        availablePhotos=new ArrayList<>();
//        availablePhotos.add("https://www.extremetech.com/wp-content/uploads/2019/12/SONATA-hero-option1-764A5360-edit.jpg");

        return view;
    }


    private void setuprecycler() {
        availablePhotos.addAll((data.getPhotos()));
        adapter.notifyDataSetChanged();
        Picasso.get().load(availablePhotos.get(0)).fit().centerCrop().into(imageView);
        Toast.makeText(getActivity(), Integer.toString(availablePhotos.size()), Toast.LENGTH_SHORT).show();
    }
    private void getDocData() {
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    data= documentSnapshot.toObject(data_schools.class);
                    if(documentSnapshot.contains("photos"))
                    {
                        setuprecycler();
                    }
                    else
                    {
//                        imageSlider.setVisibility(View.GONE);
//                        noimg.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        adapter=new RecViewSchoolPhotos(availablePhotos,getActivity());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        getDocData();

    }

    private void requestPermission() {
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                launchgalIntent();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

                Toast.makeText(getActivity(), "Please grant permission to upload photos", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void launchgalIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,"Select images"),1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode== Activity.RESULT_OK){

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            final ClipData clipdata = data.getClipData();
            List<Uri> imagesuriList = new ArrayList<>();
            if(clipdata!=null)
            {
                for(int i=0;i<clipdata.getItemCount();i++)
                {
                    imagesuriList.add(clipdata.getItemAt(i).getUri());
                }
            }
            else
            {
                imagesuriList.add(data.getData());
            }
            final int imagelistsize = imagesuriList.size();
            int count =0;
            List<Task<Uri>> uploadedImageUrlTasks = new ArrayList<>(imagelistsize);
            for(Uri imageUri: imagesuriList)
            {
                 count++;
                final String imageFilename = getfilename(imageUri);
                 downloadUriList = new ArrayList<>();
                final StorageReference currentimageRef =  photosdocref.child(imageFilename);
                UploadTask currentUploadTask = currentimageRef.putFile(imageUri);
                final int finalCount = count;
                Task<Uri> currentUrlTask= currentUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
//                            Toast.makeText(getActivity(), "Upload to storage failed for "+ finalCount, Toast.LENGTH_SHORT).show();
                            throw task.getException();
                        }
//                        Toast.makeText(getActivity(), "Uploaded storage Fetching download url", Toast.LENGTH_SHORT).show();
                        return currentimageRef.getDownloadUrl();
                    }
                }).continueWithTask(new Continuation<Uri, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<Uri> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Get download url failed for "+ finalCount, Toast.LENGTH_SHORT).show();
                            throw task.getException();
                        }
                        progressDialog.setMessage("Uploaded "+ finalCount+"/"+imagelistsize);
//                        Toast.makeText(getActivity(), "Download url for "+ finalCount+"is "+task.getResult(), Toast.LENGTH_SHORT).show();
                        downloadUriList.add(task.getResult().toString());
                        return null;
                    }
                });
                uploadedImageUrlTasks.add(currentUrlTask);
            }
            Tasks.whenAllComplete(uploadedImageUrlTasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
                @Override
                public void onComplete(@NonNull Task<List<Task<?>>> task) {
                    saveDatatoFirebase(progressDialog,downloadUriList);
                }
            });
//            if(clipdata!=null)
//            {
//                final List<String> finalImageDownuri=new ArrayList<>();
//
//                Uri imageuri;
//                for (int i = 0; i <clipdata.getItemCount() ; i++) {
//                    imageuri=clipdata.getItemAt(i).getUri();
//                     filename=getfilename(imageuri);
//
//                    final int finalI = i;
//                    photosdocref.child(filename).putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                            if(task.isSuccessful())
//                            {
//                                photosdocref.child(filename).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Uri> task) {
//
//                                        if(task.isSuccessful())
//                                        {
//                                            counter++;
//                                            finalImageDownuri.add(task.getResult().toString());
//                                            progressDialog.setMessage("Uploaded to storage  "+ finalImageDownuri.size()+"/"+clipdata.getItemCount());
////                                         imageDownuri.clear();
//                                        }
//                                        else
//                                        {
//                                            counter++;
//                                            progressDialog.dismiss();
//                                            Toast.makeText(getActivity(), "Couldn't Upload image"+(finalI +1)+task.getException().getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
//                                            photosdocref.child(filename).delete();
//                                        }
//                                        if(counter==clipdata.getItemCount())
//                                            saveDatatoFirebase(progressDialog,finalImageDownuri);
//                                    }
//                                });
//                            }
//
//                        }
//                    });
//                }
//
//            }
//            else {
////                one data only
//                Uri imageuri;
//                final List<String> imageDownuri = new ArrayList<>();
//                imageuri=data.getData();
//                final String filename=getfilename(imageuri);
//                photosdocref.child(filename).putFile(imageuri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//
//                        if(task.isSuccessful())
//                        {
//                            photosdocref.child(filename).getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Uri> task) {
//                                    if(task.isSuccessful())
//                                    {
//                                        imageDownuri.add(task.getResult().toString());
//                                        progressDialog.setMessage("Uploaded to storage  "+ imageDownuri.size()+"/1");
//                                         saveDatatoFirebase(progressDialog,imageDownuri);
////
//                                    }
//                                    else
//                                    {
//                                        imageDownuri.clear();
//                                        progressDialog.dismiss();
//                                        Toast.makeText(getActivity(), "Couldn't Upload. Try after some time."+task.getException().getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();;
//                                    }
//                                }
//                            });
//                        }
//                    }
//                });
//
//            }
            }
    }

    private void saveDatatoFirebase(final ProgressDialog progressDialog, final List<String> imageDownuri) {
        progressDialog.setMessage("Uploading....");
        data_add_photo data= new data_add_photo(imageDownuri);
        ObjectMapper oMapper = new ObjectMapper();
        Map<String, Object> map = oMapper.convertValue(data, Map.class);
        DocumentReference documentRef = db.collection("schools").document(uid);

        for (int i = 0; i <imageDownuri.size() ; i++) {

            final int finalI = i+1;
            documentRef.update(
                    "photos", FieldValue.arrayUnion(imageDownuri.get(i))
            ).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        if(finalI ==imageDownuri.size())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        progressDialog.dismiss();
                        photosdocref.child(filename).delete();
                        Toast.makeText(getActivity(), "Upload Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public String getfilename(Uri filepath)
    {
        String result = null;
        if (filepath.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(filepath, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = filepath.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}