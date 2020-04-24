package com.example.tjman.rendezvouseventfeedlist;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


//import com.coderefer.firebasedatabaseexample.adapters.MovieBoardAdapter;
//import com.coderefer.firebasedatabaseexample.fragments.AddMovieFragment;

public class myHobbies extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private RecyclerView eventList;
    private DatabaseReference myDatabase;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    public static String id;

    //created for firebaseui android tutorial by Vamsi Tallapudi

    private FloatingActionButton fab;

    ScaleAnimation shrinkAnim;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mLayoutManager;
    private TextView tvNoMovies;

    //Getting reference to Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    private static final String userId = "53";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hobby_page);
        myDatabase= FirebaseDatabase.getInstance().getReference().child("Global");
        myDatabase.keepSynced(true);
        firebaseAuth = firebaseAuth.getInstance();

        eventList = (RecyclerView)findViewById(R.id.myrecyclerview);
        eventList.setHasFixedSize(true);
        eventList.setLayoutManager(new LinearLayoutManager(this));

        dl = (DrawerLayout)findViewById(R.id.hobby_page);
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
                        Toast.makeText(myHobbies.this, "My Profile",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(myHobbies.this, MyProfile.class);
                        startActivity(intent);
                        break;
                    case R.id.mypals:
                        Toast.makeText(myHobbies.this, "My Pals",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(myHobbies.this, Pals.class);
                        startActivity(intent5);
                        break;
                    case R.id.eventsfeed:
                        Toast.makeText(myHobbies.this, "Events Feed",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(myHobbies.this, EventsPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.myhobbies:
                        Toast.makeText(myHobbies.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(myHobbies.this, myHobbies.class);
                        startActivity(intent3);
                        break;
                    case R.id.ucscHobbies:
                        Toast.makeText(myHobbies.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(myHobbies.this, HobbyPage.class);
                        startActivity(intent4);
                        break;
                    case R.id.signOut:
                        Toast.makeText(myHobbies.this, "Signed Out",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(myHobbies.this, LoginActivity.class);
                        startActivity(intent6);
                        break;
                    default:
                        return true;
                }



                return false;

            }
        });







        //Initializing our Recyclerview
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        tvNoMovies = (TextView) findViewById(R.id.tv_no_movies);

        //scale animation to shrink floating actionbar
        shrinkAnim = new ScaleAnimation(1.15f, 0f, 1.15f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        if (mRecyclerView != null) {
            //to enable optimization of recyclerview
            mRecyclerView.setHasFixedSize(true);
        }
        //using staggered grid pattern in recyclerview
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDatabaseReference.keepSynced(true);

        //Say Hello to our new FirebaseUI android Element, i.e., FirebaseRecyclerAdapter
        FirebaseRecyclerAdapter<Hobby,HobbyViewHolder> adapter = new FirebaseRecyclerAdapter<Hobby, HobbyViewHolder>(
                Hobby.class,
                R.layout.myhobby_board_item,
                HobbyViewHolder.class,
                //referencing the node where we want the database to store the data from our Object
                mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).getRef()
                //mDatabaseReference.child("Hobby").child(userId).getRef()

        ) {
            @Override
            protected void populateViewHolder(HobbyViewHolder viewHolder, Hobby model, int position) {
                if(tvNoMovies.getVisibility()== View.VISIBLE){
                    tvNoMovies.setVisibility(View.GONE);
                }
                //viewHolder.subscribe.setVisibility(View.GONE);

                viewHolder.tvMovieName.setText(model.getHobby());
                //viewHolder.ratingBar.setRating(model.getMovieRating());
                Picasso.with(myHobbies.this).load(model.getHobbyimg()).into(viewHolder.ivMoviePoster);



                mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).getRef();
               // mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).child(id).setValue(model);

                final String name = viewHolder.tvMovieName.getText().toString();

                viewHolder.unsub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Query reference=mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).getRef();
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    Map map = (Map)childSnapshot.getValue();
                                    System.out.println("KEY IS " + key);
                                    String hobby = (String)map.get("hobby");
                                    if(hobby == name){

                                        //mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("User_Events").child(key).removeValue();
                                        mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).child(key).removeValue();

                                        //Intent intent = new Intent(myHobbies.this,HobbyEventsPage.class);
                                        //intent.putExtra("key", key );
                                        //startActivity(intent);
                                        //System.out.println("I FOUND ITTTT");
                                        //System.out.println("name" + name);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });

                viewHolder.ivMoviePoster.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Query reference=mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).getRef();
                        reference.addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    Map map = (Map)childSnapshot.getValue();

                                    System.out.println("KEY IS " + key);
                                    String hobby = (String)map.get("hobby");
                                    if(hobby == name){
                                        Intent intent = new Intent(myHobbies.this,HobbyEventsPage.class);
                                        intent.putExtra("key", key );
                                        startActivity(intent);
                                        System.out.println("I FOUND ITTTT");
                                        System.out.println("name" + name);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                       // id = mDatabaseReference.child(firebaseAuth.getInstance().getCurrentUser().getUid()).child("Hobby").child(userId).push().getKey();
                        //Toast.makeText(myHobbies.this, id ,Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(myHobbies.this,HobbyEventsPage.class);
                        //startActivity(intent);
                    }
                });

            }
        };

        mRecyclerView.setAdapter(adapter);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container, new AddHobbyFragment())
                        .addToBackStack(null)
                        .commit();
                //animation being used to make floating actionbar disappear
                shrinkAnim.setDuration(400);
                fab.setAnimation(shrinkAnim);
                shrinkAnim.start();
                shrinkAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        //changing floating actionbar visibility to gone on animation end
                        fab.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {


                    }
                });

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fab.getVisibility() == View.GONE)
            fab.setVisibility(View.VISIBLE);
    }

    //ViewHolder for our Firebase UI
    public static class HobbyViewHolder extends RecyclerView.ViewHolder{

        TextView tvMovieName;
        RatingBar ratingBar;
        ImageView ivMoviePoster;
        Button unsub;


        public HobbyViewHolder(View v) {
            super(v);
            tvMovieName = (TextView) v.findViewById(R.id.tv_name);
            //ratingBar = (RatingBar) v.findViewById(R.id.rating_bar);
            ivMoviePoster = (ImageView) v.findViewById(R.id.iv_movie_poster);
            unsub = (Button) v.findViewById(R.id.unsubscribe);


        }
    }
}
