package com.tixon.colorwheel.navigationdrawer;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tixon.colorwheel.R;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {
    private static final int HEADER_TYPE = 0;
    private static final int ROW_TYPE = 1;

    private String[] rows;

    private OnDrawerItemClickListener onDrawerItemClickListener;

    public void setOnDrawerItemClickListener(OnDrawerItemClickListener listener) {
        this.onDrawerItemClickListener = listener;
    }

    public DrawerAdapter(String[] rows) {
        this.rows = rows;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_header, viewGroup, false);
            return new ViewHolder(view, viewType);
        } else if(viewType == ROW_TYPE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_row, viewGroup, false);
            return new ViewHolder(view, viewType);
        }
        return null;
    }

    private void onTouchEvent(MotionEvent event, ViewHolder viewHolder, int i) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:case MotionEvent.ACTION_MOVE:
                viewHolder.frame.setBackgroundColor(Color.parseColor("#cceeeeee"));
                break;
            case MotionEvent.ACTION_UP:case MotionEvent.ACTION_CANCEL:
                viewHolder.frame.setBackgroundColor(Color.parseColor("#00ffffff"));
                onDrawerItemClickListener.onDrawerItemClick(i);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        if(viewHolder.viewType == ROW_TYPE) {
            String rowText = rows[i-1];
            viewHolder.text.setText(rowText);

            viewHolder.icon.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    onTouchEvent(event, viewHolder, i);
                    return true;
                }
            });
            viewHolder.text.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    onTouchEvent(event, viewHolder, i);
                    return true;
                }
            });
            viewHolder.frame.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    onTouchEvent(event, viewHolder, i);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return rows.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return HEADER_TYPE;
        }
        return ROW_TYPE;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected int viewType;
        FrameLayout frame;
        ImageView icon;
        TextView text;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            if(viewType == ROW_TYPE) {
                frame = (FrameLayout) itemView.findViewById(R.id.drawer_row_frame);
                icon = (ImageView) itemView.findViewById(R.id.drawer_row_icon);
                text = (TextView) itemView.findViewById(R.id.drawer_row_text);
            }
        }
    }
}
