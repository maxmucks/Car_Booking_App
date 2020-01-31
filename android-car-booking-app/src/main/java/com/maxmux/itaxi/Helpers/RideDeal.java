package com.maxmux.itaxi.Helpers;

public class RideDeal {
    public String id;
    public String pickup;
    public String dropoff;
    public String date;
    public String time;
    public String noofpass;
    public String luggagesize;
    public String vehicletype;

    public RideDeal(){}

    public RideDeal(String pickup, String dropoff, String date, String time, String noofpass, String luggagesize, String vehicletype) {
        this.setId(id);
        this.setPickup(pickup);
        this.setDropoff(dropoff);
        this.setDate(date);
        this.setTime(time);
        this.setNoofpass(noofpass);
        this.setLuggagesize(luggagesize);
        this.setVehicletype(vehicletype);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNoofpass() {
        return noofpass;
    }

    public void setNoofpass(String noofpass) {
        this.noofpass = noofpass;
    }

    public String getLuggagesize() {
        return luggagesize;
    }

    public void setLuggagesize(String luggagesize) {
        this.luggagesize = luggagesize;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }
}
