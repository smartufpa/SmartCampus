package com.example.kaeuc.osmapp.Server;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.kaeuc.osmapp.Extras.Constants;
import com.example.kaeuc.osmapp.Extras.Local;
import com.example.kaeuc.osmapp.Extras.OsmXmlParser;
import com.example.kaeuc.osmapp.MapActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by kaeuc on 10/22/2016.
 */

public class OsmDataRequest extends AsyncTask<String,String,String> {

    private static final String TAG = "OsmDataRequest" ;
    private ServerTaskResponse callBack = null;
    private ProgressBar progressBar;

    private Context parentContext;
    private String filtro = null;

<<<<<<< HEAD
    private ProgressBar progressBar;

    public OsmDataRequest(Context parentContext,ProgressBar progressBar) {
=======
    public OsmDataRequest(Context parentContext, ProgressBar progressBar) {
>>>>>>> 75412aef04510023fabe4e60a18dfee18815e244
        this.parentContext = parentContext;
        this.callBack = (ServerTaskResponse) parentContext;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        super.onPreExecute();
    }


    @Override
    protected String doInBackground(String... params) {
        filtro = params[0];
        String xmlResponse = null;


        HttpURLConnection connection = null;
        BufferedReader reader = null;
        /*Server URL*/
        URL url = null;
        try{
            // Essa url deve se parecer algo como http://www.overpass-api.de/api/xapi?*[key=value][bbox=-48.46069,-1.47956,-48.45348,-1.47158]
            url = new URL(Constants.SERVER_URL+filtro + Constants.BOUNDING_BOX);
            Log.i(TAG,"Request sent to: "+ url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout( 10000 /*milliseconds*/ );
            connection.setConnectTimeout( 10000 /* milliseconds */ );
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/xml");
            connection.setRequestProperty("Accept", "application/xml");
            connection.connect();


            //Receives the response
            InputStream inputStream = connection.getInputStream();

            StringBuffer buffer = new StringBuffer();
            // Empty response
            if(inputStream == null){
                return null;
            }
            reader =  new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            xmlResponse = buffer.toString();

            return xmlResponse;

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String xmlIncome) {
        super.onPostExecute(xmlIncome);

        OsmXmlParser parser = new OsmXmlParser();
        try {
            final List<Local> locais = parser.parse(xmlIncome);
            callBack.onTaskCompleted(locais,filtro);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.GONE);


    }

}
