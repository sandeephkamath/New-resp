package vturesults.mosambi.com.vturesults;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class SavedSem extends ActionBarActivity {

    ListView lv;
    String semarr[];
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_sem);

        lv=(ListView)findViewById(R.id.listView2);

        Intent inte=getIntent();
        usn=inte.getStringExtra("usn");

        Toast.makeText(getApplicationContext(),
                "New Usn:" + usn, Toast.LENGTH_LONG)
                .show();

        SQLiteDatabase mydatabase = openOrCreateDatabase("studentdb",MODE_PRIVATE,null);
        Cursor resultSet = mydatabase.rawQuery("Select sem from stures where usn='"+usn+"'",null);

        if(resultSet.getCount()>0){
            semarr=new String[resultSet.getCount()];
            resultSet.moveToFirst();
            int i=0;
            do{
                semarr[i]=resultSet.getString(0);
                i++;
            }while (resultSet.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, semarr);
            lv.setAdapter(adapter);
        }else{

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String semvalue = (String) lv.getItemAtPosition(position);
                Intent i=new Intent(SavedSem.this,SemResult.class);
                i.putExtra("sem",semvalue);
                i.putExtra("usn",usn);
                startActivity(i);
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_sem, menu);
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
