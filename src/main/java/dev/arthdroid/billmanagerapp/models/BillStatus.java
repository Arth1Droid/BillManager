package dev.arthdroid.billmanagerapp.models;

public enum BillStatus {
    
    OPEN ("Open"),
    PAID("Paid"),
    OVERDUE("Overdue");

    private String description;
    BillStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

        public static BillStatus fromDescription(String description) {
        if (description == null) {
            return null; 
        }
        for (BillStatus type : BillStatus.values()) {
            if (type.description.equalsIgnoreCase(description.trim())) {
                return type; 
            }
        }
        return null; 
    }
}
