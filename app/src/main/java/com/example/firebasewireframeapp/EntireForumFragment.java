package com.example.firebasewireframeapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntireForumFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntireForumFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_ENTIRE_FORUM = "ARG_PARAM_ENTIRE_FORUM";
    private static final String TAG = "EntireForumFragment" ;


    // TODO: Rename and change types of parameters
    private String forumID;


    public EntireForumFragment() {
        // Required empty public constructor
    }


    public static EntireForumFragment newInstance(String ForumId) {
        EntireForumFragment fragment = new EntireForumFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_ENTIRE_FORUM, ForumId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forumID = getArguments().getString(ARG_PARAM_ENTIRE_FORUM);

        }
    }
    TextView title,desc,time,authorName,forumName;
    ImageView imgDelete;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    EntireForumAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_entire_forum, container, false);
        title=view.findViewById(R.id.textViewPostTitleEntireForum);
        desc=view.findViewById(R.id.textViewDescriptionEntireForum);

        authorName=view.findViewById(R.id.textViewAuthorEntireForum);

        recyclerView=view.findViewById(R.id.recylerViewEntireForum);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new EntireForumAdapter();
        recyclerView.setAdapter(adapter);



        getForum();



        postNewComment=view.findViewById(R.id.editTextCommentEntireForum);

        view.findViewById(R.id.buttonPostEntireForum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String comment=postNewComment.getText().toString();
                if(comment==""){
                    Toast.makeText(getActivity(),"comment cannot be empty",Toast.LENGTH_LONG).show();
                }else{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    Comments userComment= new Comments(comment,mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getUid(), Timestamp.now());
                    db.collection("forums").document(forumID).collection("comments").add(userComment).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
//                            Log.d(TAG,"comment Task status"+task.getException().getMessage());


                        }
                    });


                }
            }
        });


        return view;
    }
    Forum forum;
    private void getForum(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(forumID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                Log.d(TAG,"Yes Got something "+value.getId()+" >"+value.getData());
                forum =value.toObject(Forum.class);
                forum.setForumId(value.getId());
                title.setText(forum.title);
                desc.setText(forum.desc);
                authorName.setText(forum.createByName);
                Log.d(TAG,forum.toString()+"");

                getComments();

            }
        });

    }

    private void getComments(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("forums").document(forumID).collection("comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error==null){
                    commentList.clear();
                    for(QueryDocumentSnapshot document: value){

                        Log.d(TAG,document.getId()+" >"+document.getData());
                        Comments comment =document.toObject(Comments.class);
                        comment.setCommentId(document.getId());
                        Log.d(TAG,forum.toString()+"");
                        commentList.add(comment);

                    }

                    Log.d(TAG,"Comments"+commentList.toString());
                    adapter.notifyDataSetChanged();

                }else{
                    error.printStackTrace();
                }
            }
        });
    }
    EditText postNewComment;
    ArrayList<Comments> commentList= new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
class EntireForumAdapter extends RecyclerView.Adapter<EntireForumAdapter.EntireForumViewHolder>{
    public EntireForumAdapter() {
    }

    @NonNull
    @Override
    public EntireForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_each_comment, parent, false);
        EntireForumViewHolder userViewHolder = new EntireForumViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EntireForumViewHolder holder, int position) {

        Comments comment =commentList.get(position);
        holder.setupForumRow(comment,forum);

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class EntireForumViewHolder extends RecyclerView.ViewHolder{
        Comments comment;
        Forum mforum;
        TextView commentorName,commentDesc,commentTime;


            public EntireForumViewHolder(@NonNull View itemView) {
                super(itemView);

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                commentorName=itemView.findViewById(R.id.textViewEachCommentPersonName);
                commentDesc=itemView.findViewById(R.id.textViewEachCommentDesc);
                imgDelete=itemView.findViewById(R.id.imageViewDeleteComment);
                commentTime=itemView.findViewById(R.id.textViewCommentTimeStamp);
//                db.collection("forums").document()



            }


        public void setupForumRow(Comments coment, Forum forum){
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                this.comment=coment;
                this.mforum=forum;

                commentorName.setText(comment.personName);
                commentDesc.setText(comment.comments);
                commentTime.setText(""+formatter.format(mforum.getCreateAt().toDate()));
                if(mAuth.getCurrentUser().getUid().equals(comment.uid)){
                    imgDelete.setVisibility(View.VISIBLE);
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            Log.d(TAG,"Delete button pressessed");
                            db.collection("forums").document(mforum.getForumId()).collection("comments").document(comment.getCommentId()).delete();
                            adapter.notifyDataSetChanged();
                        }
                    });

                }else{
                    imgDelete.setVisibility(View.INVISIBLE);

                }



        }
        }
    }
}