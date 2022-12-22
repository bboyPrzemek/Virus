class Button {
	constructor(x, y, label, type, scene, callback) {
			this.type = type;
			this.isActive = false;
			this.text = scene.add.text(x, y, label)
			.setOrigin(0.5)
			.setPadding(10)
			.setStyle({ backgroundColor: '#111' })
			.setInteractive({ useHandCursor: true })
			.on('pointerdown', () =>  callback());
		
	}

	setVisible(value) {
		this.text.setVisible(value);
	}
	
	onPointerDown(){
		if (this.type == "action") return;
		this.isActive = !this.isActive;
		
		if (this.isActive){
			this.text.setStyle({ fill: '#03fc0f' });
			
		}else{
			this.text.setStyle({ fill: '#ffffff' });
		}
		
		
	}



}