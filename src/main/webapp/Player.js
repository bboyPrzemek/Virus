class Player {

	constructor(scene, posX, posY) {
		this.nickname = nickname;
		this.cards = [];
		this.dropZones = [];
		this.posX = posX;
		this.posY = posY;
		this.createDropZones(scene);
	}

	createDropZones(scene) {
		let x = this.posX;
		let y = this.posY;


		for (let i = 0; i < 4; i++) {
			this.dropZones.push(new CardZone(scene, x, y, 80, 120, CARD_ZONES_COLORS[i], CARD_TYPES[i]));
			x += 90;
		}
	}
	
	addCards(cards){
		let x = 450;
		let y = 750;
		let cardList = cards;
		cardList.forEach(c=>{
			c.x = x;
			c.y = y;
			x += 90;
		})
		this.cards = cardList;
	}

}