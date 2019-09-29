package RoomDb;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Transaction.class, Category.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MMDatabase extends RoomDatabase {
    public abstract MMDao mmDao();
}
