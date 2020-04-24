package com.example.tjman.rendezvouseventfeedlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class Pals extends AppCompatActivity {

    private RecyclerView eventList;
    private DatabaseReference myDatabase;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pals);
        dl = (DrawerLayout)findViewById(R.id.content_pals);
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
                        Toast.makeText(Pals.this, "My Profile",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pals.this, MyProfile.class);
                        startActivity(intent);
                        break;
                    case R.id.mypals:
                        Toast.makeText(Pals.this, "My Pals",Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(Pals.this, Pals.class);
                        startActivity(intent5);
                        break;
                    case R.id.eventsfeed:
                        Toast.makeText(Pals.this, "Events Feed",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(Pals.this, EventsPage.class);
                        startActivity(intent2);
                        break;
                    case R.id.ucscHobbies:
                        Toast.makeText(Pals.this, "UCSC Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(Pals.this, HobbyPage.class);
                        startActivity(intent4);
                        break;
                    case R.id.myhobbies:
                        Toast.makeText(Pals.this, "My Hobbies",Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(Pals.this, myHobbies.class);
                        startActivity(intent3);
                        break;
                    case R.id.signOut:
                        Toast.makeText(Pals.this, "Signed Out",Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(Pals.this, LoginActivity.class);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_pals, menu);
        return true;
    }
}
