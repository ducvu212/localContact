package com.example.ducvu212.localcontact.data.source.local;

public class DatabaseConstants {

    public static final String DATABASE_NAME = "contact_database";
    public static int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE_NAME = "contact";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String PHOTO = "photo";
    public static final String PHONE_NUMBER = "phone";
    public static final String FAVORITE = "favorite";
    public static final String CREATE_TABLE = "CREATE TABLE "
            + DATABASE_TABLE_NAME
            + "( "
            + ID
            + " integer primary key, "
            + NAME
            + " TEXT, "
            + PHONE_NUMBER
            + " TEXT, "
            + PHOTO
            + " TEXT, "
            + FAVORITE
            + " integer)";
    public static final String DROP_TABLE = "DROP TABLE IF NOT EXITS " + DATABASE_TABLE_NAME;
    public static final String QUERY_ALL_RECODRD = "SELECT * FROM " + DATABASE_TABLE_NAME;
    public static final String CHECK_TABLE =
            "SELECT DISTINCT tbl_name from sqlite_master where tbl_name = '"
                    + DATABASE_TABLE_NAME
                    + "'";
}
