/**
 * 
 */
Ext.require([    
    'Ext.data.*',
    'joe.model.examinTree'
]); 

Ext.define('joe.store.examinTree', {
	extend : 'Ext.data.TreeStore',	
//	model: 'examinTree.model.Tree',
//	proxy: {
//	   	type: 'ajax',
//	   	url: 'retrieve'         
//	},     
//	autoLoad: true,
	root: {
        expanded: true,
        children: [
            { text: "detention", leaf: true },
            { text: "homework", expanded: true, children: [
                { text: "book report", leaf: true },
                { text: "alegrbra", leaf: true}
            ] },
            { text: "buy lottery tickets", leaf: true }
        ]
    }
})

