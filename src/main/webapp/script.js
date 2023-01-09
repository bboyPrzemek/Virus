jQuery(document).ready(function() {

	const assets = {
		'1': 'assets/organs/1.png',
		'2': 'assets/organs/2.png',
		'3': 'assets/organs/3.png',
		'4': 'assets/organs/4.png',
		'5': 'assets/organs/5.png',
		'6': 'assets/viruses/6.png',
		'7': 'assets/viruses/7.png',
		'8': 'assets/viruses/8.png',
		'9': 'assets/viruses/9.png',
		'10': 'assets/viruses/10.png',
		'11': 'assets/remedies/11.png',
		'12': 'assets/remedies/12.png',
		'13': 'assets/remedies/13.png',
		'14': 'assets/remedies/14.png',
		'15': 'assets/remedies/15.png',
		'16': 'assets/actions/16.png',
		'17': 'assets/actions/17.png',
		'18': 'assets/actions/18.png',
		'19': 'assets/actions/19.png',
		'20': 'assets/actions/20.png',
		'21': 'assets/others/1.png',
		'22': 'assets/others/2.png',
	};



	const arrangement = {
		2: ["el2"],
		3: ["el1", "el3"],
		4: ["el2", "el4", "el6"],
		5: ["el1", "el3", "el4", "el6"],
		6: ["el1", "el2", "el3", "el4", "el6"]
	}





	let webSocket;
	const host = window.location.host;

	function joinGame() {

		if (webSocket != undefined) {
			return
		};
		console.log(webSocket)
		let url = 'http://localhost:8080/ServerGame/rest/game/search/';
		let gameid = $('#gameId').val();
		let username = $('#nickname').val();
		url += gameid;



		$.ajax({
			url: url, //gdzie się łączymy
			method: "get", //typ połączenia, domyślnie get
		})
			.done(res => { //reaguję na odpowiedź - o tym poniżej
				if (res == "1") {//jesli istnieje gra w bazie
					webSocket = new WebSocket("ws://localhost:8080/ServerGame/basicEndpoint/" + gameid + "?clientId=" + "kutas" + "&username=" + username);
					webSocket.onmessage = function(message) {
						let data = JSON.parse(message.data);





						console.log(data)
						messageHandler(data);
					};


				}
			});
	}


	function showJoinGameForm() {
		$('.joinGameForm').show();
		$('.curtain').show();
	}

	function cancel() {
		$('.joinGameForm').hide();
		$('.newGameForm').hide();
		$('.curtain').hide();
	}

	function showNewGameForm() {
		$('.newGameForm').show();
		$('.curtain').show();
	}

	function onChange() {
		let cards = [];

		$('.cardCmp').each((i, e) => {
			if ($(e).hasClass("active")) {
				cards.push(i)
			}
		})


		let messageObj = {
			message: "CHANGE",
			cards: cards
		}
		console.log(cards)

		webSocket.send(JSON.stringify(messageObj));
		console.log(JSON.stringify(messageObj))
	}

	function useCard() {
		if ($('.active').length != 1) {
			return;
		}
		cardUseHandler();

	}

	function showPopup() {
		$('.popup').show();
		$('.curtain').show();

		if ($('.active').data().action == 'THIEF' || $('.active').data().action == 'EXCHANGE' ||
			$('.active').data().organ == 'SPECIAL') {
			$('#organSelect').css("display", "block");
		}
	}

	function confirmCardUsage() {



		let selectedPlayer = [];
		selectedPlayer.push(parseInt($('#playerSelect').find(":selected").val()));

		let zone = "";

		if ($('.active').data().action == 'THIEF' || $('.active').data().action == 'EXCHANGE' ||
			$('.active').data().organ == 'SPECIAL') {
			zone = $('#organSelect').find(":selected").val();
		}

		let card = [];
		$('.cardCmp').each((i, e) => {
			if ($(e).hasClass("active")) {
				card.push(i)
			}
		})

		console.log(card)

		let messageObj = {
			message: "USE",
			cards: card,
			players: selectedPlayer,
			zone: zone


		}

		console.log(messageObj)

		webSocket.send(JSON.stringify(messageObj));
		closePopup();

	}

	function cardUseHandler() {

		if ($('.active').data().action == 'GLOVE') {
			gloveCardHandler();
		}
		else {
			showPopup();
		}
	}

	function thiefCardHandler() {


	}

	function gloveCardHandler() {
		let card = [];
		card.push($('.active').index());
		let messageObj = {
			message: "USE",
			cards: card,
			players: [-1]
		}
		webSocket.send(JSON.stringify(messageObj));
	}




	function doNothing() {
		let messageObj = {
			"message": "SKIP"
		}
		webSocket.send(JSON.stringify(messageObj));
	}



	function createNewGame() {
		const numOfPlayers = $('#numPlayers').val();
		let url = 'http://localhost:8080/ServerGame/rest/game/new/' + numOfPlayers;

		$.ajax({
			url: url, //gdzie się łączymy
			method: "post", //typ połączenia, domyślnie get
		})
			.done(res => { //reaguję na odpowiedź - o tym poniżej
				webSocket = new WebSocket("ws://localhost:8080/ServerGame/basicEndpoint/" + res);
				webSocket.onmessage = function(message) {
					console.log(message);
				};
				//cancel();
				//	$('.wrapper').addClass('h-auto');
				//$('.form-wrapper').hide();
			});


	}

	function handleConnect(data) {


	}

	function handleStart(data) {
		loadGame(data);
	}

	function loadGame(data) {
		hideForm();
		updateHand(data);
		updateBoard(data);
		createPlayerPicklist(data);
		$('#game-container').css('display', 'flex');
	}



	function hideForm() {
		$('.wrapper').hide();
		$('.curtain').hide();
	}

	function handleGameUpdate(data) {
		updateHand(data);
		updateBoard(data);
	}

	function createPlayerPicklist(data) {
		data.playerData.forEach(e => {
			$('.popup #playerSelect').append($('<option>', {
				value: e.clientId,
				text: e.name
			}));

		})

	}

	function messageHandler(data) {
		switch (data.state) {

			case "CONNECT":
				handleConnect(data);
				break;
			case "START":
				handleStart(data);
				break;
			case "UPDATE":
				handleGameUpdate(data);
		}
	}

	function updateHand(data) {
		$(".cards").empty();
		let hand;


		data.playerData.forEach(e => {
			if (e.hand) {
				hand = e.hand;
				enableActionButtons(!e.isPlayable)
			}

		})

		hand.cards.forEach(e => {
			$('.cards').append(card(e));
		})
	}

	function updateBoard(data) {
		$('.grid-item').empty();
		let numOfPlayers = data.playerData.length;
		let classAssignment = arrangement[numOfPlayers];
		let index = 0;



		data.playerData.forEach((e) => {
			if (e.hand) {
				$('.el8').append(createZones(e.name));


				e.playerDeck.cardZones.forEach(zone => {
					console.log(zone)
					if (zone != 0) {
						zone.cards.forEach(card => {
							if (card.cardType == "ORGAN") {
								console.log('.el8 .' + zone.type)
								$('.el8 .' + zone.type).append($('<img />').attr('src', assets[card.uniqueTypeID]));
							} else if (card.cardType == "VIRUS") {
								$('.el8 .' + zone.type).append($('<img />', { class: "virusIcon" }).attr('src', assets['22']));
							} else if (card.cardType == "REMEDY") {
								if ($('.el8 .' + zone.type + " .remedyIcon").length == 0) {
									$('.el8 .' + zone.type).append($('<img />', { class: "remedyIcon" }).attr('src', assets['21']));
								} else {
									$('.el8 .' + zone.type).append($('<img />', { class: "remedyIcon2" }).attr('src', assets['21']));
								}
							}

						})
					}

				})

			} else {
				$("." + classAssignment[index]).append(createZones(e.name));
				e.playerDeck.cardZones.forEach(zone => {

					if (zone != 0) {
						zone.cards.forEach(card => {
							if (card.cardType == "ORGAN") {
								$("." + classAssignment[index] + ' .' + zone.type).append($('<img />').attr('src', assets[card.uniqueTypeID]));
							} else if (card.cardType == "VIRUS") {
								$("." + classAssignment[index] + ' .' + zone.type).append($('<img />', { class: "virusIcon" }).attr('src', assets['22']));
							} else if (card.cardType == "REMEDY") {
								if ($("." + classAssignment[index] + ' .' + zone.type + " .remedyIcon").length == 0) {
									$("." + classAssignment[index] + ' .' + zone.type).append($('<img />', { class: "remedyIcon" }).attr('src', assets['21']));
								} else {
									$("." + classAssignment[index] + ' .' + zone.type).append($('<img />', { class: "remedyIcon2" }).attr('src', assets['21']));
								}
							}
						})
					}
				})

				index++;
			}
		})
	}

	function card(cardData) {
		$cardCmp = $('<div />', {
			class: "cardCmp", 'data-action': cardData.actionType, 'data-organ': cardData.organType, 'data-type': cardData.cardType
		})
		$cardCmp.append($('<img />').attr('src', assets[cardData.uniqueTypeID]));

		return $cardCmp;
	}

	function createZones(playerName) {
		$zoneContainer = $('<div />', {
			class: "zoneContainer",
		});

		$nick = $('<div />', {
			class: "nick", text: playerName
		});

		$zoneContainer.append($nick);

		$cardsContainer = $('<div />', {
			class: "cardsContainer"
		});

		$cardsContainer.append($('<div />', { class: "heart" }))
		$cardsContainer.append($('<div />', { class: "bone" }))
		$cardsContainer.append($('<div />', { class: "maw" }))
		$cardsContainer.append($('<div />', { class: "brain" }))

		$zoneContainer.append($cardsContainer);

		return $zoneContainer;
	}

	function enableActionButtons(value) {
		$('.action-buttons button').prop('disabled', value);
	}

	function closePopup() {
		$('.popup').hide();
		$('.curtain').hide();
		$('#organSelect').hide();

	}





	$('.hand').on('contextmenu', '.cardCmp', function(e) {
		e.preventDefault();
		let select = $('.actionSelect');
		if ($(select).length) {
			$(select).remove();
			$(e.currentTarget).append($('<select />', { class: "actionSelect" }))
		} else {
			$(e.currentTarget).append($('<select />', { class: "actionSelect" }))
		}

		$('.nick').each((element, val) => {
			$('.actionSelect').append($('<option>', {
				value: element,
				text: $(val).text()
			}));
		});
	})

	$(document).click(function(event) {
		if (!$(event.target).closest(".actionSelect").length) {
			$('.actionSelect').remove();
		}

	});

	function selectCard() {
		if (!$('.action-buttons button').prop('disabled')) {
			$(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active")
		}
	}


	$('#confirmCardUsage').on('click', confirmCardUsage)
	$('.hand').on('click', '.cardCmp', selectCard);
	$('#joinBtn').on("click", showJoinGameForm);
	$('#connectBtn').on("click", joinGame);
	$('#cancelPopup').on("click", closePopup);
	$('#cancelBtn').on("click", cancel);
	$('#changeCards').on("click", onChange);
	$('#useCard').on("click", useCard);
	$('#doNothing').on("click", doNothing);

	//})();







	/*
		var webSocket = new WebSocket(
				"ws://localhost:8080/ServerGame/basicEndpoint");
		var echoText = document.getElementById("echoText");
		echoText.value = "";
		var message = document.getElementById("message");
		webSocket.onopen = function(message) {
			wsOpen(message);
		};
		webSocket.onmessage = function(message) {
			wsGetMessage(message);
		};
		webSocket.onclose = function(message) {
			wsClose(message);
		};
		webSocket.onerror = function(message) {
			wsError(message);
		};
		function wsOpen(message) {
			echoText.value += "Connected ... \n";
		}
		function wsSendMessage() {
			webSocket.send(message.value);
			echoText.value += "Message sended to the server : " + message.value
					+ "\n";
			message.value = "";
		}
		function wsCloseConnection() {
			webSocket.close();
		}
		function wsGetMessage(message) {
			echoText.value += "Message received from to the server : "
					+ message.data + "\n";
		}
		function wsClose(message) {
			echoText.value += "Disconnect ... \n";
		}
	
		function wsError(message) {
			echoText.value += "Error ... \n";
		}
		
		*/

});

