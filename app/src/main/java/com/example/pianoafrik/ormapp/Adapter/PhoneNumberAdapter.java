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
import com.example.pianoafrik.ormapp.model.PhoneNumber;

import java.util.List;

/**
 * Created by pianoafrik on 3/15/17.
 */

public class PhoneNumberAdapter extends ArrayAdapter<PhoneNumber> {

    private static class ViewHolder {

        TextView phoneNumber;

    }

    public PhoneNumberAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PhoneNumber> objects) {
        super(context, R.layout.phone_number_item, objects);
    }

    @Nullable
    @Override
    public PhoneNumber getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        PhoneNumber phoneNumber = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.phone_number_item, parent , false);
            viewHolder.phoneNumber = (TextView) convertView.findViewById(R.id.tvPhoneNumber);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.phoneNumber.setText(phoneNumber.getNumber());

        return convertView;

    }
}
