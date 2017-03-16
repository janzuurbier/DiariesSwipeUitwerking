package nl.hu.zrb.diarieswithswipe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ShowContent extends AppCompatActivity {
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        String title = intent.getStringExtra("title");
        TextView tv1 = (TextView)findViewById(R.id.titleView);
        tv1.setText(title);
        String content = intent.getStringExtra("content");
        TextView tv2 = (TextView) findViewById(R.id.contentView);
        tv2.setText(content);
        String tijdstip = intent.getStringExtra("recorddate");
        TextView tv3 = (TextView) findViewById(R.id.dateView);
        tv3.setText(tijdstip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.deleteitem) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("entries").child(key).removeValue();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
