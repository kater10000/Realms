package net.krglok.productionrules;



public class ProductionEngine
{
    public  void executer(ProductionRule r)
	{
		if (r.canProduce())
		{
			r.produce();
			//product zur�ckgeben
		}else
		{
			r.getMissingIngredients();
			
			// jede Zutat auf Stack legen
		}
		
	}
	
	
	
	public ProductionEngine()
	{
		// rule auf den Stack
		
		// while (!stack.isEmpty)
		  //if stack.peek.canproduce -> produce, produkt in speicher, stacvk.pop
		  //else stack.peek.getmissing auf stack, bereits vorh zutataten reservieren
		
		//prodkt aus speicher zur�ckgeben
		
		
	}
	
}