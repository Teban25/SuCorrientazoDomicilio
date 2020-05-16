package com.co.s4n.SuCorrientazoDomiclio.util;

import java.util.HashMap;
import java.util.Map;

import com.co.s4n.SuCorrientazoDomiclio.model.Movement;

public class BussinesInfo {

	public enum CARDINAL_DIRECTIONS {
		N, S, E, O
	};

	public static final int ORDERS_BY_DELIVERY_IN_DRONE = 3;

	public static final Map<Character, Movement> MOVEMENTS_BY_DIRECTION = new HashMap<Character, Movement>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('N', new Movement(CARDINAL_DIRECTIONS.N, 1));
			put('S', new Movement(CARDINAL_DIRECTIONS.S, -1));
			put('O', new Movement(CARDINAL_DIRECTIONS.O, -1));
			put('E', new Movement(CARDINAL_DIRECTIONS.E, 1));
		}

	};

	public static final Map<Character, CARDINAL_DIRECTIONS> MOVES_TO_RIGHT = new HashMap<Character, CARDINAL_DIRECTIONS>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put('N', CARDINAL_DIRECTIONS.E);
			put('E', CARDINAL_DIRECTIONS.S);
			put('S', CARDINAL_DIRECTIONS.O);
			put('O', CARDINAL_DIRECTIONS.N);
		}
	};

	public static final Map<Character, CARDINAL_DIRECTIONS> MOVES_TO_LEFT = new HashMap<Character, CARDINAL_DIRECTIONS>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put('N', CARDINAL_DIRECTIONS.O);
			put('O', CARDINAL_DIRECTIONS.S);
			put('S', CARDINAL_DIRECTIONS.E);
			put('E', CARDINAL_DIRECTIONS.N);
		}
	};

	public static final Map<CARDINAL_DIRECTIONS, String> USER_DIRECTION_TRANSLATE = new HashMap<BussinesInfo.CARDINAL_DIRECTIONS, String>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		{
			put(CARDINAL_DIRECTIONS.N, "direccion Norte");
			put(CARDINAL_DIRECTIONS.S, "direccion Sur");
			put(CARDINAL_DIRECTIONS.E, "direccion Oriente");
			put(CARDINAL_DIRECTIONS.O, "direccion Occidente");
		}

	};
}
