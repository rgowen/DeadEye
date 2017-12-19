package com.cascadiaoccidental.deadeye.data;

import java.io.Serializable;
import java.util.Arrays;

public class AmazonResultData implements Serializable {
    public String title;
    public String isbn;
    public String type;
    public String listingsUrl;
    public byte[] image;
    public double amazonPrice;
    public double lowestPrice;
    public boolean errorFlag;
    public String errorText;
    public String toString() {
        return "AmazonResultData [title=" + title + ", isbn=" + isbn
                + ", type=" + type + ", listingsUrl=" + listingsUrl
                + ", image=" + Arrays.toString(image) + ", amazonPrice="
                + amazonPrice + ", lowestPrice=" + lowestPrice + "]";
    }
}
