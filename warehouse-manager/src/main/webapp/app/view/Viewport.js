/**
 * 
 */
 Ext.require('joe.view.uploadPanel');
 Ext.define('joe.view.Viewport', {	
	extend: 'Ext.container.Viewport',	
	layout: 'fit',
	items: [{
           		xtype:'panel',
           		title: '奉璋围棋训练',
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
           				id: 'ExaminationPanel',
           				title: '试题',
           				split: true,   
           				region: 'center',
           				autoWidth: true,
           				items: [
           					
           				]
           			},
           			{
           				xtype:'panel',
           				title: '属性设置',
           				split: true, 
           				collapsible : true,
						animCollapse : true,
						collapsed : true,
           				region: 'east',
           				width: 300,
           				items:[
           					 Ext.create('joe.view.propertyGrid')
           				]
           			}
           		]
           }] 
});