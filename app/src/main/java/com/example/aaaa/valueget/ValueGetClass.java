package com.example.aaaa.valueget;

public class ValueGetClass {
    String name, printername, printerMpdel, macaddress, ipaddress, wifiprinter;

    public ValueGetClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrintername() {
        return printername;
    }

    public void setPrintername(String printername) {
        this.printername = printername;
    }

    public String getPrinterMpdel() {
        return printerMpdel;
    }

    public void setPrinterMpdel(String printerMpdel) {
        this.printerMpdel = printerMpdel;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getWifiprinter() {
        return wifiprinter;
    }

    public void setWifiprinter(String wifiprinter) {
        this.wifiprinter = wifiprinter;
    }

    public ValueGetClass(String name,
                         String printername, String printerMpdel, String macaddress, String ipaddress, String wifiprinter) {
        this.name = name;
        this.printername = printername;
        this.printerMpdel = printerMpdel;
        this.macaddress = macaddress;
        this.ipaddress = ipaddress;
        this.wifiprinter = wifiprinter;
    }
}
