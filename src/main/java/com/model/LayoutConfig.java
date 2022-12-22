package com.model;


public class LayoutConfig {

	public static int[][] getConfig(int index) {

		int[][][] positionsArray = { 
				{ 
					{400, 100},
					{400, 540}
				}, 
				
				{ 
					{120, 100}, 
					{720, 100}, 
					{400, 540}
				},
				
				{ 
					{120, 100}, 
					{720, 100}, 
					{120, 540}, 
					{720, 540} 
				},
				
				{ 
					{10,  100}, 
					{430, 100}, 
					{840, 100}, 
					{120, 540}, 
					{720, 540} 
				},
				
				{ 
					{10,  100}, 
					{430, 100}, 
					{840, 100}, 
					{10,  540},
					{430, 540}, 
					{840, 540} 
				}

		};
		
		return positionsArray[index];
	}

}
