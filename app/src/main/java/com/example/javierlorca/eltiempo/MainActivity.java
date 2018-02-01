package com.example.javierlorca.eltiempo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView tvxdiasemana;
    TextView tvxhora;
    TextView tvxgrados;
    TextView tvxprevision;
    ListView lvlista;
    LinearLayout lyhorizontal;

    TextView prueba;
    TextView txverror;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tvxdiasemana=(TextView)findViewById(R.id.txvdiasemana);
        TextView tvxhora=(TextView)findViewById(R.id.txvhora);
        TextView tvxgrados=(TextView)findViewById(R.id.txvgrados);
        TextView tvxprevision=(TextView)findViewById(R.id.txvprevision);
        //ListView lvlista=(ListView)findViewById(R.id.lvlista);
        LinearLayout lyhorizontal=(LinearLayout)findViewById(R.id.lyhorizontal);
        final TextView txvprueba=(TextView)findViewById(R.id.txvprueba);
        final TextView txverror=(TextView)findViewById(R.id.txverror);
    final Context context=this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                /**----------------------INICIO API------------------------**/
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(context);
                String url ="https://api.openweathermap.org/data/2.5/weather?lat=35&lon=139";

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                               txvprueba.setText(response);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txvprueba.setText(error.getMessage());
                    }
                    /**----------------------FIN API------------------------**/
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                //COMENTARIO

            }

        });
    }

   /** @Override
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
    }**/
}