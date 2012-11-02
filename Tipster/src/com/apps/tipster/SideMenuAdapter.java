package com.apps.tipster;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SideMenuAdapter extends ArrayAdapter<SideMenuItem>{
	
	private List<SideMenuItem> items;
	private LayoutInflater vi;

	public SideMenuAdapter(Context context, int textViewResourceId,
			List<SideMenuItem> objects) {
		super(context, textViewResourceId, objects);
		
		items = objects;
		this.vi = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;         
        convertView = vi.inflate(R.layout.side_menu_item, null);
        holder = new ViewHolder();
        holder.name = (TextView) convertView.findViewById(R.id.side_menu_text);
        holder.icon = (ImageView)convertView.findViewById(R.id.side_menu_icon);
        convertView.setTag(holder);

        SideMenuItem row = items.get(position);
        if (row != null) {
            holder.name.setText(row.menuText);
            holder.icon.setImageDrawable(row.menuIcon);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView icon;
    }
}
