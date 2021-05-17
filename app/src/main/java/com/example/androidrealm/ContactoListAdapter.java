package com.example.androidrealm;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class ContactoListAdapter extends RealmBaseAdapter<Contacto> implements ListAdapter {
    public ContactoListAdapter(OrderedRealmCollection<Contacto> data) {
        super(data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            // create a top-level layout for our item views
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            // create a text view to display item names
            TextView titleView = new TextView(parent.getContext());
            titleView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
            // attach the text view to the item view layout
            layout.addView(titleView);
            convertView = layout;
            viewHolder = new ViewHolder(titleView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // as long as we
        if (adapterData != null) {
            final Contacto contacto = adapterData.get(position);
            viewHolder.textView.setText(contacto.toString());
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;

        public ViewHolder(TextView textView) {
            this.textView = textView;
        }
    }
}
