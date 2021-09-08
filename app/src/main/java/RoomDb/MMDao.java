package RoomDb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface MMDao {

    @Insert
    public  void AddTransaction(Transaction transaction);

    @Insert
    public  void AddCategory(Category category);


    @Update
    public void UpdateTransaction(Transaction transaction);
    @Update
    public  void UpdateCategory (Category category);

    @Delete
    public void DeleteTransaction(Transaction transaction);
    @Delete
    public void DeleteCategory(Category category);

    @Query("SELECT * FROM `Transactions`")
    public List<Transaction> GetTransaction();

    @Query("SELECT * FROM `Transactions` ORDER BY date DESC")
    public List<Transaction> GetTransactionByDateSort();

    @Query("SELECT * FROM `Transactions` WHERE IsIncome = :isIncome")
    public  List<Transaction> GetTransactionofType (Boolean isIncome);

    @Query("SELECT * FROM `Transactions` WHERE Id = :id")
    public Transaction GetTransactionById(Long id);

    @Query("SELECT * FROM `Transactions` WHERE date >= :date")
    public List<Transaction> GetTallransactionsAfteraDate(Date date);


    @Query("SELECT * FROM `Categories`")
    public List<Category> GetCategories();

    @Query("SELECT * FROM `Categories` WHERE  IsIncome = :isIncome")
    public  List<Category> GetCategoriesofType(Boolean isIncome);

    @Query("SELECT * FROM `Categories` WHERE Id = :id")
    public Category GetCategoryById(Long id);

}
