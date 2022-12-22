class HandData{
	constructor(scene, hand){
		this.scene = scene;
		this.hand = hand;
		this.handSprites = [];
		this.updateHandSprites();
	}
	
	updateHandSprites() {
		if (this.handSprites.length != 0) {
			this.handSprites.forEach(sprite => {
				sprite.destroy();
			})
			this.handSprites = [];

		}


		this.hand.cards.forEach((e, i) => {
			this.handSprites.push(new Card(this.scene, 435 + (i * 100), 700, e.uniqueTypeID));
		})
	}
	
	setHand(hand){
		this.hand = hand;
		this.updateHandSprites();
		
	}
}