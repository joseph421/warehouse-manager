/**
 * 
 */
 
Ext.define('joe.view.propertyGrid',{
	extend: 'Ext.grid.property.Grid',
	autoWidth:true,	
	iconCls: 'order' ,
	title: '设置',
	forceFit:true,
	stripeRows: true,
	columnLines: true,
	source: {
		'日期': ''	
	}

})