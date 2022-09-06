package com.niyel.mrpound.globalVariables;

public class Order {

    /**
     * "Id": "3265856",
     * "Name": "71/OUT/02903",
     * "ScheduledDate": "2021-08-24 17:41:08",
     * "UserId": "",
     * "UserName": "",
     * "LocationId": "46",
     * "LocationName": "Zeytinlik Stock",
     * "LocationDestId": "9",
     * "LocationDestName": "Customers",
     * "PickingTypeId": "39",
     * "PickingTypeName": "Delivery Orders"
     */
    private int Id;
    private String name;
    private String date;
    private int locationID;
    private String locationName;
    private int LocationDestId;
    private String LocationDestName;
    private int pickingTypeID;
    private String pickingTypeName;
    private boolean orderType;

    public Order() {
    }

    public Order(int id, String name, String date, int locationID, String locationName, int locationDestId, String locationDestName, int pickingTypeID, String pickingTypeName,boolean orderType) {
        Id = id;
        this.name = name;
        this.date = date;
        this.locationID = locationID;
        this.locationName = locationName;
        LocationDestId = locationDestId;
        LocationDestName = locationDestName;
        this.pickingTypeID = pickingTypeID;
        this.pickingTypeName = pickingTypeName;
        this.orderType=orderType;
    }


    public String getPickingTypeName() {
        return pickingTypeName;
    }

    public void setPickingTypeName(String pickingTypeName) {
        this.pickingTypeName = pickingTypeName;
    }

    public int getPickingTypeID() {
        return pickingTypeID;
    }

    public void setPickingTypeID(int pickingTypeID) {
        this.pickingTypeID = pickingTypeID;
    }

    public String getLocationDestName() {
        return LocationDestName;
    }

    public void setLocationDestName(String locationDestName) {
        LocationDestName = locationDestName;
    }

    public int getLocationDestId() {
        return LocationDestId;
    }

    public void setLocationDestId(int locationDestId) {
        LocationDestId = locationDestId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isOrderType() {
        return orderType;
    }

    public void setOrderType(boolean orderType) {
        this.orderType = orderType;
    }
}
