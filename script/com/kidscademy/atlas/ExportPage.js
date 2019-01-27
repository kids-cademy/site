$package("com.kidscademy.atlas");

$include("com.kidscademy.atlasService");

/**
 * ExportPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.atlas.ExportPage = class extends com.kidscademy.page.Page {
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
        return "com.kidscademy.atlas.ExportPage";
    }
};

WinMain.createPage(com.kidscademy.atlas.ExportPage);
