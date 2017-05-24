package info.androidhive.customlistviewvolley.adapter;

import info.androidhive.customlistviewvolley.R;
import info.androidhive.customlistviewvolley.app.AppController;
import info.androidhive.customlistviewvolley.model.Movie;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<Movie> movieItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public CustomListAdapter(Activity activity, List<Movie> movieItems) {
		this.activity = activity;
		this.movieItems = movieItems;
	}

	@Override
	public int getCount() {
		return movieItems.size();
	}

	@Override
	public Object getItem(int location) {
		return movieItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		NetworkImageView thumbNail = (NetworkImageView) convertView
				.findViewById(R.id.thumbnail);
		TextView category = (TextView) convertView.findViewById(R.id.category);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		//TextView genre = (TextView) convertView.findViewById(R.id.genre);
		TextView comment = (TextView) convertView.findViewById(R.id.comment);

		// getting movie data for the row
		Movie m = movieItems.get(position);

		// thumbnail image
//		thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
		
		// category
		category.setText(m.getCategory());

		// name
		name.setText(m.getName());
		
		// phone
		phone.setText("Phone: " + m.getPhone());

		// comment
		comment.setText("Bill says: " + m.getComment());
		// genre
/*		String genreStr = "";
		for (String str : m.getGenre()) {
			genreStr += str + ", ";
		}
		genreStr = genreStr.length() > 0 ? genreStr.substring(0,
				genreStr.length() - 2) : genreStr;
		genre.setText(genreStr);*/
		
		// release year
		//comment.setText(String.valueOf(m.getYear()));

		return convertView;
	}

}