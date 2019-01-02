$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

/**
 * ExportPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.ExportPage = class extends com.kidscademy.page.Page {
    /**
     * Construct an instance of ExportPage class.
     */
    constructor() {
        super();
    }

    /**
     * Class string representation.
     * 
     * @return this class string representation.
     */
    toString() {
        return "com.kidscademy.admin.ExportPage";
    }
};

WinMain.setPage(com.kidscademy.admin.ExportPage);
