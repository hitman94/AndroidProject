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

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Florian on 06/03/2015.
 */
public class PropertiesAdaptaters extends BaseAdapter implements Filterable {

   // private PropertyFilter propertyFilter;
    private ArrayList<Map.Entry<String,String>> props;
    private ArrayList<Map.Entry<String,String>> filteredProps;
    private Activity mContext;
    private propertyFilter filter;
    private LayoutInflater mLayoutInflater = null;



    public PropertiesAdaptaters(Activity mContext,ArrayList<Map.Entry<String,String>> list){
        this.mContext = mContext;
        props = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        filteredProps = list;
    }


    @Override
    public int getCount() {
        return filteredProps.size();
    }

    @Override
    public Map.Entry<String,String> getItem(int position) {
        return filteredProps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final Map.Entry<String,String> entry = (Map.Entry<String,String>) getItem(position);


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
        holder.propertyName.setText(entry.getKey());
        holder.propertyValue.setText(entry.getValue());
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
                ArrayList<Map.Entry<String,String>> tmp = new ArrayList<Map.Entry<String, String>>();
                    for(Map.Entry<String,String> entry : props){
                        if(entry.getKey().toLowerCase().contains(constraint.toString().toLowerCase())){
                            tmp.add(entry);
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
            filteredProps = (ArrayList<Map.Entry<String,String>>) results.values;
            notifyDataSetChanged();
        }
    }
}
