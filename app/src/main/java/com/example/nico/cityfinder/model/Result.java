package com.example.nico.cityfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Nico on 17/06/2017.
 */

public class Result implements Serializable, Parcelable {

    //------------------
    // ATTRIBUT
    //------------------

    private String ville;
    private String cp;

    //------------------
    //  CONSTRUCTOR
    //------------------

    public Result(String ville, String cp) {
        this.ville = ville;
        this.cp = cp;
    }

    //------------------
    //  GETTER / SETTER
    //------------------

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    protected Result(Parcel in) {
        ville = in.readString();
        cp = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ville);
        dest.writeString(cp);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
