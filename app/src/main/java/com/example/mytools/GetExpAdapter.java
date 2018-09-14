package com.example.mytools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.Entity.GetExpItem;
import com.example.expresscab.InputConfirmActivity;
import com.example.expresscab.R;
import com.example.expresscab.RetrieveActivity;

import java.util.List;

public class GetExpAdapter extends RecyclerView.Adapter<GetExpAdapter.ViewHolder> {

    private String TAG = "适配器";

    private List<GetExpItem> myExpList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView item_tv_type;
        TextView item_cell_code;
        TextView item_tv_exp_code;
        TextView item_tv_addr;
        TextView item_tv_in_time;
        Button one_key_get;

        public ViewHolder(@NonNull View view) {
            super(view);
            item_tv_type = view.findViewById(R.id.item_tv_type);
            item_cell_code = view.findViewById(R.id.item_cell_code);
            item_tv_exp_code = view.findViewById(R.id.item_tv_exp_code);
            item_tv_addr = view.findViewById(R.id.item_tv_addr);
            item_tv_in_time = view.findViewById(R.id.item_tv_in_time);
            one_key_get = view.findViewById(R.id.one_key_get);
        }
    }

    public GetExpAdapter(List<GetExpItem> getExpItemList){
        myExpList = getExpItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.get_exp_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.one_key_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                final GetExpItem getExpItem = myExpList.get(position);
                Log.d(TAG, "onClick: uid:" + GlobalData.getUid());
                Log.d(TAG, "onClick: order_id:" + getExpItem.getOrder_id());
                final Context now_context = parent.getContext();
                Log.d(TAG, "onClick: 当前活动" + now_context);
                AlertDialog.Builder dialog = new AlertDialog.Builder(parent.getContext());
                dialog.setTitle("提示");
                dialog.setMessage("请确认您的当前位置是否为包裹存放位置，以免造成不良后果.\n\n" +
                        "快件取回后，用户的取件密码将会失效.");
                dialog.setPositiveButton("确认取回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(now_context, RetrieveActivity.class);
                        intent.putExtra("uid", GlobalData.getUid());
                        intent.putExtra("order_id", getExpItem.getOrder_id());
                        now_context.startActivity(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GetExpItem getExpItem = myExpList.get(position);
        String type = getExpItem.getDesc_type();
        String cell_code = getExpItem.getDesc_code();
        String exp_code = getExpItem.getExp_code();
        String addr = getExpItem.getAddr();
        String in_time = getExpItem.getIn_time();

        holder.item_tv_type.setText(type);
        holder.item_cell_code.setText(cell_code);
        holder.item_tv_exp_code.setText(exp_code);
        holder.item_tv_addr.setText(addr);
        holder.item_tv_in_time.setText(in_time);

    }

    @Override
    public int getItemCount() {
        return myExpList.size();
    }
}
