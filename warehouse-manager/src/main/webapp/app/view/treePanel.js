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

Ext.define('joe.view.treePanel',{
	extend: 'Ext.tree.Panel',
	id : 'mainTree',
    title: '习题库',
    height: 400,
    store: store,
    tbar : ['->',
    	{
			text : '新增题库',
			id :'addLibraryBtn',
			disabled: false,
			listeners : {
				'click' : function() {
					
				}
			}
		},'-',{
			text : '上传试题',
			id :'uploadBtn',
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
        text: "习题"
    },
    listeners:{
    	scope: this,
    	render : function(p){
    		var rootNode = p.getRootNode();
			
    		var store = p.getStore();
    		store.load({
    			params: {
		        	action: 'getTree',
		        	orderingType : '习题'
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
    				
    				embedString = '<embed allowScriptAccess="never" allowNetworking="internal" autostart="0" ' 
    					+'  HEIGHT="500" TYPE="application/x-shockwave-flash" WIDTH="700"' 
    					+' SRC="./flash/goxiti.swf?ver=1.02" ALLOWNETWORKING="none"' 
    					+' FLASHVARS="home=&sgftext='
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