Ext.define('joe.component.MessageWindow', {
	statics : {
		self : null,
		show : function(caption, msg, msgContent, isHtml) {
			if (!this.self)
				this.self = new this();

			this.self.show(caption, msg, msgContent, isHtml);
		}
	},
	constructor : function() {
		this.win = Ext.create('Ext.window.Window', {
			title : 'Window',
			width : 410,
			// minWidth : 410,
			resizable : false,
			modal : true,
			closeAction : 'hide',
			items : [{
				xtype : 'panel',
				border : false,
				height : 100,
				width : 400,
				baseCls : 'x-plain',
				style : 'margin:10 0 0 20',
				layout : {
					type : 'table',
					columns : 2
				},
				items : [{
							xtype : 'panel',
							baseCls : 'x-plain',
							width : 50,
							height : 35,
							items : {
								xtype : 'box',
								cls : 'img-busy',
								style : 'z-index:9005',
								autoEl : {
									tag : 'img',
									src : 'images/icon-warning.gif'
								}
							}
						}, {
							xtype : 'panel',
							baseCls : 'x-plain',
							width : 320,
							height : 60,
							items : this.msgLabel = Ext.create(
									'Ext.form.Label', {
										text : ' ',
										style : 'font-size:12;'
									})
						}, {
							xtype : 'label',
							width : 35,
							text : ' '
						}, {
							xtype : 'panel',
							baseCls : 'x-plain',
							style : 'margin:4 0 0 60;align:center',
							layout : 'column',
							width : 300,
							items : [this.okBtn = Ext.create('Ext.Button', {
												text : 'OK',
												width : 67,
												handler : this.okClick,
												scope : this
											}),
									this.detailBtn = Ext.create('Ext.Button', {
												text : 'Details',
												width : 67,
												style : 'margin:0 0 0 10',
												handler : this.detailsClick,
												scope : this
											})],
							buttons : []
						}]
			}, this.detailDiv = Ext.create('Ext.panel.Panel', {
				xtype : 'panel',
				width : 400,
				height : 100,
				border : false,
				hidden : true,
				autoScroll : true,
				style : "overflow-y:auto;background-color:#FFFFFF;border-top:1px solid #6fa0df;padding-bottom:1px;padding-right:1px",
				items : this.errmsgLabel = Ext.create('Ext.form.Label', {
							hidden : true,
							width : 400,
							text : ''
						}),
				html : '<iframe id="showMessageiframe" style="visibility: hidden;" width="100%" height="99%" frameborder=0 ></iframe>'
			})]

		});

		return this;
	},
	show : function(caption, msg, msgContent, isHtml) {
		if (caption == undefined || caption == '')
			return;

		if (msg == undefined || msg == '') {
			alert(caption);
			return;
		}
		if (msgContent == undefined || msgContent == '') {
			Ext.Msg.show({
						title : caption,
						msg : msg,
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
			return;
		}

		if (!this.win)
			return;

		try {
			this.win.setTitle(caption);
			this.win.show();
			this.msgLabel.setText(msg);
			if (msgContent == '') {
				this.detailBtn.disable();
			} else {
				this.detailBtn.enable();
				var iframe = window.document
						.getElementById('showMessageiframe');
				if (!isHtml) {
					iframe.style.height = '0';
					iframe.style.visibility = 'hidden';
					this.errmsgLabel.show();
					this.errmsgLabel.setText(msgContent);
				} else {// show html
					this.errmsgLabel.hide();
					var afDoc;
					if (!iframe) {
						iframe.style.height = '0';
						iframe.style.visibility = 'hidden';
						this.errmsgLabel.show();
						this.errmsgLabel.setText(msgContent);
					} else {
						this.errmsgLabel.hide();
						iframe.style.height = '100%';
						iframe.style.visibility = 'visible';
						var afDoc;
						if (iframe.contentWindow.document) {
							afDoc = iframe.contentWindow.document;
						} else {
							afDoc = frames[id].document;
						}
						afDoc.write(this.unescapeXml(msgContent));
						afDoc.close();
					}
				}
			}
		} catch (ex) {
			Ext.Msg.alert('error', ex);
		}

		this.detailDiv.hide();
		this.detailDiv.setHeight(0);
		this.win.setHeight(142);
	},
	unescapeXml : function(xml) {
		if (!xml)
			return xml;
		var HUMAN = ["&", "\"", "<", ">"];
		var XMLS = ["&amp;", "&quot;", "&lt;", "&gt;"];
		for (var i = 0; i < HUMAN.length; i++) {
			var re = new RegExp(XMLS[i], "gi");
			xml = xml.replace(re, HUMAN[i]);
		}

		return xml;
	},
	okClick : function() {
		if (this.win) {
			this.win.hide();
		}
	},
	detailsClick : function() {
		if (this.detailDiv.isVisible()) {
			this.detailDiv.hide();
			this.detailDiv.setHeight(0);
			this.win.setHeight(142);
		} else {
			this.detailDiv.show();
			this.detailDiv.setHeight(100);
			this.win.setHeight(240);
		}
	}
})