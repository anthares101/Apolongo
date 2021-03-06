package com.apolongo.apolongo.DB;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity(tableName = "purchases_table", indices = {@Index("purchase_cardId")}, //The index ignores a warning
        foreignKeys = @ForeignKey(entity = Card.class,
        parentColumns = "card_id",
        childColumns = "purchase_cardId",
        onDelete = ForeignKey.CASCADE))
public class Purchase {
    
    //Private attributes declaration
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "purchase_id")
    private int mPurchaseId;
    @NonNull
    @ColumnInfo(name = "purchase_name")
    private String mPurchaseName;
    @NonNull
    @ColumnInfo(name = "purchase_date")
    private Date mPurchaseDate;
    @NonNull
    @ColumnInfo(name = "purchase_price")
    private Float mPurchasePrice;
    @Nullable
    @ColumnInfo(name = "purchase_sdescp")
    private String mPurchaseSDescp; //Short description of the purchase
    @NonNull
    @ColumnInfo(name = "purchase_cardId")
    private int mPurchaseCardId;

    //Constructor
    public Purchase(@NonNull String mPurchaseName, @NonNull Date mPurchaseDate,
                    @NonNull Float mPurchasePrice, @Nullable String mPurchaseSDescp,
                    @NonNull int mPurchaseCardId){
        this.mPurchaseId = 0;
        this.mPurchaseName = mPurchaseName;
        this.mPurchaseDate = mPurchaseDate;
        this.mPurchasePrice = mPurchasePrice;
        this.mPurchaseSDescp = mPurchaseSDescp; //Short description is gonna be optional
        this.mPurchaseCardId = mPurchaseCardId;
    }

    //Getters
    public int getPurchaseId() {return mPurchaseId;}
    public Date getPurchaseDate() {return mPurchaseDate;}
    public String getPurchaseName(){return mPurchaseName;}
    public Float getPurchasePrice(){return mPurchasePrice;}
    public String getPurchaseSDescp() {return mPurchaseSDescp;}
    public int getPurchaseCardId() {return mPurchaseCardId;}

    //Setters
    public void setPurchaseId(int PurchaseId) {this.mPurchaseId = PurchaseId;}
    public void setPurchaseName(String PurchaseName) {this.mPurchaseName = PurchaseName;}
    public void setPurchaseDate(Date PurchaseDate) {this.mPurchaseDate = PurchaseDate;}
    public void setPurchasePrice(Float PurchasePrice) {this.mPurchasePrice = PurchasePrice;}
    public void setPurchaseSDescp(String PurchaseDescp) {this.mPurchaseSDescp = PurchaseDescp;}
    public void setPurchaseCardId(int PurchaseCard) {this.mPurchaseCardId = PurchaseCard;}
}
