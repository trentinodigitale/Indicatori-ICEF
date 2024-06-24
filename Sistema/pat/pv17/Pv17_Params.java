package c_elab.pat.pv17;

public class Pv17_Params {

	//CAMBIAMI: va cambiata ogni anno
	public static int pv_IDServizio_casalinghe 		= 9292;
	public static int pv_IDServizio_disoccupati 	= 9297;
	
	public static String anno = "2016";
	
	public static int pv_casa_IDRiferimento_1Trimestre = 18101; //vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	public static String pv_casa_lista_IDRelazioneParentela_figli = "28514,28515,28516,28517,28518"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_casa_lista_IDRelazioneParentela_richiedente = "28510"; 

	public static int pv_dis_IDRiferimento_1Trimestre = 18001; //vedi 1 trimestre nella tabella PNS_tp_riferimenti per il servizio in questione
	public static String pv_dis_lista_IDRelazioneParentela_figli = "287534,28535,28536,28537,28538"; //vedi tabella r_relazione_parentela per il servizio in questione, formato "id1, id2, id3, ..."
	public static String pv_dis_lista_IDRelazioneParentela_richiedente = "28530"; 
}
