package vturesults.mosambi.com.vturesults;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    Button b,savedb,prev;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SQLiteDatabase mydatabase = openOrCreateDatabase("studentdb",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS stures(name VARCHAR,usn VARCHAR,sem VARCHAR,tmarks VARCHAR,result VARCHAR" +
                ",scode1 VARCHAR,scode2 VARCHAR,scode3 VARCHAR,scode4 VARCHAR,scode5 VARCHAR,scode6 VARCHAR,scode7 VARCHAR,scode8 VARCHAR" +
                ",sname1 VARCHAR,sname2 VARCHAR,sname3 VARCHAR,sname4 VARCHAR,sname5 VARCHAR,sname6 VARCHAR,sname7 VARCHAR,sname8 VARCHAR" +
                ",exmark1 VARCHAR,exmark2 VARCHAR,exmark3 VARCHAR,exmark4 VARCHAR,exmark5 VARCHAR,exmark6 VARCHAR,exmark7 VARCHAR,exmark8 VARCHAR" +
                ",inmark1 VARCHAR,inmark2 VARCHAR,inmark3 VARCHAR,inmark4 VARCHAR,inmark5 VARCHAR,inmark6 VARCHAR,inmark7 VARCHAR,inmark8 VARCHAR" +
                ",subto1 VARCHAR,subto2 VARCHAR,subto3 VARCHAR,subto4 VARCHAR,subto5 VARCHAR,subto6 VARCHAR,subto7 VARCHAR,subto8 VARCHAR" +
                ",subres1 VARCHAR,subres2 VARCHAR,subres3 VARCHAR,subres4 VARCHAR,subres5 VARCHAR,subres6 VARCHAR,subres7 VARCHAR,subres8 VARCHAR" +
                ",PRIMARY KEY (usn,sem));");

        b=(Button)findViewById(R.id.button);
        savedb=(Button)findViewById(R.id.button3);
        et=(EditText)findViewById(R.id.editText);
        prev=(Button)findViewById(R.id.prev);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=et.getText().toString();
                if(usn!=null && !usn.isEmpty()){
                    if(usn.matches("\\d{1}[A-Za-z]{2}\\d{2}[A-Za-z]{2}\\d{3}")) {
                        if(isNetworkAvailable(MainActivity.this)) {
                            Intent i = new Intent(MainActivity.this, Result.class);
                            i.putExtra("usn", usn);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    Toast.makeText(getApplicationContext(),usn+"Incorrect Form", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Null Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });


        savedb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SavedResults.class);
                startActivity(i);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn=et.getText().toString();
                if(usn!=null && !usn.isEmpty()){
                    if(usn.matches("\\d{1}[A-Za-z]{2}\\d{2}[A-Za-z]{2}\\d{3}")) {
                        if(isNetworkAvailable(MainActivity.this)) {
                            Intent i = new Intent(MainActivity.this, PrevResults.class);
                            i.putExtra("usn", usn);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"No internet connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(),usn+"Incorrect Form", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Null Entered", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public boolean isNetworkAvailable(Context ctx)
    {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()&& cm.getActiveNetworkInfo().isAvailable()&& cm.getActiveNetworkInfo().isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
