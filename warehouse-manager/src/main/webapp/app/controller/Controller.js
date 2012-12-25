/**
 * 
 */
 
Ext.define('joe.controller.Controller', {
	extend: 'Ext.app.Controller',	  
//    stores: ['goStore'],  
//    models: ['goModel'],  
//    views: ['mainview'],  
//    refs: [  
//        {  
//            ref: 'mainview',  
//            selector: 'mainview'  
//        }  
//    ],  
	init:function(){
//		console.log('Initialized Users!');
		 this.control({
		 	 'viewport > mainview': {
		 	 	
		 	 }
		 });
	}
});