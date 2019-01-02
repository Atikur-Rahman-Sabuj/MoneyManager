package RoomDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    public  void AddExpense(Expense expense);

    @Query("Select * from Expenses")
    public List<Expense> GetExpenses();

}
