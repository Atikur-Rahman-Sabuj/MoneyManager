package RoomDb;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.lang.annotation.Native;
import java.util.Date;

@Entity(tableName = "Transactions")
@TypeConverters(Converters.class)
public class Transaction implements Comparable<Transaction>{
    @PrimaryKey(autoGenerate = true)
    private long Id;
    private String Name;
    private double Amount;
    private Date date;
    private Boolean IsIncome;
    private long CategoryId;
    @Ignore
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(long categoryId) {
        CategoryId = categoryId;
    }



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
