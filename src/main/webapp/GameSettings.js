const CANVAS_WIDTH = 1200;
const CANVAS_HEIGHT = 900;

const CARD_ZONES_COLORS = [0xc40010, 0x07e337, 0xeaed1c, 0x07b0e3]; //heart maw bone brain
const CARD_TYPES = ["heart", "maw", "bone", "brain"];
const PLAYERS_POSITIONS = new Map(

	[
		[2,
			[
				[400, 10], [400, 440]
			]
		],

		[3, 
			[
				[120, 10], [720, 10], [400, 440]
			]
		],

		[4, 
			[
				[120, 10], [720, 10], [120, 440], [720, 440]
			]
		],

		[5, 
			[
				[10, 10], [430, 10], [840, 10], [120, 440], [720, 440]
			]
		],

		[6, 
			[
				[10, 10], [430, 10], [840, 10], [10, 440], [430, 440], [840, 440]
			]
		]
	]);
