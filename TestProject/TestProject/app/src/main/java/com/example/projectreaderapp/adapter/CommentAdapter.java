package com.example.projectreaderapp.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectreaderapp.DBHelper;
import com.example.projectreaderapp.DisplayDetailsNews;
import com.example.projectreaderapp.MainActivity;
import com.example.projectreaderapp.PrefManager;
import com.example.projectreaderapp.R;
import com.example.projectreaderapp.UpdateDetailsNews;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHoler> {

    public CommentAdapter(Activity activity, Context context, ArrayList idcomment, ArrayList namecommentuser, ArrayList commentrcv, ArrayList idnewscm, ArrayList idusercm, ArrayList<Bitmap> imgUser) {
        this.activity = activity;
        this.context = context;
        this.idcomment = idcomment;
        this.namecommentuser = namecommentuser;
        this.commentrcv = commentrcv;
        this.idnewscm = idnewscm;
        this.idusercm = idusercm;
        this.imgUser = imgUser;
    }

    Activity activity;
    Context context;
    ArrayList idcomment, namecommentuser, commentrcv, idnewscm, idusercm;
    ArrayList<Bitmap> imgUser;
    DBHelper dbHelper;
    Integer IDUser;
    Integer IDComment;

    @NonNull
    @Override
    public CommentAdapter.CommentViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.itemcomment, parent, false);
        return new CommentViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHoler holder, int position) {
        holder.tv_id_comment.setText(String.valueOf(idcomment.get(position)));
        holder.tv_name_user_comment.setText(String.valueOf(namecommentuser.get(position)));
        holder.tv_comment_user.setText(String.valueOf(commentrcv.get(position)));
        holder.tv_id_news.setText(String.valueOf(idnewscm.get(position)));
        holder.tv_id_user.setText(String.valueOf(idusercm.get(position)));
        holder.imgv_user_comment.setImageBitmap(imgUser.get(position));
        dbHelper = new DBHelper(context);
        displayDataUser();
        if(Integer.valueOf(String.valueOf(idusercm.get(position)))==IDUser){
            holder.btnsettingcomment.setVisibility(View.VISIBLE);
        }else{
            holder.btnsettingcomment.setVisibility(View.GONE);
        }
    }
    //Mở dialog
    public void openDialogUpdateComment(int gravity){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_comment);
        dbHelper=new DBHelper(context);

        Window window = dialog.getWindow();
        if(window==null){
            return;
        }else{
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttribute = window.getAttributes();
            windowAttribute.gravity = gravity;
            window.setAttributes(windowAttribute);

            if(Gravity.BOTTOM == gravity){
                dialog.setCancelable(true);
            }else{
                dialog.setCancelable(false);
            }

            EditText editText = dialog.findViewById(R.id.edt_update_comment);
            Button btn_no_update_comment = dialog.findViewById(R.id.btn_no_update_comment);
            Button btn_yes_update_comment = dialog.findViewById(R.id.btn_yes_update_comment);


            btn_no_update_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btn_yes_update_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String cmt = editText.getText().toString().trim();
                    if(cmt.isEmpty()){
                        Toast.makeText(activity, "Vui lòng nhập bình luận mới", Toast.LENGTH_SHORT).show();
                    }else{
                        dbHelper.updateComment(IDComment, cmt);
                        Toast.makeText(activity, "Cập nhật bình luận thành công", Toast.LENGTH_SHORT).show();
                        activity.recreate();
                    }
                }
            });

            dialog.show();
        }
    }

    //Tạo xác nhận xóa
    public void comfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xóa bình luận"+ commentrcv + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa bình luận này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBHelper dbHelper = new DBHelper(context);
                dbHelper.deleteCommentOneRow(IDComment);
                activity.recreate();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    //Lấy data cho báo chính
    public void displayDataUser(){
        Cursor cursor = dbHelper.getUser(new PrefManager(context).getUsername());
        if(cursor.getCount()==0){
            Toast.makeText(context, "Bạn chưa đăng nhập", Toast.LENGTH_LONG).show();
        }else{
            while (cursor.moveToNext()){
                String UserID = cursor.getString(0);
                IDUser = Integer.valueOf(UserID);
            }
        }
    }
    //Lấy dữ liệu đã đăng nhập hay chưa
    public String getUsername() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userName", "");
    }

    @Override
    public int getItemCount() {
        return idcomment.size();
    }

    public class CommentViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView tv_name_user_comment, tv_comment_user, tv_id_comment, tv_id_news, tv_id_user;
        ImageView imgv_user_comment;
        ImageButton btnsettingcomment;
        public CommentViewHoler(@NonNull View itemView) {
            super(itemView);
            tv_name_user_comment=itemView.findViewById(R.id.tv_name_user_comment);
            tv_comment_user=itemView.findViewById(R.id.tv_comment_user);
            tv_id_comment=itemView.findViewById(R.id.tv_id_comment);
            tv_id_news=itemView.findViewById(R.id.tv_id_news);
            tv_id_user=itemView.findViewById(R.id.tv_id_user);
            imgv_user_comment=itemView.findViewById(R.id.imgv_user_comment);
            btnsettingcomment=itemView.findViewById(R.id.btnsettingcomment);
            btnsettingcomment.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu_comment);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            IDComment = getAdapterPosition()+1;
            if(item.getItemId()==R.id.edt_comment_user){
                openDialogUpdateComment(Gravity.CENTER);
            }
            if(item.getItemId()==R.id.delete_comment_user){
                comfirmDialog();
            }
            return false;
        }
    }
}
