package Cyberia.CyberiaFramework.database.storage;

import java.util.LinkedHashMap;

public class DatabaseObject {
	public LinkedHashMap<String,DatabaseField<?>> databaseFields = new LinkedHashMap<>();
	
	public void addDatabaseField(DatabaseField<?> d) {
		databaseFields.put(d.dbName, d);
	}
}
