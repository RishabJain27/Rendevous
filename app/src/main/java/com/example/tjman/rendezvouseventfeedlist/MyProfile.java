package com.example.tjman.rendezvouseventfeedlist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

public class MyProfile extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView name, email, number, age;
    private ImageView iv;
    private RecyclerView eventList;
    private DatabaseReference myDatabase;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        eventList = (RecyclerView)findViewById(R.id.myrecyclerview);
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        name = (TextView)findViewById(R.id.editName);
        email = (TextView)findViewById(R.id.editEmail);
        number = (TextView)findViewById(R.id.editNumber);
        age = (TextView)findViewById(R.id.editAge);
        iv =  (ImageView)findViewById(R.id.profilePic);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(firebaseAuth.getInstance().getCurrentUser().getUid());
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        final String userID = firebaseAuth.getCurrentUser().getUid();

        url = "https://firebasestorage.googleapis.com/v0/b/rendezvous-event-feed.appspot.com/o/images%2F"
                + userID + "?alt=media&token=dda325e6-2d7d-4973-8bed-3979b35f7dbe";

        storageReference.child("images").child(userID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(url).into(iv);
            }
        });

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserProfile value = dataSnapshot.getValue(UserProfile.class);
                String fullName = value.getFirstName() + " " + value.getLastName();

                name.setText(fullName);
                email.setText(value.getEmail());
                number.setText(value.getNumber());
                age.setText(value.getAge());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        dl = (DrawerLayout)findViewById(R.id.activity_profile);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)

                {
                    case R.id.myprofile:
                        Toast.makeText(MyProfile.this, "My Profile",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(MyProfile.this, MyProfile.class);
                        startActivity(intent2);
                        break;
                    case R.id.mypals:
                        Toast.makeText(MyProfile.this, "My Pals",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(MyProfile.this, Pals.class);
                        startActivity(intent5);
                        break;
                    case R.id.eventsfeed:
                        Toast.makeText(MyProfile.this, "Events Feed",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MyProfile.this, EventsPage.class);
                            startActivity(intent);
                        break;
                    case R.id.myhobbies:
                        Toast.makeText(MyProfile.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(MyProfile.this, myHobbies.class);
                        startActivity(intent3);
                        break;
                    case R.id.ucscHobbies:
                        Toast.makeText(MyProfile.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(MyProfile.this, HobbyPage.class);
                        startActivity(intent4);
                        break;
                    case R.id.signOut:
                        Toast.makeText(MyProfile.this, "Signed Out",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(MyProfile.this, LoginActivity.class);
                        startActivity(intent6);
                        break;
                    default:
                        return true;
                }

                return false;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
