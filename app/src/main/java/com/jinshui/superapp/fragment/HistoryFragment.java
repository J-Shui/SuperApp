package com.jinshui.superapp.fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jinshui.superapp.R;
import com.jinshui.superapp.UpdateActivity;
import com.jinshui.superapp.providers.DataContract;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,AdapterView.OnItemClickListener{

    private static final int REQUESTCODE = 222;
    private SimpleCursorAdapter adapter;
    private int currentPosition;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView_history);

        if (listView != null) {
            adapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1,
                    null, //cursor
                    new String[]{DataContract.History.URL},
                    new int[]{android.R.id.text1},
                    SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
                    );
            listView.setAdapter(adapter);

            //loader的初始化
            Bundle args = new Bundle();

            args.putString("action","sayHi");
            getLoaderManager().initLoader(998, args, this);

            listView.setOnItemClickListener(this);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshHistory();
    }

    public void refreshHistory(){
        getLoaderManager().restartLoader(998,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader ret = null;

        switch (id){
            case 998:
                ret = new CursorLoader(getContext(),
                        DataContract.History.CONTENT_URI,
                        null,
                        null,
                        null,
                        null
                );
        }
        return ret;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        //使用changeCursor(cursor),因为旧的cursor在方法内部关闭了
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        currentPosition = position;

        Cursor cursor = adapter.getCursor();

        if (cursor.moveToPosition(position)){
            String url = cursor.getString(cursor.getColumnIndex(DataContract.History.URL));
            String rid = cursor.getString(cursor.getColumnIndex(DataContract.History._ID));
            if (url != null) {
                Intent intent = new Intent(getContext(),UpdateActivity.class);

                intent.putExtra("url", url);
                intent.putExtra("id",rid);

                startActivityForResult(intent,REQUESTCODE);
            }
        }
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUESTCODE && resultCode == Activity.RESULT_OK){
//            String extra = data.getStringExtra("url");
//
//            Cursor cursor = adapter.getCursor();
//
//            if (cursor.moveToPosition(currentPosition)){
//
//            }
//            refreshHistory();
//        }
//    }
}
