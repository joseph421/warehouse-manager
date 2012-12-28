/**
 * 
 */
Ext.define('joe.view.uploadPanel',{
	extend: 'Ext.window.Window',
//	requires:['Ext.form.field.ComboBox'],
	width: 500,
	height: 200,
	layout: 'fit',
	renderTo: Ext.getBody(),
	initComponent : function() { 
		var form = Ext.create('Ext.form.Panel', {		
		    frame: true,
		    title: 'File Upload Form',
		    bodyPadding: '10 10 0',
		
		    defaults: {
		    	anchor: '100%',
		        allowBlank: false,
		        msgTarget: 'side',
		        labelWidth: 50
		    },
		
		    items: [
		    {
		    	xtype: 'textfield',
		    	id: 'tf_orderingId',
		    	disabled: true,
		        fieldLabel: '题库ID'
		    },
		    {
		    	xtype: 'textfield',
		    	id: 'tf_examName',
		        fieldLabel: '题目'
		    },{
		    	xtype: 'filefield',
		        id: 'form-file',
		        emptyText: 'Select a sgf file',
		        fieldLabel: 'SGF文件',
		        name: 'sgffile-path',
		        buttonText: '',
		        buttonConfig: {
		        	iconCls: 'upload-icon'
		    	}
		    }],
		
		    buttons: [{
		    	text: 'Save',
		        handler: function(){
			        var form = this.up('form').getForm();
			        var examName = Ext.getCmp('tf_examName').getValue();
			        var orderingId = Ext.getCmp('tf_orderingId').getValue();
			        	if(form.isValid()){
				            form.submit({
					            url: 'upload?orderingId='+orderingId+'&name='+examName,					           
					            waitMsg: 'Uploading your sgf file...',
					            success: function(fp, o) {
					            	var rtnf = fp;
					            	var rtno = o;
					            	msg('Success', 'Processed file "' + o.result.file + '" on the server');
					            }
				        	});
			    		}
			        }
		    },{
		    	text: 'Reset',
		        handler: function() {
		        	this.up('form').getForm().reset();
		        }
		    }]
		});
		this.items = [form];  
		this.callParent(arguments);
	}
})