package com.example.acropolisadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.acropolisadmin.databinding.ActivityUploadNoticeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UploadNotice extends AppCompatActivity {

    ActivityUploadNoticeBinding binding;
    ProgressDialog dialog;
    FirebaseDatabase mDatabase;
    DatabaseReference mRef;
    FirebaseStorage mStorage;
    private static final int Gallery_Code = 1;
    Uri imageUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadNoticeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("NoticeNode");
        mStorage = FirebaseStorage.getInstance();
        dialog  = new ProgressDialog(UploadNotice.this);
        dialog.setMessage("uploading...");

        binding.noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent , Gallery_Code);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Code && resultCode == RESULT_OK){
            imageUrl = data.getData();
            binding.noticeImage.setImageURI(imageUrl);
        }

        binding.btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fn = binding.noticeTxt.getText().toString().trim();
                if (!(fn.isEmpty() && imageUrl!= null)){

                    dialog.show();

                    StorageReference filepath = mStorage.getReference().child("NoticeFolder").child(imageUrl.getLastPathSegment());
                    filepath.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String t = task.getResult().toString();
                                    DatabaseReference newPost = mRef.push();
                                    String currentDate = getToadaysDate();
                                    newPost.child("date").setValue(currentDate);
                                    newPost.child("notice").setValue(fn);
                                    newPost.child("image").setValue(task.getResult().toString());
                                    Toast.makeText(UploadNotice.this , "Uploaded" , Toast.LENGTH_SHORT).show();

                                    binding.noticeImage.setImageResource(R.drawable.upload);
                                    binding.noticeTxt.setText("");
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        });

    }

    private String getToadaysDate(){
        return new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault()).format(new Date());
    }

}