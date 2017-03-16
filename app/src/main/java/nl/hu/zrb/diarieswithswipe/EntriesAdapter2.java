package nl.hu.zrb.diarieswithswipe;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by JZuurbier on 3-1-2017.
 */

public class EntriesAdapter2 extends FirebaseRecyclerAdapter<DiaryEntry, EntriesAdapter2.MyViewHolder> {
    public EntriesAdapter2() {
        super(DiaryEntry.class, R.layout.diary_row, EntriesAdapter2.MyViewHolder.class,
                FirebaseDatabase.getInstance().getReference().child("entries"));
    }


    @Override
    protected void populateViewHolder(MyViewHolder viewHolder, DiaryEntry model, int position) {
        viewHolder.title.setText(model.getTitle());
        Date date = new Date(model.getDate());
        String datedata = DateFormat.format("MMM dd, yyyy h:mmaa", date).toString();
        viewHolder.dateText.setText(datedata);
        viewHolder.entry = model;
        viewHolder.key = getRef(position).getKey();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, dateText;
        public DiaryEntry entry;
        public String key;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            dateText = (TextView) view.findViewById(R.id.datetext);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(v.getContext(), ContentPager.class);

            intent.putExtra("pos", getAdapterPosition());
            v.getContext().startActivity(intent);

        }
    }
}
