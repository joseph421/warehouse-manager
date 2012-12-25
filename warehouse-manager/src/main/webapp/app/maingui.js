/**
 * 
 */
Ext.define('joe.maingui', {
			extend : 'Ext.container.Container',
			initComponent : function() {
				Ext.apply(this, {
					renderTo: Ext.getBody(),
					layout: 'fit',
					items:[{
						xtype: 'panel',
						item:[]
					}]
				});
				this.callParent(arguments);
			}

		});
