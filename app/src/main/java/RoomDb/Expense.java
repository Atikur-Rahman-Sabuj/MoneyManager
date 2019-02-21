package RoomDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "Expenses")
@TypeConverters(Converters.class)
public class Expense implements Comparable<Expense>{
    @PrimaryKey(autoGenerate = true)
    private long Id;
    private String Name;
    private  double Amount;
    private Date date;

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
    public int compareTo(Expense o) {
        return  getDate().compareTo(o.getDate());
    }
}
