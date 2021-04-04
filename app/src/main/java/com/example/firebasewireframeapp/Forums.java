package com.example.firebasewireframeapp;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Forums#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forums extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "Forums";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Forums() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Forums.
     */
    // TODO: Rename and change types and number of parameters
    public static Forums newInstance(String param1, String param2) {
        Forums fragment = new Forums();
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
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseAuth mAuth =FirebaseAuth.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_forums, container, false);
       recyclerView=view.findViewById(R.id.recylerViewForums);
       recyclerView.setHasFixedSize(true);
       layoutManager= new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
       adapter=new ForumAdapter();
       recyclerView.setAdapter(adapter);
       getActivity().setTitle(mAuth.getCurrentUser().getDisplayName());

        getForums();

        view.findViewById(R.id.buttonForumsLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.logoutFromForums();
            }
        });

        view.findViewById(R.id.buttonNewForums).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlistener.ForumtoNewForums();
            }
        });

    return view;
    }

    ForumAdapter adapter;
   private void getForums(){
       FirebaseFirestore db = FirebaseFirestore.getInstance();

//       db.collection("forums").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<QuerySnapshot> task) {
//          if(task.isSuccessful()){
//              for(QueryDocumentSnapshot document: task.getResult()){
//                  Log.d(TAG,document.getId()+" >"+document.getData());
//                  Forum forum =document.toObject(Forum.class);
//                    Log.d(TAG,forum.toString()+"");
//              }
//          }else{
//              task.getException().printStackTrace();
//          }
//           }
//       });

        db.collection("forums").orderBy("createAt").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error==null){
                    forumList.clear();
                    for(QueryDocumentSnapshot document: value){

                        Log.d(TAG,document.getId()+" >"+document.getData());
                        Forum forum =document.toObject(Forum.class);
                        forum.setForumId(document.getId());
                        Log.d(TAG,forum.toString()+"");
                        forumList.add(forum);

                    }
                    adapter.notifyDataSetChanged();

                }else{
                    error.printStackTrace();
                }
            }
        });

//            Forum forum = new Forum(mAuth.getCurrentUser().getDisplayName(),"Sports and other","Lets go brother and sisters the quiditch match begin",mAuth.getUid(), Timestamp.now());
//
//            db.collection("forums").add(forum).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                @Override
//                public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                }
//            });
    }

    ArrayList<Forum> forumList= new ArrayList<>();
    class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder>{


        public ForumAdapter() {
        }

        @NonNull
        @Override
        public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_display_each_forum, parent, false);
            ForumViewHolder userViewHolder = new ForumViewHolder(view);

            return userViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ForumViewHolder holder, int position) {
            Forum forum =forumList.get(position);
            holder.setupForumRow(forum);


        }

        @Override
        public int getItemCount() {
            return forumList.size();
        }

        public class ForumViewHolder extends RecyclerView.ViewHolder{
            Forum mforum;
            TextView title,desc,time,authorName;
            ImageView imgLike,imgDelete;

            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
            public ForumViewHolder(@NonNull View itemView) {
                super(itemView);
                title=itemView.findViewById(R.id.textViewPostTitleEntireForum);
                desc=itemView.findViewById(R.id.textViewDescriptionEntireForum);
                time=itemView.findViewById(R.id.textViewNumberOfCommentsEntireForum);
                authorName=itemView.findViewById(R.id.textViewAuthorEntireForum);
                imgLike=itemView.findViewById(R.id.imageViewunLike);
                imgDelete=itemView.findViewById(R.id.imageViewDelete);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mlistener.ForumToEntireForum();
                        }
                    });

            }


            public void setupForumRow(Forum forum){
                this.mforum=forum;

                if(mforum.getCreateByUid().equals(mAuth.getCurrentUser().getUid())){
                    imgDelete.setVisibility(View.VISIBLE);
                    imgDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            db.collection("forums").document(mforum.getForumId()).delete();
                        }
                    });
                }else{
                    imgDelete.setVisibility(View.INVISIBLE);

                }

                if(mforum.likedBy.contains(mAuth.getCurrentUser().getUid())){

                    imgLike.setImageResource(R.drawable.like_favorite);
                }else{
                    imgLike.setImageResource(R.drawable.like_not_favorite);
                }


                imgLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        if(mforum.likedBy.contains(mAuth.getCurrentUser().getUid())){

                            forum.likedBy.remove(mAuth.getCurrentUser().getUid());
                            imgLike.setImageResource(R.drawable.like_not_favorite);
                            Log.d(TAG,forum.toString());
                            db.collection("forums").document(forum.getForumId()).update("likedBy", FieldValue.arrayRemove(mAuth.getCurrentUser().getUid())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    Log.d(TAG,"TASK removal"+task.getException().getMessage());
                                }
                            });
                        }else{
                            forum.likedBy.add(mAuth.getCurrentUser().getUid());
                            imgLike.setImageResource(R.drawable.like_favorite);
                            Log.d(TAG,forum.toString());
                            db.collection("forums").document(forum.getForumId()).update("likedBy", FieldValue.arrayUnion(mAuth.getCurrentUser().getUid()));

                        }

//                        db.collection("forums").document().update("likedBy",forum.likedBy).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Log.d(TAG,""+forum.toString());
//                                Log.d(TAG,"TASK"+task.getException().getMessage());
//                            }
//                        });




                    }
                });






                String first200chars;
                first200chars = mforum.getDesc().substring(0,Math.min(mforum.getDesc().length(),200));
                title.setText(mforum.title);
                desc.setText(first200chars);
                authorName.setText(mforum.createByName);
                time.setText(""+formatter.format(mforum.getCreateAt().toDate()));

            }
        }
    }





    ForumsListener mlistener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof ForumsListener){
            mlistener=(ForumsListener)context;
        }else{
            throw new RuntimeException(context.toString()+"Must implement ForumsListener");
        }
    }

    interface ForumsListener {
        void logoutFromForums();
        void ForumtoNewForums();
        void ForumToEntireForum();
    }


}