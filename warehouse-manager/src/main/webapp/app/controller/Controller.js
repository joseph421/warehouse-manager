/**
 * 
 */

Ext.define('joe.controller.Controller', {
	extend: 'Ext.app.Controller',	  
    stores: ['examinTree'],  
    models: ['examinTree'],  
    views: ['Viewport'],  
//    refs: [  
//        {  
//            ref: 'mainview',  
//            selector: 'mainview'  
//        }  
//    ],  
	init:function(){		
		Ext.create('joe.view.Viewport').show();
	}
});