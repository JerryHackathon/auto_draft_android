package com.example.pianoafrik.ormapp.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pianoafrik.ormapp.R;
import com.example.pianoafrik.ormapp.api.Globall;
import com.example.pianoafrik.ormapp.model.OnlineTeam;
import com.example.pianoafrik.ormapp.model.PhoneNumber;
import com.example.pianoafrik.ormapp.model.Team;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pianoafrik on 4/26/17.
 */

public class FromGroupsAdapter extends ArrayAdapter<Team> {

    public FromGroupsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Team> objects) {
        super(context, R.layout.group_item, objects);
    }

    private static class ViewHolder {

        TextView groupName, groupMembers;

    }

    @Nullable
    @Override
    public Team getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Team group = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.group_item, parent , false);
            viewHolder.groupName = (TextView) convertView.findViewById(R.id.group_name);
            viewHolder.groupMembers = (TextView)convertView.findViewById(R.id.group_members);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.groupName.setText(group.getName());
        //viewHolder.groupMembers.setText( "Hey" + String.valueOf(group.getList().get(0)));
        viewHolder.groupMembers.setText(Globall.groupMembers(group.getList()));



        return convertView;
    }


}
