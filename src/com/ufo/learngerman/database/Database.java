package com.ufo.learngerman.database;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.google.android.gms.nearby.sharing.SharedContent;
import com.ufo.learngerman.R;
import com.ufo.learngerman.adapter.CategoryItem;
import com.ufo.learngerman.adapter.PhraseItem;
import com.ufo.learngerman.utils.DecodeUtil;
import com.ufo.learngerman.utils.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

public class Database {

	public String PACKAGE = "com.ufo.learnjapanese";

	private SQLiteOpenHelper mOpenHelper = null;
	private SQLiteDatabase mDatabase = null;
	private Context mContext = null;
	public String folderPath = "data/data/" + PACKAGE + "/databases";
	public static final String KEY_DB_VER = "db_version";
	String filePath = "";
	String dbName = "";
	String categoryColumnName;
	String phraseColumnName;

	public int dbVersion;

	private static Database mainDatabase;

	public static Database newInstance(Context context, String dbName) {
		if (mainDatabase == null) {
			mainDatabase = new Database(context, dbName);
		}

		return mainDatabase;
	}

	private Database(Context context, String DB_NAME) {
		// System.out.println("Database.Database()");
		mContext = context;
		this.dbName = DB_NAME;
		this.PACKAGE = mContext.getPackageName();
		this.folderPath = "data/data/" + PACKAGE + "/databases";
		this.filePath = folderPath + "/" + dbName;
		this.categoryColumnName = mContext.getResources().getString(R.string.category_name_column);
		this.phraseColumnName = mContext.getResources().getString(R.string.phrase_name_column);

		if (dbName == Utils.PHRASE_DATABASE_NAME) {
			this.dbVersion = Utils.PHRASE_DATABASE_VERSION;
		} else if (dbName == Utils.GRAMMAR_DATABASE_NAME) {
			this.dbVersion = Utils.GRAMMAR_DATABASE_VERSION;
		}
		copyDBFromAsset(mContext, dbName);
		mOpenHelper = new SQLiteOpenHelper(mContext, dbName, null, dbVersion) {

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// System.out.println("Database.Database(...).new SQLiteOpenHelper() {...}.onUpgrade()");
				// db.execSQL("DROP TABLE IF EXISTS " + dbName);
				// new File(filePath).delete();
				// mContext.getDatabasePath(dbName).delete();
				// onCreate(db);

			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				// System.out.println("Database.Database(...).new SQLiteOpenHelper() {...}.onCreate()");

			}
		};
		open();

	}

	public void copyDBFromAsset(Context mContext, String dbName) {
		// System.out.println("Database.copyDBFromAsset()");

		// if not exist, copy from asset
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

		if (isCheckDB(dbName)) {// if exist, check version
			int currentDBVersion = prefs.getInt(KEY_DB_VER, 1);
			if (currentDBVersion != dbVersion) {
				File dbFile = new File(filePath);
				if (!dbFile.delete()) {
					System.out.println("eo delete dc db");
				}
			}
		}

		if (!isCheckDB(dbName)) {
			// System.out.println("db is not exits");
			try {

				// String filePath = folderPath + "/" + dbName;

				File folder = new File(folderPath);
				if (!folder.exists())
					folder.mkdirs();

				AssetManager manager = mContext.getAssets();
				File f = new File(filePath);
				FileOutputStream outStream = new FileOutputStream(f);
				InputStream inStream = manager.open(dbName);
				BufferedInputStream mBufferedInputStream = new BufferedInputStream(inStream);

				byte[] buffer = new byte[1024];
				int read = -1;
				while ((read = mBufferedInputStream.read(buffer, 0, 1024)) != -1) {
					// System.out.println("Database.copyDBFromAsset() copy buffer");
					outStream.write(buffer, 0, read);
				}
				outStream.flush();
				outStream.close();
				inStream.close();
				mBufferedInputStream.close();
				// save dbversion in preference
				SharedPreferences.Editor editor = prefs.edit();
				editor.putInt(KEY_DB_VER, dbVersion);
				editor.commit();
			} catch (FileNotFoundException e) {
				// System.out.println("file not found");
				e.printStackTrace();
			} catch (IOException e) {
				// System.out.println("IO exception");
				e.printStackTrace();
			}
		}
		// copy from asset
	}

	private boolean isCheckDB(String dbName) {
		// String folderPath = "data/data/"+PACKAGE+"/database";
		String filePath = "data/data/" + PACKAGE + "/databases/" + dbName;
		File f = new File(filePath);
		if (f.exists()) {
			// System.out.println("Database.isCheckDB() db exist");
			return true;
		} else
			return false;
	}

	private void open() {

		mDatabase = mOpenHelper.getWritableDatabase();
	}
	
	public void close() {
		if (mDatabase != null && mDatabase.isOpen())
			mDatabase.close();
		mOpenHelper.close();
		mainDatabase = null;
		
	}

	public Cursor getAll(String tableName) {
		String query = "Select * from " + tableName;
		return mDatabase.rawQuery(query, null);
	}

	public Cursor getListGrammar() {
		String query = "select _id, title from " + Utils.GRAMMAR_TABLE;
		return mDatabase.rawQuery(query, null);
	}

	public String getGrammarContent(int grammarId) {
		String content = mContext.getResources().getString(R.string.content_not_found);
		String query = "select content from " + Utils.GRAMMAR_TABLE + " where _id = " + grammarId;
		// System.out.println("Database.getGrammarContent() "+query);
		Cursor mCursor = mDatabase.rawQuery(query, null);
		if (mCursor.moveToFirst()) {
			content = mCursor.getString(mCursor.getColumnIndex(Utils.GRAMMAR_COLUMN_CONTENT));
			// DecodeUtil decodeUtil = new
			// DecodeUtil(DecodeUtil.keydecode_grammar);
			content = DecodeUtil.decodeGrammar(content);
		}
		return content;
	}

	/*
	 * public Cursor getLesson(int type) { String query =
	 * "SELECT * FROM BaiHoc where type = " + type; return
	 * mDatabase.rawQuery(query, null); }
	 */

	public Cursor getListPhrases(int catId) {
		// System.out.println("Database.getListPhrases() dbversion = "+mDatabase.getVersion()
		// );
		Cursor mCursor;
		String query = "SELECT * FROM " + Utils.PHRASE_TABLE + " where category_id = " + catId;
		mCursor = mDatabase.rawQuery(query, null);
		return mCursor;
	}

	public ArrayList<PhraseItem> getListPhraseItem(int catId) {
		ArrayList<PhraseItem> mList = new ArrayList<PhraseItem>();
		Cursor mCursor;
		String query = "SELECT * FROM " + Utils.PHRASE_TABLE + " where category_id = " + catId;
		mCursor = mDatabase.rawQuery(query, null);
		if (mCursor != null && mCursor.getCount() > 0 && mCursor.moveToFirst())
			do {
				PhraseItem localPhraseItem = new PhraseItem();
				localPhraseItem.id = mCursor.getInt(mCursor.getColumnIndex("_id"));
				// localPhraseItem.txtEnglish =
				// mCursor.getString(mCursor.getColumnIndex("english"));
				// localPhraseItem.txtPinyin =
				// mCursor.getString(mCursor.getColumnIndex("pinyin"));
				localPhraseItem.txtKorean = DecodeUtil.decodeFrance(mCursor.getString(mCursor.getColumnIndex(Utils.LANGUAGE_COLLUNM)));
				localPhraseItem.favorite = mCursor.getInt(mCursor.getColumnIndex("favorite"));
				localPhraseItem.voice = mCursor.getString(mCursor.getColumnIndex("voice"));
				// String status =
				// mCursor.getString(mCursor.getColumnIndex("status"));
				localPhraseItem.txtVietnamese = mCursor.getString(mCursor.getColumnIndex(this.phraseColumnName));
				// localPhraseItem.txtChinese =
				// mCursor.getString(mCursor.getColumnIndex("chinese"));
				mList.add(localPhraseItem);
			} while (mCursor.moveToNext());

		return mList;

	}

	public Cursor getListPhraseFavorite() {
		String query = "SELECT * FROM " + Utils.PHRASE_TABLE + " where favorite = 1";
		return mDatabase.rawQuery(query, null);
	}

	public ArrayList<PhraseItem> getListPhraseFavorite2() {
		ArrayList<PhraseItem> mList = new ArrayList<PhraseItem>();
		String query = "SELECT * FROM " + Utils.PHRASE_TABLE + " where favorite = 1";
		Cursor mCursor = mDatabase.rawQuery(query, null);
		if (mCursor != null && mCursor.getCount() > 0 && mCursor.moveToFirst())
			do {
				PhraseItem localPhraseItem = new PhraseItem();
				localPhraseItem.id = mCursor.getInt(mCursor.getColumnIndex("_id"));
				// localPhraseItem.txtEnglish =
				// mCursor.getString(mCursor.getColumnIndex("english"));
				// localPhraseItem.txtPinyin =
				// mCursor.getString(mCursor.getColumnIndex("pinyin"));
				localPhraseItem.txtKorean = DecodeUtil.decodeFrance(mCursor.getString(mCursor.getColumnIndex(Utils.LANGUAGE_COLLUNM)));
				localPhraseItem.favorite = mCursor.getInt(mCursor.getColumnIndex("favorite"));
				localPhraseItem.voice = mCursor.getString(mCursor.getColumnIndex("voice"));
				// String status =
				// mCursor.getString(mCursor.getColumnIndex("status"));
				localPhraseItem.txtVietnamese = mCursor.getString(mCursor.getColumnIndex(this.phraseColumnName));
				// localPhraseItem.txtChinese =
				// mCursor.getString(mCursor.getColumnIndex("chinese"));
				mList.add(localPhraseItem);
			} while (mCursor.moveToNext());
		return mList;
	}

	public Cursor searchPhrase(String key) {
		String query;

		// no need for learn japanese
		/*
		 * if(Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET)) query =
		 * "SELECT * FROM "+Utils.PHRASE_TABLE+" WHERE "
		 * +this.phraseColumnName+" LIKE '%"+key+"%'" +
		 * " OR "+Utils.LANGUAGE_COLLUNM+" LIKE '%"+key+"%'" +
		 * " OR search LIKE '%"+key+"%'" +
		 * " OR pinyin LIKE '%"+key+"%' limit 30"; else
		 */
		query = "SELECT * FROM " + Utils.PHRASE_TABLE + " WHERE " + this.phraseColumnName + " LIKE '%" + key + "%'" + " OR " + Utils.LANGUAGE_COLLUNM
				+ " LIKE '%" + key + "%'" + "limit 30";

		// System.out.println("Database.searchPhrase() query = "+query);
		return mDatabase.rawQuery(query, null);
	}

	public ArrayList<PhraseItem> searchPhrase2(String key) {

		ArrayList<PhraseItem> mList = new ArrayList<PhraseItem>();
		String query;
		if (Utils.DEVICE_LANGUAGE.equalsIgnoreCase(Utils.TIENGVIET))
			query = "SELECT * FROM " + Utils.PHRASE_TABLE + " WHERE " + this.phraseColumnName + " LIKE '%" + key + "%'" + " OR " + Utils.LANGUAGE_COLLUNM
					+ " LIKE '%" + key + "%'" + " OR search LIKE '%" + key + "%'" + " limit 30";
		else
			query = "SELECT * FROM " + Utils.PHRASE_TABLE + " WHERE " + this.phraseColumnName + " LIKE '%" + key + "%'" + " OR " + Utils.LANGUAGE_COLLUNM
					+ " LIKE '%" + key + "%'" + " limit 30";

		Cursor mCursor = mDatabase.rawQuery(query, null);
		if (mCursor != null && mCursor.getCount() > 0 && mCursor.moveToFirst())
			do {
				PhraseItem localPhraseItem = new PhraseItem();
				localPhraseItem.id = mCursor.getInt(mCursor.getColumnIndex("_id"));
				// localPhraseItem.txtEnglish =
				// mCursor.getString(mCursor.getColumnIndex("english"));
				// localPhraseItem.txtPinyin =
				// mCursor.getString(mCursor.getColumnIndex("pinyin"));
				localPhraseItem.txtKorean = DecodeUtil.decodeFrance(mCursor.getString(mCursor.getColumnIndex(Utils.LANGUAGE_COLLUNM)));
				localPhraseItem.favorite = mCursor.getInt(mCursor.getColumnIndex("favorite"));
				localPhraseItem.voice = mCursor.getString(mCursor.getColumnIndex("voice"));
				// String status =
				// mCursor.getString(mCursor.getColumnIndex("status"));
				localPhraseItem.txtVietnamese = mCursor.getString(mCursor.getColumnIndex(this.phraseColumnName));
				// localPhraseItem.txtChinese =
				// mCursor.getString(mCursor.getColumnIndex("chinese"));
				mList.add(localPhraseItem);
			} while (mCursor.moveToNext());
		return mList;
	}

	public void updateFavorite(int phraseId, int status) {

		/*
		 * String query =
		 * "UPDATE  "+Utils.PHRASE_TABLE+" SET favorite = "+status
		 * +" WHERE _id = "+phraseId; System.out.println(query); Cursor cursor =
		 * mDatabase.rawQuery(query, null); cursor.moveToFirst();
		 * cursor.close();
		 */

		ContentValues cv = new ContentValues();
		cv.put("favorite", status);
		String selection = "_id = " + phraseId;
		String[] selectionArgs = null;
		mDatabase.update(Utils.PHRASE_TABLE, cv, selection, selectionArgs);
	}

	/*
	 * public ArrayList<String> getListCategoryPhrase(){ ArrayList<String>
	 * listCategory = new ArrayList<String>(); String query =
	 * "select * from category"; Cursor mCursor = mDatabase.rawQuery(query,
	 * null); if(mCursor != null && mCursor.getCount() >0 &&
	 * mCursor.moveToFirst() ){ do{
	 * listCategory.add(mCursor.getString(mCursor.getColumnIndex
	 * (this.categoryColumnName))); }while(mCursor.moveToNext()); }
	 * 
	 * return listCategory; }
	 */

	public ArrayList<CategoryItem> getListCategoryItems() {
		ArrayList<CategoryItem> listCategoryItems = new ArrayList<CategoryItem>();
		String query = "select * from category where _id < 100";
		Cursor mCursor = mDatabase.rawQuery(query, null);
		if (mCursor != null && mCursor.getCount() > 0 && mCursor.moveToFirst()) {
			do {
				String catName = mCursor.getString(mCursor.getColumnIndex(this.categoryColumnName));
				int catId = mCursor.getInt(mCursor.getColumnIndex("_id"));
				String thumbnail = mCursor.getString(mCursor.getColumnIndex("thumbnail"));
				/*
				 * int weight =
				 * mCursor.getInt(mCursor.getColumnIndex("weight"));
				 */
				CategoryItem item = new CategoryItem(catName, catId, thumbnail);
				listCategoryItems.add(item);
			} while (mCursor.moveToNext());
		}

		CategoryItem item = new CategoryItem("See More", 100, "ic_seemore");
		listCategoryItems.add(item);

		return listCategoryItems;
	}

	public int[] getAllCatId() {
		int[] listId = new int[1000];
		String query = "select distinct category_id from phrase";
		Cursor mCursor = mDatabase.rawQuery(query, null);
		mCursor.moveToFirst();
		int i = 0;
		do {
			int id = mCursor.getInt(mCursor.getColumnIndex("category_id"));
			// String name =
			// mCursor.getString(mCursor.getColumnIndex("english"));
			// System.out.println("Database.getAllCatId() catid = " + id);
			listId[i++] = id;
		} while (mCursor.moveToNext());

		return listId;
	}

}
