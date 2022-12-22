class Game extends Phaser.Scene {

	constructor(gameData) {
		super("MAIN_SCENE");
		this.gameData = gameData;
		this.playersData = [];
		this.handData = 'undefined';

		this.updateGame = false;
		this.textPlayablePlayer;
		this.controlButtons = [];
	}

	preload() {
		this.load.image('1', 'assets/organs/1.png');
		this.load.image('2', 'assets/organs/2.png');
		this.load.image('3', 'assets/organs/3.png');
		this.load.image('4', 'assets/organs/4.png');
		this.load.image('5', 'assets/organs/5.png');

		this.load.image('6', 'assets/viruses/6.png');
		this.load.image('7', 'assets/viruses/7.png');
		this.load.image('8', 'assets/viruses/8.png');
		this.load.image('9', 'assets/viruses/9.png');
		this.load.image('10', 'assets/viruses/10.png');

		this.load.image('11', 'assets/remedies/11.png');
		this.load.image('12', 'assets/remedies/12.png');
		this.load.image('13', 'assets/remedies/13.png');
		this.load.image('14', 'assets/remedies/14.png');
		this.load.image('15', 'assets/remedies/15.png');

		this.load.image('16', 'assets/actions/16.png');
		this.load.image('17', 'assets/actions/17.png');
		this.load.image('18', 'assets/actions/18.png');
		this.load.image('19', 'assets/actions/19.png');
		this.load.image('20', 'assets/actions/20.png');
	}

	onInit() {
		this.gameData.game.playerList.forEach(player => {
			console.log("x")
			this.playersData.push(new PlayerData(this, player));
		})
		this.handData = new HandData(this, this.gameData.hand);
	}
	
	handleUseCard(){
		let message = "USE";
		console.log(this)

		let tinted = false;
		for (var i = 0; i < this.handData.handSprites.length; i++) {
			if (this.handData.handSprites[i].isTinted) {
				tinted = true;
				message += "," + i;
			}
		}
		let playerLocal = "";
		this.playersData.forEach((playerData, index)=>{
			if (playerData.button.isActive){
				playerLocal += index + ";"
			}
		})
		playerLocal=playerLocal.slice(0,-1);
		message+= "," + playerLocal;
		if (!tinted) return;
		webSocket.send(message);
	}

 

	create() {

		this.onInit();
		this.createControlButtons();

		if (!this.handData.hand.isPlayable) {
			this.enableControlButtons(false);
		} else {
			this.enableControlButtons(true);
			
		}
	}

	update() {
		if (this.updateGame) {
			if (!this.handData.hand.isPlayable) {
				this.enableControlButtons(false);
			} else {
				this.enableControlButtons(true);
			}

			/*this.players.forEach(player => {
				if (player.isPlayable) {
					this.pointPlayablePlayer(player);
				}
			});
			*/
		}
		this.updateGame = false;

	}
	
	



	createControlButtons() {
		this.controlButtons.push(new Button(400, 850, "Wymień karty", "action", this, () => this.handleCardChange()));
		this.controlButtons.push(new Button(600, 850, "Nic nie rób", "action", this, () => this.handleSkipAction()));
		this.controlButtons.push(new Button(800, 850, "Użyj karty", "action", this, () => this.handleUseCard()));
	}

	enableControlButtons(value) {
		this.controlButtons.forEach(button => {
			button.setVisible(value);
		});
	}

	handleSkipAction() {
		webSocket.send("SKIP");
	}

	handleCardChange() {
		let message = "CHANGE";
		console.log(this)

		let tinted = false;
		for (var i = 0; i < this.handData.handSprites.length; i++) {
			if (this.handData.handSprites[i].isTinted) {
				tinted = true;
				message += "," + i;
			}
		}
		if (!tinted) return;
		webSocket.send(message);
	}


	pointPlayablePlayer(player) {

		if (this.textPlayablePlayer) {
			this.textPlayablePlayer.destroy();
		}
		this.textPlayablePlayer = this.add.text(player.posX, player.posY - 90, "V",
			{
				fontFamily: 'Arial',
				fontSize: '20px',
				color: '#fff'
			})

	}

	updateScene(gameData) {
		this.gameData = gameData;
		console.log(this.playersData);
		this.playersData.forEach((playerData, i)=>{
			console.log(this.playersData);
			console.log(this.gameData)
			playerData.setPlayer(this.gameData.game.playerList[i]);
		});
		this.handData.setHand(gameData.hand);
		this.updateGame = true;
	}





}




