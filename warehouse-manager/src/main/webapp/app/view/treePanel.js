/**
 * 
 */ 
var store = Ext.create('Ext.data.TreeStore', {
	model: 'examinTree.model.Tree',
	proxy: {
	   	type: 'ajax',
	   	url: 'warehousemanagerservlet?action=getTree'	   	
	}
});

Ext.define('joe.view.treePanel',{
	extend: 'Ext.tree.Panel',
	id : 'mainTree',
    title: '习题库',
    store: store,
    root : {
        text: "奉璋的习题"
    },
    listeners:{
    	scope: this,
    	render : function(p){
    		var rootNode = p.getRootNode();
			
			
    		var store = p.getStore();    		
    		
    		rootNode.expand(false, false);
    	},
    	select: function(p,record, index,eOpts){    		
    		if (record.data.depth == 1){
    			//expand this node
    			Ext.Ajax.request({
    				url: 'warehousemanagerservlet',
				    params: {
				        action: 'getExaminNodes',
				        orderingId: record.data.id 
				    },
				    success: function(r){				        
				        var reponseObj = Ext.decode(r.responseText);
				        var mainTree  = Ext.getCmp('mainTree');
				        var currentNode = mainTree.getSelectionModel.selections[0];
				        for(var i = 0; i < reponseObj.childNodes.length ; i++){
				        	var item = reponseObj.childNodes;
				        	var tokenRecord = new Ext.data.Record.create([{
								name : 'token',
								type : 'string'
							}]);
//				        	currentNode.add()
				        	
				        }
				    }    			
    			})
    		}
    		else if(record.data.depth == 2){
    			//show this <embed > into 
    			
    		}
    	}
    }
});