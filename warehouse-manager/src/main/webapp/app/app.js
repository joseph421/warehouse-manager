/**
 * 
 */
//var maingui;



Ext.Loader.setConfig({
			enabled : true
		});
		

Ext.Loader.setPath("Ext.ux", "./ext/examples/ux");

Ext.application({
	name : 'joe',
	appFolder : 'app',
	requires : [
		'joe.component.MessageWindow',
		'joe.view.treePanel',
		'joe.view.qipuTreePanel',
		'joe.view.propertyGrid',
		'joe.view.mainContainer'
	],
	controllers: ['Controller'],
	autoCreateViewport: false,
	launch : function() {
		Ext.QuickTips.init();		  
	}
});