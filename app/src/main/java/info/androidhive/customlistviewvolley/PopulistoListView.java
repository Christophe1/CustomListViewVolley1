package info.androidhive.customlistviewvolley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class PopulistoListView extends Activity {

	public static final String KEY_PHONENUMBER_USER = "phonenumberofuser";
	String phoneNoofUser;
	// Log tag
	private static final String TAG = PopulistoListView.class.getSimpleName();

	public static final String USER_ID = "useridofuser";
	String user_id;
	private static final String url2 = "http://www.populisto.com/SelectUserReviews2.php";

	// Movies json url
	private static final String url = "http://www.populisto.com/SelectUserReviews.php";
	private ProgressDialog pDialog;
	private List<Review> reviewList = new ArrayList<Review>();
	private ListView listView;
	private CustomListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.list);
		adapter = new CustomListAdapter(this, reviewList);
		listView.setAdapter(adapter);

		pDialog = new ProgressDialog(this);
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();

		phoneNoofUser = "+353872934480";
	//	user_id = "10219";
		// changing action bar color
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#1b1b1b")));



// change JSONArrayrequest to your appropiate request to get the user id
		//url = get the user_id as response.php";
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						// parse your response to get the user_id
						final String user_id = response;
						//toast the user_id in my app, this works correctly
						Toast.makeText(PopulistoListView.this, user_id, Toast.LENGTH_LONG).show();
						//now I want to get a json array based on the user_id
						//url2 = get the json array as response.php";
						JsonArrayRequest movieReq = new JsonArrayRequest(url2,

								new Response.Listener <JSONArray> () {
									@Override
									public void onResponse(JSONArray response) {

                                        Log.d(TAG, response.toString());

										hidePDialog();

										// Parsing json
										for (int i = 0; i < response.length(); i++) {
											try {


												JSONObject obj = response.getJSONObject(i);
												Review review = new Review();
												review.setCategory(obj.getString("category"));

												review.setName(obj.getString("name"));
												review.setPhone(obj.getString("phone"));
												review.setComment(obj.getString("comment"));

												reviewList.add(review);

											} catch (JSONException e) {
												e.printStackTrace();
											}

										}

										// notifying list adapter about data changes
										// so that it renders the list view with updated data
										adapter.notifyDataSetChanged();
                                        Toast.makeText(PopulistoListView.this, response.toString(), Toast.LENGTH_LONG).show();

                                        //Toast.makeText(PopulistoListView.this, user_id + "hehe", Toast.LENGTH_LONG).show();

									}

								}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								VolleyLog.d(TAG, "Error geting user info: " + error.getMessage());
								hidePDialog();

							}

						}) {
							@Override
							//post the user_id to get the json array as response.php
							protected Map<String, String> getParams() {
								Map<String, String> params2 = new HashMap<String, String>();
								params2.put(USER_ID, user_id);
								return params2;

							}

						};

						// Adding request for getting user info to request queue
						AppController.getInstance().addToRequestQueue(movieReq);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error getting user: " + error.getMessage());
				hidePDialog();
			}
		})
		{
			@Override
			//post the user's phone number, and based on this will get the user_id
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put(KEY_PHONENUMBER_USER, phoneNoofUser);
				return params;

			}

		};

// Adding request for getting user_id to request queue
		RequestQueue requestQueue = Volley.newRequestQueue(this) ;
		requestQueue.add(stringRequest);

	}



	@Override
	public void onDestroy() {
		super.onDestroy();
		hidePDialog();
	}

	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
