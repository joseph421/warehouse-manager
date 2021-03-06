/**
 * 
 */
 
Ext.define('examinTreeModel', {
	extend: 'Ext.data.TreeModel',
	fields:['leaf','text','id','level','iconCls'],
    fields: [
    	{name: 'id', type: 'int'},
        {name: 'text',  type: 'string'},
        {name: 'leaf',       type: 'boolean'},
        {name: 'iconCls',  type: 'string'},
        {name: 'level', type: 'string'}
     ]
 });