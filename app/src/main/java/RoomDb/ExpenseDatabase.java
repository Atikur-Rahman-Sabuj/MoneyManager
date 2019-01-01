package RoomDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.constraint.ConstraintLayout;

@Database(entities = {Expense.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ExpenseDatabase extends RoomDatabase {
    public abstract ExpenseDao expenseDao();
}
