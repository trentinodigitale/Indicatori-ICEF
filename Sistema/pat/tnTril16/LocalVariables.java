package c_elab.pat.tnTril16;

public class LocalVariables {
	
	//CAMBIAMI - VERIFICAMI: vanno verificate ogni anno 4800 come da delibera
	
	//Assegno_imposto
	public static final double importoMinimoObbligoMantenimento  = 4800; //4800 euro

	//C1_pens
	public static final double C_PNS = 1.0;
	
	//Figli_fam_numerose
	public static final double DF3 = 2000.0;  
	public static final double DF4 = 1500.0;  
	public static final double DF5 = 1000.0;  
	
	//Invalidi
	public static final double QBI = 5400.0;  
	
	
	public static final double DMI = 0.0;
	
	//Lavoratori
	// Deduzione per nuclei in cui i genitori lavorano fissata a 2.500 € (Art. 13 comma 5)
	public static final double DGL = 2500.0; 
	
	//Lavoro_fem
	// Ulteriore Deduzione individuale per lavoro femminile fissata a 1500 € (Art. 13-bis comma 1.b)
	public static final double DLF = 1500.0;
	
	//Monogenitore
	// Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5)
	// Da pannello Monogenitore alla data di presentazione della domanda
	public static final double DMG = 2500.0; 
	
	//PM
	// Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3)
	public static final double FIM = 5000.0;  
	
	//Canone
	// Deduzione massima individuale per canone e mutuo 4.000 €
	public static final double LDC = 4000.0;
	
	//PI
	// Franchigia Individuale di non dichiarabilità sui terreni non edificabili fissata a 4687,5 
	// di valore ai fini ICI equivalente a 50 € di reddito dominicale (Art. 16 comma 4)
	public static final double FIT = 4687.5;
	
	//Mediche
	// Deduzione massima individuale per spese mediche, funebri e di istruzione fissata a 2.000 € (Art. 13 comma 3)
	public static final double DMSPMFI = 2000.0;
	// Deduzione massima individuale per spese studenti universitari fissata a 2.000 €
	public static final double DMSPU = 2000.0;
	
	//Spese redditi
	public static final double C_IMP = 0.95;
	public static final double DMA = 1250.0;
	
	public static final double DMD = 2500.0;
	public static final double C_DIP = 0.9;
	
}
