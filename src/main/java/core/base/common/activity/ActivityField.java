package core.base.common.activity;

public enum ActivityField {

    SECTOR("BnzActivitySector"),
    SEGMENT("BnzActivitySegment"),
    SUB_SEGMENT("BnzActivitySubSegment"),

    SALES_TYPE("BnzSaleType"),
    MARKET("BnzSaleMarket");

    public final String marker;

    ActivityField(String marker) {
        this.marker = marker;


    }

}
