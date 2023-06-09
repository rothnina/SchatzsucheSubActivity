package de.hsas.inf.schatzsuchesubactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MiniAdapter extends RecyclerView.Adapter<MiniAdapter.FirstViewHolder> {
    private ArrayList<String> content;
    private Context context;
    private int position;
    //private int drawable = R.id.

    @NonNull
    @Override
    public FirstViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.layout_recyclerview, parent, false);
        FirstViewHolder viewHolder = new FirstViewHolder(v);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FirstViewHolder holder, final int position){
        holder.bind(content.get(position));
    }

    @Override
    public int getItemCount(){
        return content.size();
    }

    public class FirstViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        private View itemView;

        public FirstViewHolder(@NonNull View itemView){
            super(itemView);
            this.itemView = itemView;
            tv = itemView.findViewById(R.id.scoreItem);
        }
        public void bind (final String dataString){
            tv.setText(dataString);
        }
    }

    public MiniAdapter(ArrayList<String> content){
        this.content = content;
    }

    public void add(String item){
        int sz = content.size();
        content.add(sz, item);
        notifyItemInserted(sz);
        notifyItemChanged(sz);
    }

    public void clear(){
        for (int i = 0; i < content.size(); i++){
            content.remove(i);
            notifyItemRemoved(i);
            notifyDataSetChanged();
        }
    }
}
