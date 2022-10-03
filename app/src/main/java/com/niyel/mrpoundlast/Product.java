package com.niyel.mrpoundlast;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {


    private int Id;
    private int ProductId;
    private String ProductName;
    private long Barcode;
    private String Tracking;
    private int UomId;
    private String UomName;
    private int ProductUomQty;
    private int QtyDone;
    private int LocationId;
    private String LocationName;
    private int LocationDestId;
    private String LocationDestName;
    private ArrayList<Long> Barcodes;
    private int process;  // 0 default ,1 processed , 2 cancelled
    private String ImageURL;
    private int cancelReason;
    private int revisedAmount;

    public int getRevisedAmount() {
        return revisedAmount;
    }

    public void setRevisedAmount(int revisedAmount) {
        this.revisedAmount = revisedAmount;
    }

    public int getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(int cancelReason) {
        this.cancelReason = cancelReason;
    }

    public void setBarcode(long barcode) {
        Barcode = barcode;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public Product() {
    }

    public Product(int id, int productId, String productName, long barcode, String tracking, int uomId, String uomName, int productUomQty, int qtyDone, int locationId, String locationName, int locationDestId, String locationDestName, ArrayList<Long> barcodes,String ImageURL) {

        Id = id;
        ProductId = productId;
        ProductName = productName;
        Barcode = barcode;
        Tracking = tracking;
        UomId = uomId;
        UomName = uomName;
        ProductUomQty = productUomQty;
        QtyDone = qtyDone;
        LocationId = locationId;
        LocationName = locationName;
        LocationDestId = locationDestId;
        LocationDestName = locationDestName;
        Barcodes = barcodes;
        this.ImageURL=  ImageURL;

    }
    public Product(int id, int productId, String productName, long barcode, String tracking, int uomId, String uomName, int productUomQty, int qtyDone, int locationId, String locationName, int locationDestId, String locationDestName, ArrayList<Long> barcodes,int process,String ImageURL) {
        Id = id;
        ProductId = productId;
        ProductName = productName;
        Barcode = barcode;
        Tracking = tracking;
        UomId = uomId;
        UomName = uomName;
        ProductUomQty = productUomQty;
        QtyDone = qtyDone;
        LocationId = locationId;
        LocationName = locationName;
        LocationDestId = locationDestId;
        LocationDestName = locationDestName;
        Barcodes = barcodes;
        this.process=process;
        this.ImageURL=ImageURL;
    }


    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public long getBarcode() {
        return Barcode;
    }

    public void setBarcode(int barcode) {
        Barcode = barcode;
    }

    public String getTracking() {
        return Tracking;
    }

    public void setTracking(String tracking) {
        Tracking = tracking;
    }

    public int getUomId() {
        return UomId;
    }

    public void setUomId(int uomId) {
        UomId = uomId;
    }

    public String getUomName() {
        return UomName;
    }

    public void setUomName(String uomName) {
        UomName = uomName;
    }

    public int getProductUomQty() {
        return ProductUomQty;
    }

    public void setProductUomQty(int productUomQty) {
        ProductUomQty = productUomQty;
    }

    public int getQtyDone() {
        return QtyDone;
    }

    public void setQtyDone(int qtyDone) {
        QtyDone = qtyDone;
    }

    public int getLocationId() {
        return LocationId;
    }

    public void setLocationId(int locationId) {
        LocationId = locationId;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }

    public int getLocationDestId() {
        return LocationDestId;
    }

    public void setLocationDestId(int locationDestId) {
        LocationDestId = locationDestId;
    }


    public String getLocationDestName() {
        return LocationDestName;
    }

    public void setLocationDestName(String locationDestName) {
        LocationDestName = locationDestName;
    }

    public ArrayList<Long> getBarcodes() {
        return Barcodes;
    }

    public void setBarcodes(ArrayList<Long> barcodes) {
        Barcodes = barcodes;
    }
}
