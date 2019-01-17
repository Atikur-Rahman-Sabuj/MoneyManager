package RoomDb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface ExpenseDao {

    @Insert
    public  void AddExpense(Expense expense);

    @Update
    public void UpdateExpense(Expense expense);

    @Delete
    public void DeleteExpense(Expense expense);

    @Query("SELECT * FROM Expenses")
    public List<Expense> GetExpenses();

    @Query("SELECT * FROM Expenses WHERE Id = :id")
    public Expense GetExpenseById(Long id);

    @Query("SELECT * FROM Expenses WHERE date = :date")
    public List<Expense> GetExpensesOfaDate(Date date);
}
