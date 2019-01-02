$package("com.kidscademy.page");

/**
 * Base page class provides functionality common to all pages. It is designed to be extended by concrete subclasses.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.Page = class extends js.ua.Page {
    /**
     * Construct an instance of Page class.
     */
    constructor() {
        super();

        window.onscroll = () => {
            WinMain.doc.getByTag("body").addCssClass("scroll", window.pageYOffset > 40);
        }
    }

    /**
     * Class string representation.
     * 
     * @return this class string representation.
     */
    toString() {
        return "com.kidscademy.page.Page";
    }
};

// this page class is designed to be extended ; to not call WinMain
// WinMain.setPage(com.kidscademy.page.Page)
