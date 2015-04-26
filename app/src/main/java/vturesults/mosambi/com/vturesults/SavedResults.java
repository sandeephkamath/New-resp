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
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


public class SavedResults extends ActionBarActivity {

    TextView tv;
    ListView lv;
    String usnarr[],namearr[];
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_results);

        tv=(TextView)findViewById(R.id.tvres);
        lv=(ListView)findViewById(R.id.listView);


        SQLiteDatabase mydatabase = openOrCreateDatabase("studentdb",MODE_PRIVATE,null);
        Cursor resultSet = mydatabase.rawQuery("Select * from stures",null);
        if(resultSet.getCount()>0) {
            resultSet.moveToFirst();
            usnarr=new String[resultSet.getCount()];
            namearr=new String[resultSet.getCount()];
            int i=0;
            do{
                usnarr[i]=resultSet.getString(1);
                namearr[i]=resultSet.getString(0);
               // tv.append(usnarr[0]+" "+namearr[i]+"\n");
                namearr[i]=namearr[i]+"\n("+usnarr[i]+")";
                i++;
            }while (resultSet.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, namearr);
            lv.setAdapter(adapter);

        }else{
            tv.setText("No Results found");
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                 String value = (String) lv.getItemAtPosition(position);
                String usnvalue = value.substring(value.indexOf("(") + 1, value.indexOf(")"));

                Intent i=new Intent(SavedResults.this,SavedSem.class);
                i.putExtra("usn",usnvalue);
                startActivity(i);
                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Usn:"+usnvalue, Toast.LENGTH_LONG)
                        .show();

            }

        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_results, menu);
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
