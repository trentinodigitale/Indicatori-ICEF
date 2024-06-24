package c_elab.pat.pv22;

public class Pv22_Params {

	//CAMBIAMI: va cambiata ogni anno
	
	/** 
	9292	9292	Casalinghe pensione vecchiaia 2018			-	9293	9293
	9297	9297	Disoccupati pensione vecchiaia 2018			-	9298	9298
		
	 SELECT ID_riferimento, riferimento FROM   PNS_tp_riferimenti	WHERE  (ID_servizio = 9293) AND (riferimento = N'1 trimestre')
	 SELECT D_relazione_parentela, parentela FROM R_Relazioni_parentela	WHERE (ID_servizio = 9293) AND (parentela IN (N'FE', 'FR', 'FC', 'AR', 'AC')) 
	 SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9293) AND (parentela IN (N'Richiedente'))	
	 
	 SELECT ID_riferimento, riferimento FROM   PNS_tp_riferimenti	WHERE  (ID_servizio = 9298) AND (riferimento = N'1 trimestre')
	 SELECT D_relazione_parentela, parentela FROM R_Relazioni_parentela	WHERE (ID_servizio = 9298) AND (parentela IN (N'FE', 'FR', 'FC', 'AR', 'AC')) 
	 SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9298) AND (parentela IN (N'Richiedente'))	
	 

	 */
	
	
	public static int pv_IDServizio_casalinghe 		= 9283;
	public static int pv_IDServizio_disoccupati 	= 9302;
	
	public static String anno = "2021";
	//CAMBIAMI 
	public static int pv_casa_IDRiferimento_1Trimestre = 23101; // CAMBIAMI - DA FARE vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	
	public static String pv_casa_lista_IDRelazioneParentela_figli = "33514,33515,33516,33517,33518"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_casa_lista_IDRelazioneParentela_richiedente = "33510"; 

	public static int pv_dis_IDRiferimento_1Trimestre = 23001; // CAMBIAMI - DA FARE vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	public static String pv_dis_lista_IDRelazioneParentela_figli = "33534,33535,33536,33537,33538"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_dis_lista_IDRelazioneParentela_richiedente = "33530"; 
}
