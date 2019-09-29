package RoomDb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long Id;
    private String Name;
    private Boolean IsIncome;

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

    public Boolean getIsIncome() {
        return IsIncome;
    }

    public void setIsIncome(Boolean income) {
        IsIncome = income;
    }
}
