package hr.foi.thor.logic;

import hr.foi.thor.amused.R;
import hr.foi.thor.entities.Author;
import hr.foi.thor.entities.Piece;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class AuthorPieceListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private List<Author> listHeader;
	//private List<Piece>_listChild;
	private HashMap<Author, List<Piece>> listChild;
	
	public AuthorPieceListAdapter(Context context, List<Author> listHeader, HashMap<Author, List<Piece>> listChild) {
		this.context = context;
		this.listHeader = listHeader;
		this.listChild =  listChild;
	}
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
	     String headerTitle = ((Author) getGroup(groupPosition)).getName();
	     
	        if (convertView == null) {
	            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            convertView = infalInflater.inflate(R.layout.author_list_group, null);
	        }
	 
	        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
	        lblListHeader.setTypeface(null, Typeface.BOLD);
	        lblListHeader.setText(headerTitle);
	 
	        return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		
		final Piece child = (Piece) getChild(groupPosition, childPosition);
	 
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.author_list_item, null);
        }
 
        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
 
        txtListChild.setText(child.getName());
        return convertView;
	}

	
	@Override
	public int getGroupCount() {
		return this.listHeader.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this.listChild.get(this.listHeader.get(groupPosition)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this.listHeader.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return this.listChild.get(this.listHeader.get(groupPosition)).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}
	
	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
