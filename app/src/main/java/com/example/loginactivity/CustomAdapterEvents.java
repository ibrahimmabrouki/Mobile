package com.example.loginactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomAdapterEvents extends BaseAdapter {
    //Fields or attributes
    String []text;
    Context context;
    int [] imageids;
    LayoutInflater inflater;

    //contructor
    public CustomAdapterEvents(Context context, String[] text, int[] imageids) {
        this.context = context;
        this.text = text;
        this.imageids = imageids;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    //mandatory methods: getCount(), getItem(), getItemId(), getView()

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowview=inflater.inflate(R.layout.row_event, null);

        ((TextView)rowview.findViewById(R.id.row_tv)).setText(text[i]);//i is the position
        ((ImageView)rowview.findViewById(R.id.row_imageView)).setImageResource(imageids[i]);//i is the position

        rowview.setTag(i);
        /*
        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked on position "+i, Toast.LENGTH_SHORT).show();
            }
        });

         */
        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "You clicked on position "+rowview.getTag(), Toast.LENGTH_SHORT).show();
            }
        });

        //text [(int)rowview.getTag()]
        return rowview;
    }
}
