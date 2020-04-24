package com.example.tjman.rendezvouseventfeedlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class EventsPage extends AppCompatActivity {

    private TextView display;
    private RecyclerView eventList;
    private DatabaseReference myDatabase;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventspage);
        setTitle("");
        firebaseAuth = firebaseAuth.getInstance();
        myDatabase= FirebaseDatabase.getInstance().getReference().child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("User_Events");
        //myDatabase= FirebaseDatabase.getInstance().getReference().child("Global");
        myDatabase.keepSynced(true);

        display = (TextView)findViewById(R.id.textView3);
        eventList = (RecyclerView)findViewById(R.id.myrecyclerview);
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        dl = (DrawerLayout)findViewById(R.id.activity_eventspage);
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
                        Toast.makeText(EventsPage.this, "My Profile",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EventsPage.this, MyProfile.class);
                            startActivity(intent);
                        break;
                    case R.id.mypals:
                        Toast.makeText(EventsPage.this, "My Pals",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(EventsPage.this, Pals.class);
                        startActivity(intent5);
                        break;
                    case R.id.eventsfeed:
                        Toast.makeText(EventsPage.this, "Events Feed",Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(EventsPage.this, EventsPage.class);
                            startActivity(intent2);
                        break;
                    case R.id.ucscHobbies:
                        Toast.makeText(EventsPage.this, "UCSC Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(EventsPage.this, HobbyPage.class);
                        startActivity(intent4);
                        break;
                    case R.id.myhobbies:
                        Toast.makeText(EventsPage.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(EventsPage.this, myHobbies.class);
                        startActivity(intent3);
                        break;
                    case R.id.signOut:
                        Toast.makeText(EventsPage.this, "Signed Out",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(EventsPage.this, LoginActivity.class);
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
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Event,EventViewHolder>firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Event, EventViewHolder>
                (Event.class,R.layout.event_info,EventViewHolder.class,myDatabase) {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                if(position != -1){
                    display.setVisibility(View.INVISIBLE);
                    System.out.println("testposition is" + position);
                }
            }
        };

        eventList.setAdapter(firebaseRecyclerAdapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
    public static class EventViewHolder extends RecyclerView.ViewHolder{
        View myView;

        public EventViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView)myView.findViewById(R.id.post_title);
            post_title.setText(title);
        }

        public void setDesc(String desc){
            TextView post_desc=(TextView)myView.findViewById(R.id.post_desc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image){
            ImageView post_Image = (ImageView)myView.findViewById(R.id.post_image);
            Picasso.with(ctx).load(image).into(post_Image);
        }

    }
}
