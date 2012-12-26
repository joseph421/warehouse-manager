/**
 * 
 */
Ext.require([    
    'Ext.data.*'    
]); 

Ext.define('joe.model.examinTree', {
	extend: 'Ext.data.Model',
	fields:['leaf','text','id','level','iconCls'],
    fields: [
    	{name: 'id', type: 'int'},
        {name: 'text',  type: 'string'},
        {name: 'leaf',       type: 'boolean'},
        {name: 'iconCls',  type: 'string'},
        {name: 'level', type: 'string'}
     ]
 });