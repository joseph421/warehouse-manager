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
           				layout: 'border',
           				width: 300,
           				items: [{
           						xtype: 'container',
           						region: 'center',
           						height: 405,
           						layout: 'fit',           						
           						items:[Ext.create('joe.view.treePanel')]
           					},{
           						xtype: 'container',
           						layout: 'fit',
           						split: true,
           						region: 'south',
           						autoHeight: true,
           						items:[Ext.create('joe.view.qipuTreePanel')]
           					}
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