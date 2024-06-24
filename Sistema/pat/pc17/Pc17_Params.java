package c_elab.pat.pc17;

public class Pc17_Params {
	
	//CAMBIAMI: va cambiata ogni anno
	public static String anno = "2016";  
	public static int pc_IDServizio_casalinghe 	= 9392;
	public static  int pc_IDServizio_disoccupati = 9397;

	
	//CAMBIAMI ANNUALMENTE
	public static int pc_casa_IDRiferimento_gennaio = 18201; 
	public static String pc_casa_lista_IDRelazioneParentela_figli = "28944, 28945, 28946, 28947, 28948"; //vedi tabella R_Relazioni_parentela 
	public static String pc_casa_lista_IDRelazioneParentela_parenti = "28949, 28950, 28951, 28952"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pc_casa_lista_IDRelazioneParentela_richiedente = "28940"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."

	public static int pc_dis_IDRiferimento_gennaio = 18301; 
	public static String pc_dis_lista_IDRelazioneParentela_figli = "28924, 28925, 28926, 28927, 28928"; //vedi tabella R_Relazioni_parentela 
	public static String pc_dis_lista_IDRelazioneParentela_parenti = "28929, 28930, 28931, 28932"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pc_dis_lista_IDRelazioneParentela_richiedente = "28920"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	
}