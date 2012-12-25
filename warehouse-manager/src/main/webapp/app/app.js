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
//	requires : ['joe.component.MessageWindow'],
	controllers: ['Controller'],
	launch : function() {
		Ext.QuickTips.init();

		Ext.create('Ext.container.Viewport', {  
           layout: 'fit',  
           items: [{
           		xtype:'container',
           		title: 'Joseph Lee',
           		layout: 'border',
           		items:[
           			{
           				xtype:'panel',
           				title: 'Ordering',
           				split: true,
           				region: 'east',
           				width: 300
           			},
           			{
           				xtype:'panel',           				
           				title: 'Examination',
           				split: true,   
           				region: 'center',
           				autoWidth: true
           			},
           			{
           				xtype:'panel',
           				title: 'Property',
           				split: true,   
           				region: 'west',
           				width: 400
           			}
           		]
           }] 
        });  
	}

});