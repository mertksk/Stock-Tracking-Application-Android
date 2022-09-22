package com.niyel.mrpoundlast;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {


    private int Id;
    private int ProductId;
    private String ProductName;
    private int Barcode;
    private String Tracking;
    private int UomId;
    private String UomName;
    private int ProductUomQty;
    private int QtyDone;
    private int LocationId;
    private String LocationName;
    private int LocationDestId;
    private String LocationDestName;
    private ArrayList<Integer> Barcodes;
    private int process;  // 0 default ,1 processed , 2 cancelled
    private String cancelReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Product() {
    }

    public Product(int id, int productId, String productName, int barcode, String tracking, int uomId, String uomName, int productUomQty, int qtyDone, int locationId, String locationName, int locationDestId, String locationDestName, ArrayList<Integer> barcodes) {

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

    }
    public Product(int id, int productId, String productName, int barcode, String tracking, int uomId, String uomName, int productUomQty, int qtyDone, int locationId, String locationName, int locationDestId, String locationDestName, ArrayList<Integer> barcodes,int process) {
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

    public int getBarcode() {
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

    public ArrayList<Integer> getBarcodes() {
        return Barcodes;
    }

    public void setBarcodes(ArrayList<Integer> barcodes) {
        Barcodes = barcodes;
    }
}
