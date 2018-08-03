package com.example.ducvu212.localcontact;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements ContactListAction {

    private ArrayList<Contact> mContactArrayList;
    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private RecyclerView mRecyclerViewContact;
    private DatabaseHelper mDatabaseHelper;
    private static final int INIT_PERMISSION = 111;

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
        findViewByIds(getActivity());
        initComponents();
    }

    private void findViewByIds(FragmentActivity activity) {
        mSearchView = activity.findViewById(R.id.search_view);
        mSearchAutoComplete =
                mSearchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        mRecyclerViewContact = activity.findViewById(R.id.recycle_contact);
    }

    private void initComponents() {
        mSearchView.setIconified(false);
        mSearchView.setIconifiedByDefault(false);
        mSearchAutoComplete.setHintTextColor(getResources().getColor(R.color.hint_text_color));
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.text_search_color));
        mContactArrayList = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(getContext());
        if (mDatabaseHelper.isTableExists(true)
                && mDatabaseHelper.getAllContactDatabase().size() > 0) {
            mContactArrayList = mDatabaseHelper.getAllContactDatabase();
        } else {
            getAllContact();
            mContactArrayList = mDatabaseHelper.getAllContactDatabase();
        }
        ContactAdapter adapter = new ContactAdapter(this, getContext(), mDatabaseHelper);
        mRecyclerViewContact.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerViewContact.setAdapter(adapter);
    }

    private void getAllContact() {
        ContentResolver resolver = getContext().getContentResolver();
        String sort = ContactsContract.Contacts.DISPLAY_NAME;
        Cursor cursor =
                resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, sort);
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if (Integer.parseInt(cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCursor =
                            resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[] { id }, sort);
                    do {
                        String name = pCursor.getString(pCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String number = pCursor.getString(pCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String photo = pCursor.getString(
                                pCursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                        mDatabaseHelper.addContact(name, number, photo, 0);
                        break;
                    } while (pCursor.moveToNext());
                    pCursor.close();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public Contact getItem(int position) {
        return mContactArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mContactArrayList == null ? 0 : mContactArrayList.size();
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
}
