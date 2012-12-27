/**
 * 
 */
Ext.define('joe.view.uploadPanel',{
	extend: 'Ext.window.Window',
//	requires:['Ext.form.field.ComboBox'],
	width: 500,
	height: 180,
	layout: 'fit',
	renderTo: Ext.getBody(),
	items:[
		Ext.create('Ext.form.Panel', {		
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
		        fieldLabel: 'Name'
		    },{
		    	xtype: 'filefield',
		        id: 'form-file',
		        emptyText: 'Select a sgf file',
		        fieldLabel: 'SGF file',
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
			        	if(form.isValid()){
				            form.submit({
					            url: 'upload',
					            waitMsg: 'Uploading your sgf file...',
					            success: function(fp, o) {
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
		})
	]
})