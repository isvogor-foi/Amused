package hr.foi.thor.logic;

import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Entity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EntityAdapter extends ArrayAdapter<Entity> {

	public EntityAdapter(Context context, List<Entity> mItem){
		super(context, R.layout.string_list_item, mItem);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//return super.getView(position, convertView, parent);
		
		ViewHolder viewHolder;
		
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.string_list_item, parent, false);
			
			viewHolder = new ViewHolder();
			viewHolder.tvName = (TextView) convertView.findViewById(R.id.listText);
			
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Entity item = getItem(position);
		viewHolder.tvName.setText(item.getName());
	
		return convertView;
	}
	
	private static class ViewHolder{
		TextView tvName;
	}
}
