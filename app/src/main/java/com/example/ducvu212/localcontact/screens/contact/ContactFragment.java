package com.example.ducvu212.localcontact.screens.contact;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.example.ducvu212.localcontact.data.model.Contact;
import com.example.ducvu212.localcontact.data.source.ContactDataSource;
import com.example.ducvu212.localcontact.data.source.local.ContactListAction;
import com.example.ducvu212.localcontact.data.source.local.DatabaseHelper;
import com.example.ducvu212.localcontact.R;
import com.example.ducvu212.localcontact.screens.contact.adapter.ContactAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactAdapter.OnClickListener {
    /**
     * Request read contact permission
     */
    private static final int INIT_PERMISSION = 111;

    private List<Contact> mContacts;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private RecyclerView mRecyclerViewContact;
    private ContactDataSource mContactDataSource;

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
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        checkPermissions(getContext());
        findViews(getActivity());
        updateContact();
    }

    private void findViews(FragmentActivity activity) {
        mSearchView = activity.findViewById(R.id.search_view);
        mSearchAutoComplete =
                mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mRecyclerViewContact = activity.findViewById(R.id.recycle_contact);
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.text_search_color));
    }

    private void updateContact() {
        List<Contact> contacts = mContactDataSource.getContacts();
        updateRecyclerContact(contacts);
    }

    private void updateRecyclerContact(List<Contact> contacts) {
        ContactAdapter adapter = new ContactAdapter(contacts, this);
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewContact.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void checkPermissions(Context context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {
                    Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS
            }, INIT_PERMISSION);
        }
    }

    @Override
    public void onUpdateFavoriteClick(View view, Contact contact) {
        int fav = contact.getFavorite();
        fav = fav == 0 ? 1 : 0;
        contact.setFavorite(fav);
        if (mContactDataSource.updateContact(contact)){
            // TODO: 18/08/03
        }else {
            // TODO: 18/08/03
        }
    }

    @Override
    public void onCallClick(View view, Contact contact) {
        // TODO: 18/08/03
    }

    @Override
    public void onSmsClick(View view, Contact contact) {
        // TODO: 18/08/03
    }
}
