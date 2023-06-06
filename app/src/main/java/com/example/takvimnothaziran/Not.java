package com.example.takvimnothaziran;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notlar")
public class Not {

    @PrimaryKey
    public int id;


    @ColumnInfo(name = "not")
    public String not;

    @ColumnInfo(name = "saat")
    public String notSaat;


    @ColumnInfo(name = "tarih")
    public String notTarih;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNot() {
        return not;
    }

    public void setNot(String not) {
        this.not = not;
    }

    public String getNotSaat() {
        return notSaat;
    }

    public void setNotSaat(String notSaat) {
        this.notSaat = notSaat;
    }

    public String getNotTarih() {
        return notTarih;
    }

    public void setNotTarih(String notTarih) {
        this.notTarih = notTarih;
    }
}
