package RoomDb;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Transactions", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "Id",
        childColumns = "CategoryId",
        onDelete = ForeignKey.NO_ACTION))
@TypeConverters(Converters.class)
public class Transaction implements Comparable<Transaction>{
    @PrimaryKey(autoGenerate = true)
    private long Id;
    private String Name;
    private  double Amount;
    private Date date;
    private Boolean IsIncome;

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    private int CategoryId;

    public Boolean getIsIncome() {
        return IsIncome;
    }

    public void setIsIncome(Boolean income) {
        IsIncome = income;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Transaction o) {
        return  getDate().compareTo(o.getDate());
    }
}
