package com.example.androidproject.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.example.androidproject.R;
import com.example.projectandroid2015.util.ContentProviderUtil;
import com.example.projetandroid2015.tables.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florian on 06/03/2015.
 */
public class PropertiesAdaptaters extends BaseAdapter implements Filterable {

   // private PropertyFilter propertyFilter;
    private ArrayList<String> props;
    private ArrayList<String> filteredProps;
    private Activity mContext;
    private propertyFilter filter;
    private LayoutInflater mLayoutInflater = null;
    private ContentProviderUtil util;



    public PropertiesAdaptaters(Activity mContext,ArrayList<String> list){
        this.mContext = mContext;
        util = new ContentProviderUtil(mContext);
        props = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filteredProps = list;
    }


    @Override
    public int getCount() {
        return filteredProps.size();
    }

    @Override
    public String getItem(int position) {
        return filteredProps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final String idEntry = getItem(position);
        final HashMap<String,String> entry = util.getEntry(idEntry);

        if(convertView==null){
               convertView = mLayoutInflater.inflate(R.layout.simple_map_layout,parent,false);
                holder = new ViewHolder();
            holder.propertyName = (TextView) convertView.findViewById(R.id.name_list_view);
            holder.propertyValue = (TextView) convertView.findViewById(R.id.value_list_view);

            //TODO custom les textview ???
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        HashMap<String,String> obj = util.getObject(entry.get(ObjectEntryTable.VALUE));
        holder.propertyName.setText(entry.get(EntryTable.NAME));
        String valueProp;
        if((valueProp = obj.get(ObjectTable.NAME))!=null){
            holder.propertyValue.setText(valueProp);
        }else{
            holder.propertyValue.setText(entry.get(ObjectEntryTable.VALUE));
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new propertyFilter();
        }
        return filter;
    }

    static class ViewHolder{
        TextView propertyName;
        TextView propertyValue;
    }

    private class propertyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<String> tmp = new ArrayList<String>();
                    for(String idEntry : props){
                        HashMap<String,String> entry = util.getEntry(idEntry);
                        if(entry.get(EntryTable.NAME).toLowerCase().contains(constraint.toString().toLowerCase())){
                            tmp.add(idEntry);
                        }
                    }
                result.count = tmp.size();
                result.values = tmp;
            }else{
                result.count = props.size();
                result.values = props;
            }
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredProps = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }
    }
}
