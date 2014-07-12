package it.open.androidlab.db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * 
 * @author c.luchessa
 *
 * @param <T>
 */

// TODO: serve rendere findBy e removeBy multivalore per le performance, ma ora non c'è tempo
public abstract class AbstractDAO<T> {
	
	public static final int ERR_CODE = -1;

	protected static final String CREATE_TABLE = "CREATE TABLE ";

	protected static final String DROP_TABLE = "DROP TABLE IF EXISTS ";

	protected SQLiteOpenHelper dbHelper;


	public AbstractDAO( SQLiteOpenHelper dbHelper ) {
		this.dbHelper = dbHelper;
	}

	/**
	 * 
	 * @return
	 */
	public int getCount() {
		Cursor c = null;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getReadableDatabase();
			String query = "SELECT COUNT(*) FROM "+this.getTableName();
			c = db.rawQuery(query, null);
			if (c.moveToFirst()) {
				return c.getInt(0);
			}
			return 0;
		}
		finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean exists(long id) {
		Cursor c = null;
		SQLiteDatabase db = null;
		try {
			db = dbHelper.getReadableDatabase();
			String query = "SELECT COUNT(*) FROM "+this.getTableName()+" WHERE "+BaseColumns._ID+" = ?";
			c = db.rawQuery(query, new String[] {id+""});
			if (c.moveToFirst()) {
				return c.getInt(0) > 0;
			}
			return false;
		}
		finally {
			if (c != null) {
				c.close();
			}
			if (db != null) {
				db.close();
			}
		}
	}

	/**
	 * 
	 * @param item
	 * @return
	 */
	public long insertOrUpdate( T item ) {
		return this.insertOrUpdate(item, false);
	}
	
	/**
	 * 
	 * @param itemList
	 * @return
	 */
	public List<Long> insertOrUpdate( List<T> itemList ) {
		return this.insertOrUpdate(itemList, false);
	}

	/**
	 * 
	 * @param item
	 * @param cascade
	 * @return
	 */
	public long insertOrUpdate( T item, boolean cascade ) {
		return this.insertOrUpdate(null, item, cascade);
	}
	
	
	/**
	 * 
	 * @param itemList
	 * @param cascade
	 * @return
	 */
	public List<Long> insertOrUpdate( List<T> itemList, boolean cascade ) {
		List<Long> itemRowIdList = new LinkedList<Long>();
		
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		for ( T item : itemList ) {
			itemRowIdList.add(this.insertOrUpdate(db, item, cascade));
		}
		
		db.close();
		
		return itemRowIdList;
	}

	/**
	 * 
	 * @param db
	 * @param item
	 * @param cascade
	 * @return
	 */
	public long insertOrUpdate(SQLiteDatabase db, T item, boolean cascade) {
		boolean isDbNotInjected = db == null; 

		if ( isDbNotInjected ) {
			db = dbHelper.getWritableDatabase();
		}

		long itemRowId = db.insertWithOnConflict( // insert or update
				this.getTableName()
				, null
				, this.objectToContentValues(item)
				, SQLiteDatabase.CONFLICT_REPLACE
				);

		if ( cascade && itemRowId != ERR_CODE ) {
			this.cascade(db, item, itemRowId);
		}

		if ( isDbNotInjected ) {
			db.close();
		}

		return itemRowId;
	}
	
	/**
	 * 
	 */
	public void removeAll() {
		this.removeAll(false);
	}
	
	/**
	 * 
	 * @param db
	 * @param propagate
	 * @param partial
	 */
	public void removeAll(boolean propagate) {
		this.removeAll(null, propagate);
	}
	
	/**
	 * 
	 * @param db
	 * @param propagate
	 * @param partial
	 */
	public void removeAll(SQLiteDatabase db, boolean propagate) {
		this.removeBy(db, propagate, "1", "1"); // WHERE 1 = 1
	}

	/**
	 * 
	 * @param propagate
	 * @param columnName
	 * @param columnValue
	 */
	public void removeBy(boolean propagate, String columnName, String columnValue) {
		this.removeBy(null, propagate, columnName, columnValue);
	}
	
	/**
	 * 
	 * @param db
	 * @param propagate
	 * @param columnName
	 * @param columnValue
	 */
	public void removeBy(SQLiteDatabase db, boolean propagate, String columnName, String columnValue) {
		boolean isDbNotInjected = db == null; 

		if ( isDbNotInjected ) {
			db = dbHelper.getWritableDatabase();
		}
		
		if ( propagate ) {
			this.propagateDeletion(db, this.findBy(db, false, columnName, columnValue));
		}
		
		db.delete(this.getTableName(), columnName+"=?", new String[]{columnValue});
		
		db.execSQL(	"DELETE " +
					"FROM '"+this.getTableName()+"' "+
					"WHERE "+columnName+" = "+columnValue
					);
		
		if ( isDbNotInjected ) {
			db.close();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<T> findAll() {
		return this.findAll(null, false);
	}

	/**
	 * 
	 * @param populate
	 * @return
	 */
	public List<T> findAll( boolean populate ) {
		return this.findAll(null, populate);
	}

	/**
	 * 
	 * @param db
	 * @param populate
	 * @return
	 */
	public List<T> findAll( SQLiteDatabase db, boolean populate ) { 
		return this.findBy(db, populate, "1", "1"); // WHERE 1 = 1
	}

	/**
	 * 
	 * @param populate
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	public List<T> findBy( boolean populate, String columnName, String columnValue ) {
		return this.findBy(null, populate, columnName, columnValue);
	}

	/**
	 * 
	 * @param db
	 * @param populate
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	public List<T> findBy(SQLiteDatabase db, boolean populate, String columnName, String columnValue) {

		boolean isDbNotInjected = db == null; 

		if ( isDbNotInjected ) {
			db = dbHelper.getWritableDatabase();
		}

		Cursor c = db.rawQuery(	"SELECT * " +
				"FROM '"+this.getTableName()+"' "+
				"WHERE "+columnName+" = "+columnValue
				, null);

		List<T> resultList = new ArrayList<T>(c.getCount());
		
		while ( c.moveToNext() ) {
			resultList.add(this.rowToObject(c));
		}
		
		c.close(); // release cursor

		if ( populate ) {
			for (T item : resultList) {
				this.populate(db, item);
			}
		}

		if ( isDbNotInjected ) {
			db.close(); // release db
		}

		return resultList;
	}

	/**
	 * 
	 * @param populate
	 * @param idValue
	 * @return
	 */
	public T findById( boolean populate, String idValue ) {
		return this.findById(null, populate, idValue);
	}

	/**
	 * 
	 * @param db
	 * @param populate
	 * @param idValue
	 * @return
	 */
	public T findById( SQLiteDatabase db, boolean populate, String idValue ) {
		List<T> resList = this.findBy(db, populate, BaseColumns._ID, idValue);
		return resList == null || resList.isEmpty() ? null : resList.get(0);
	}


	/*
	 * Facility methods
	 */

	protected abstract ContentValues objectToContentValues( T item );

	/**
	 * 
	 * @param c
	 * @return
	 */
	protected abstract T rowToObject( Cursor c );

	/**
	 * 
	 * @return
	 */
	protected abstract String getTableName();

	/**
	 * Populate retrieving
	 * @param item
	 */
	protected abstract void populate(SQLiteDatabase db, T item);

	/**
	 * Cascade insertion
	 * @param item
	 */
	protected abstract void cascade(SQLiteDatabase db, T item, long itemRowId);
	
	/**
	 * Propagate deletions
	 * @param db
	 */
	protected abstract void propagateDeletion(SQLiteDatabase db, List<T> itemList);
	
}
