package org.landoria.mac.playpaddy;

import HttpAdapter.TopicHttpServiceAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class ViewTopicsActivity extends AppCompatActivity {

    TopicHttpServiceAdapter topicHttpServiceAdapter = new TopicHttpServiceAdapter();


    ListView listViewTopics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_topics);




        showBackButton();


        //showing activity name and app icon on action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logomainwhite);
//        getSupportActionBar().setTitle(sessionDb.getSessionBySessionId("1").get("NAME").toString());
        getSupportActionBar().setDisplayUseLogoEnabled(true);


//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#800000")));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#006400")));



        listViewTopics = (ListView)findViewById(R.id.listViewTopics);


        loadTopics();


    }



    ArrayList<JSONObject> topicJsonList = new ArrayList<>();
    ArrayList<String> topicNamesList = new ArrayList<>();

    public void loadTopics() {


        //        //setting up the error dialog
        AlertDialog.Builder alert = new AlertDialog.Builder(ViewTopicsActivity.this, R.style.AppTheme);
        alert.setTitle("Error!!!");


        try {

            //running the time consuming tasks in a seperate thread
            AsyncTask<Void, Void, Void> updateTask = new AsyncTask<Void, Void, Void>() {
                ProgressDialog dialog = new ProgressDialog(ViewTopicsActivity.this);


                int errorCode = 0;
                String errorMessage = "";


                @Override
                protected void onPreExecute() {
                    // what to do before background task
                    dialog.setTitle("Loading Topics...");
                    dialog.setMessage("Please wait...");
                    dialog.setIndeterminate(true);
                    dialog.setCancelable(false);
                    dialog.show();
                }

                @Override
                protected Void doInBackground(Void... params) {


                    try {

                        //topic
                        if (topicHttpServiceAdapter != null) {
                            if (topicHttpServiceAdapter.GetAllTopics().size() > 0) {

                                topicJsonList = topicHttpServiceAdapter.GetAllTopics();

                                for (int i = 0; i < topicJsonList.size(); i++) {

                                    // Toast.makeText(HomeActivity.this, i + "", Toast.LENGTH_LONG).show();
                                    topicNamesList.add(topicJsonList.get(i).getString("TopicDetail"));

                                }
                            } else {


                                errorMessage = "There are no Topics to load!";
                                // Toast.makeText(HomeActivity.this, "Nothing to backup", Toast.LENGTH_LONG).show();

                            }
                        } else {
                            errorMessage = "No Topics to load!";
                        }


                    } catch (Exception ex) {


                        errorCode = 1;
                        errorMessage = ex.getMessage();

                    }

                    return null;


                }

                @Override
                protected void onPostExecute(Void result) {
                    // what to do when background task is completed
                    dialog.dismiss();
                    if (errorCode == 1) {
//                            alert.setMessage("Error has occured");
//                            alert.show();

                        Toast.makeText(ViewTopicsActivity.this, "Error has occurred" + errorMessage, Toast.LENGTH_LONG).show();
                        return;
                    } else {



                        //spinnerLocation.getSelectedItem();

                        if (topicNamesList.size() > 0) {

                            populateListView();//load the populate list view method


                            Toast.makeText(ViewTopicsActivity.this, "Topics Loaded", Toast.LENGTH_LONG).show();

                        }
                        else{

                            Toast.makeText(ViewTopicsActivity.this, "No topics found", Toast.LENGTH_LONG).show();


                        }


                        return;
                    }


                }

                ;

                @Override
                protected void onCancelled() {
                    dialog.dismiss();
                    super.onCancelled();
                }
            };
            updateTask.execute((Void[]) null);


        } catch (Exception ex) {
            Toast.makeText(ViewTopicsActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }


    public void populateListView() {

        ArrayAdapter<String> adapter = new MyListAdapter();
        listViewTopics.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<String> {

        public MyListAdapter() {
            super(ViewTopicsActivity.this, R.layout.topic_layout, topicNamesList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View itemView = null;

            try {

                if(convertView == null){
                    itemView = getLayoutInflater().inflate(R.layout.topic_layout, parent, false);


                    //get the search text views
                    final TextView textViewTopicId = (TextView) itemView.findViewById(R.id.textViewTopicIdList);
                    TextView textViewTopicDetail = (TextView) itemView.findViewById(R.id.textViewTopicDetail);

                    if (topicJsonList.size() > 0) {//if there are grades to load


                        textViewTopicId.setText("" + topicJsonList.get(position).get("TopicId"));
                        textViewTopicDetail.setText("" + topicJsonList.get(position).get("TopicDetail"));



                    } else {

                        Toast.makeText(ViewTopicsActivity.this, "Topics could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }



                }
                else{

                    itemView = convertView;


                    //get the search text views
                    final TextView textViewTopicId = (TextView) itemView.findViewById(R.id.textViewTopicIdList);
                    TextView textViewTopicDetail = (TextView) itemView.findViewById(R.id.textViewTopicDetail);

                    if (topicJsonList.size() > 0) {//if there are grades to load


                        textViewTopicId.setText("" + topicJsonList.get(position).get("TopicId"));
                        textViewTopicDetail.setText("" + topicJsonList.get(position).get("TopicDetail"));



                    } else {

                        Toast.makeText(ViewTopicsActivity.this, "Topics could not be loaded. Please check your internet connection and try again.", Toast.LENGTH_LONG).show();
                    }


                }

            } catch (Exception ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

            return itemView;

        }


    }


    public void showBackButton() {

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
