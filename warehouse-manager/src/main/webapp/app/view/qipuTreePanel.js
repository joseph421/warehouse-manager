/**
 * 
 */
 
var qipuStore = Ext.create('Ext.data.TreeStore', {
	model: 'examinTree.model.Tree',
	proxy: {
	   	type: 'ajax',
	   	url: 'warehousemanagerservlet'
	}
});
 
Ext.define('joe.view.qipuTreePanel',{
	extend: 'Ext.tree.Panel',
	id : 'qipuTree',
    title: '打谱练习',
    height: 400,
    store: qipuStore,
    tbar : ['->',
    	{
			text : '新增棋谱库',
			id :'addQipuBtn',
			disabled: false,
			listeners : {
				'click' : function() {
					
				}
			}
		},'-',{
			text : '上传棋谱',
			id :'uploadQipuBtn',
			disabled: true,
			listeners : {
				'click' : function() {
					var uploadPanel = Ext.create('joe.view.uploadPanel');
					var mainTree = Ext.getCmp('mainTree');
					
					var orderingId = mainTree.getSelectionModel().selected.items[0].data.id;
					Ext.getCmp('tf_orderingId').setValue(orderingId);
					Ext.getCmp('tf_orderingType').setValue('棋谱');
					uploadPanel.show();
				}
			}
		}
	],
    root : {
        text: "棋谱"
    },
    listeners:{
    	scope: this,
    	render : function(p){
    		var rootNode = p.getRootNode();
			
    		var store = p.getStore();
    		store.load({
    			params: {
		        	action: 'getTree',
		        	orderingType: '棋谱'		        	
		    	}
    		});
    		rootNode.expand(false, false);
    	},
    	select: function(p,record, index,eOpts){
    		if (record.data.depth == 1){
    			//expand this node
    			var store = Ext.getCmp('mainTree').getStore();
    			store.load({
    				node:record,
    				params: {
	    				action: 'getQipuNodes',
					    orderingId: record.data.id
    				}
    			});
    			record.expand(false, false);
    			Ext.getCmp('uploadBtn').enable();
    		}
    		else if(record.data.depth == 2){
    			//show this <embed > into
    			var embedString ='';
    			if (record.raw.content != ''){
    				
    				embedString = '<EMBED src="./flash/goview.swf?ver=1.1.11" width="788" height="615"  '
    					+'  type="application/x-shockwave-flash"  '
    					+' ALLOWSCRIPTACCESS="samedomain"'
    					+' FLASHVARS="panel=250&sgftext='
    					+ record.raw.content
    					+'"></EMBED><br />';
    				
    			}
    			
    			var mainContainer = Ext.create('joe.view.mainContainer',{
    				extend: 'Ext.container.Container',
//    				layout: 'fit',
    				items: [
    					{
    						xtype: 'panel',
    						region: 'center',
//    						width: 400,
    						split: true,
    						html: embedString 
    					}
//    					,
//    					{
//    						xtype: 'panel',
//    						region: 'east',
//    						items: [
//    							
//    						]
//    					}
    				]
    			});
    			var examinPanel = Ext.getCmp('ExaminationPanel');
    			examinPanel.removeAll();
    			examinPanel.add(mainContainer);
    			Ext.getCmp('uploadBtn').disable();
    		}
    		else {
    			Ext.getCmp('uploadBtn').disable();
    		}
    	}
    	
    }
});