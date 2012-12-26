/**
 * 
 */ 
Ext.define('joe.view.Viewport', {	
	extend: 'Ext.container.Viewport',	
	layout: 'fit',
	items: [{
           		xtype:'panel',
           		title: 'Joseph Lee Go traning base',
           		layout: 'border',
           		items:[
           			{
           				xtype:'panel',
           				border: false,
           				split: true,
           				region: 'west',
           				layout: 'fit',
           				width: 300,
           				items: [
           					Ext.create('joe.view.treePanel')
           				]           				
           			},
           			{
           				xtype:'panel',           				
           				title: 'Examination',
           				split: true,   
           				region: 'center',
           				autoWidth: true,
           				items: [
           					Ext.create('joe.view.mainContainer')
           				]           			},
           			{
           				xtype:'panel',
           				title: 'Property',
           				split: true,   
           				region: 'east',
           				width: 300,
           				items:[
           					 Ext.create('joe.view.propertyGrid')
           				]
           			}
           		]
           }] 
});