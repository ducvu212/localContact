package com.example.ducvu212.localcontact.data.source.local;

import com.example.ducvu212.localcontact.data.model.Contact;

public interface ContactListAction {

    Contact getItem(int position);

    int getCount();
}
