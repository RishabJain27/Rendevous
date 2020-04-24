package com.example.tjman.rendezvouseventfeedlist;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.coderefer.firebasedatabaseexample.models.Movie;
//import com.coderefer.firebasedatabaseexample.R;

/**
 * Created by vamsi on 08-Jul-16.
 */

    public class AddHobbyFragment extends Fragment implements View.OnClickListener {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabaseReference;
    private TextInputEditText hobbyName;
    private TextInputEditText hobbyLogo;
    private Button bSubmit;




    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.event_fragment,container,false);

        hobbyName = (TextInputEditText) v.findViewById(R.id.hobby_name);
        hobbyLogo = (TextInputEditText) v.findViewById(R.id.hobby_logo);
        bSubmit = (Button) v.findViewById(R.id.b_submit);

        //initializing database reference
        firebaseAuth = firebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        bSubmit.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.b_submit:
                if(!isEmpty(hobbyName) && !isEmpty(hobbyLogo)){
                    myNewMovie("53", hobbyName.getText().toString().trim(),hobbyLogo.getText().toString());
                }else{
                    if(isEmpty(hobbyName)){
                        Toast.makeText(getContext(), "Please enter a movie name!", Toast.LENGTH_SHORT).show();
                    }else if(isEmpty(hobbyLogo)){
                        Toast.makeText(getContext(), "Please specify a url for the logo", Toast.LENGTH_SHORT).show();
                    }
                }
                //to remove current fragment
                getActivity().onBackPressed();
                break;
        }
    }

    private void myNewMovie(final String userId, String movieName, String moviePoster) {
        //Creating a movie object with user defined variables
        final Hobby hobby = new Hobby(movieName,moviePoster);
        //referring to movies node and setting the values from movie object to that location
        //Add if subscribe

        mDatabaseReference.child("Hobby").child(userId).push().setValue(hobby);

    }

    //check if edittext is empty
    private boolean isEmpty(TextInputEditText textInputEditText) {
        if (textInputEditText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }
}
