package net.ceronio.dstvguide.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper for SQLite database
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "guideStore";

    public static final String COUNTRY_TABLE = "countries";
    public static final String CHANNEL_TABLE = "channels";
    public static final String BOUQUET_TABLE = "bouquets";
    public static final String GENRE_TABLE = "genres";
    public static final String FAVORITE_TABLE = "favorites";
    public static final String BOUQUET_CHANNEL_TABLE = "bouquet_channels";
    public static final String GENRE_CHANNEL_TABLE = "genre_channels";

    public static enum COUNTRY_COLUMNS {
        CODE, FOOTPRINT, ID, IMAGE, TITLE
    }

    public static enum CHANNEL_COLUMNS {
        NUMBER, SHORT_DESCRIPTION, ID, IMAGE, LONG_DESCRIPTION
    }

    public static enum FAVORITE_COLUMNS {
        CHANNEL
    }

    public static enum BOUQUET_COLUMNS {
        CODE, NAME, ID
    }

    public static enum BOUQUET_CHANNEL_COLUMNS {
        BOUQUET_CODE, CHANNEL_NUMBER
    }

    public static enum GENRE_CHANNEL_COLUMNS {
        GENRE_ID, CHANNEL_NUMBER
    }

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    /**
     * onCreate
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_COUNTRY_TABLE =
                "CREATE TABLE countries ( code TEXT PRIMARY KEY, footprint TEXT, id INTEGER, image TEXT, title TEXT )";
        db.execSQL(CREATE_COUNTRY_TABLE);
        String CREATE_CHANNEL_TABLE =
                "CREATE TABLE channels ( number INTEGER PRIMARY KEY, short_description TEXT, id INTEGER, image TEXT, long_description TEXT )";
        db.execSQL(CREATE_CHANNEL_TABLE);
        String CREATE_BOUQUET_TABLE =
                "CREATE TABLE bouquets ( code INTEGER PRIMARY KEY, name TEXT, id TEXT )";
        db.execSQL(CREATE_BOUQUET_TABLE);
        String CREATE_GENRE_TABLE =
                "CREATE TABLE genres ( code INTEGER PRIMARY KEY, name TEXT, id TEXT )";
        db.execSQL(CREATE_GENRE_TABLE);
        String CREATE_BOUQUET_CHANNEL_TABLE =
                "CREATE TABLE bouquet_channels ( bouquet_code INTEGER, channel_number INTEGER, PRIMARY KEY (bouquet_code, channel_number) )";
        db.execSQL(CREATE_BOUQUET_CHANNEL_TABLE);
        String CREATE_FAVORITE_TABLE =
                "CREATE TABLE favorites ( channel INTEGER PRIMARY KEY )";
        db.execSQL(CREATE_FAVORITE_TABLE);
    }

    /*----------------------------------------------------------------------------------------------------------------/
    /* BOUQUETS
    /*---------------------------------------------------------------------------------------------------------------*/

    public List<Bouquet> getBouquets() {
        List<Bouquet> bouquets = new ArrayList<Bouquet>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + BOUQUET_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Bouquet bouquet = new Bouquet();
                bouquet.setCode(cursor.getInt(0));
                bouquet.setName(cursor.getString(1));
                bouquet.setId(cursor.getString(2));
                bouquets.add(bouquet);
            } while (cursor.moveToNext());
        }
        return bouquets;
    }

    public void addBouquets(List<Bouquet> bouquets) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Bouquet bouquet : bouquets) {
            ContentValues values = new ContentValues();
            values.put(BOUQUET_COLUMNS.CODE.toString(), bouquet.getCode());
            values.put(BOUQUET_COLUMNS.NAME.toString(), bouquet.getName());
            values.put(BOUQUET_COLUMNS.ID.toString(), bouquet.getId());
            db.insert(BOUQUET_TABLE, null, values);
        }
        db.close();
    }

    /**
     * Deletes all bouquet entries from the bouquets database table
     */
    public void deleteBouquets() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM bouquets";
        db.execSQL(query);
        db.close();
    }


    /*----------------------------------------------------------------------------------------------------------------/
    /* BOUQUET CHANNELS
    /*---------------------------------------------------------------------------------------------------------------*/
    public void addBouquetChannels(Bouquet bouquet, List<Channel> channels) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Channel channel : channels) {
            ContentValues values = new ContentValues();
            values.put(BOUQUET_CHANNEL_COLUMNS.BOUQUET_CODE.toString(), bouquet.getCode());
            values.put(BOUQUET_CHANNEL_COLUMNS.CHANNEL_NUMBER.toString(), channel.getNumber());
            db.insert(BOUQUET_CHANNEL_TABLE, null, values);
        }
        db.close();
    }

    public void deleteBouquetChannels() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM bouquet_channels";
        db.execSQL(query);
        db.close();
    }

    public List<Channel> getBouquetChannels(Bouquet bouquet) {
        List<Channel> channels = new ArrayList<Channel>();
        String query = "SELECT * FROM bouquet_channels WHERE bouquet_code = ?";
        Cursor cursor = getReadableDatabase().rawQuery(query, new String[]{String.valueOf(bouquet.getCode())});
        if (cursor.moveToFirst()) {
            do {
                try {
                    Channel channel = getChannel(cursor.getInt(1));
                    channels.add(channel);
                } catch (DataNotExistException e) {
                    // TODO: Should we not have exception in getChannel?
                }
            } while (cursor.moveToNext());
        }
        return channels;
    }

    /*----------------------------------------------------------------------------------------------------------------/
    /* FAVORITES
    /*---------------------------------------------------------------------------------------------------------------*/

    /**
     * Add favorite channel to list of favorite channels
     *
     * @param channelNumber
     */
    public void addFavorite(int channelNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FAVORITE_COLUMNS.CHANNEL.toString(), channelNumber);

        db.insert(FAVORITE_TABLE, null, values);
        db.close();
    }

    /**
     * Delete a single favorite by its channel number
     *
     * @param channelNumber
     */
    public void deleteFavorite(int channelNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FAVORITE_TABLE, FAVORITE_COLUMNS.CHANNEL.toString() + " = ?", new String[]{String.valueOf(channelNumber)});
        db.close();
    }

    /**
     * Delete all favorites
     */
    public void deleteFavorites() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM favorites";
        db.execSQL(query);
        db.close();
    }

    public List<Channel> getFavorites() {
        List<Channel> favorites = new ArrayList<Channel>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT  * FROM " + FAVORITE_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Channel channel = getChannel(cursor.getInt(0));
                    favorites.add(channel);
                } catch (DataNotExistException e) {
                    // Ignore channels not existing in DB
                    // TODO - should we delete such favorites?
                }
            } while (cursor.moveToNext());
        }

        return favorites;
    }

    /*----------------------------------------------------------------------------------------------------------------/
    /* CHANNELS
    /*---------------------------------------------------------------------------------------------------------------*/

    /**
     * Add multiple channels
     *
     * @param channels
     */
    public void addChannels(List<Channel> channels) {
        SQLiteDatabase db = this.getWritableDatabase();
        for (Channel channel : channels) {
            ContentValues values = new ContentValues();
            values.put(CHANNEL_COLUMNS.NUMBER.toString(), channel.getNumber());
            values.put(CHANNEL_COLUMNS.ID.toString(), channel.getId());
            values.put(CHANNEL_COLUMNS.IMAGE.toString(), channel.getImage());
            values.put(CHANNEL_COLUMNS.LONG_DESCRIPTION.toString(), channel.getLongDescription());
            values.put(CHANNEL_COLUMNS.SHORT_DESCRIPTION.toString(), channel.getShortDescription());
            db.insert(CHANNEL_TABLE, null, values);
        }
        db.close();
    }

    /**
     * Add single channel to list of channels
     * @param channel
     */
    public void addChannel(Channel channel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CHANNEL_COLUMNS.NUMBER.toString(), channel.getNumber());
        values.put(CHANNEL_COLUMNS.ID.toString(), channel.getId());
        values.put(CHANNEL_COLUMNS.IMAGE.toString(), channel.getImage());
        values.put(CHANNEL_COLUMNS.LONG_DESCRIPTION.toString(), channel.getLongDescription());
        values.put(CHANNEL_COLUMNS.SHORT_DESCRIPTION.toString(), channel.getShortDescription());

        //db.insert(CHANNEL_TABLE, null, values);
        // TODO: Verify the conflict resolution algorithm used here
        db.insertWithOnConflict(CHANNEL_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    /**
     * Get a channel by its channel number
     *
     * @param channelNumber
     * @return
     * @throws DataNotExistException
     */
    public Channel getChannel(int channelNumber) throws DataNotExistException {
        SQLiteDatabase db = this.getReadableDatabase();
        Channel channel = new Channel();
        String query = String.format("SELECT * FROM %s WHERE number = ?", CHANNEL_TABLE);
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(channelNumber)});
        if (cursor.moveToFirst()) {
            channel.setNumber(cursor.getInt(0));
        } else {
            throw new DataNotExistException();
        }
        return channel;
    }

    /**
     * Delete channel by its channel number
     *
     * @param channelNumber
     */
    public void deleteChannel(int channelNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CHANNEL_TABLE, CHANNEL_COLUMNS.NUMBER.toString() + " = ?", new String[]{String.valueOf(channelNumber)});
        db.close();
    }

    public void deleteChannel(Channel channel) {
        deleteChannel(channel.getNumber());
    }

    /**
     * Delete all Channels
     */
    public void deleteChannels() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM channels";
        db.execSQL(query);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String[] tables = new String[]{COUNTRY_TABLE, CHANNEL_TABLE, FAVORITE_TABLE, GENRE_TABLE, BOUQUET_TABLE,
                BOUQUET_CHANNEL_TABLE, GENRE_CHANNEL_TABLE};
        for (String table : tables) {
            db.execSQL(String.format("DROP TABLE IF EXISTS", table));
        }
        onCreate(db);
    }
}
