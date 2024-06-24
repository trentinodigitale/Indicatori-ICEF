package c_elab.pat.pv21;

public class Pv21_Params {

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
	
	
	public static int pv_IDServizio_casalinghe 		= 9282;
	public static int pv_IDServizio_disoccupati 	= 9301;
	
	public static String anno = "2020";
	//CAMBIAMI 
	public static int pv_casa_IDRiferimento_1Trimestre = 22101; // CAMBIAMI - DA FARE vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	
	public static String pv_casa_lista_IDRelazioneParentela_figli = "32514,32515,32516,32517,32518"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_casa_lista_IDRelazioneParentela_richiedente = "32510"; 

	public static int pv_dis_IDRiferimento_1Trimestre = 22001; // CAMBIAMI - DA FARE vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	public static String pv_dis_lista_IDRelazioneParentela_figli = "32534,32535,32536,32537,32538"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_dis_lista_IDRelazioneParentela_richiedente = "32530"; 
}
