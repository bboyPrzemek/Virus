class Card extends Phaser.GameObjects.Sprite {

	constructor(scene, x, y, cardName) {
		super(scene, x, y, cardName);
		scene.add.existing(this).setOrigin(0, 0);
		this.setScale(.55);
		this.setInteractive();

		this.on('pointerdown', function(pointer) {
			this.isTinted ? this.clearTint() : this.setTint(0x07e337);
		});
		
	}
	
	setSprite(uniqueId){
		this.setTexture(uniqueId);
	}

}