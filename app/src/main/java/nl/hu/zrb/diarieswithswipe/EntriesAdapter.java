package nl.hu.zrb.diarieswithswipe;

/**
 * Created by JZuurbier on 28-12-2016.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntriesAdapter extends RecyclerView.Adapter<EntriesAdapter.MyViewHolder> {

    public List<DiaryEntry> diariesList ;
    private DatabaseReference mFirebaseDatabaseReference;
    private String TAG = "EntriesAdapter";


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, dateText;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            dateText = (TextView) view.findViewById(R.id.datetext);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Intent intent = new Intent(v.getContext(), ContentPager.class);
            intent.putExtra("pos", pos);
            v.getContext().startActivity(intent);
        }
    }


    public EntriesAdapter() {
        diariesList = new ArrayList();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabaseReference.child("entries").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DiaryEntry e  = dataSnapshot.getValue(DiaryEntry.class);
                e.key = dataSnapshot.getKey();
                diariesList.add(e);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("EntriesAdapter", "remove");
                diariesList.remove( dataSnapshot.getValue(DiaryEntry.class));
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.toString());

            }
        });


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DiaryEntry entry = diariesList.get(position);
        holder.title.setText(entry.getTitle());
        String datedata = DateFormat.format("MMM dd, yyyy h:mmaa",
                entry.getDate()).toString();
        holder.dateText.setText(datedata);
    }

    @Override
    public int getItemCount() {
        return diariesList.size();
    }
}

