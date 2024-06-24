package c_elab.pat.edilGC;

import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;

import it.clesius.clesius.util.Sys;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/** idoneità dichiarazioni 08
 * @author s_largher
 */
public class Icef08 extends Icef08Tables {

	private static Log log = LogFactory.getLog( Icef08.class );
	
	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
		super.reset();
	}

    int tot_componenti = 0;
    //Numero dei componenti del nucleo	1	2		3		4		5		Per ogni componente in più 
	//C_EQV(n) 							1	1,57	2,04	2,46	2,85	0,35
    double c_eqv_val [] = new double[]{0,1,1.57,2.04,2.46,2.85,0.35};
    
    private double aggiusta = 0.01;
    private double round = 1.0;
    
	private double round_euro = -10.0;
	
    
    //DMG = Deduzione per nuclei monogenitori fissata a 2.500 € (Art. 13 comma 5) 
      public static final double DMG = 2500.0; 
      //DGL = Deduzione per nuclei in cui i genitori lavorano fissata a 2.500 € (Art. 13 comma 5)
  	public static final double DGL = 2500.0; 
  	//DF3 = Deduzione per il terzo figlio fissata a 2.000 € (Art. 13 comma 5) 
  	//DF4 = Deduzione per il quarto figlio fissata a 1.500 € (Art. 13 comma 5)
  	//DF5 = Deduzione per il quinto figlio e successivi fissata a 1.000 € (Art. 13 comma 5)
  	public static final double DF3 = 2000.0;  
  	public static final double DF4 = 1500.0;  
  	public static final double DF5 = 1000.0;
      //DMS = Deduzione massima individuale per spese mediche, funebri e di istruzione fissata a 2.000 € (Art. 13 comma 3)
  	public static final double DMS = 2000.0;
  	//QBI = Quota base per i soggetti non autosufficienti fissata a 5.400 € (Art. 13 comma 5)
      public static final double QBI = 5400.0;  
  	//DMI = Deduzione massima per ogni soggetto non autosufficiente fissata a 10.800 € (Art. 13 comma 5)
  	public static final double DMI = 10800.0;  
      
  	
  	//CPP(j) = coefficiente di valutazione del patrimonio del componente j in base alla relazione di parentela con il beneficiario fissato dalle singole politiche di settore (Art. 18 comma 1) 
  	//FIM = Franchigia Individuale di non dichiarabilità sul patrimonio Mobiliare fissata a 5.000 € (Art. 15 comma 3) 
  	private static final double FIM = 5000.0;  
  	//FIT = Franchigia Individuale di non dichiarabilità sui terreni non edificabili fissata 4.687,5 di valore ai fini ICI equivalente a 50 € di reddito dominicale (Art. 16 comma 4) 
  	 
    private double assegni;
    private double c1_aut;
    private double c1_dip;
    private double c1_pens;
    private double c2_agr;
    private double c3_imp;
    private double c4_part;
    private double c5_altri;
    private double canone;
    private double figli_fam_numerose;
    private double imposte;
    private double invalidi;
    private double lavoratori;
    private double mediche;
    private double monogenitore;
    private double mutuo;
    private double n_componenti;
    private double p_complementare;
    private double pi;
    private double pm;
    private double previdenza;
    private double res;
    
    //defaultIN
    //AL1 = prima ALiquota di conversione del patrimonio in reddito equivalente 
    private double AL1	 = 0.05;
    //AL2 = seconda ALiquota di conversione del patrimonio in reddito equivalente
    private double AL2 = 	0.2;
    //AL3 = terza ALiquota di conversione del patrimonio in reddito equivalente
    private double AL3 = 	0.6;
    private double Dettagli =	0;
    //FAR = Franchigia sul valore dell’Abitazione di Residenza 
    private double FAR	 =9999999;
    //FPM = Franchigia sul Patrimonio Mobiliare familiare
    private double FPM =	40000;
    //LDC = Limite di Detrazione per le spese del Canone di locazione o per la quota interessi sul mutuo ipotecario sull'abitazione di residenza fissato annualmente in base all'ammontare massimo complessivo degli interessi riconosciuti fiscalmente nel caso di mutuo stipulato per l'acquisto dell'abitazione principale (Art. 13 comma 4)
    private double LDC	 =4000;
    //LS1 = Limite Superiore del primo scaglione sul patrimonio mobiliare e immobiliare familiare eccedente le franchigie FAR e FPM 
    private double LS1	 =30000;
    //LS2 = Limite Superiore del secondo scaglione sul patrimonio mobiliare e immobiliare familiare eccedente le franchigie FAR e FPM
    private double LS2 =	90000;
    private double RIF	 =50000;
    private double Xml =	0;
    
    //C_NodOUT
    private double C_RIF_ICEF;
    private double df;
    private double icef;
    private double pf;
    private double rf;
    private double rl;
    private double vf = 0.0;
    private double vpf;
    private double vrf;
    
	//C_PR(j) = coefficiente di valutazione del reddito del componente j in base alla relazione di parentela con il richiedente beneficiario fissato dalle singole politiche di settore (Art. 18 comma 1)
	//C_DIP = coefficiente per la valutazione delle spese di produzione del reddito da dipendente e assimilato fissato a 0,9 (Art. 13 comma 5)
	//C_IMP = coefficiente per la valutazione delle spese di produzione del reddito da lavoro autonomo o impresa fissato a 0,95 (Art. 13 comma 5)
	//C_PNS = coefficiente per la valutazione delle spese di produzione del reddito da pensione fissato a 1 (Art. 13 comma 5)
	public static final double C_DIP = 0.9;
	public static final double C_IMP = 0.95;
	public static final double C_PNS = 1.0;
	
	
	public void setValues() {
    	
    	setAssegni();
    	
    	setC1_aut();
    	c1_aut = Sys.round( getC1_aut() - aggiusta, round);
    	
    	setC1_dip();

    	setC1_pens();
    	
    	setC2_agr();
    	c2_agr = Sys.round(getC2_agr()- aggiusta, round);
    	
    	setC3_imp();
    	c3_imp =  Sys.round(getC3_imp(), round); //-0.5, round);
    	
    	setC4_part();
    	
    	setC5_altri();
    	c5_altri = Sys.round(getC5_altri()-aggiusta, round);
    	
    	setCanone();
    	
    	setFigli_fam_numerose();
    	
    	setImposte();
    	
    	setInvalidi();
    	
    	setLavoratori();
    	
    	setMediche();
    	
    	setMonogenitore();
    	
    	setMutuo();
    	
    	setN_componenti();
    	n_componenti = Sys.round(getN_componenti(), 1);
    	
    	setP_complementare();
    	
    	setPI();
    	
    	setPM();
    	
    	setPrevidenza();
    	previdenza = Sys.round(getPrevidenza()-0.5, 1);
    	
    	setRes(true);
    	
    	/*
    	tot_componenti = 0;
    	int row = 1;
    	while( row <= getComponenti().getRows() ) {
    		tot_componenti += 1;
    		row++;
    	}   	
    	*/
    	return;
    }
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {

        try {
        	
        	setValues();
        	
        	vf = getVF();
        	rl = getRL();
        	df = getDF();
        	rf = getRF(rl, df);
			pf = getPF();
			vrf = getVRF();
			vpf = getVPF();
			
			// ICEF = Indicatore della condizione economica familiare
			//Calcolo ICEF
			//ICEF = VRF + VPF - VRF * VPF
C_RIF_ICEF    =  2.040*icef * RIF;

			icef =  vrf + vpf - vrf * vpf;

			C_RIF_ICEF    =  2.040*icef* RIF;
		    //double EQV_ICEF    =  icef * RIF * VF;
			
		    icef = Sys.round(icef,  -10000.0);
		    
		    //reset();
		    
            return icef;
        } catch(NullPointerException n) {
        	log.error("Null pointer in " + getClass().getName() + ": " + n.toString());
        	n.printStackTrace();
            return 0.0;
        } catch (NumberFormatException nfe) {
        	log.error("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
        	nfe.printStackTrace();
            return 0.0;
        }
    }
    
	
	private double getVF() {
		//VF = Valutazione del peso Familiare ai fini ICEF
		//C_EQV(n) = coefficiente della scala d'equivalenza per numero componenti del nucleo familiare = n (Art. 21 comma 2)
		//Calcolo VF
		//VF = C_EQV(n)	
		//dove n è il numero di componenti del nucleo familiare.
	
		tot_componenti = new Double(n_componenti).intValue();
		
		// +0.35 x comp
		if( n_componenti>5 ) {
			vf = c_eqv_val[5];
			vf += c_eqv_val[6] * ( n_componenti - 5 );
		}else {
			vf = c_eqv_val[ tot_componenti ];
		}
		
		return vf;
	}
	
	
	private double getRL() {
			//RL - Reddito familiare Lordo pesato
			//C1_pens = SUM C1(i,j) * C_PR(j);	C1_pensione = C1_pens * C_PNS
			//C1_dip = SUM C1(i,j) * C_PR(j); C1_dipendente = C1_dip * C_DIP
			//C1_aut = SUM C1(i,j) * C_PR(j); C1_autonomo = C1_aut * C_IMP
			//C2_agr = SUM [quantità(i,j) * redditività_unitaria(i,j,k) - costi(i,j)] * quota(i,j) * C_PR(j) ; 	C2_agricolo = C2_agr * C_IMP
			//C3_imp = SUM C3(i,j) * C_PR(j) ; C3_impresa = C3_imp * C_IMP
			//C4_part_uf = SUM [ C4_utile_fiscale(i,j) * C4_quota(i,j) ] * C_PR(j) ;
			//C4_part_ud = SUM [ C4_utile_distribuito(i,j) ] * C_PR(j) ;
			//C4_partecipazione = (C4_part_uf + C4_part_ud) * C_IMP
			//C5_altri = SUM C5(i,j) * C_PR(j)
		
			//RL = C1_pensione + C1_dipendente + C1_autonomo + C2_agricolo + C3_impresa + C4_partecipazione + C5_altri

		double pens    =  c1_pens;
		double dip    =  c1_dip;
		double aut    =  c1_aut + c3_imp + c4_part + c2_agr;
		double ra    =  C_IMP*aut;
		double rd    =  C_DIP*dip;
		double rp    =  C_PNS*pens;
		
		rl    =  rd + rp + ra + c5_altri;
	
		rl = Sys.round( rl , -100.0 ); //Sys.round(DF, round_euro);
		
		return rl;
	}
	
	private double getRF(double rl, double df) {
		//RF = Reddito Familiare valutabile ai fini ICEF
	
		//RF = Max [ 0 ; RL – DF ]
		rf =  java.lang.Math.max( 0 , rl - df );
		rf = Sys.round( rf - 0.001, -100.0 );
		return rf;
	}
	
	
	private double getDF() {
			//Deduzione Non Autosufficiente =  Min ( Deduzione Massima Soggetto ; Max ( Coefficiente Classe Non Autosufficiente * Quota Base ; Spesa Assistenza Medica ) )
			//DNA(j) = Min( DMI ; Max ( CNA(j) * QBI ; SNA(j) ) )
			//DFD = SUM D(i,j) * C_PR(j)
		double canone_mutuo = Math.min(  LDC,  canone+mutuo );
		double d = assegni + canone_mutuo + imposte + p_complementare + previdenza;
			//DFD(j) = Min( DMS * C_PR(j) ; SUM D(i,j) * C_PR(j) )
		//dfd = //Math.min( mediche,  d );
		double dfd = mediche + d;
			//DF = DFD + DMG + DGL + DF3 + DF4 + DF5 + DNA
		df = dfd + monogenitore + lavoratori + figli_fam_numerose + invalidi;
		
		df = Sys.round( df - aggiusta, round_euro ); //Sys.round(df, round_euro);
		
		return df;
	}
	
	private double getPF() {
			//PF = Patrimonio Familiare valutabile ai fini ICEF
			//R = SUM F1(i,j) * quota(i,j) * C_PP(j)
			//RES = Max [ 0 ; R – FAR ]
	//ystem.out.println( " res "+res);
	//pof = Math.max( 0, res + pi + pm );
	//setOutPrint( " pof "+pof);
	//pf = Math.max( 0, Sys.round(( (pof * AL1) + ((pof - LS1) * AL2) + ((pof - LS2) * AL3) ) , 0 ) );
	//setOutPrint( " pf "+pf);
	
			//res = java.lang.Math.max( 0, res - FAR );
			//PI = SUM F2(i,j) * quota(i,j) * C_PP(j)
		
			//M = Max[ 0 , SUM E(i,j) - FIM ] * C_PP(j)
			//PM = Max [ 0 ; M – FPM ]
		//pm = Math.max( 0, pm - FPM);
			//POF = RES + PI + PM
		//pi = Math.max(0, pi - FIM);
	
	
		double val_pi = 0;
		double val_pm = 0;
		
		double pi_netto = Math.max(0, pi);
		
		//Se 0 < POF <= LS1			allora			PF = POF * AL1
		if( 0 < pi_netto && pi_netto <= LS1 ) {
			val_pi = Math.max( 0, (pi_netto * AL1) );
			//Se LS1 < POF <= LS2		allora			PF = POF * AL1 + (POF – LS1) * AL2
		}else if( LS1 < pi_netto && pi_netto <= LS2 ) {
			val_pi = Math.max(0, (LS1 * AL1) + ( (pi_netto - LS1) * AL2) );
			//Se POF> LS2				allora			PF = POF * AL1 + (POF – LS1) * AL2 + (POF – LS2) * AL3
		}else if( pi_netto > LS2 ) {
			val_pi = Math.max(0, (LS1 * AL1) + ((LS2-LS1) * AL2) + ( (pi_netto - LS2) * AL3 ) );
		}
		

		double pm_netto = Math.max(0, (pm - FPM) );
		//Se 0 < POF <= LS1			allora			PF = POF * AL1
		if( 0 < pm_netto && pm_netto <= LS1 ) {
			val_pm = Math.max( 0, (pm_netto * AL1) );
			//Se LS1 < POF <= LS2		allora			PF = POF * AL1 + (POF – LS1) * AL2
		}else if( LS1 < pm_netto && pm_netto <= LS2 ) {
			val_pm = Math.max(0, (LS1 * AL1) + ( (pm_netto - LS1) * AL2) );
			//Se POF> LS2				allora			PF = POF * AL1 + (POF – LS1) * AL2 + (POF – LS2) * AL3
		}else if( pm_netto > LS2 ) {
			val_pm = Math.max(0, (LS1 * AL1) + ((LS2-LS1) * AL2) + ( (pm_netto - LS2) * AL3 ) );
			//val_pm = Math.max(0, (pm_netto * AL1) + (pm_netto-LS1 * AL2) + ( (pm_netto - LS2) * AL3 ) );
		}
		
		double pf = val_pi + val_pm;//getCalcoloPF();
		return pf;
			
	}
	
	private double getVRF() {
		// RIF = reddito di RIFerimento (Art. 21 comma 3)
		
		//VRF = Valutazione del Reddito Familiare (il flusso reddituale familiare)
		//VRF = min(1 ; RF / (RIF * VF) )
		
		vrf = ( rf / (RIF * vf )) ;
		vrf = Math.min( 1, Sys.round( vrf -0.0000000000001, -10000000000.0 )  );
		
		return vrf;
	}
	

	private double getVPF() {
		//VPF = Valutazione del patrimonio familiare (lo stock patrimoniale familiare)
		//VPF = min(1 ; PF / (RIF * VF) )

		vpf = Sys.round((pf / (RIF * vf) )- 0.0000000000001,  -10000000000.0 );
		return vpf;
	}
	

	
    public void setAssegni() {

		if (getDetrazioni() == null)
			this.assegni = 0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("ASM") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.assegni =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.assegni =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.assegni =  0.0;
		}
	}
    
    public void setC1_aut() {

    	if(getC_1() == null){
    		this.c1_aut =  0.0;
    	}
    	double round = 1.0;
	    double tot = 0.0;
	
		try {
			for (int i = 1; i <= getC_1().getRows(); i++) {
				if ( ((String) getC_1().getElement(i, 1)).equals("ANP") 
					|| ((String) getC_1().getElement(i, 1)).equals("DIV") ) {
					tot = tot + Sys.round(new Double((String) getC_1().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getC_1().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.c1_aut =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c1_aut =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c1_aut =  0.0;
		}
    }
    
    public void setC1_dip() {

		if (getC_1() == null)
			this.c1_dip =  0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
		try {
			for (int i = 1; i <= getC_1().getRows(); i++) {
				if ( ((String) getC_1().getElement(i, 1)).equals("DIP") ) {
					tot = tot +  Sys.round(new Double((String) getC_1().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getC_1().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.c1_dip = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c1_dip = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c1_dip = 0.0;
		}
	}
    
    public void setC1_pens() {

    	if ( getC_1() == null)
    		this.c1_pens = 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getC_1().getRows(); i++) {
				if ( ((String) getC_1().getElement(i, 1)).equals("PNS") ) {
					tot = tot + Sys.round(new Double((String) getC_1().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getC_1().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.c1_pens = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c1_pens = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c1_pens = 0.0;
		}
	}
    
    public void setC2_agr() {

		if (getC_2() == null) {
			this.c2_agr = 0.0;
		}

		double tot = 0.0;
		
		try {
			for (int i = 1; i <= getC_2().getRows(); i++) {
				double agricolo = (java.lang.Math.max (0,
						// quantità *
						new Double((String) getC_2().getElement(i, 2)).doubleValue() *
						// importo -
						new Double((String) getC_2().getElement(i, 3)).doubleValue() -
						// costo locazione terreni -
						new Double((String) getC_2().getElement(i, 4)).doubleValue() -
						// costo dipendenti
						new Double((String) getC_2().getElement(i, 5)).doubleValue()
				)) * 
				// quota possesso
				(new Double((String) getC_2().getElement(i, 6)).doubleValue()) / 100.0;
				//            agricolo * peso reddito
				tot = tot + (agricolo * new Double((String) getC_2().getElement(i, 1)).doubleValue() / 100.0);
			}
			this.c2_agr =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c2_agr =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c2_agr =  0.0;
		}
	}
    
    public void setC3_imp() {

		if (getC_3() == null) {
			this.c3_imp =  0.0;
		}

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getC_3().getRows(); i++) {
				tot = tot + Sys.round(new Double((String) getC_3().getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) getC_3().getElement(i, 1)).doubleValue() / 100.0;
			}
			this.c3_imp = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c3_imp = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c3_imp = 0.0;
		}
	}
    
    public void setC4_part() {

    	if (getC_4() == null)
    		this.c4_part = 0.0;

		double tot = 0.0;
		double round = 1.0;

		try {
			for (int i = 1; i <= getC_4().getRows(); i++) {
				tot = tot + (
						java.lang.Math.max(
								// reddito dichiarato
								Sys.round(new Double((String) getC_4().getElement(i, 2)).doubleValue() - aggiusta, round), 
								// utile fiscale * quota
								Sys.round(new Double((String) getC_4().getElement(i, 3)).doubleValue() - aggiusta, round) * (new Double((String) getC_4().getElement(i, 4)).doubleValue()) / 100.0 
						)
				) * new Double((String) getC_4().getElement(i, 1)).doubleValue() / 100.0;
			}
			this.c4_part = tot;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c4_part = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c4_part = 0.0;
		}
	}
    
    public void setC5_altri() {
    	
    	if (getC_5() == null)
    		this.c5_altri = 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getC_5().getRows(); i++) {
				tot = tot + Sys.round(new Double((String) getC_5().getElement(i, 2)).doubleValue() - aggiusta, round) * new Double((String) getC_5().getElement(i, 1)).doubleValue() / 100.0;
			}
			this.c5_altri =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.c5_altri =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.c5_altri =  0.0;
		}
    }
    
    public void setCanone() {

		if (getDetrazioni() == null)
			this.canone =  0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("CNL") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.canone =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.canone =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.canone =  0.0;
		}
	}
    
    public void setFigli_fam_numerose() {
    	
		if (getParticolarita() == null)
			this.figli_fam_numerose =  0.0;

		if (getParticolarita().getRows() == 0)
			this.figli_fam_numerose =  0.0;
		
		double figli = 0.0;

		try {
			// data riferimento può essere:      TODO: verificare quale va bene !!!!
			// 1) fino al 31-12 anno redditi
			// 2) fino alla data domanda
			
			// 1) fino al 31-12 anno redditi
			Calendar dataRif = Calendar.getInstance();
			int yearRif = getParticolarita().getInteger(1,5);
			int monthRif = 12;
			int dayRif = 31;
			dataRif.set(yearRif, monthRif -1, dayRif);
			
			// 2) fino alla data domanda
            //try {
    		//	dataRif = records.getCalendar(1, 6);
            //} catch (Exception e) {
            //	System.out.println("ERRORE in " + getClass().getName() + ": "	+ e.toString());
            //}
			
			for (int i = 1; i <= getParticolarita().getRows(); i++) {
				Calendar dataNascita = Calendar.getInstance();
	            try {
					dataNascita = getParticolarita().getCalendar(i, 3);
	            } catch (Exception e) {
	            	dataNascita.add(Calendar.YEAR, - 100);
	            	System.out.println("ERRORE in " + getClass().getName() + ": "	+ e.toString());
	            }
	            double pesoComponente = new Double((String) getParticolarita().getElement(i, 8)).doubleValue();
	            if(pesoComponente>0)
	            {
					if ( getParticolarita().getBoolean(i, 4) ) {                        // se studente fino a 25 anni
						figli = figli + isFiglio(dataNascita, dataRif, 25);
					} else {                                                 // per i non studenti fino a 18 anni
						figli = figli + isFiglio(dataNascita, dataRif, 18);
					}
	            }
			}
			
			if (figli < 3) {
				this.figli_fam_numerose =  0.0;
			} else if (figli == 3) {
				this.figli_fam_numerose =  DF3;
			} else if (figli == 4) {
				this.figli_fam_numerose =  DF3 + DF4;
			} else {
				this.figli_fam_numerose =  DF3 + DF4 + (figli - 4) * DF5;
			}
		} catch (NullPointerException n) {
			n.printStackTrace();
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.figli_fam_numerose =  0.0;
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.figli_fam_numerose =  0.0;
		}
	}
    
    
    public int isFiglio(Calendar dataNascita, Calendar dataRif, int eta) {
		
		Calendar tmpDate = null;
		
		//int IDdomandaInt = 0;
		//modalità normale con domanda
		//IDdomandaInt = Integer.parseInt(IDdomanda);
		
//System.out.println( "dt nascita "+ dataNascita.getTime().toString() );
//System.out.println( "dt rif "+ dataRif.getTime().toString() );
//System.out.println( "eta "+ eta );
		
		tmpDate = (Calendar)dataRif.clone();
		tmpDate.add(Calendar.YEAR, - eta);
		
//System.out.println( "dt calc " +tmpDate.getTime().toString() );
			
		if ( dataNascita.after(tmpDate) )	{
			return 1;
		} else {
			return 0;
		}
	}
    
    public void setImposte() {

		if (getDetrazioni() == null)
			this.imposte =  0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("IMP") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.imposte =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.imposte =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.imposte =  0.0;
		}
	}
    
    public void setInvalidi() {

		if (getParticolarita() == null)
			this.invalidi =  0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
		try {
			for (int i = 1; i <= getParticolarita().getRows(); i++) {
				// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
				double value = java.lang.Math.min( DMI, java.lang.Math.max( QBI * getParticolarita().getDouble(i, 1), getParticolarita().getDouble(i, 2) ) );
				double pesoComponente = new Double((String) getParticolarita().getElement(i, 8)).doubleValue();
				value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;	
					
				tot = tot + value;
			}
			this.invalidi =  tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.invalidi =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.invalidi =  0.0;
		}
	}
    
    public void setLavoratori() {

    	if(getDati()==null) {
    		this.lavoratori = 0.0;
    	}
		try {
			this.lavoratori =  java.lang.Math.abs(new Double((String) getDati().getElement(1, 2)).doubleValue()) * DGL;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.lavoratori =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.lavoratori =  0.0;
		}
	}
    
    private Hashtable hashtableMaxImportoSpeseForDich = new Hashtable();

	private void addImportoSpese(String idDich, double pesoComponente) {
		
		Double oldImportoSpese = (Double)hashtableMaxImportoSpeseForDich.get(idDich);
		if(oldImportoSpese==null)
		{
			double round = 1.0;
			double aggiusta = 0.01;
			double importoSpese = (Sys.round(DMS - aggiusta, round) * pesoComponente / 100.0);
			hashtableMaxImportoSpeseForDich.put(idDich, new Double(importoSpese));
		}
	}
    
    private double getImportoSpese(double importoCalcolato)
	{
		double maxImportoSpese = 0;
		
		Enumeration e = hashtableMaxImportoSpeseForDich.keys();
		while (e.hasMoreElements()) 
		{
			Object key = e.nextElement();
			Double importoSpese = (Double)hashtableMaxImportoSpeseForDich.get(key);
			maxImportoSpese+=importoSpese.doubleValue();
		}
		
		double ret = importoCalcolato;
		if(importoCalcolato>maxImportoSpese)
		{
			ret = maxImportoSpese;
		}
		return ret;
	}
    
    public void setMediche() {
    	boolean usaDeduzioneMassima = true;
	
		if (getDetrazioni() == null)
			this.mediche =  0.0;
		
		boolean usaMetodoCorretto = true;
		
		
		if(usaMetodoCorretto)
		{
			for (int i = 1; i <= getComponenti().getRows(); i++) {
				double pesoComponente = new Double((String) getComponenti().getElement(i, 2)).doubleValue();
				String idDich = ((String)getComponenti().getElement(i,1));
				addImportoSpese(idDich,pesoComponente);
			}
		}
		
		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		
		
		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if(usaDeduzioneMassima) {
					if ( ((String) getDetrazioni().getElement(i, 1)).equals("SPM") 
							|| ((String) getDetrazioni().getElement(i, 1)).equals("SPF")
							|| ((String) getDetrazioni().getElement(i, 1)).equals("SPI") ) 
						{
							double importo = new Double((String) getDetrazioni().getElement(i, 3)).doubleValue();
							
							double pesoComponente = new Double((String) getDetrazioni().getElement(i, 2)).doubleValue();
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
							String idDich = ((String)getDetrazioni().getElement(i,4));
							
						}
				} else {
					if ( ((String) getDetrazioni().getElement(i, 1)).equals("SPM") ) 
						{
							double importo = new Double((String) getDetrazioni().getElement(i, 3)).doubleValue();
							
							double pesoComponente = new Double((String) getDetrazioni().getElement(i, 2)).doubleValue();
							tot = tot + Sys.round(importo - aggiusta, round) * pesoComponente / 100.0;
							String idDich = ((String)getDetrazioni().getElement(i,4));
							
						}
				}
			}
			
			if(usaDeduzioneMassima)
			{
				tot = getImportoSpese(tot);
			}
			
			this.mediche =  tot;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.mediche =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.mediche =  0.0;
		}
	}
    
    public void setMonogenitore() {
		
		try {
			if (getDati() == null)
				this.monogenitore =  0.0;
			
			this.monogenitore =  java.lang.Math.abs(new Double((String) getDati().getElement(1, 1)).doubleValue()) * DMG;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.monogenitore =  0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.monogenitore =  0.0;
		}
	}
    
    public void setMutuo() {

		if (getDetrazioni() == null)
			this.mutuo =  0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("IMR") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.mutuo = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.mutuo = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.mutuo = 0.0;
		}
	}
    
    public void setN_componenti() {

		try {
			
			double tot=0;
			double round = 1.0;
			double aggiusta = 0.01;
			
			for (int i = 1; i <= getComponenti().getRows(); i++) {
					// min ( max detrazione invalidi , max (quota base * coeff  , spese) )
					double value = 1.0;
					double pesoComponente = new Double((String) getComponenti().getElement(i, 2)).doubleValue();
					value =	Sys.round( value - aggiusta, round) * pesoComponente / 100.0;
					tot = tot + value;
			}
			
			//this.n_componenti =  tot;//Sys.round(tot-aggiusta, round);//tot;
			
			//this.n_componenti = Sys.round(tot-0, 1);
			
			//System.out.println(" *** n_componenti "+tot);
			this.n_componenti = tot;
			//System.out.println(" *** *** *** n_componenti "+n_componenti);
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.n_componenti = 0.0;
		}
	}
    
    public void setP_complementare() {

		if (getDetrazioni() == null)
			this.p_complementare = 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("CPC") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.p_complementare = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.p_complementare = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.p_complementare = 0.0;
		}
	}
    
    public void setPrevidenza() {

		if (getDetrazioni() == null)
			this.previdenza = 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= getDetrazioni().getRows(); i++) {
				if ( ((String) getDetrazioni().getElement(i, 1)).equals("CPA") ) {
					tot = tot + Sys.round(new Double((String) getDetrazioni().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getDetrazioni().getElement(i, 2)).doubleValue() / 100.0;
				}
			}
			this.previdenza = tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.previdenza = 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.previdenza = 0.0;
		}
	}
    
    public void setPI() {
    	try{
    		
			if (getEdilDati() == null)
				this.pi = 0.0;
	
			this.pi = getEdilDati().getDouble(1,1);
			/*
			// se residenza = false ritorna gli immobili oltre la residenza
			boolean usaDetrazioneMaxValoreNudaProprieta = true;
			boolean residenza = false;
			this.pi = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
			
			this.pi = Sys.round(pi-0.5, 1);
			*/
			
	    } catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.pi = 0.0;
		}
		
	}
    
    public void setPM() {
    	
		if (getEdilDati() == null)
			this.pm = 0.0;

    	double val = 0.0;
    	
    	double pm = getEdilDati().getDouble(1,2);
    	double prefinanziamento = getEdilDati().getDouble(1,3);
    	
    	//pm al netto del prefinanziamento
    	val = pm - prefinanziamento;
		
		this.pm = val;
		
		/*
		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		double fpim = 0.0;

		try 
		{
			String familiare="";
			for (int i = 1; i <= getPatr_mob().getRows(); i++) 
			{
				if (familiare.equals(""))
				{
					familiare=(String)getPatr_mob().getElement(i,5);
				}
				else 
				{
					String nextFam=(String)getPatr_mob().getElement(i,5);
					if (!nextFam.equals(familiare))
					{
						//togliere franchigia(5000) e sommare se positivo
						fpim = fpim - FIM;
						if (fpim>0)
						{
							tot = tot + fpim;
						}
						familiare = nextFam;
						fpim = 0.0;
					}//if diverso familiare
				}//else familiare vuoto
				// se depositi bancari media della somma delle consistenze * peso patrimonio
				if ( ((String) getPatr_mob().getElement(i, 1)).equals("BN3") )
				{
					double consistenza_31_03 = new Double((String) getPatr_mob().getElement(i, 6)).doubleValue();
					double consistenza_30_06 = new Double((String) getPatr_mob().getElement(i, 7)).doubleValue();
					double consistenza_30_09 = new Double((String) getPatr_mob().getElement(i, 8)).doubleValue();
					double consistenza_31_12 = new Double((String) getPatr_mob().getElement(i, 3)).doubleValue();
					
					double numSomme = 4.0;
					double mediaConsistenza = (consistenza_31_03 + consistenza_30_06 + consistenza_30_09 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * new Double((String) getPatr_mob().getElement(i, 2)).doubleValue() / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				}
				else if ( ((String) getPatr_mob().getElement(i, 1)).equals("BN6") )
				{
					
					double consistenza_30_06 = new Double((String) getPatr_mob().getElement(i, 7)).doubleValue();
					double consistenza_31_12 = new Double((String) getPatr_mob().getElement(i, 3)).doubleValue();
					
					double numSomme = 2.0;
					double mediaConsistenza = (consistenza_30_06 + consistenza_31_12)/numSomme;
					// consistenza
					fpim = fpim + (Sys.round(mediaConsistenza - aggiusta, round) * new Double((String) getPatr_mob().getElement(i, 2)).doubleValue() / 100.0);
					// consistenza altri tipi di patrimonio valevoli per l'ICEF * peso patrimonio
				} 
				else if ( ((String) getPatr_mob().getElement(i, 1)).equals("TIT")
						|| ((String) getPatr_mob().getElement(i, 1)).equals("PNQQ")
						|| ((String) getPatr_mob().getElement(i, 1)).equals("ALT") ) 
				{
					fpim = fpim + Sys.round(new Double((String) getPatr_mob().getElement(i, 3)).doubleValue() - aggiusta, round) * new Double((String) getPatr_mob().getElement(i, 2)).doubleValue() / 100.0;
				}
			}//for
			//ultimo familiare se presente: togliere franchigia(5000) e sommare se positivo
			fpim = fpim - FIM;
			if (fpim>0)
			{
				tot = tot + fpim;
			}
			this.pm = tot;
		} 
		catch (NullPointerException n) 
		{
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			this.pm = 0.0;
		} 
		catch (NumberFormatException nfe) 
		{
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			this.pm = 0.0;
		}
		*/
	}
    
    public void setRes(boolean usaMetodoCorretto) {

    	/*
    	records = getPatr_imm();
		if (records == null)
			this.res = 0.0;
			
		// se residenza = true ritorna la residenza
		boolean usaDetrazioneMaxValoreNudaProprieta = true;
		boolean residenza = true;
		this.res = getValoreImmobili(residenza,usaDetrazioneMaxValoreNudaProprieta);
		
		if(!usaMetodoCorretto) {
			double test = getTotalOfMaxUsofruttoForDich();
			this.res = res + test;
		}	
		
		this.res = Math.max( 0.0 , res);
    	 */
    	this.res = 0.0;
	}
    
    

	public double getAssegni() {
		return assegni;
	}


	public double getC1_aut() {
		return c1_aut;
	}



	public double getC1_dip() {
		return c1_dip;
	}



	public double getC1_pens() {
		return c1_pens;
	}


	public double getC2_agr() {
		return c2_agr;
	}



	public double getC3_imp() {
		return c3_imp;
	}

	public double getC4_part() {
		return c4_part;
	}



	public double getC5_altri() {
		return c5_altri;
	}




	public double getCanone() {
		return canone;
	}



	public double getFigli_fam_numerose() {
		return figli_fam_numerose;
	}



	public double getImposte() {
		return imposte;
	}



	public double getInvalidi() {
		return invalidi;
	}




	public double getLavoratori() {
		return lavoratori;
	}



	public double getMediche() {
		return mediche;
	}




	public double getMonogenitore() {
		return monogenitore;
	}


	public double getMutuo() {
		return mutuo;
	}




	public double getN_componenti() {
		return n_componenti;
	}





	public double getP_complementare() {
		return p_complementare;
	}



	public double getPrevidenza() {
		return previdenza;
	}



	public double getRes() {
		return res;
	}





	public double getPi() {
		return pi;
	}



	public double getPm() {
		return pm;
	}
    

}