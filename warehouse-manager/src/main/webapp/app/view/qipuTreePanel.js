/**
 * 
 */
 
var store = Ext.create('Ext.data.TreeStore', {
	model: 'examinTree.model.Tree',
	proxy: {
	   	type: 'ajax',
	   	url: 'warehousemanagerservlet'
	}
});
 
Ext.define('joe.view.qipuTreePanel',{
	extend: 'Ext.tree.Panel',
	id : 'qipuTree',
    title: '棋谱库',
    height: 400,
    store: store,
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
					uploadPanel.show();
				}
			}
		}
	],
    root : {
        text: "奉璋的棋谱"
    },
    listeners:{
    	scope: this,
    	render : function(p){
//    		var rootNode = p.getRootNode();
//			
//    		var store = p.getStore();
//    		store.load({
//    			params: {
//		        	action: 'getTree'
//		    	}
//    		});
//    		rootNode.expand(false, false);
    	},
    	select: function(p,record, index,eOpts){
    		if (record.data.depth == 1){
    			//expand this node
    			var store = Ext.getCmp('mainTree').getStore();
    			store.load({
    				node:record,
    				params: {
	    				action: 'getExaminNodes',
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
    				
    				embedString = '<EMBED src="goview.swf?ver=1.2" width="788" height="615"  ' 
    					+'  type="application/x-shockwave-flash"  ' 
    					+' SRC="./flash/goview.swf?ver=1.2" ALLOWNETWORKING="none" ALLOWSCRIPTACCESS="samedomain"' 
    					+' FLASHVARS="encoding=utf-8&panel=250&sgftext='
    					+ record.raw.content
    					+'"></EMBED><br />';
    					
    					//<EMBED src="goview.swf?ver=1.2" width="788" height="615" type="application/x-shockwave-flash"  FLASHVARS="encoding=utf-8&panel=250&sgfurl=utf8.sgf" ALLOWSCRIPTACCESS="samedomain"></EMBED> 
    			}
    			var mainContainer = Ext.create('joe.view.mainContainer',{
    				extend: 'Ext.container.Container',
    				html: embedString
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