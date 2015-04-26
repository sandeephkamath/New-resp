package vturesults.mosambi.com.vturesults;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Result extends ActionBarActivity {
    private ProgressDialog pDialog;
    Button b;
    private static String url = "http://vtucgprojects.co.in/wscrap/wtest.php?usn=";
    String jsonStr,name,usn,tmarks,sem,success,result,so;
    String[] scodearr,snames,exmarks,inmarks,tot,subres;
    int fail=0;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv=(TextView)findViewById(R.id.tv);
        b=(Button)findViewById(R.id.button2);

        Intent i=getIntent();
        usn=i.getStringExtra("usn");
        url = "http://vtucgprojects.co.in/wscrap/wtest.php?usn=";

        if(isNetworkAvailable(this)) {
            new GetContacts().execute();
        }else{
            Toast.makeText(getApplicationContext(),"No internet connection", Toast.LENGTH_SHORT).show();
        }

        final SQLiteDatabase mydatabase = openOrCreateDatabase("studentdb",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS stures(name VARCHAR,usn VARCHAR,sem VARCHAR,tmarks VARCHAR,result VARCHAR" +
                ",scode1 VARCHAR,scode2 VARCHAR,scode3 VARCHAR,scode4 VARCHAR,scode5 VARCHAR,scode6 VARCHAR,scode7 VARCHAR,scode8 VARCHAR" +
                ",sname1 VARCHAR,sname2 VARCHAR,sname3 VARCHAR,sname4 VARCHAR,sname5 VARCHAR,sname6 VARCHAR,sname7 VARCHAR,sname8 VARCHAR" +
                ",exmark1 VARCHAR,exmark2 VARCHAR,exmark3 VARCHAR,exmark4 VARCHAR,exmark5 VARCHAR,exmark6 VARCHAR,exmark7 VARCHAR,exmark8 VARCHAR" +
                ",inmark1 VARCHAR,inmark2 VARCHAR,inmark3 VARCHAR,inmark4 VARCHAR,inmark5 VARCHAR,inmark6 VARCHAR,inmark7 VARCHAR,inmark8 VARCHAR" +
                ",subto1 VARCHAR,subto2 VARCHAR,subto3 VARCHAR,subto4 VARCHAR,subto5 VARCHAR,subto6 VARCHAR,subto7 VARCHAR,subto8 VARCHAR" +
                ",subres1 VARCHAR,subres2 VARCHAR,subres3 VARCHAR,subres4 VARCHAR,subres5 VARCHAR,subres6 VARCHAR,subres7 VARCHAR,subres8 VARCHAR" +
                ",PRIMARY KEY (usn,sem));");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues insertValues = new ContentValues();
                insertValues.put("name",name);
                insertValues.put("sem",sem);
                insertValues.put("usn",usn.toUpperCase());
                insertValues.put("tmarks",tmarks);
                insertValues.put("result",result);
                insertValues.put("scode1",scodearr[0]);
                insertValues.put("scode2",scodearr[1]);
                insertValues.put("scode3",scodearr[2]);
                insertValues.put("scode4",scodearr[3]);
                insertValues.put("scode5",scodearr[4]);
                insertValues.put("scode6",scodearr[5]);
                insertValues.put("scode7",scodearr[6]);
                insertValues.put("scode8",scodearr[7]);
                insertValues.put("sname1",snames[0]);
                insertValues.put("sname2",snames[1]);
                insertValues.put("sname3",snames[2]);
                insertValues.put("sname4",snames[3]);
                insertValues.put("sname5",snames[4]);
                insertValues.put("sname6",snames[5]);
                insertValues.put("sname7",snames[6]);
                insertValues.put("sname8",snames[7]);
                insertValues.put("exmark1",exmarks[0]);
                insertValues.put("exmark2",exmarks[1]);
                insertValues.put("exmark3",exmarks[2]);
                insertValues.put("exmark4",exmarks[3]);
                insertValues.put("exmark5",exmarks[4]);
                insertValues.put("exmark6",exmarks[5]);
                insertValues.put("exmark7",exmarks[6]);
                insertValues.put("exmark8",exmarks[7]);
                insertValues.put("inmark1",inmarks[0]);
                insertValues.put("inmark2",inmarks[1]);
                insertValues.put("inmark3",inmarks[2]);
                insertValues.put("inmark4",inmarks[3]);
                insertValues.put("inmark5",inmarks[4]);
                insertValues.put("inmark6",inmarks[5]);
                insertValues.put("inmark7",inmarks[6]);
                insertValues.put("inmark8",inmarks[7]);
                insertValues.put("subto1",tot[0]);
                insertValues.put("subto2",tot[1]);
                insertValues.put("subto3",tot[2]);
                insertValues.put("subto4",tot[3]);
                insertValues.put("subto5",tot[4]);
                insertValues.put("subto6",tot[5]);
                insertValues.put("subto7",tot[6]);
                insertValues.put("subto8",tot[7]);
                insertValues.put("subres1",subres[0]);
                insertValues.put("subres2",subres[1]);
                insertValues.put("subres3",subres[2]);
                insertValues.put("subres4",subres[3]);
                insertValues.put("subres5",subres[4]);
                insertValues.put("subres6",subres[5]);
                insertValues.put("subres7",subres[6]);
                insertValues.put("subres8",subres[7]);
                mydatabase.insert("stures",null,insertValues );
                Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Result.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            url=url+usn;
            jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            if(jsonStr!=null){
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    success = jsonObj.getString("success");
                    if(success.equals("1")){
                        name = jsonObj.getString("name");
                        sem = jsonObj.getString("sem");
                        tmarks = jsonObj.getString("tmarks");
                        result = jsonObj.getString("result");
                        JSONArray sc;
                        sc=jsonObj.getJSONArray("scodes");
                        scodearr=new String[sc.length()];
                        for(int i=0;i<sc.length();i++){
                            scodearr[i]=sc.getString(i);
                        }
                        JSONArray sn;
                        sn=jsonObj.getJSONArray("snames");
                        snames=new String[sn.length()];
                        for(int i=0;i<sn.length();i++){
                            snames[i]=sn.getString(i);
                        }
                        JSONArray ex;
                        ex=jsonObj.getJSONArray("exmarks");
                        exmarks=new String[ex.length()];
                        for(int i=0;i<ex.length();i++){
                            exmarks[i]=ex.getString(i);
                        }
                        JSONArray in;
                        in=jsonObj.getJSONArray("inmarks");
                        inmarks=new String[in.length()];
                        for(int i=0;i<in.length();i++){
                            inmarks[i]=in.getString(i);
                        }
                        JSONArray to;
                        to=jsonObj.getJSONArray("subtm");
                        tot=new String[to.length()];
                        for(int i=0;i<to.length();i++){
                            tot[i]=to.getString(i);
                        }
                        JSONArray re;
                        re=jsonObj.getJSONArray("subres");
                        subres=new String[re.length()];
                        for(int i=0;i<re.length();i++){
                            subres[i]=re.getString(i);
                        }
                    }else{
                        fail=1;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result1) {
            super.onPostExecute(result1);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(fail!=1) {
                tv.setText(name + " " + usn + " " + sem+" "+result+"\n");
                for(int i=0;i<tot.length;i++){
                    tv.append("\n"+snames[i]+"\t"+scodearr[i]+"\n");
                    tv.append(exmarks[i]+" "+inmarks[i]+" "+tot[i]+" "+subres[i]+"\n");
                    b.setVisibility(View.VISIBLE);
                }
            }else{
                tv.setText("Invalid USN");
            }
        }

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
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
    @Override
    public void onResume(){
        super.onResume();
        String[] scodearr={""},snames={""},exmarks={""},inmarks={""},tot={""},subres={""};

    }



}

