package com.example.infinitbank;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.example.infinitbank.adapter.HistoryAdapter;
import com.example.infinitbank.fragment.BaseFragment;
import com.example.infinitbank.model.HistoryItem;
import com.example.infinitbank.service.OnServiceResponseHandler;
import com.example.infinitbank.service.ServiceEndpoint;
import com.example.infinitbank.service.ServiceUtil;
import com.example.infinitbank.util.AppNavigation;
import com.example.infinitbank.util.EndlessScrollListener;
import com.example.infinitbank.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment {



    // History-activity summarized data
    @Bind(R.id.list_history_activity)
    ListView mListView;
    @Bind(R.id.layout_refresh)
    SwipeRefreshLayout mRefreshLayout;

    private List<HistoryItem> mData;
    private HistoryAdapter mAdapter;
    private int mStart = 0;
    private boolean mLoadMore = false;



    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("kyhoolee", "init home fragment");
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, mRootView);


        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mData.clear();
                mStart = 0;
                mLoadMore = false;
                loadItems();
            }
        });

        mData = new ArrayList<HistoryItem>();
        mAdapter = new HistoryAdapter(this.getContext(), R.layout.adapter_history, mData);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HistoryItem tip = mAdapter.getItem(i);
                AppNavigation.viewHistoryDetail(mActivity, tip);
            }
        });

        mListView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                if (mLoadMore) {
                    mStart = mData.size() + 1;
                    loadItems();
                    return true;
                }
                return false;
            }
        });

        //loadItems();
        initData();

        return mRootView;
    }

    private void initData() {
        Log.e("kyhoolee", "init data in history list");
        for(int i = 0 ; i < 5 ; i ++)
            mData.add(new HistoryItem());
        mAdapter.notifyDataSetChanged();
        mLoadMore = false;
        mRefreshLayout.setRefreshing(false);
    }

    private void loadItems() {
        ServiceUtil.get(mActivity, ServiceEndpoint.GET_HEALTH_TIPS_BY_TOPIC_ENDPOINT
                .replace("{token}", mApp.getProfileToken())
                .replace("{start}", String.valueOf(mStart))
                , new OnServiceResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Type type = new TypeToken<List<HistoryItem>>(){}.getType();
                try {
                    List<HistoryItem> data = new Gson().fromJson(response.getJSONObject("data").getJSONArray("tips").toString(), type);

                    mData.addAll(data);
                    mAdapter.notifyDataSetChanged();

                    int more = response.getJSONObject("data").getInt("more");
                    if (more == 1)
                        mLoadMore = true;
                    else
                        mLoadMore = false;
                } catch (JSONException e) {
                    mRefreshLayout.setRefreshing(false);
                    e.printStackTrace();
                }

                mRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode) {
                ToastUtil.showError(HomeFragment.this.getActivity());
                mRefreshLayout.setRefreshing(false);
            }
        });
    }



//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if (resultCode == mActivity.RESULT_OK) {
                mData.clear();
                mStart = 0;
                mLoadMore = false;
                loadItems();
            }
        }
    }

    @Override
    public void scrollToTop() {
        super.scrollToTop();
        mListView.smoothScrollToPosition(0);
    }
}
