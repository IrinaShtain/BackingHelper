package com.shtainyky.backinghelper;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shtainyky.backinghelper.model.ThemeItem;
import com.shtainyky.backinghelper.utils.Parsing;

import java.util.ArrayList;
import java.util.List;

public class ListThemesFragment extends Fragment {
    private int position;

    private RecyclerView mRecyclerView;
    private TextView mTitleForum;

    private List<ThemeItem> mthemeItems = new ArrayList<>();


    public ListThemesFragment(int position) {
        this.position = position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        refresh();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_themes, container, false);

        mTitleForum = (TextView) view.findViewById(R.id.title_site);
        initTitleForum();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupAdapter();
        return view;

    }

    public void refresh() {
        new ParseTitles().execute(position);
    }

    private void initTitleForum() {
        switch (position) {
            case 0:
                mTitleForum.setText(R.string.title);
                break;
            case 1:
                mTitleForum.setText(R.string.title_1);
                break;
            case 2:
                mTitleForum.setText(R.string.title_2);
                break;
            case 3:
                mTitleForum.setText(R.string.title_3);
                break;
            case 4:
                mTitleForum.setText(R.string.title_4);
                break;
            case 5:
                mTitleForum.setText(R.string.title_5);
                break;
        }
    }

    private void setupAdapter() {
        ThemeAdapter adapter = new ThemeAdapter(mthemeItems);
        if (isAdded()) {
            mRecyclerView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();

    }


    private class ParseTitles extends AsyncTask<Integer, Void, List<ThemeItem>> {

        @Override
        protected void onPreExecute() {
            if (mRecyclerView != null)
                mRecyclerView.invalidate();
        }

        @Override
        protected List<ThemeItem> doInBackground(Integer... integers) {
            return Parsing.parsingResult(integers[0]);
        }

        @Override
        protected void onPostExecute(List<ThemeItem> resultList) {
            mthemeItems = resultList;
            new ThemeAdapter(mthemeItems);
            setupAdapter();
        }
    }

    private class ThemeHolder extends RecyclerView.ViewHolder implements View.OnClickListener

    {
        private TextView mTitleTextView;
        private TextView mHrefTextView;
        private TextView mViewsTextView;
        String url1;

        public ThemeHolder(View itemView) {
            super(itemView);
            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.user_item);
            mHrefTextView = (TextView)
                    itemView.findViewById(R.id.title_item);
            mViewsTextView = (TextView)
                    itemView.findViewById(R.id.views_item);
            mHrefTextView.setOnClickListener(this);
        }

        public void bindItem(ThemeItem item) {
            mTitleTextView.setText(item.getUser());
            mHrefTextView.setText(item.getTitle());
            String text = item.getViews() + " "
                    + getResources().getString(R.string.new_theme_text2);
            mViewsTextView.setText(text);
            url1 = item.getLink();
        }

        @Override
        public void onClick(View view) {
            Uri address = Uri.parse(url1);
            Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
            startActivity(openlinkIntent);
        }
    }

    private class ThemeAdapter extends RecyclerView.Adapter<ThemeHolder> {
        private List<ThemeItem> mListThemes;


        public ThemeAdapter(List<ThemeItem> listThemes) {
            mListThemes = listThemes;
        }

        @Override
        public ThemeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item, parent, false);
            return new ThemeHolder(view);

        }

        @Override
        public void onBindViewHolder(ThemeHolder holder, int position) {
            ThemeItem oneTheme = mListThemes.get(position);
            holder.bindItem(oneTheme);

        }

        @Override
        public int getItemCount() {
            return mListThemes.size();
        }

    }

}
