package br.com.sevencode.android.feia2014.db;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import de.greenrobot.dao.AbstractDaoMaster;
import de.greenrobot.dao.identityscope.IdentityScopeType;

import br.com.sevencode.android.feia2014.db.EventDao;
import br.com.sevencode.android.feia2014.db.MyEventDao;
import br.com.sevencode.android.feia2014.db.EventDao.Properties;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * Master of DAO (schema version 1): knows all DAOs.
*/
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 2;//1;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        EventDao.createTable(db, ifNotExists);
        MyEventDao.createTable(db, ifNotExists);
    }
    
    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        EventDao.dropTable(db, ifExists);
        MyEventDao.dropTable(db, ifExists);
    }
    
    public static abstract class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }
    
    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            
            if(newVersion == 2 && oldVersion == 1){
            	makeChangesV1toV2(db);
            } else{
            	dropAllTables(db, true);
            	onCreate(db);
            }
        }
        
        public void makeChangesV1toV2(SQLiteDatabase db){
    		List<Event> events = null;
    		
    		DaoMaster daoMaster = null;
    		DaoSession daoSession = null;
    		EventDao eventDao = null;

    		daoMaster = new DaoMaster(db);
    		daoSession = daoMaster.newSession();
    		eventDao = daoSession.getEventDao();
    		
    		events = eventDao.queryBuilder().where(Properties.EventId.eq(93)).list();
    		
    		// Altera Jo�o Bosco para apresenta��es ao inves de noite FEIA
    		for (Event e : events) {
    			if(e.getName().contains("Bosco")){
    				e.setType(1);
    				e.setCategory(1);
    				e.setName(e.getName().substring(e.getName().indexOf("Jo�o Bosco")));
    		
    				eventDao.update(e);
    			}
    		}
    		
    		events = eventDao.queryBuilder().where(Properties.EventId.eq(83)).list();
    		
    		// Remove Duo Catrumano 
    		for (Event e : events) {
    			if(e.getName().contains("Catrumano")){
    				eventDao.delete(e);
    			}
    		}
    		
    		events = eventDao.queryBuilder().where(Properties.EventId.eq(85)).list();
    		
    		// Altera lugar do Meia duzia de 3 ou 4
    		for (Event e : events) {
    			if(e.getName().contains("3 ou 4")){
    				e.setPlaceData("V�o do PB");
    				
    				eventDao.update(e);
    			}
    		}
    		
    		events = eventDao.queryBuilder().where(Properties.EventId.eq(90)).list();
    		
    		// Altera noite feia do padre do bal�o para galo de briga
    		for (Event e : events) {
    			if(e.getName().contains("Padre")){
    				e.setName("Noite FEIA - Galo de Briga");
    				e.setDescription("O grupo Galos de Briga se prop�e a compartilhar um " +
    						"repert�rio vasto que abrange v�rias vertentes do samba, " +
    						"valorizando tanto a forma��o tradicional de instrumentos " +
    						"que comp�em uma cl�ssica roda de samba como uma " +
    						"instrumenta��o mais moderna. De Nelson Cavaquinho, Cartola, " +
    						"Paulinho da Viola a sambistas mais recentes como compositores " +
    						"do Cacique de Ramos e outros contempor�neos, o repert�rio " +
    						"abarca de maneira significativa a trajet�ria pela qual esse " +
    						"estilo musical se consolidou e vem se ressignificando. O " +
    						"grupo � composto por m�sicos amigos que estudaram e se " +
    						"conheceram na Unicamp e em projetos culturais que t�m como " +
    						"norte principal o samba, buscando n�o somente a execu��o " +
    						"mec�nica da m�sica, mas sim o engrandecimento deste em sua " +
    						"express�o no �mbito s�cio-cultural.");
    				
    				eventDao.update(e);
    			}
    		}
    		
    	}
    }

    public DaoMaster(SQLiteDatabase db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(EventDao.class);
        registerDaoClass(MyEventDao.class);
    }
    
    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }
    
    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }
    
}
