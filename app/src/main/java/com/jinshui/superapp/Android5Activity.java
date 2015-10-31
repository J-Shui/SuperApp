package com.jinshui.superapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Android5Activity extends AppCompatActivity {

    private List<String> data;

    private RecyclerView recyclerView;
    private MyRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android5);

        data = new ArrayList<String>();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //2.设置布局管理器支持：
        // 单列线性排列
        //GridView
        //瀑布流模式

        //线性方向布局
//        LinearLayoutManager layout = new LinearLayoutManager(this,
//                LinearLayout.HORIZONTAL,//垂直布局
//                false);
//        GridLayoutManager layout = new GridLayoutManager(this,
//                2,
//                GridLayoutManager.HORIZONTAL,
//                false
//        );
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layout);

        //1.设置RecyclerView 的Adapter
        //一定在设置了布局管理器之后调用
        adapter = new MyRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 点击添加数据，并且更新RecyclerView
     * @param view
     */
    public void btnAddItem(View view) {
        data.add(0, "Time:" + System.currentTimeMillis());

//        adapter.notifyItemInserted(0);
        adapter.notifyDataSetChanged();


    }

    public void btnRemoveItem(View view) {
        if (!data.isEmpty()){
            data.remove(0);
        }
        adapter.notifyItemRemoved(0);
    }

    /**
     * 第一步：
     * 继承RecyclerView。Adapter。用于显示数据
     * 需要定义并且使用 ViewHolder，必须要使用的
     */
    private class MyRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<String> strings;

        public MyRecyclerAdapter(List<String> strings) {
            this.strings = strings;
        }

        /**
         * 获取数据总数
         * @return
         */
        @Override
        public int getItemCount() {
            int ret = 0;
            if (strings != null) {
                ret = strings.size();
            }

            return ret;
        }

        /**
         * 根据ViewType来加载，创建特定的布局，将创建、加载好的布局交给ViewHolder
         * 创建新的ViewHolder对象，并且返回，因为RecyclerView,在ViewHolder为null的情况
         * 才会调用这个方法，如果ViewHolder,以及存在，那么不会进入这个方法，方法自身只负责holder
         * 的创建，不处理数据,不需要检查是否有复用，因为进入这个方法，必然没有复用
         * 因为RecyclerView通过Holder来检查复用
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder ret = null;

            View view =
                    LayoutInflater.from(Android5Activity.this).inflate(R.layout.item_recycler, parent, false);

            ret = new ViewHolder(view);

            return ret;
        }

        /**
         * 用于显示指定位置的数据的内容，通过ViewHolder来进行相应控件的更新
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(strings.get(position));

            int index = position % 3;

            int resId = 0;

            switch (index){
                case 0:
                    resId = R.mipmap.image1;
                    break;
                case 1:
                    resId = R.mipmap.image2;
                    break;
                case 2:
                    resId = R.mipmap.image3;
                    break;
            }
            holder.imageView.setImageResource(resId);
        }


    }

    /**
     * 第二步：
     * 创建自己的ViewHolder。必须继承RecyclerView.ViewHolder
     */
    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);

            //通常ViewHolder的构造，就是用于获取控件视图的
            imageView = (ImageView) itemView.findViewById(R.id.item_image);
            textView = (TextView) itemView.findViewById(R.id.item_text);

            //后续处理点击事件的操作，
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            int layoutPosition = getLayoutPosition();

            Context context = itemView.getContext();
//            Toast.makeText(context,"显示" + position,Toast.LENGTH_SHORT).show();
            Toast.makeText(context,"显示" + layoutPosition,Toast.LENGTH_SHORT).show();
        }
    }
}
