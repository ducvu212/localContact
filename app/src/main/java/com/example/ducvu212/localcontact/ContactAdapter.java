package com.example.ducvu212.localcontact;

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
import com.squareup.picasso.Picasso;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private ContactListAction iAction;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;

    ContactAdapter(ContactListAction iAction, Context context, DatabaseHelper databaseHelper) {
        this.iAction = iAction;
        mContext = context;
        mDatabaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.contact_item, viewGroup, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        Contact contact = iAction.getItem(i);
        contactViewHolder.binData(mContext, contact.getName(), contact.getPhone(),
                contact.getPhoto(), contact.getFavorite(), contact, i);
    }

    @Override
    public int getItemCount() {
        return iAction.getCount();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImageViewPhoto;
        private ImageView mImageViewFav;
        private ImageView mImageViewCall;
        private ImageView mImageViewSms;
        private TextView mTextViewName;
        private TextView mTextViewSdt;
        private Context mContext;
        private int mFav;
        private Contact mContact;
        private int mPosition;

        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            findViewByIds();
        }

        private void findViewByIds() {
            mImageViewCall = itemView.findViewById(R.id.imageView_call);
            mImageViewFav = itemView.findViewById(R.id.imageView_fav);
            mImageViewPhoto = itemView.findViewById(R.id.imageViewPhoto);
            mImageViewSms = itemView.findViewById(R.id.imageView_sms);
            mTextViewName = itemView.findViewById(R.id.textView_name);
            mTextViewSdt = itemView.findViewById(R.id.textView_number);
        }

        private void binData(Context context, String name, String sdt, String photo, int fav,
                Contact contact, int pos) {
            mContext = context;
            mFav = fav;
            mContact = contact;
            mPosition = pos + 1;
            checkData(name, mTextViewName);
            checkData(sdt, mTextViewSdt);
            if (fav == 1) {
                mImageViewFav.setImageResource(R.drawable.ic_star);
            } else {
                mImageViewFav.setImageResource(R.drawable.ic_un_star);
            }
            if (photo != null) {
                Picasso.get().load(photo).into(mImageViewPhoto);
            }
            mImageViewFav.setOnClickListener(this);
            mImageViewCall.setOnClickListener(this);
            mImageViewSms.setOnClickListener(this);
        }

        private void checkData(String data, TextView textView){
            if (data.equals("")) {
                textView.setText("");
            } else {
                textView.setText(data);
            }
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.imageView_call:
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + mTextViewSdt.getText()));
                    mContext.startActivity(intent);
                    break;

                case R.id.imageView_sms:
                    composeSmsMessage(mContext);
                    break;

                case R.id.imageView_fav:
                    if (mFav == 1) {
                        mImageViewFav.setImageResource(R.drawable.ic_un_star);
                        mDatabaseHelper.update(0, mPosition);
                        mContact.setFavorite(0);
                    } else {
                        mImageViewFav.setImageResource(R.drawable.ic_star);
                        mDatabaseHelper.update(1, mPosition);
                        mContact.setFavorite(1);
                    }

                default:
            }
        }

        void composeSmsMessage(Context context) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
            context.startActivity(intent);
        }
    }
}
