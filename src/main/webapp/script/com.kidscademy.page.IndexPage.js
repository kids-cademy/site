$package("com.kidscademy.page");

/**
 * IndexPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of IndexPage class.
 */
com.kidscademy.page.IndexPage = function() {
	this.$super();
	// $E("button.fx").on("click", this._onFX, this);
	//js.ua.System.alert("Mesage for script variable.");
	// js.ua.System.alert("/site/media/asset_kids-cademy-logo.png");

//	js.ua.System.confirm("bla bla bla", function(ok) {
//		$E("#sys-message-id").success("Lorem ipsum dolor sit amet, nonumes docendi mel te, id illum corpora quo, sale facilis ius ex. Senserit argumentum sed no, usu te epicuri invidunt prodesset, tacimates patrioque eu eos. Sea nostrum reformidans ex. Vero nonumes officiis eam eu, munere legendos pri ei. Ea adhuc omittam convenire qui, cum ei nisl referrentur, cu vis quis exerci pertinacia. Mei prompta vulputate id. Qui congue ignota veritus no. Adhuc consequat moderatius his te, soluta euismod at usu. Impetus docendi salutatus mei ex, pro ex honestatis signiferumque. Ut dicit apeirian has. His commodo aliquam vocibus ei, dico inimicus complectitur pri ne, lobortis scriptorem cotidieque et pro. Mea homero iracundia an, nibh etiam eloquentiam cum et.");
//	});
	
	this.findByCss("#success, #info, #warning, #danger").on("click", function(ev) {
		this.getByCss("#sys-message-id")[ev.target.getAttr("id")]("Lorem ipsum dolor sit amet, nonumes docendi mel te, id illum corpora quo, sale facilis ius ex. Senserit argumentum sed no, usu te epicuri invidunt prodesset, tacimates patrioque eu eos. Sea nostrum reformidans ex. Vero nonumes officiis eam eu, munere legendos pri ei. Ea adhuc omittam convenire qui, cum ei nisl referrentur, cu vis quis exerci pertinacia. Mei prompta vulputate id. Qui congue ignota veritus no. Adhuc consequat moderatius his te, soluta euismod at usu. Impetus docendi salutatus mei ex, pro ex honestatis signiferumque. Ut dicit apeirian has. His commodo aliquam vocibus ei, dico inimicus complectitur pri ne, lobortis scriptorem cotidieque et pro. Mea homero iracundia an, nibh etiam eloquentiam cum et.");
	}, this);
};

com.kidscademy.page.IndexPage.prototype = {
	_onFX : function(ev) {
		this.getByCss("h2").addCssClass("go");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.IndexPage";
	}
};
$extends(com.kidscademy.page.IndexPage, com.kidscademy.page.Page);
