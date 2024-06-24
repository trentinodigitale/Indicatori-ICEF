package c_elab.pat.pc20;

public class Pc20_Params  {
	
	//CAMBIAMI: va cambiata ogni anno - FATTO
	// DA VERIFICARE PERCHE' NON E' IN USO LA PARTE DEI DUPLICATI Disoccupati pensione complementare 2017
/** 
9292	9292	Casalinghe pensione vecchiaia 2018			-	9293	9293
9297	9297	Disoccupati pensione vecchiaia 2018			-	9298	9298
9392	9392	Casalinghe pensione complementare 2018		-	9393	9393
	
	SELECT      ID_riferimento, ID_servizio, riferimento
	 FROM            PNS_tp_riferimenti WHERE    
    (ID_servizio IN (9392, 9292, 9297)) AND (riferimento = N'gennaio')
    
SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9393) AND (parentela IN (N'FE', 'FR', 'FC', 'AR', 'AC')) 
SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9393) AND (parentela IN (N'Parente o affine di 1° grado', 'Parente o affine di 2° grado', 'Parente  affine di  3° o 4° grado', 'minore non affidato a tempo pieno'))
SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9393) AND (parentela = N'Richiedente')
SELECT * FROM R_Relazioni_parentela	WHERE (ID_servizio = 9393) AND (parentela IN (N'Richiedente'))	

 */
	public static String anno = "2019";  
	public static int pc_IDServizio_casalinghe 	= 9395;
//	public static int pc_IDServizio_disoccupati = 9397;

	//NON IN USO - NON DUPLICARE Disoccupati pensione complementare 2017
	
	//CAMBIAMI ANNUALMENTE - DA FARE
	public static int pc_casa_IDRiferimento_gennaio = 21201; //CAMBIAMI - DA FARE

	public static String pc_casa_lista_IDRelazioneParentela_figli = "31944, 31945, 31946, 31947, 31948"; //vedi tabella R_Relazioni_parentela 
	public static String pc_casa_lista_IDRelazioneParentela_parenti = "31949, 31950, 31951, 31952"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pc_casa_lista_IDRelazioneParentela_richiedente = "31940"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."

//	public static int pc_dis_IDRiferimento_gennaio = 18301; 
//	public static String pc_dis_lista_IDRelazioneParentela_figli = "28924, 28925, 28926, 28927, 28928"; //vedi tabella R_Relazioni_parentela 
//	public static String pc_dis_lista_IDRelazioneParentela_parenti = "28929, 28930, 28931, 28932"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
//	public static String pc_dis_lista_IDRelazioneParentela_richiedente = "28920"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	
	
	
}