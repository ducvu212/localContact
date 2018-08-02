package com.example.ducvu212.localcontact;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {


    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;

    public ContactFragment() {
        // Required empty public constructor
    }

    public static ContactFragment newInstance() {

        Bundle args = new Bundle();

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    , WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewByIds(getActivity());
        initComponents();
    }

    private void findViewByIds(FragmentActivity activity) {
        mSearchView = activity.findViewById(R.id.search_view);
        mSearchAutoComplete =
                mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
    }

    private void initComponents() {
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.text_search_color));
    }
}
