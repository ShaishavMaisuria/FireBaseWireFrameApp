package com.example.firebasewireframeapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewForum#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewForum extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewForum() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewForum.
     */
    // TODO: Rename and change types and number of parameters
    public static NewForum newInstance(String param1, String param2) {
        NewForum fragment = new NewForum();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    EditText editTextNewForumTitle;
    EditText editTextNewForumDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_forum, container, false);
        editTextNewForumDesc=view.findViewById(R.id.editTextNewForumDesc);

        editTextNewForumTitle=view.findViewById(R.id.editTextNewForumTitle);

        view.findViewById(R.id.buttonNewForumSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String desc= editTextNewForumDesc.getText().toString();
                String title= editTextNewForumTitle.getText().toString();

                if(desc.isEmpty()){
                    Toast.makeText(getActivity(),"desc cant be empty",Toast.LENGTH_LONG).show();
                } else if(title.isEmpty()){
                    Toast.makeText(getActivity(),"title cant be empty",Toast.LENGTH_LONG).show();
                }else{
                    addNewForum(desc,title);

//                    loginUser("a@a.com","test123");
                }
            }
        });

        view.findViewById(R.id.buttonNewForumCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mlistener.OnSuccesfulForumCreation();
                Toast.makeText(getActivity(),"No new Forum Created",Toast.LENGTH_LONG).show();
            }
        });
        return view;


    }
    private FirebaseAuth mAuth =FirebaseAuth.getInstance();
    private void addNewForum(String desc, String title) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            ArrayList<String> likedList = new ArrayList<>();
            Forum forum = new Forum(mAuth.getCurrentUser().getDisplayName(),title,desc,mAuth.getUid(), Timestamp.now(),likedList);

            db.collection("forums").add(forum).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    Toast.makeText(getActivity(),"New Forum Created",Toast.LENGTH_LONG).show();
                    mlistener.OnSuccesfulForumCreation();
                }
            });
        }
    NewForumListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof LoginFragment.LoginListener){
            mlistener=(NewForumListener)context;
        }else{
            throw new RuntimeException(context.toString()+"Must implement LoginListener");
        }
    }

    interface NewForumListener {
        void OnSuccesfulForumCreation();

    }

}