class CardZone extends Phaser.GameObjects.Rectangle {

	constructor(scene, x, y, width, height, color, type) {
		super(scene, x, y, width, height, 0X000000, type)
		this.cards = [];
		this.type = type;
		scene.add.existing(this);
		this.setStrokeStyle(2, color);
		this.setOrigin(0, 0)
	}

	putCard(card) {
		this.cards.push(new Card(this.scene, this.x ,this.y, card.uniqueTypeID));
	}
}