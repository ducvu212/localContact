package com.example.ducvu212.localcontact.screens.contact.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ducvu212.localcontact.data.model.Contact;
import com.example.ducvu212.localcontact.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private List<Contact> mContacts;
    private ContactAdapter.OnClickListener mListener;

    public ContactAdapter(List<Contact> contacts, ContactAdapter.OnClickListener listener) {
        mContacts = contacts;
        mListener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);
        return new ContactViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int position) {
        Contact contact = mContacts.get(position);
        contactViewHolder.binData(contact);
    }

    @Override
    public int getItemCount() {
        return mContacts != null ? mContacts.size() : 0;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageViewPhoto;
        private ImageView mImageViewFav;
        private ImageView mImageViewCall;
        private ImageView mImageViewSms;
        private TextView mTextViewName;
        private TextView mTextViewPhoneNumber;
        private OnClickListener mListener;
        private Contact mContact;

        ContactViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            mListener = listener;
            initViews();
        }

        private void initViews() {
            mImageViewCall = itemView.findViewById(R.id.imageView_call);
            mImageViewFav = itemView.findViewById(R.id.imageView_fav);
            mImageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
            mImageViewSms = itemView.findViewById(R.id.imageView_sms);
            mTextViewName = itemView.findViewById(R.id.textView_name);
            mTextViewPhoneNumber = itemView.findViewById(R.id.textView_number);
            mImageViewFav.setOnClickListener(this);
            mImageViewCall.setOnClickListener(this);
            mImageViewSms.setOnClickListener(this);
        }

        private void binData(Contact contact) {
            if (contact == null) {
                return;
            }
            mContact = contact;
            updatetTextview(mTextViewName, contact.getName());
            updatetTextview(mTextViewPhoneNumber, contact.getPhone());
            updateFavoriteView(mImageViewFav, contact.getFavorite());
            updatePersonalImage(mImageViewPhoto, contact.getPhoto());
        }

        /**
         *
         * @param imageViewPhoto
         * @param photo
         */
        private void updatePersonalImage(ImageView imageViewPhoto, String photo) {
            Picasso.get().load(photo).into(imageViewPhoto);
        }

        /**
         * display data into textview
         *
         * @param textView : Current text view
         * @param data : current data
         */
        private void updatetTextview(TextView textView, String data) {
            if (data != null && !data.isEmpty()) {
                textView.setText(data);
            } else {
                textView.setText("");
            }
        }

        /**
         *
         * @param imageView
         * @param isFavorite
         */
        private void updateFavoriteView(ImageView imageView, int isFavorite) {
            imageView.setImageResource(
                    isFavorite != 0 ? R.drawable.ic_star : R.drawable.ic_un_star);
        }

        @Override
        public void onClick(View view) {
            if (mListener == null) {
                return;
            }
            switch (view.getId()) {
                case R.id.imageView_call:
                    mListener.onCallClick(view, mContact);
                    break;

                case R.id.imageView_sms:
                    mListener.onSmsClick(view, mContact);
                    break;

                case R.id.imageView_fav:
                    mListener.onUpdateFavoriteClick(view, mContact);
                    break;
            }
        }
    }

    public interface OnClickListener {
        void onUpdateFavoriteClick(View view, Contact contact);

        void onCallClick(View view, Contact contact);

        void onSmsClick(View view, Contact contact);
    }
}
