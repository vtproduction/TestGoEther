package com.midsummer.web3jlight.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by NienLe on 10,August,2018
 * Midsummer.
 * Ping me at nienbkict@gmail.com
 * Happy coding ^_^
 */
public class Wallet implements Parcelable {
    public final String address;

    public Wallet(String address) {
        this.address = address;
    }

    private Wallet(Parcel in) {
        address = in.readString();
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        @Override
        public Wallet createFromParcel(Parcel in) {
            return new Wallet(in);
        }

        @Override
        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };

    public boolean sameAddress(String address) {
        return this.address.equals(address);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(address);
    }
}
