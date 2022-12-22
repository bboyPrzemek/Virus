class PlayerData {

	constructor(scene, player) {
		this.scene = scene;
		this.player = player;
		this.heartZone;
		this.boneZone;
		this.mawZone;
		this.brainZone;
		this.createDropZones();
		this.button;
		this.createNames();
	}

	createDropZones() {
		let x = this.player.posX;
		let y = this.player.posY;
		
		this.heartZone = new CardZone(this.scene, x, y, 80, 120, CARD_ZONES_COLORS[0], CARD_TYPES[0]);
		this.mawZone = new CardZone(this.scene, x + 180, y, 80, 120, CARD_ZONES_COLORS[1], CARD_TYPES[1]);
		this.boneZone = new CardZone(this.scene, x + 90, y, 80, 120, CARD_ZONES_COLORS[2], CARD_TYPES[2]);
		this.brainZone = new CardZone(this.scene, x + 270, y, 80, 120, CARD_ZONES_COLORS[3], CARD_TYPES[3]);
		
	}
	
	createNames() {
		this.button = new Button(this.player.posX, this.player.posY - 30, this.player.name, "name", this.scene, () => this.onPointerDown());
	}
	
	setPlayer(player){
		this.player = player;
		this.redrawCards();
	}
	
	redrawCards(){
		let heartCards = this.player.playerDeck.heart;
		let boneCards = this.player.playerDeck.bone;
		let mawCards = this.player.playerDeck.maw;
		let brainCards = this.player.playerDeck.brain;
		
		
		if (heartCards.length > 0){
			heartCards.forEach(card=>{
				this.heartZone.putCard(card);
			})
		}
		
		if (boneCards.length > 0){
			boneCards.forEach(card=>{
				this.boneZone.putCard(card);
			})
		}
		
		if (mawCards.length > 0){
			mawCards.forEach(card=>{
				this.mawZone.putCard(card);
			})
		}
		
		if (brainCards.length > 0){
			brainCards.forEach(card=>{
				this.brainZone.putCard(card);
			})
		}
			
		
		
	}
	
	
	onPointerDown(){
		this.button.isActive = !this.button.isActive;
		
		if (this.button.isActive){
			this.button.text.setStyle({ fill: '#03fc0f' });
			
		}else{
			this.button.text.setStyle({ fill: '#ffffff' });
		}
	}

}