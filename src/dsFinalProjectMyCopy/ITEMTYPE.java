package dsFinalProjectMyCopy;

import java.io.Serializable;

public enum ITEMTYPE implements Serializable {
	
		ALUMINUM ("TAXED"), 
		BAKERY ("NONTAX"),
		CHICKEN ("NONTAX"), 
		FISH ("NONTAX"),
		FRUIT("NONTAX"),
		GROCERY("NONTAX"),
		MAGAZINES("TAXED"),
		MEAT("NONTAX"),
		PAPERGOODS("TAXED");


		private TAXABLE symbol;



		private ITEMTYPE ( String symbol){

		     this.symbol = TAXABLE.valueOf(symbol);
		}



		public TAXABLE getSymbol(){
			return symbol;
		}
	


}
