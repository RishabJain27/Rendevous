package com.example.tjman.rendezvouseventfeedlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEvent extends AppCompatActivity {

    private TextInputEditText title, desc, url;
    private Button submit;
    private FirebaseAuth firebaseAuth;
    String userTitle, userDesc, userUrl;
    //private DatabaseReference myDatabase;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        title = (TextInputEditText)findViewById(R.id.activity_name);
        desc = (TextInputEditText)findViewById(R.id.activity_desc);
        url = (TextInputEditText)findViewById(R.id.event_logo);
        submit = (Button)findViewById(R.id.b_submit);


        firebaseAuth = firebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTitle = title.getText().toString();
                userDesc = desc.getText().toString();
                userUrl = url.getText().toString();

                Intent i = getIntent();
                String key = i.getStringExtra("key");

                // Intent i = getIntent();
                //String key = i.getStringExtra("key");

                Event e = new Event(userTitle, userDesc, userUrl);
                mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child("53").child(key).child("Events").push().setValue(e);
                //mDatabaseReference.child("Global").push().setValue(e);
                mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("User_Events").push().setValue(e);
                Intent intent = new Intent(AddEvent.this, HobbyEventsPage.class);
                intent.putExtra("key", key);
                startActivity(intent);


            }
        });


        //Event e = new Event(userTitle, userDesc, userUrl);
       //mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child("53").child(key).child("Events").push().setValue(e);


       // myDatabase = FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child("53").child(key).child("Events").getRef();
        //myDatabase.push().setValue(e);

        //myDatabase
        //myDatabase.keepSynced(true);


    }
   /* @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
        String key = i.getStringExtra("key");

        Event e = new Event(userTitle, userDesc, userUrl);
        mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child("53").child(key).child("Events").push().setValue(e);

    }*/
}
