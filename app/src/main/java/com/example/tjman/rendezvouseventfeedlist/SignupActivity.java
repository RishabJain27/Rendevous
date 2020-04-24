package com.example.tjman.rendezvouseventfeedlist;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    String name, lastName, userEmail, number, password, age, rePassword;
    private static final String TAG = "Something";

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_LastName) EditText _LastNameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_Repassword) EditText _RepasswordText;
    @BindView(R.id.input_Age) EditText _AgeText;
    @BindView(R.id.input_Number) EditText _NumberText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.btnChoose) Button btnChoose;
    @BindView(R.id.link_login) TextView _loginLink;
    @BindView(R.id.imgView) ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        firebaseAuth = firebaseAuth.getInstance();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String SUserEmail = _emailText.getText().toString().trim();
                    String SUserPassword = _passwordText.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(SUserEmail,SUserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                sendUserData();
                                uploadImage();
                                Toast.makeText(SignupActivity.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity( new Intent(SignupActivity.this, LoginActivity.class));
                            }
                            else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(SignupActivity.this,"Already Registered", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            //final ProgressDialog progressDialog = new ProgressDialog(this);
            //progressDialog.setTitle("Uploading...");
            //progressDialog.show();

            final String userID = firebaseAuth.getCurrentUser().getUid();
            StorageReference ref = storageReference.child("images/"+ userID);
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //    progressDialog.dismiss();
                         //   Toast.makeText(SignupActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        //    progressDialog.dismiss();
                              Toast.makeText(SignupActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                   // .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    //    @Override
                    //    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                     //       double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                      //              .getTotalByteCount());
                       //     progressDialog.setMessage("Uploaded "+(int)progress+"%");
                       // }
                    //});*/
        }
    }



    private Boolean validate(){
        Boolean result = false;

        name = _nameText.getText().toString();
        lastName = _LastNameText.getText().toString();
        userEmail = _emailText.getText().toString();
        password = _passwordText.getText().toString();
        age = _AgeText.getText().toString();
        rePassword = _RepasswordText.getText().toString();
        number = _NumberText.getText().toString();

        if(name.isEmpty() || lastName.isEmpty() || userEmail.isEmpty() || password.isEmpty() || age.isEmpty() || number.isEmpty()){
            Toast.makeText(this, "Please enter information in all the fields", Toast.LENGTH_SHORT).show();
        }
        else if ( ! password.equals(rePassword)){
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }
        return result;
    }

    private void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getInstance().getCurrentUser().getUid());
        UserProfile userProfile = new UserProfile(name, lastName, age, userEmail, number);
        myRef.setValue(userProfile);

    }
}
