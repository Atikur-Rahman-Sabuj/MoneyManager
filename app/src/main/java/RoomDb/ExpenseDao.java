package RoomDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface ExpenseDao {

    @Insert
    public  void AddExpense(Expense expense);

}
