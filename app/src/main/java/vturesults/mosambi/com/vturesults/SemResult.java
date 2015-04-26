package vturesults.mosambi.com.vturesults;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SemResult extends ActionBarActivity {

    String usn,sem;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sem_result);

        Intent i1=getIntent();
        usn=i1.getStringExtra("usn");
        sem=i1.getStringExtra("sem");
        tv=(TextView)findViewById(R.id.init);


        SQLiteDatabase mydatabase = openOrCreateDatabase("studentdb",MODE_PRIVATE,null);
        Cursor resultSet = mydatabase.rawQuery("Select * from stures where usn='"+usn+"' and sem='"+sem+"'",null);
        resultSet.moveToFirst();
        int n=resultSet.getColumnCount();
        String f="";
        for(int i=0;i<n;i++){
            f=f+resultSet.getString(i)+"\n";
        }
        tv.append(f);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sem_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
