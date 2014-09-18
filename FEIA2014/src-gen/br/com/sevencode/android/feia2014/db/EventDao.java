package br.com.sevencode.android.feia2014.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import br.com.sevencode.android.feia2014.db.Event;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table EVENT.
*/
public class EventDao extends AbstractDao<Event, Long> {

    public static final String TABLENAME = "EVENT";

    /**
     * Properties of entity Event.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property EventId = new Property(1, Integer.class, "eventId", false, "EVENT_ID");
        public final static Property Duration = new Property(2, Integer.class, "duration", false, "DURATION");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Author = new Property(4, String.class, "author", false, "AUTHOR");
        public final static Property Type = new Property(5, Integer.class, "type", false, "TYPE");
        public final static Property Category = new Property(6, Integer.class, "category", false, "CATEGORY");
        public final static Property Date = new Property(7, java.util.Date.class, "date", false, "DATE");
        public final static Property DateArray = new Property(8, String.class, "dateArray", false, "DATE_ARRAY");
        public final static Property PlaceData = new Property(9, String.class, "placeData", false, "PLACE_DATA");
        public final static Property Description = new Property(10, String.class, "description", false, "DESCRIPTION");
    };


    public EventDao(DaoConfig config) {
        super(config);
    }
    
    public EventDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'EVENT' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'EVENT_ID' INTEGER," + // 1: eventId
                "'DURATION' INTEGER," + // 2: duration
                "'NAME' TEXT," + // 3: name
                "'AUTHOR' TEXT," + // 4: author
                "'TYPE' INTEGER," + // 5: type
                "'CATEGORY' INTEGER," + // 6: category
                "'DATE' INTEGER," + // 7: date
                "'DATE_ARRAY' TEXT," + // 8: dateArray
                "'PLACE_DATA' TEXT," + // 9: placeData
                "'DESCRIPTION' TEXT);"); // 10: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'EVENT'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Event entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Integer eventId = entity.getEventId();
        if (eventId != null) {
            stmt.bindLong(2, eventId);
        }
 
        Integer duration = entity.getDuration();
        if (duration != null) {
            stmt.bindLong(3, duration);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String author = entity.getAuthor();
        if (author != null) {
            stmt.bindString(5, author);
        }
 
        Integer type = entity.getType();
        if (type != null) {
            stmt.bindLong(6, type);
        }
 
        Integer category = entity.getCategory();
        if (category != null) {
            stmt.bindLong(7, category);
        }
 
        java.util.Date date = entity.getDate();
        if (date != null) {
            stmt.bindLong(8, date.getTime());
        }
 
        String dateArray = entity.getDateArray();
        if (dateArray != null) {
            stmt.bindString(9, dateArray);
        }
 
        String placeData = entity.getPlaceData();
        if (placeData != null) {
            stmt.bindString(10, placeData);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(11, description);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Event readEntity(Cursor cursor, int offset) {
        Event entity = new Event( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // eventId
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // duration
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // author
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // type
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // category
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // date
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // dateArray
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // placeData
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10) // description
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Event entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setEventId(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setDuration(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAuthor(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setCategory(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setDate(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setDateArray(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setPlaceData(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setDescription(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Event entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Event entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}